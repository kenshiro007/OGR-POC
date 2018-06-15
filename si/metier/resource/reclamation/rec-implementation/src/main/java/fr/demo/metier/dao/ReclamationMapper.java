package fr.demo.metier.dao;

import fr.demo.metier.dao.core.impl.BasicMapper;
import fr.demo.metier.dto.*;
import fr.demo.metier.model.referentielstatique.EnumReclamationStatus;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface ReclamationMapper extends BasicMapper<ReclamationDto> {

    ReclamationDto getReclamationById(Long idReclamation);

    List<Long> getIdsReclamationForBatch(@Param("executionDate") Date executionDate, @Param("statutPublie") Long statutPublie, @Param("statutOuvert") EnumReclamationStatus statutOuvert, RowBounds rowBounds);

    List<Long> getIdsReclamationByIntervalForBatch(@Param("startDate") Date startDate, @Param("endDate") Date endDate, RowBounds rowBounds);

}
