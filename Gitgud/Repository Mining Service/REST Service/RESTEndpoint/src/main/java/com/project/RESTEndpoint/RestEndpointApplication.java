package com.project.RESTEndpoint;

import com.opencsv.exceptions.CsvException;
import com.project.RESTEndpoint.CsvReaderService.GitCsvReader;
import com.project.RESTEndpoint.PydrillerCaller.HunkServiceCaller;
import com.project.RESTEndpoint.PydrillerCaller.HunkServiceUserInput;
import com.project.RESTEndpoint.PydrillerCaller.PydrillerCaller;
import com.project.RESTEndpoint.CsvReaderService.GitHunkCsvReader;
import com.project.RESTEndpoint.Service.UpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
public class RestEndpointApplication {

	public static void main(String[] args) throws IOException, CsvException, URISyntaxException, InterruptedException {
		SpringApplication.run(RestEndpointApplication.class, args);
		URL pydrillerCsv = RestEndpointApplication.class.getClassLoader().getResource("pydriller.csv");
		URL pydrillerHunksCsv = RestEndpointApplication.class.getClassLoader().getResource("pydriller-hunks.csv");
		PydrillerCaller pydrillerCaller = new PydrillerCaller();
		HunkServiceCaller hunkServiceCaller = new HunkServiceCaller();
		GitCsvReader gitReader = new GitCsvReader();
		GitHunkCsvReader gitHunkReader = new GitHunkCsvReader();
		UpdateService updateService = new UpdateService();

		startup(pydrillerCsv, pydrillerHunksCsv, pydrillerCaller, hunkServiceCaller, gitReader, gitHunkReader, updateService);

		while(true) {
			TimeUnit.MINUTES.sleep(60);
			updateService.updateCSV(pydrillerCaller,  gitReader, pydrillerCsv);
			//updateService.updateHunkCSV(hunkServiceCaller , gitHunkReader , pydrillerHunksCsv);
		}
//
//
//		HunkServiceUserInput hunkServiceUserInput = new HunkServiceUserInput();
//		try {
//			hunkServiceUserInput.callPydrillerHunksUserInput("a19bbe9736463a39374082501dfc05842c667d90" , "0236a5cf4d69784436bd317cc18564ad71024e15");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public static void startup(URL pydrillerCsv, URL pydrillerHunksCsv, PydrillerCaller pydrillerCaller, HunkServiceCaller hunkServiceCaller, GitCsvReader gitReader, GitHunkCsvReader gitHunkReader, UpdateService updateService) throws URISyntaxException, IOException, CsvException {


		Boolean pydrillerCsvStart = true;
		Boolean pydrillerHunkCsvStart = true;

		if(pydrillerCsv == null) {
			pydrillerCsvStart = false;
			updateService.updateCSV(pydrillerCaller, gitReader, pydrillerCsv);
		}

		if(pydrillerHunksCsv == null) {
			pydrillerHunkCsvStart = false;
			updateService.updateHunkCSV(hunkServiceCaller, gitHunkReader, pydrillerHunksCsv);
		}

		assert pydrillerCsv != null : "URI to pydriller.csv Resource is empty";
		gitReader.readCSV(pydrillerCsv);

		assert pydrillerHunksCsv != null : "URI to pydriller-hunks.csv Resource is empty";
		gitHunkReader.readCSV(pydrillerHunksCsv);

		if(pydrillerCsvStart == true){
			updateService.updateCSV(pydrillerCaller, gitReader, pydrillerCsv);
		}

		if(pydrillerHunkCsvStart == true){
			updateService.updateHunkCSV(hunkServiceCaller, gitHunkReader, pydrillerHunksCsv);
		}

	}

}
