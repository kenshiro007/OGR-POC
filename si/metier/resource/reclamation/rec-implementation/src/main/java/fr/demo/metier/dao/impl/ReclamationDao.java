package fr.demo.metier.dao.impl;

import fr.demo.metier.dao.core.impl.MyBatisMapperDao;
import fr.demo.metier.dao.*;
import fr.demo.metier.dto.*;
import fr.demo.metier.model.referentielstatique.EnumReclamationStatus;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("ReclamationDao")
public class ReclamationDao extends MyBatisMapperDao<ReclamationDto, ReclamationMapper> {

    public ReclamationDao() {
        super(ReclamationMapper.class);
    }

    public ReclamationDto getReclamationById(Long idReclamation){
        SqlSession session = getSqlSession();
        return session.getMapper(getBasicMapper()).getReclamationById(idReclamation);
    }

    public List<Long> getIdsReclamationForBatch(Date executionDate, int numPage, int maxResult) {
        SqlSession session = getSqlSession();
        return session.getMapper(getBasicMapper()).getIdsReclamationForBatch(
                executionDate,
                EnumReclamationStatus.AFFECTEE.getId(),
                EnumReclamationStatus.OUVERT,
                new RowBounds(numPage * maxResult, maxResult));
    }

    public List<Long> getIdsReclamationByIntervalForBatch(Date startDate, Date endDate, int numPage, int maxResult) {
        SqlSession session = getSqlSession();
        return session.getMapper(getBasicMapper()).getIdsReclamationByIntervalForBatch(
                startDate,
                endDate,
                new RowBounds(numPage * maxResult, maxResult));
    }
}

