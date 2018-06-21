package fr.demo.metier.converter.security;

import org.springframework.stereotype.Component;

import fr.demo.metier.converter.Transformer;
import fr.demo.metier.dto.utilisateurdetail.UtilisateurHabilitationsDto;
import fr.demo.metier.service.security.domain.User;

@Component("securisation.utilisateurHabilitationsTransformer")
public class UtilisateurHabilitationsDtoTransformer implements Transformer<UtilisateurHabilitationsDto, User> {

  @Override
  public User transform(UtilisateurHabilitationsDto utilisateurHabilitationsDto) {

    if (utilisateurHabilitationsDto == null) {
      return null;
    }
    User result =
        new User(
            utilisateurHabilitationsDto.getNumeroDossier(),
            utilisateurHabilitationsDto.getIdTechnique(),
            utilisateurHabilitationsDto.getNom(),
            utilisateurHabilitationsDto.getTypeUser());
    result.setHabilitations(utilisateurHabilitationsDto.getHabilitations());
    return result;
  }

}
