package fr.demo.metier.rest.utilisateurdetail;

import com.wordnik.swagger.annotations.*;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurDetailDto;
import fr.demo.metier.service.utilisateurdetail.UtilisateurDetailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/utilisateurDetail")
@Component("utilisateurDetailResource")
@Api(value = "/rest/utilisateurDetail", description = "Accès aux informations simples des utilisateurs")
public class UtilisateurDetailRest {

  @Resource(name = "utilisateurDetailService")
  private UtilisateurDetailService utilisateurDetailService;

  @POST
  @Path("/liste")
  @ApiOperation(value = "retourne une liste d'UtilisateurDetailDto correpsondant à la liste de numeros de dossiers en entrée", response = UtilisateurDetailDto.class, responseContainer = "List")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "requête OK"),
      @ApiResponse(code = 400, message = "Syntaxe erronée") })
  public Response getDetailUtilisateurs(
      @ApiParam(value = "liste des numéros de dossier", required = true) List<String> listeNumerosDossier) {
    if (listeNumerosDossier == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    List<UtilisateurDetailDto> result = utilisateurDetailService.getDetailUtilisateurs(listeNumerosDossier);
    return Response.ok(result).build();
  }

  @GET
  @Path("/{numeroDossier}")
  @ApiOperation(value = "retourne un UtilisateurDetailDto selon le numéro de dossier en entrée", response = UtilisateurDetailDto.class)
  @Produces({ MediaType.APPLICATION_JSON })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "requête OK"),
      @ApiResponse(code = 400, message = "Syntaxe erronée"),
      @ApiResponse(code = 404, message = "Utilisateur non trouvé") })
  public Response getDetailUtilisateur(
      @ApiParam(value = "Numéro de dossier", required = true) @PathParam("numeroDossier") String numeroDossier) {
    if (numeroDossier == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    List<String> listeNumeroDossierUnique = new ArrayList<>(Arrays.asList(new String[] { numeroDossier }));
    List<UtilisateurDetailDto> result = utilisateurDetailService.getDetailUtilisateurs(listeNumeroDossierUnique);
    if (result.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(result.get(0)).build();
  }

}