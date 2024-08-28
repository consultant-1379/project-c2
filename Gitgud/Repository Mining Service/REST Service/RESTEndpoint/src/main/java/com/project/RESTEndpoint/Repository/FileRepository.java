package com.project.RESTEndpoint.Repository;

import com.project.RESTEndpoint.Model.FileModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Integer> {
}
