package fr.demo.metier.service.client;

import fr.demo.metier.dto.*;
import fr.demo.metier.exception.ValidationException;


public interface ReclamationServiceClient {

    ReclamationDto getReclamationById(Long idReclamation);

    Long create(ReclamationDto reclamation) throws ValidationException;

    void update(ReclamationDto reclamation) throws ValidationException;
    
}
