package dsa;

import dsa.models.*;

import java.util.List;


public interface Covid19Manager {
    //Crear persona del sistema
    int addPersona(Persona user);


    //Extraer muestra de una persona
    int addMuestraToUser(String idUser, Muestra muestra);

    //Enviarla a un laboratorio
    int sendMuestra(String idUser, String idMuestra, String idLaboratorio);

    //Procesar una muestra en un laboratorio
    void processMuestra(String idLaboratorio);

    //Listado de muestras de un usuario
    List<Muestra> getMuestrasUsuario(String idPersona);

    //Crear un laboratorio
    int addLaboratorio(Laboratorio laboratorio);

    //EXTRA
    Laboratorio getLaboratorio(String idLaboratorio);

    void liberarRecursos();
    int getNumUsers();
    List<Persona> getUsersList();
    String generateId();
    Persona getUser(String id);
}
