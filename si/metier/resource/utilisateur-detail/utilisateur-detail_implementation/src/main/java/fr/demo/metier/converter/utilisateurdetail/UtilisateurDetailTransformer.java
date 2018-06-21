package fr.demo.metier.converter.utilisateurdetail;

import fr.demo.metier.converter.Transformer;
import fr.demo.metier.domain.utilisateurdetail.UtilisateurDetail;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurDetailDto;
import org.springframework.stereotype.Component;

@Component("utilisateurdetail.utilisateurDetailTransformer")
public class UtilisateurDetailTransformer implements Transformer<UtilisateurDetail, UtilisateurDetailDto> {

  @Override
  public UtilisateurDetailDto transform(UtilisateurDetail utilisateurDetail) {

    if (utilisateurDetail == null) {
      return null;
    }
    UtilisateurDetailDto result = new UtilisateurDetailDto();
    result.setNumeroDossier(utilisateurDetail.getId());
    result.setNom(utilisateurDetail.getNom());
    result.setPrenom(utilisateurDetail.getPrenom());
    result.setTypeUserApec(utilisateurDetail.getTypeUser());
    return result;
  }

}
