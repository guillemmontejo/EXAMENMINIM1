
package dsa.services;

import dsa.Covid19Manager;
import dsa.Covid19ManagerImpl;
import dsa.models.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

//Models or Element Entity
//Swagger Imports
@Api(value = "/covid19", description = "Endpoint to User Service")
@Path("/covid19Manager")
public class Service {
    static final Logger logger = Logger.getLogger(Service.class);
    private Covid19Manager manager;

    //Estructura de datos
    Persona user;
    List<Muestra> listaMuestras;

    public Service() {
        //Configuring Log4j, location of the log4j.properties file and must always be inside the src folder
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        this.manager = Covid19ManagerImpl.getInstance();
        if (this.manager.getNumUsers() == 0) {
            //Adding Users to the system
            Persona userX = new Persona("001", "Guille", "Montejo", 22, "A");
            Persona userY = new Persona("002", "Prueba", "JJ", 221, "C");
            Persona userZ = new Persona("003", "Prueba2", "AA", 222, "D");

            this.manager.addPersona(userX);
            this.manager.addPersona(userY);
            this.manager.addPersona(userZ);

            //Adding muestras to the users
            Date fechaMuestra = new Date(1997, 9, 23, 16, 55, 20);
            Muestra muestrax = new Muestra("x1", "Clinico1", "001", fechaMuestra, "Lab1");
            Muestra muestray = new Muestra("x2", "Clinico2", "003", fechaMuestra, "Lab1");
            this.manager.addMuestraToUser("001", muestrax);
            this.manager.addMuestraToUser("003", muestray);
        }
    }

    //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
    //Lista de Usuarios
    @GET
    @ApiOperation(value = "Get all Users", notes = "Retrieves the list of users")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Persona.class, responseContainer = "List"),
    })
    @Path("/listUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<Persona> user = this.manager.getUsersList();
        GenericEntity<List<Persona>> entity = new GenericEntity<List<Persona>>(user) {
        };
        return Response.status(201).entity(entity).build();
    }

    //Añadir un nuevo usuario al sistema
    @POST
    @ApiOperation(value = "Create a new User", notes = "Adds a new user given name and surname")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Persona.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addUser/{id}/{healthLevel}/{age}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser(@PathParam("id") String id, @PathParam("healthLevel") String healthlevel , @PathParam("age") String age) {
        if (id.isEmpty() || healthlevel.isEmpty()) return Response.status(500).entity(new Persona()).build();
        Persona persona = new Persona(id, healthlevel,Integer.parseInt(age));
        this.manager.addPersona(persona);
        return Response.status(201).entity(manager.getUser(id)).build();
    }


}