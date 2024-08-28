package com.project.RESTEndpoint.Repository;

import com.project.RESTEndpoint.Model.ContributorModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends CrudRepository<ContributorModel, String> {
}
