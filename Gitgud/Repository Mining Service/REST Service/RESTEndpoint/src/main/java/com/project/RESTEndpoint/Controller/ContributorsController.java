package com.project.RESTEndpoint.Controller;

import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Service.ContributorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/contributors")
public class ContributorsController {
    @Autowired
    private ContributorsService contributorsService;


    /**
     * Calls the contributor service method to find all contributors and
     * passes them to a list
     * @return a HttpStatus message depending on whether or not the contributors
     * were found
     */
    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContributorModel>> getAll(){
        List<ContributorModel> allFiles = contributorsService.findAll();

        if(allFiles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(allFiles);
        }
    }

    /**
     * Method adds contributors to database. Firstly, it checks that the contributor being added hasn't
     * been added before by creating a set of contributors.
     * @param contributor Contributor object that is to be added to the DB
     * @return ResponseEntity
     */
    @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addContributor(@RequestBody ContributorModel contributor){
        //TODO: Could be inefficient as we get all the contributors from the DB when calling addContributor
        List<ContributorModel> allContributors = contributorsService.findAll();

        Set<ContributorModel> setContributors =  new HashSet<ContributorModel>(allContributors);

        boolean userExists=setContributors.stream().anyMatch(con ->
                con.getContributor_email().equals(contributor.getContributor_email()));

        if(userExists){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }else{
            contributorsService.saveContributor(contributor);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }


}
