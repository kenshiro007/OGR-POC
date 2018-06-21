package fr.demo.metier.rest.authentification;

import com.wordnik.swagger.annotations.*;

import fr.demo.metier.dto.authentification.*;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.service.authentification.AuthentificationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Map;

@Path("/authentification")
@Api(value = "/api/authentification", description = "Opérations d'authentification (Cadres, Interlocuteurs et ADEP)")
@Component("authentification")
public class AuthentificationRest {

  @Resource(name = "authentificationService")
  private AuthentificationService service;


  @POST
  @Path("/comptePersonnel")
  @ApiOperation(value = "Retourne un utilisateur personnel si authentification réussie", notes = "authentification par login", response = UtilisateurPersonnelDto.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "requête OK"),
    @ApiResponse(code = 400, message = "Syntaxe erronée"),
    @ApiResponse(code = 404, message = "Authentification erronée")})
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public Response getAuthentificationComptePersonnel(
    @ApiParam(value = "informations de login", required = true) AuthentificationDto authentification)
    throws ValidationException {

    UtilisateurPersonnelDto personnel = service.getAuthentificationComptePersonnel(authentification);
    return authentificationComptePersonnelToResponse(personnel);
  }


  @POST
  @Path("/validation/{scenario}")
  @ApiOperation(value = "Valide une Authentification à modifier")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Json non valide")})
  public Map<String, String> validate(
    @ApiParam(value = "Scénario selon lequel l'entité doit être validée", required = true) @PathParam("scenario") String scenario,
    AuthentificationModificationDto authentificationModification) {

    return service.validateUtilisateurModification(authentificationModification, scenario, null);
  }

  @POST
  @Path("/validation/securise/{scenario}/{numeroDossier}")
  @ApiOperation(value = "Valide une Authentification à modifier")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Json non valide")})
  public Map<String, String> validateSecurise(
    @ApiParam(value = "Scénario selon lequel l'entité doit être validée", required = true) @PathParam("scenario") String scenario,
    AuthentificationModificationSecuriseDto authentificationModification,
    @ApiParam(value = "numero compte de l'utilisateur", required = true) @PathParam("numeroDossier") String numeroDossier) {

    return service.validateUtilisateurModification(authentificationModification, scenario, numeroDossier);
  }

  @GET
  @Path("/decrypt")
  @Produces({MediaType.APPLICATION_JSON})
  @ApiOperation(value = "Retourne l'id de l'évenementTampon à partir d'un token", response = String.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "requête OK"),
          @ApiResponse(code = 400, message = "Syntaxe erronée ou token invalide")})
  public Response getParamsFromToken(@ApiParam(value = "token encrypté ", required = true) @QueryParam("token") String token) {
    if (token == null || StringUtils.isEmpty(token)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    return Response.ok(service.getParamsFromToken(token)).build();
  }
  
  private Response authentificationComptePersonnelToResponse(UtilisateurPersonnelDto personnel) {
	    if (personnel == null) {
	      return Response.status(Response.Status.NOT_FOUND).build();
	    }
	    return Response.ok().entity(personnel).build();
	  }

}
