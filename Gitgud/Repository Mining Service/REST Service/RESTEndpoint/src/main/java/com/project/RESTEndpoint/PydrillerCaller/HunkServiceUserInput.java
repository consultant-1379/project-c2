package com.project.RESTEndpoint.PydrillerCaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class HunkServiceUserInput {

    public void callPydrillerHunksUserInput(String fromCommit, String toCommit) throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder("python", ("../project-c2/Gitgud/Repository Mining Service/Pydriller/pydriller_hunks_mining_userinput.py"), fromCommit, toCommit);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (var reader = new BufferedReader(

                new InputStreamReader(process.getInputStream()))) {
            System.out.println("Pydriller is being called.....Creating CSV file pydriller-hunks.csv in resources containing the hunk count information between " + fromCommit + " " + toCommit);

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("exitcode: " + exitCode);

        }

    }
}
