package com.project.RESTEndpoint.Service;

import com.project.RESTEndpoint.Model.FileModel;
import com.project.RESTEndpoint.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public List<FileModel> findAll(){
        return (List<FileModel>) fileRepository.findAll();
    }

    public FileModel saveFile(FileModel f){
        return fileRepository.save(f);
    }

}
