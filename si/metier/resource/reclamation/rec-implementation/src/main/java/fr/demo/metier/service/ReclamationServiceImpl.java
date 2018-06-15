package fr.demo.metier.service;

import fr.demo.metier.dto.*;
import fr.demo.metier.dao.impl.ReclamationDao;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.model.core.AuditObject;
//import fr.demo.metier.model.referentielstatique.EnumProfilStatut;
import fr.demo.metier.service.ReclamationService;

import fr.demo.metier.validator.ValidatorConstantes;
import fr.demo.metier.validator.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component("reclamationService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReclamationServiceImpl implements ReclamationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReclamationServiceImpl.class);
    private static final String MANDATORY = "obligatoire";

    @Autowired
    private ValidatorUtil validator;

    @Resource(name = "reclamationDao")
    private ReclamationDao reclamationDao;
    
    //@Resource(name = "recIndexingOrchestrationService")
    //private RecIndexingOrchestrationService recIndexingOrchestrationService;


    public ReclamationDto getReclamationById(Long idReclamation) {
        return reclamationDao.getReclamationById(idReclamation);
    }
    
    @Override
    public Long create(ReclamationDto reclamation) throws ValidationException {
        return reclamationDao.create(reclamation).getId();
    }

    @Override
    public void update(ReclamationDto reclamation) throws ValidationException {
        reclamationDao.update(reclamation);
    }


    @Override
    public Boolean exists(Long idReclamation) {
        return reclamationDao.exists(idReclamation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getIdsReclamationForBatch(Date executionDate, int numPage, int maxResult) throws ValidationException {
        Map<String, String> errors = new HashMap<>();
        if (executionDate == null) {
            errors.put("executionDate", MANDATORY);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return reclamationDao.getIdsReclamationForBatch(executionDate, numPage, maxResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getIdsReclamationByIntervalForBatch(Date startDate, Date endDate, int numPage, int maxResult)
            throws ValidationException {
        Map<String, String> errors = new HashMap<String, String>();
        if (startDate == null) {
            errors.put("startDate", MANDATORY);
        }
        if (endDate == null) {
            errors.put("endDate", MANDATORY);
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return reclamationDao.getIdsReclamationByIntervalForBatch(startDate, endDate, numPage, maxResult);
    }

	@Override
	public Map<String, String> validateReclamation(ReclamationDto reclamation, String scenario) {
		// TODO Auto-generated method stub
		return null;
	}


}
