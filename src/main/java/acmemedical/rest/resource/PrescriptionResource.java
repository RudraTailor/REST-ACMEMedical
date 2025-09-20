/*******************************************************************************************************
 * File:  PrescriptionResource.java
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
import acmemedical.entity.Prescription;

@Path(PRESCRIPTION_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getPrescriptions() {
        LOG.debug("Retrieving all prescriptions...");
        List<Prescription> prescriptions = service.getAll(Prescription.class, "Prescription.findAll");
        return Response.ok(prescriptions).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPrescriptionById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Retrieving prescription with id = {}", id);
        Prescription prescription = service.getById(Prescription.class, "Prescription.findById", id);
        if (prescription == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(prescription).build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPrescription(Prescription newPrescription) {
        Prescription tempPrescription = service.persistPrescription(newPrescription);
        return Response.ok(tempPrescription).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updatePrescription(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Prescription updatingPrescription) {
        Prescription updatedPrescription = service.updatePrescriptionById(id, updatingPrescription);
        return Response.ok(updatedPrescription).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deletePrescription(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        service.deletePrescriptionById(id);
        return Response.ok().build();
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
