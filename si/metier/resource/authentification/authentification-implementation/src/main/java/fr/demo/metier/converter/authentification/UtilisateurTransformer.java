package fr.demo.metier.converter.authentification;

import fr.demo.metier.converter.Transformer;
import fr.demo.metier.domain.authentification.Utilisateur;
import fr.demo.metier.dto.authentification.AuthentificationModificationDto;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

@Component("authentification.utilisateurTransformer")
public class UtilisateurTransformer implements Transformer<Utilisateur, AuthentificationModificationDto> {

  @Override
  public AuthentificationModificationDto transform(Utilisateur utilisateur) {

    if (utilisateur == null) {
      return null;
    }
    Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    return mapper.map(utilisateur, AuthentificationModificationDto.class);
  }

}
