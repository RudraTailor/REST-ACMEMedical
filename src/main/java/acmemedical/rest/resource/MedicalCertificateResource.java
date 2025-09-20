/*******************************************************************************************************
 * File:  MedicalCertificateResource.java
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
import acmemedical.entity.MedicalCertificate;

@Path(MEDICAL_CERTIFICATE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicalCertificateResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMEMedicalService service;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getMedicalCertificates() {
        LOG.debug("Retrieving all medical certificates...");
        List<MedicalCertificate> certificates = service.getAll(MedicalCertificate.class, "MedicalCertificate.findAll");
        return Response.ok(certificates).build();
    }

    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMedicalCertificateById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        LOG.debug("Retrieving medical certificate with id = {}", id);
        MedicalCertificate cert = service.getById(MedicalCertificate.class, "MedicalCertificate.findById", id);
        if (cert == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(cert).build();
    }

    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMedicalCertificate(MedicalCertificate newCertificate) {
        MedicalCertificate tempCertificate = service.persistMedicalCertificate(newCertificate);
        return Response.ok(tempCertificate).build();
    }

    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateMedicalCertificate(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, MedicalCertificate updatingCert) {
        MedicalCertificate updatedCert = service.updateMedicalCertificateById(id, updatingCert);
        return Response.ok(updatedCert).build();
    }

    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deleteMedicalCertificate(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        service.deleteMedicalCertificateById(id);
        return Response.ok().build();
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
