package com.project.RESTEndpoint.Service;

import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CommitsService {
    @Autowired
    private CommitRepository commitRepository;
    
    public List<CommitModel> findAll() {
        return (List<CommitModel>) commitRepository.findAll();
    }

    public CommitModel saveCommit(CommitModel c) {
        return commitRepository.save(c);
    }

    public Integer getAverageCodeChurn(Date date1, Date date2){
        return commitRepository.getCodeChurnAvg(date1, date2);
    }

    public Integer getMaxCodeChurn(Date date1, Date date2){
        return commitRepository.getCodeChurnMax(date1, date2);
    }

    public Integer getAverageChangeSet(Date date1, Date date2){
        return commitRepository.getChangeSetAvg(date1, date2);
    }

    public Integer getMaxChangeSet(Date date1, Date date2){
        return commitRepository.getChangeSetMax(date1, date2);
    }

    public Integer getCountContributors(Date date1, Date date2){
        return commitRepository.getContributorsCount(date1, date2);
    }

    public HashMap<String, Integer> getContributionsPerContributors(Date date1, Date date2){

        List<String> list1= commitRepository.getContributors(date1, date2);
        List<Integer> list2 = commitRepository.getContributions(date1, date2);

        Map<String, Integer> map = IntStream.range(0, list1.size())
                .boxed()
                .collect(Collectors.toMap(i -> list1.get(i), i -> list2.get(i)));
        HashMap<String, Integer> hashMap = new HashMap<>(map);

        return hashMap;
    }

    public Integer numCommits(Date date1, Date date2){
        return commitRepository.getNumCommits(date1, date2);
    }

    public Integer getMinorConts(Date date1, Date date2){

        int numRows = commitRepository.getNumCommits(date1,date2);
        int sumMinorConts = 0;

        HashMap<String, Integer> hashMap = getContributionsPerContributors(date1,date2);

        for(HashMap.Entry<String, Integer> entry : hashMap.entrySet()) {

            String key = entry.getKey();
            Integer value = entry.getValue();

            if(value < 0.05 * numRows){
                sumMinorConts++;
            }
        }
        return sumMinorConts;
    }

}