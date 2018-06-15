package fr.demo.metier.rest;

import com.wordnik.swagger.annotations.*;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.dto.ReclamationDto;
import fr.demo.metier.exception.ForbiddenException;
import fr.demo.metier.rest.core.annotation.AuditablePropagationCreation;
import fr.demo.metier.rest.core.annotation.AuditablePropagationModification;
import fr.demo.metier.service.ReclamationService;
import fr.demo.metier.validator.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;

@Path("/reclamation")
@Component("reclamationResource")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "/rest/reclamation", description = "Opérations sur les reclamations")
public class ReclamationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReclamationResource.class);

    @Resource(name = "reclamationService")
    private ReclamationService service;

    @Autowired
    private ValidatorUtil validator;

    @GET
    @Path("/{idReclamation}")
    @ApiOperation(value = "Retourne une reclamation à partir de son id", response = ReclamationDto.class)
    public Response get(@PathParam("idReclamation") Long idReclamation) {
        // check for security
        ReclamationDto reclamation = service.getReclamationById(idReclamation);
        return reclamationToResponse(reclamation);
    }

    private Response reclamationToResponse(ReclamationDto reclamation) {
        if (reclamation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reclamation).build();
    }

    @POST
    @AuditablePropagationCreation
    @ApiOperation(value = "Crée une nouvelle reclamation", response = ReclamationDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Objet correctement créé"),
            @ApiResponse(code = 400, message = "Erreur de validation ou json non valide")})
    public Response create(ReclamationDto reclamation) throws ValidationException {
        service.create(reclamation);
        return Response.status(Response.Status.CREATED).entity(reclamation).build();
    }

    @PUT
    @Path("/{idReclamation}")
    @AuditablePropagationModification
    @ApiOperation(value = "Modifie une reclamation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Erreur de validation ou json non valide"),
            @ApiResponse(code = 404, message = "Objet non trouvé"),
            @ApiResponse(code = 409, message = "Conflit de version ou ID invalide")})
    public Response update(@PathParam("idReclamation") Long idReclamation, ReclamationDto reclamation) throws ValidationException {
        if (reclamation != null && reclamation.getId().equals(idReclamation)) {
            service.update(reclamation);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

   
    /*  SECURISATION */

}
