package fr.demo.metier.service;

import fr.demo.metier.dto.ReclamationDto;
import fr.demo.metier.exception.ValidationException;
import fr.demo.metier.service.client.ReclamationServiceClient;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReclamationService extends ReclamationServiceClient {

    Boolean exists(Long reclamationId);

    List<Long> getIdsReclamationForBatch(Date executionDate, int numPage, int maxResult)
            throws ValidationException;

    List<Long> getIdsReclamationByIntervalForBatch(Date executionStartDate, Date executionEndDate, int numPage,
                                          int maxResult) throws ValidationException;
 // TODO : validate dto by  business scenario
    
    Map<String, String> validateReclamation(ReclamationDto reclamation, String scenario);
}
