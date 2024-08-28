package com.project.RESTEndpoint.Service;

import com.opencsv.exceptions.CsvException;
import com.project.RESTEndpoint.CsvReaderService.GitCsvReader;
import com.project.RESTEndpoint.CsvReaderService.GitHunkCsvReader;
import com.project.RESTEndpoint.PydrillerCaller.HunkServiceCaller;
import com.project.RESTEndpoint.PydrillerCaller.PydrillerCaller;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class UpdateService {

    @Async
    public void updateCSV(PydrillerCaller pydrillerCaller, GitCsvReader gitReader, URL pydrillerCsv) throws URISyntaxException, IOException, CsvException {
        try {
            pydrillerCaller.callPydrillerMining();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert pydrillerCsv != null : "URI to pydriller.csv Resource is empty";
        gitReader.readCSV(pydrillerCsv);
    }

    @Async
    public void updateHunkCSV(HunkServiceCaller hunkServiceCaller, GitHunkCsvReader gitReader, URL pydrillerCsv) throws URISyntaxException, IOException, CsvException {
        try {
            hunkServiceCaller.callPydrillerHunksMining();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert pydrillerCsv != null : "URI to pydriller.csv Resource is empty";
        gitReader.readCSV(pydrillerCsv);
    }
}
