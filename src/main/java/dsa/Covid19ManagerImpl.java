package dsa;

import dsa.models.Laboratorio;
import dsa.models.Muestra;
import dsa.models.Persona;
import org.apache.log4j.Logger;

import java.util.*;

public class Covid19ManagerImpl implements  Covid19Manager{
    private static Covid19Manager instance;
    private static Logger log = Logger.getLogger(Covid19Manager.class);

    private HashMap<String, Persona> diccionarioPersonas;
    private ArrayList<Laboratorio> listaLaboratorios;
    private Queue<Muestra> muestrasPendientes;

    //Singleton fachada
    public static Covid19Manager getInstance(){
        if(instance == null) {
            instance = new Covid19ManagerImpl();
        }
        return instance;
    }

    //Private constructor
    private Covid19ManagerImpl(){
        //Singleton Private Constructor
        this.diccionarioPersonas = new HashMap<>();
        this.listaLaboratorios = new ArrayList<>();
        this.muestrasPendientes = new LinkedList<>();
    }


    public int addPersona(Persona user) {
        log.info("Creating new User: " + user);
        try{
            diccionarioPersonas.put(user.getId(),user);
            log.info("User Added: " + user );
            return 201; //OK CREATED
        }
        catch (IndexOutOfBoundsException e){
            log.error("UserMap Full Error");
            return 507; //INSUFFICIENT STORAGE
        }
        catch (IllegalArgumentException e){
            log.error("Incorrect format exception");
            return 400; //BAD REQUEST
        }
    }


    public int addMuestraToUser(String idUser, Muestra muestra) {
        Persona user = diccionarioPersonas.get(idUser);
        if (user != null && muestra != null){
            log.info("You want to add the muestra with ID: " + muestra.getIdMuestra() + " to the user: " + user.getNombre());
            int code = user.addMuestra(muestra);
            if (code == 201) {
                log.info("Muestra added to user " + user.getNombre() + " : (ID)" + muestra.getIdMuestra());

                return 201; //OK CREATED
            } else if (code == 400) {
                log.error("Bad Format");
                return 400; //BAD REQUEST
            } else {
                log.error("No Muestras space for user: " + user.getNombre());
                return 507; //INSUFFICIENT STORAGE
            }
        }
        else{
            log.error("User: "+idUser +" &/or Muestra ID: " + muestra.getIdMuestra() +" NOT FOUND!");
            return 404; //USER NOT FOUND
        }
    }


    public int sendMuestra(String idUser, String idMuestra, String idLaboratorio) {
        Persona user = diccionarioPersonas.get(idUser);
        List<Muestra> listadoMuestrasUser = user.getListadoMuestras();
        Muestra muestraToSend = null;
        for(Muestra m : listadoMuestrasUser){
            if (m.getIdMuestra().compareTo(idMuestra) == 0){
                muestraToSend= m;
                log.info("302: Muestra found: " + m.getIdMuestra());
            }
            else{
                log.error("The user: " + user.getNombre() + "doesn't have the muestra: " + idMuestra);
            }
        }
        Laboratorio lab = getLaboratorio(idLaboratorio);

        if(muestraToSend != null && lab != null) {
            log.info("You want to send the muestra: " + muestraToSend.getIdMuestra() + " to the laboratory: " + lab.getNombreLaboratorio());
            int code = lab.addMuestra(muestraToSend);
            if (code == 201) {
                log.info("Muestra sended to lab " + lab.getNombreLaboratorio() + " : " + muestraToSend.getIdMuestra());
                muestrasPendientes.add(muestraToSend);
                return 201; //OK CREATED
            } else if (code == 400) {
                log.error("Bad Format");
                return 400; //BAD REQUEST
            } else {
                log.error("No Laboratory space for muestra: " + muestraToSend.getIdMuestra());
                return 507; //INSUFFICIENT STORAGE
            }
        }
        else{
            log.error("Laboratory: "+idLaboratorio +" &/or Muestra: " + idMuestra +" NOT FOUND!");
            return 404;
        }

    }

    public void processMuestra(String idLaboratorio) {
        Laboratorio lab = getLaboratorio(idLaboratorio);
        lab.processMuestra();
        muestrasPendientes.poll();
        log.info("Muestra processed");
    }

    @Override
    public List<Muestra> getMuestrasUsuario(String idPersona) {
        Persona user = diccionarioPersonas.get(idPersona);
        if(user != null){
            log.info("User: "+user.getNombre() + " has numMuestras: "+user.getListadoMuestras().size());
            return user.getListadoMuestras();
        }
        else{
            log.error("User Not found");
            List<Muestra> lista = new LinkedList<>();
            return lista;
        }
    }

    @Override
    public int addLaboratorio(Laboratorio laboratorio) {
        int result;
        try {
            this.listaLaboratorios.add(laboratorio);
            log.info("201: Laboratry Added: " + laboratorio.getNombreLaboratorio());
            result = 201;//OK CREATED
        } catch (IllegalArgumentException e) {
            log.error("400: Bad Lab parameters");
            result = 400;//BAD REQUEST
        } catch (IndexOutOfBoundsException e) {
            log.error("507: Insufficient Storage");
            result = 507;//INSUFFICIENT STORAGE
        }
        return result;
    }

    @Override
    public Laboratorio getLaboratorio(String idLaboratorio) {
        Laboratorio resultadolab = null;
        try{
            for(Laboratorio lab : this.listaLaboratorios){
                if (lab.getIdLaboratio().compareTo(idLaboratorio) == 0){
                    resultadolab = lab;
                    log.info("302: Laboratory found: " + lab.getNombreLaboratorio());
                }
            }
        }catch(ExceptionInInitializerError e){
            log.error("400: Laboratory list not initialized");
            return null; //400 ERROR List of Objects not initialized
        }
        return resultadolab;
    }


    @Override
    public void liberarRecursos() {
        diccionarioPersonas.clear();
        listaLaboratorios.clear();
        muestrasPendientes.clear();
    }

    @Override
    public int getNumUsers() {
        return diccionarioPersonas.size();
    }

    @Override
    public List<Persona> getUsersList() {
        List<Persona> lista = null;
        if(this.diccionarioPersonas.size() != 0){
            lista = new LinkedList<>(this.diccionarioPersonas.values());
            log.info("User List: "+lista.toString());
        }
        return lista; //Null: 404 Empty User HashMap
    }

    public String generateId(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random generated ID
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
    public Persona getUser(String id){
        return diccionarioPersonas.get(id);
    }
}
