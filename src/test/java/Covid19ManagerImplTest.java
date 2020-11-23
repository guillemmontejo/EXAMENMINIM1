package dsa;

import dsa.models.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class Covid19ManagerImplTest {
    // THE QUICK REMINDER: Remember to name the test class public smh
    //Log4j Logger initialization
    private static Logger logger = Logger.getLogger(Covid19ManagerImplTest.class);
    //GameManager
    public Covid19Manager manager = null;
    //Estructura de datos
    Persona user;
    List<Muestra> listaMuestras;
    Laboratorio lab;


    HashMap<String, Persona> diccionarioPersonas;
    ArrayList<Laboratorio> listaLaboratorios;
    List<Muestra> muestrasPendientes;



    //Metodo SetUp
    @Before
    public void setUp() {
        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");

        //Instancing Covid19Manager Implementation
        manager = Covid19ManagerImpl.getInstance();

        //Initialzing Test User
        lab = new Laboratorio("Lab1","Laboratorio Moderna");
        user = new Persona("001","Guille", "Montejo", 22, "A");
        Date fechaMuestra = new Date(1997,9,23,16,55,20);
        Muestra muestra = new Muestra("x1", "Clinico1", "001", fechaMuestra, "Lab1");
        user.addMuestra(muestra);
        manager.addMuestraToUser("001",muestra);

    }

    //Tests
    //Método Test para crear un nuevo usuario en el sistema y verificar
    @Test
    public void addUserTest(){
        //Initial Test, initial users in game Zero!
        Assert.assertEquals(0, this.manager.getNumUsers());
        //Test adding user to Covid Manager
        user = new Persona("002","Guillexx", "Montejoxx", 22, "D");
        manager.addPersona(user);
        Assert.assertEquals(1, this.manager.getNumUsers());
    }

    //Tests
    //Metodo Test para procesa una muestra
    @Test
    public void procesarMuestraTest(){
        manager.addPersona(user);
        manager.addLaboratorio(lab);
        listaMuestras = manager.getMuestrasUsuario(user.getId());
        manager.sendMuestra(user.getId(),listaMuestras.get(0).getIdMuestra(),lab.getIdLaboratio());
        //Al usuario ya le hemos añadido una muestra
        Assert.assertEquals(1, listaMuestras.size());
        lab.addMuestra(manager.getMuestrasUsuario(user.getId()).get(0));
        manager.processMuestra(lab.getIdLaboratio());

    }
    @After
    public void tearDown() {
        manager.liberarRecursos();
        logger.debug("Cleaning reserves");
    }

}
