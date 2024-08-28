package com.project.RESTEndpoint.Controller;

import com.project.RESTEndpoint.Model.FileModel;
import com.project.RESTEndpoint.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    //get all files
    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileModel>> getAll(){
        List<FileModel> allFiles = fileService.findAll();

        if(allFiles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(allFiles);
        }
    }

    //add file
    @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addFile(@RequestBody FileModel file){
        fileService.saveFile(file);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
