package com.project.RESTEndpoint.Service;

import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Repository.ContributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContributorsService {
    @Autowired
    private ContributorRepository contributorRepository;

    public List<ContributorModel> findAll(){
        return (List<ContributorModel>) contributorRepository.findAll();
    }

    public ContributorModel saveContributor(ContributorModel c){
        return contributorRepository.save(c);
    }
}
