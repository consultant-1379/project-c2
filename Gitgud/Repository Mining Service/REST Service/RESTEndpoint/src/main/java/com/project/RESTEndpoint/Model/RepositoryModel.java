package com.project.RESTEndpoint.Model;

import lombok.*;

import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RepositoryModel {

    private int changeSetAverage;
    private int changeSetMax;
    private int codeChurnAverage;
    private int codeChurnMax;
    private int countContributors;
    private HashMap<String, Integer> contributionsPerContributors;
    private int numCommits;
    private int sumMinorConts;
}
