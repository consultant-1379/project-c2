package com.project.RESTEndpoint.CsvReaderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Model.FileModel;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public final class GitHunkCsvReader {

    public void readCSV(URL resource) throws IOException, CsvException, URISyntaxException {

        try (CSVReader reader = new CSVReader(new FileReader(resource.toURI().getPath()))){
            List<String[]> lines = reader.readAll();

            //get rid of first line of CSV which is headers
            lines.remove(0);
            System.out.println("Creating file objects and posting them to endpoint");
            //Investigate: Could below can be turned into an list of tasks like executors, for concurrency and performance?
            lines.forEach(this::parseCSV);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void parseCSV(String[] line){
        String fileName = "";
        fileName = line[0];
        double hunkCount = Double.parseDouble(line[1]);

        FileModel fileModel = createFile( fileName, hunkCount);
        try {
            postFile(fileModel);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //System.out.println("data: " +fileName+","+hunkCount);
    }

    public FileModel createFile( String fileName, double hunkCount){
        FileModel file = new FileModel();
        file.setFile_name(fileName);
        file.setHunk_count(hunkCount);

        return file;
    }

    public void postFile(FileModel fileModel) throws URISyntaxException{

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try{
            json = mapper.writeValueAsString(fileModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println("Posting commit:"+json);

        HttpClient client = HttpClient.newHttpClient();

        // Is thenAccept a closing statement?
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/file"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

    }


}
