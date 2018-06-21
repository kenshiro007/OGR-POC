package fr.demo.metier.service.authentification.impl;

import fr.demo.metier.converter.authentification.UtilisateurGeneriqueTransformer;
import fr.demo.metier.dao.authentification.impl.UtilisateurDao;
import fr.demo.metier.dao.authentification.impl.UtilisateurPersonnelLdap;
import fr.demo.metier.dto.authentification.*;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.exception.BadRequestException;
import fr.demo.metier.exception.ForbiddenException;
import fr.demo.metier.exception.ResourceNotFoundException;
import fr.demo.metier.service.authentification.AuthentificationService;
import fr.demo.metier.domain.authentification.Utilisateur;
import fr.demo.metier.utils.BlowFishUtils;
import fr.demo.metier.utils.Sha512Utils;
import fr.demo.metier.validator.ValidatorUtil;
import fr.demo.metier.validator.authentification.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static fr.demo.metier.validator.ValidatorUtil.addValidationVariable;
import static fr.demo.metier.validator.authentification.AuthentificationValidatorScenario.*;

/**
 * AuthentificationService
 */
@Component("authentificationService")
@Transactional(propagation = Propagation.REQUIRED)
public class AuthentificationServiceImpl implements AuthentificationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationServiceImpl.class);

  @Resource(name = "authentification.utilisateurGeneriqueTransformer")
  UtilisateurGeneriqueTransformer utilisateurGeneriqueTransformer;

  @Autowired
  private ValidatorUtil validator;

  @Resource(name = "blowfishUtils")
  private BlowFishUtils blowFishUtils;

  @Resource(name = "sha512Utils")
    private Sha512Utils sha512Utils;

    @Resource(name = "authentification.utilisateurDao")
  private UtilisateurDao utilisateurDao;

  @Resource(name = "utilisateurPersonnelLdap")
  private UtilisateurPersonnelLdap utilisateurPersonnelLdap;

  @Transactional(readOnly = true)
  UtilisateurGeneriqueDto getUserGeneriqueByNumeroDossier(String numeroDossier, String motDePasse) {
    LOGGER.debug("getUserGeneriqueByNumeroDossier({}, [PROTECTED])", numeroDossier);
    Utilisateur user = utilisateurDao.getUtilisateur(numeroDossier);
    if (user != null
            && StringUtils.isNotEmpty(user.getMotDePasseSel())
            && StringUtils.isNotEmpty(user.getMotDePasseSha512())
            && sha512Utils.encrypt(motDePasse, user.getMotDePasseSel()).equals(user.getMotDePasseSha512())
        ) {
      return utilisateurGeneriqueTransformer.transform(user);
    }
    return null;
  }

  @Transactional
  protected void createUtilisateurMotDePasse(String numeroDossier, AuthentificationModificationDto authentificationInfo) {
    encryptPassword(authentificationInfo, new Date(), numeroDossier); //date d'aujourd'hui si utilisateur inexistant
    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setId(numeroDossier);
    utilisateur.setMotDePasse(authentificationInfo.getMotDePasse()); //TODO : clean after delivery
    utilisateur.setMotDePasseSha512(authentificationInfo.getMotDePasseSha512());
    utilisateur.setMotDePasseSel(authentificationInfo.getMotDePasseSel());
    utilisateurDao.create(utilisateur);
  }

  private void validate(AuthentificationDto authentification, Map<String, String> errors, Class<?> scenario) {
    errors.putAll(validator.validate(authentification, scenario));
    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, String> validateUtilisateurModification(AuthentificationModificationDto authentificationModification, String scenario, String numeroDossier) {
    if (numeroDossier != null) {
      addValidationVariable("numeroDossier", numeroDossier);
    }
    Map<String, String> errors = validator.validate(authentificationModification, convert(scenario));
    ValidatorUtil.clearValidationVariables();
    return errors;
  }


  @Override
  public UtilisateurPersonnelDto getAuthentificationComptePersonnel(AuthentificationDto authentification) throws ValidationException {
    Map<String, String> errors = validator.validate(authentification, AuthentificationBasique.class);
    if (!errors.containsKey("typeAuthentifiant")) {
      if (authentification.getTypeAuthentifiant().equals(AuthentificationDto.EnumTypeIdentifiant.LOGIN)) {
        errors.putAll(validator.validate(authentification, AuthentificationLogin.class));
        if (!errors.isEmpty()) {
          throw new ValidationException(errors);
        }
        return getPersonnelByLogin(authentification.getLogin(), authentification.getMotDePasse());
      } else {
        throw new BadRequestException();
      }
    } else {
      throw new ValidationException(errors);
    }
  }

  @Override
  public UtilisateurGeneriqueDto getAuthentificationCompteGenerique(AuthentificationDto authentification)
          throws ValidationException {
    Map<String, String> errors = validator.validate(authentification, AuthentificationBasique.class);
    if (!errors.containsKey("typeAuthentifiant")) {
      if (authentification.getTypeAuthentifiant().equals(AuthentificationDto.EnumTypeIdentifiant.NUMERO_COMPTE)) {
        errors.putAll(validator.validate(authentification, AuthentificationNumeroCompte.class));
        if (!errors.isEmpty()) {
          throw new ValidationException(errors);
        }
        return getUserGeneriqueByNumeroDossier(authentification.getNumeroCompte(), authentification.getMotDePasse());
      } else {
        throw new BadRequestException();
      }
    } else {
      throw new ValidationException(errors);
    }
  }

  private UtilisateurPersonnelDto getPersonnelByLogin(String login, String motDePasse) {
    LOGGER.debug("getPersonnelByLogin({}, [PROTECTED])", login);
    return utilisateurPersonnelLdap.getByLogin(login, motDePasse);
  }

  @Override
  public String generateToken(String numeroDossier) {
    return blowFishUtils.generateToken(numeroDossier);
  }

  @Override
  public String generateToken(String numeroDossier, Date date) {
    return blowFishUtils.generateToken(numeroDossier, date);
  }

  @Override
  public String generateToken(String numeroDossier, Date dateGeneration, Long duration, String parametres) {
    return blowFishUtils.generateToken(dateGeneration, numeroDossier, duration, parametres);
  }


  @Override
  public Boolean isNewToken(String token) {
      if (token.contains(BlowFishUtils.TOKEN_BODY_SIGNATURE_SEPARATOR)) {
          return true;
      }
      return false;
  }

    @Override
    public String getParamsFromToken(String token) {
        try {
            if (sha512Utils.encrypt(StringUtils.substringBefore(token, BlowFishUtils.TOKEN_BODY_SIGNATURE_SEPARATOR),
                    BlowFishUtils.TOKEN_SEL).compareTo(StringUtils.substringAfter(token, BlowFishUtils.TOKEN_BODY_SIGNATURE_SEPARATOR)) != 0) {
                throw new BadRequestException();
            }
            String decryptedToken = blowFishUtils.decrypt(StringUtils.substringBetween(token, "V", BlowFishUtils.TOKEN_BODY_SIGNATURE_SEPARATOR));
            if (decryptedToken == null) {
                throw new BadRequestException();
            }
            List<String> listParams = Arrays.asList(decryptedToken.split(BlowFishUtils.TOKEN_PARAMS_SEPARATOR));
            String numeroDossier = listParams.get(1);
            Date dateGeneration = blowFishUtils.getTokenWithVersionDateFormatter().parse((String) listParams.get(0));
            UtilisateurCadreDto cadre = utilisateurCadreDao.getLastConnectionDate(numeroDossier);
            if (cadre != null && utilisateurCadreDao.get(cadre.getId()) != null &&
                    blowFishUtils.isTokenValidWithDurationInSecond(dateGeneration, Integer.valueOf(listParams.get(2)))) {
                return listParams.get(3);
            }
            return null;
        } catch (StringIndexOutOfBoundsException e) {
            LOGGER.error("StringIndexOutOfBoundsException !", e);
            throw new BadRequestException();
        } catch (ParseException e) {
            LOGGER.error("ParseException !", e);
            throw new BadRequestException();
        }
    }

  private boolean isSamePassword(UtilisateurGeneriqueDto user, String pwd) {
    return user != null && isSamePassword(user.getMotDePasseSha512(), user.getMotDePasseSel(), pwd);
  }

  private boolean isSamePassword(String pwdSha512, String pwdSel, String pwd) {
    return StringUtils.isNotEmpty(pwdSha512)
            && sha512Utils.encrypt(pwd, pwdSel).equals(pwdSha512);
  }

  private AuthentificationModificationDto encryptPassword(AuthentificationModificationDto auth, String numeroDossier) {
    Utilisateur user = utilisateurDao.getUtilisateur(numeroDossier);
    if (user == null) {
      throw new ResourceNotFoundException();
    }
    return encryptPassword(auth, user.getAudit().getDateCreation(), user.getId());
  }

  private AuthentificationModificationDto encryptPassword(AuthentificationModificationDto auth, Date dateCreation, String numeroDossier) {
    auth.setMotDePasseSel(sha512Utils.encryptUserPasswordSalt(dateCreation, numeroDossier));
    auth.setMotDePasseSha512(sha512Utils.encrypt(auth.getMotDePasse(), auth.getMotDePasseSel()));
    auth.setMotDePasse(blowFishUtils.encrypt(auth.getMotDePasse())); //TODO : clean after delivery
    return auth;
  }


@Override
public boolean isTokenCadreValid(String token) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public AuthentificationInfoDto getCadreAuthentificationInfoFromToken(String token) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isFormerIdenticalPasswordCadre(String numeroDossier, String password) {
	// TODO Auto-generated method stub
	return false;
}
}
