/*******************************************************************************************************
 * File:  PhysicianResource.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
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
import jakarta.ws.rs.ForbiddenException;
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
import org.glassfish.soteria.WrappingCallerPrincipal;

import acmemedical.ejb.ACMEMedicalService;
import acmemedical.entity.Medicine;
import acmemedical.entity.SecurityUser;
import acmemedical.entity.Physician;

@Path(PHYSICIAN_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhysicianResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getPhysicians() {
        LOG.debug("Retrieving all physicians ...");
        List<Physician> physicians = service.getAllPhysicians();
        return Response.ok(physicians).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getPhysicianById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Trying to retrieve physician with ID: " + id);
        Physician physician = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            physician = service.getPhysicianById(id);
            return Response.status(physician == null ? Status.NOT_FOUND : Status.OK).entity(physician).build();
        } else if (sc.isCallerInRole(USER_ROLE)) {
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser) wCallerPrincipal.getWrapped();
            physician = sUser.getPhysician();
            if (physician != null && physician.getId() == id) {
                return Response.ok(physician).build();
            } else {
                throw new ForbiddenException("User trying to access resource it does not own (wrong userid)");
            }
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addPhysician(Physician newPhysician) {
        Physician newPhysicianWithIdTimestamps = service.persistPhysician(newPhysician);
        service.buildUserForNewPhysician(newPhysicianWithIdTimestamps);
        return Response.ok(newPhysicianWithIdTimestamps).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(PHYSICIAN_PATIENT_MEDICINE_RESOURCE_PATH)
    public Response updateMedicineForPhysicianPatient(
            @PathParam("physicianId") int physicianId,
            @PathParam("patientId") int patientId,
            Medicine newMedicine) {
        Medicine medicine = service.setMedicineForPhysicianPatient(physicianId, patientId, newMedicine);
        return Response.ok(medicine).build();
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
