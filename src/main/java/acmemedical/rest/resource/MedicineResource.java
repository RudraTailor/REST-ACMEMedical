/*******************************************************************************************************
 * File:  MedicineResource.java
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
import acmemedical.entity.Medicine;

@Path(MEDICINE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicineResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getMedicines() {
        LOG.debug("Retrieving all medicines...");
        List<Medicine> medicines = service.getAll(Medicine.class, "Medicine.findAll");
        return Response.ok(medicines).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMedicineById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Retrieving medicine with id = {}", id);
        Medicine medicine = service.getById(Medicine.class, "Medicine.findById", id);
        if (medicine == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(medicine).build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMedicine(Medicine newMedicine) {
        Medicine tempMedicine = service.persistMedicine(newMedicine);
        return Response.ok(tempMedicine).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateMedicine(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, Medicine updatingMedicine) {
        Medicine updatedMedicine = service.updateMedicineById(id, updatingMedicine);
        return Response.ok(updatedMedicine).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteMedicine(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        service.deleteMedicineById(id);
        return Response.ok().build();
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
