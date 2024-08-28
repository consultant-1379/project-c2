package com.project.RESTEndpoint.CsvReaderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Model.FileModel;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@RestController
@NoArgsConstructor
public final class GitCsvReader {

    private int count = 0;

    public void readCSV(URL resource) throws IOException, CsvException, URISyntaxException {

        try (CSVReader reader = new CSVReader(new FileReader(resource.toURI().getPath()))){
            List<String[]> lines = reader.readAll();

            //get rid of first line of CSV which is headers
            lines.remove(0);
            System.out.println("Creating commit objects and posting them to endpoint");
            //Investigate: Could below can be turned into an list of tasks like executors, for concurrency and performance?
            lines.forEach(this::parseCSV);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the CSV into properties and call relevant methods to turn those properties
     * into their respective models.
     * @param line String array representing a single line in a csv file
     */
    public void parseCSV(String[] line){
        //TODO: Could be made more 'loosely' coupled by searching header of CSV and getting index of each
        // property, in case of any future changes. Better yet, perhaps an interface?

        //System.out.println(count++);

        //Properties needed for schema
        String commitHashId = line[0];
        String contributorName = line[1];
        String contributorEmail = line[2];
        int filesChanged = Integer.parseInt(line[3]);
        int insertions = Integer.parseInt(line[4]);
        int deletions = Integer.parseInt(line[5]);
        Date dateSubmitted = new Date();
        try {
            dateSubmitted = new SimpleDateFormat("yyyy-MM-dd").parse(line[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Convert properties to objects
        CommitModel commit = createCommit(commitHashId, contributorEmail, filesChanged, insertions, deletions,
                dateSubmitted);
        ContributorModel contributor = createContributor(contributorEmail,contributorName);
        //TODO: Figure out why below requires a value for fileId despite being auto generated in H2.
        // Could it be lombok requiring the extra parameter?

        //FileModel file = createFile(0, fileName, hunkCount);


        // Post data to REST Endpoint
        try {
            postCommit(commit);
            postContributor(contributor);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    //TODO: Below could be refactored to use the models constructors, instead they were
    // chosen because wanted to be sure on parameter order with lombok. It could
    // or *should* be refactored to take an interface of the models
    /**
     *
     * @param commitHashId
     * @param contributorEmail
     * @param filesChanged
     * @param insertions
     * @param deletions
     * @param dateSubmitted
     * @return
     */
    public CommitModel createCommit(String commitHashId, String contributorEmail, int filesChanged, int insertions,
                                    int deletions, Date dateSubmitted) {

        CommitModel commit = new CommitModel();
        commit.setCommit_hash_id(commitHashId);
        commit.setContributor_email(contributorEmail);
        commit.setFiles_changed(filesChanged);
        commit.setInsertions(insertions);
        commit.setDeletions(deletions);
        commit.setDate_submitted(dateSubmitted);

        return commit;
    }

    public ContributorModel createContributor(String contributorEmail, String contributorName){
        ContributorModel contributor = new ContributorModel();
        contributor.setContributor_email(contributorEmail);
        contributor.setContributor_name(contributorName);

        return contributor;
    }

    public FileModel createFile(int fileId, String fileName, int hunkCount){
        FileModel file = new FileModel();
        file.setFile_id(fileId);
        file.setFile_name(fileName);
        file.setHunk_count(hunkCount);

        return file;
    }

    public void postCommit(CommitModel commit) throws URISyntaxException{

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try{
            json = mapper.writeValueAsString(commit);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println("Posting commit:"+json);

        HttpClient client = HttpClient.newHttpClient();

        // Is thenAccept a closing statement?
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/commits"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

    }

    public void postContributor(ContributorModel contributorModel) throws URISyntaxException{

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try{
            json = mapper.writeValueAsString(contributorModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println("Posting commit:"+json);

        HttpClient client = HttpClient.newHttpClient();

        // Is thenAccept a closing statement?
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/contributors"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

    }


}
