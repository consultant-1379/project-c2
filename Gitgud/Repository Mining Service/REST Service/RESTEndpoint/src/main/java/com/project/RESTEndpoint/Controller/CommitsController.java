package com.project.RESTEndpoint.Controller;

import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Model.RepositoryModel;
import com.project.RESTEndpoint.Service.CommitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commits")
public class CommitsController {

    @Autowired
    private CommitsService commitService;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private Date date_1 = dateFormatter.parse("2018-03-20");
    private Date date_2 = dateFormatter.parse("2018-03-31");

    public CommitsController() throws ParseException {
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    /**
     * Calls the commits service method to find all commits and passes
     * them to a list
     * @return a HttpStatus message depending on whether or not the commits
     * were found
     */
    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommitModel>> getAll(){
        List<CommitModel> commitsList = commitService.findAll();

        if(commitsList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(commitsList);
        }
    }

    /**
     * Calls the save method in the commit service and passes a
     * commit object in order to persist it to the database
     * @param commits A commit object that is to be passed in through
     *                the request body as JSON
     * @return a HttpStatus message depending on whether or not the commit
     * was successfully added to the database
     */
    @PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addCommit(@RequestBody CommitModel commits){
        commitService.saveCommit(commits);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/codeChurn/avg/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> codeChurnAverage() throws ParseException {

        int average = commitService.getAverageCodeChurn(date_1, date_2);

            return ResponseEntity.ok(average);
    }

    @GetMapping(value = "/codeChurn/max/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> codeChurnMaximum(){
        int max = commitService.getMaxCodeChurn(date_1, date_2);
        return ResponseEntity.ok(max);
    }

    @GetMapping(value = "/changeSet/avg/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> changeSetMax(){
        int average = commitService.getAverageChangeSet(date_1, date_2);
        return ResponseEntity.ok(average);
    }

    @GetMapping(value = "/changeSet/max/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> changeSetAvg(){
        int max = commitService.getMaxChangeSet(date_1, date_2);
        return ResponseEntity.ok(max);
    }

    @GetMapping(value = "/contributors" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> countContributors(@RequestParam String date1, @RequestParam String date2) throws ParseException {

        Date givenDate2 = dateFormatter.parse(date2);
        Date givenDate1 = dateFormatter.parse(date1);

        int count = commitService.getCountContributors(givenDate1, givenDate2);
        return ResponseEntity.ok(count);
    }

    @GetMapping(value = "/repoData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepositoryModel> getRepositoryData(@RequestParam String date1, @RequestParam String date2) throws ParseException{
        Date givenDate2 = dateFormatter.parse(date2);
        Date givenDate1 = dateFormatter.parse(date1);

        RepositoryModel repositoryModel = new RepositoryModel();
        repositoryModel.setChangeSetAverage(commitService.getAverageChangeSet(givenDate1, givenDate2));
        repositoryModel.setChangeSetMax(commitService.getMaxChangeSet(givenDate1,givenDate2));
        repositoryModel.setCodeChurnAverage(commitService.getAverageCodeChurn(givenDate1, givenDate2));
        repositoryModel.setCodeChurnMax(commitService.getMaxCodeChurn(givenDate1, givenDate2));
        repositoryModel.setCountContributors(commitService.getCountContributors(givenDate1, givenDate2));
        repositoryModel.setContributionsPerContributors(commitService.getContributionsPerContributors(givenDate1, givenDate2));
        repositoryModel.setNumCommits(commitService.numCommits(givenDate1, givenDate2));
        repositoryModel.setSumMinorConts(commitService.getMinorConts(givenDate1, givenDate2));
        return ResponseEntity.ok(repositoryModel);
    }

}
