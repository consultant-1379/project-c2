package com.project.RESTEndpoint.Repository;

import com.project.RESTEndpoint.Model.CommitModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CommitRepository extends CrudRepository<CommitModel, String>{
    @Query(value = "SELECT AVG(insertions + deletions) " +
                    "FROM COMMITS " +
                    "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getCodeChurnAvg(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT MAX(insertions + deletions) " +
            "FROM COMMITS " +
            "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getCodeChurnMax(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT AVG(files_changed) " +
            "FROM COMMITS " +
            "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getChangeSetAvg(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT MAX(files_changed) " +
            "FROM COMMITS " +
            "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getChangeSetMax(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT COUNT(DISTINCT  contributor_email) " +
                    "FROM COMMITS " +
                    "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getContributorsCount(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT COUNT(*) " +
            "FROM commits " +
            "WHERE date_submitted BETWEEN :date1 AND :date2 " +
            "GROUP BY contributor_email " +
            "ORDER BY contributor_email ASC", nativeQuery = true)
    List<Integer> getContributions(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "SELECT contributor_email " +
            "FROM commits " +
            "WHERE date_submitted BETWEEN :date1 AND :date2 " +
            "GROUP BY contributor_email " +
            "ORDER BY contributor_email ASC", nativeQuery = true)
    List<String> getContributors(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value =  "SELECT COUNT(*) " +
            "FROM commits " +
            "WHERE date_submitted BETWEEN :date1 AND :date2", nativeQuery = true)
    Integer getNumCommits(@Param("date1") Date date1, @Param("date2") Date date2);


}
