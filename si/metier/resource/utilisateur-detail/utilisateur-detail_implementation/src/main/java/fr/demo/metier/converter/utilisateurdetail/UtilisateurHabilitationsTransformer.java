package fr.demo.metier.converter.utilisateurdetail;

import fr.demo.metier.converter.Transformer;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurHabilitations;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurHabilitationsDto;
import org.springframework.stereotype.Component;

@Component("utilisateurdetail.utilisateurHabilitationsTransformer")
public class UtilisateurHabilitationsTransformer
    implements
      Transformer<UtilisateurHabilitations, UtilisateurHabilitationsDto> {

  @Override
  public UtilisateurHabilitationsDto transform(UtilisateurHabilitations utilisateurHabilitations) {

    if (utilisateurHabilitations == null) {
      return null;
    }
    UtilisateurHabilitationsDto result = new UtilisateurHabilitationsDto();
    result.setNumeroDossier(utilisateurHabilitations.getId());
    result.setIdTechnique(utilisateurHabilitations.getIdTechnique());
    result.setNom(utilisateurHabilitations.getNom());
    result.setTypeUserApec(utilisateurHabilitations.getTypeUser());
    result.setHabilitations(utilisateurHabilitations.getHabilitations());
    return result;
  }

}
