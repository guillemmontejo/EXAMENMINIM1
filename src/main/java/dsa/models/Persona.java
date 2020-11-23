package dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Persona {
    String id;
    String nombre;
    String apellido;
    int edad;
    String nivelSalud;
    List<Muestra> listadoMuestras;

    //Constructor

    public Persona(String id, String nombre, String apellido, int edad, String nivelSalud) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nivelSalud = nivelSalud;
        this.listadoMuestras = new LinkedList<>();
    }

    public Persona(){

    }

    public Persona(String id, String nombre, String apellido, int edad, String nivelSalud, List<Muestra> listadoMuestras) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nivelSalud = nivelSalud;
        this.listadoMuestras = listadoMuestras;
    }

    public Persona(String id, String nivelSalud, int edad) {
        this.id = id;
        this.nivelSalud = nivelSalud;
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNivelSalud() {
        return nivelSalud;
    }

    public void setNivelSalud(String nivelSalud) {
        this.nivelSalud = nivelSalud;
    }

    public List<Muestra> getListadoMuestras() {
        return listadoMuestras;
    }

    public void setListadoMuestras(List<Muestra> listadoMuestras) {
        this.listadoMuestras = listadoMuestras;
    }

    public String toString(){
        return "ID: " + this.getId() + " |Name: " + this.getNombre() + " |Surname: " + this.getApellido() + " |Age: " + this.getEdad() + " |Health level:" + this.getNivelSalud();
    }


    //EXTRA
    public int addMuestra(Muestra m){
        try{
            this.listadoMuestras.add(m);
        }
        catch (ExceptionInInitializerError e)
        {
            return 400;//400 Bad Request
        }
        catch (IndexOutOfBoundsException e){
            return 507;//Insufficient storage
        }
        return 201;//201 Created

    }
}
