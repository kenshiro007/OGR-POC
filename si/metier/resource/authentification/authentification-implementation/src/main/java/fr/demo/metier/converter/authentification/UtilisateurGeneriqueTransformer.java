package fr.demo.metier.converter.authentification;

import fr.demo.metier.converter.Transformer;
import fr.demo.metier.domain.authentification.Utilisateur;
import fr.demo.metier.dto.authentification.UtilisateurGeneriqueDto;
import org.springframework.stereotype.Component;

@Component("authentification.utilisateurGeneriqueTransformer")
public class UtilisateurGeneriqueTransformer implements Transformer<Utilisateur, UtilisateurGeneriqueDto> {

  @Override
  public UtilisateurGeneriqueDto transform(Utilisateur utilisateur) {

    if (utilisateur == null) {
      return null;
    }
    UtilisateurGeneriqueDto result = new UtilisateurGeneriqueDto();
    result.setNumeroCompte(utilisateur.getId());
    result.setMotDePasse(utilisateur.getMotDePasse());
    return result;
  }

}
