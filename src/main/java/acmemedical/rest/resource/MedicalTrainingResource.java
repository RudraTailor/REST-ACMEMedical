/*******************************************************************************************************
 * File:  MedicalTrainingResource.java
 * Course Materials CST 8277
 *
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and polished by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.rest.resource;

import static acmemedical.utility.MyConstants.*;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.MedicalTraining;

@Path(MEDICAL_TRAINING_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalTrainingResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getMedicalTrainings() {
        LOG.debug("Retrieving all medical trainings...");
        List<MedicalTraining> trainings = service.getAll(MedicalTraining.class, "MedicalTraining.findAll");
        return Response.ok(trainings).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMedicalTrainingById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Retrieving medical training with id = {}", id);
        MedicalTraining training = service.getById(MedicalTraining.class, "MedicalTraining.findById", id);
        if (training == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(training).build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMedicalTraining(MedicalTraining newMedicalTraining) {
        MedicalTraining tempTraining = service.persistMedicalTraining(newMedicalTraining);
        return Response.ok(tempTraining).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateMedicalTraining(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, MedicalTraining updatingTraining) {
        MedicalTraining updatedTraining = service.updateMedicalTraining(id, updatingTraining);
        return Response.ok(updatedTraining).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteMedicalTraining(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        service.deleteMedicalTrainingById(id);
        return Response.ok().build();
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
