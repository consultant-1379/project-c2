package com.project.RESTEndpoint.ServiceTests;

import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Repository.CommitRepository;
import com.project.RESTEndpoint.Service.CommitsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitsServiceTest {
    @Mock
    private CommitRepository commitRepository;

    @InjectMocks
    private CommitsService commitsService;

    /**
     * Test method for saving commit to database
     * First create a new commit object and then a stub so that the commit repo returns a commit object
     * when the new commit object is passed to it.
     * Then save the commit object and assert they are equal - checks that the correct value was inserted
     * Verify that the commit repository is called correctly
     */
    @Test
    public void testWhenCommitSavedReturnCorrectCommit(){
        CommitModel commit = new CommitModel("hash", 1,1,new Date(),"test@email.com", 3);

        when(commitRepository.save(ArgumentMatchers.any(CommitModel.class))).thenReturn(commit);

        CommitModel created = commitsService.saveCommit(commit);
        assertThat(created.getCommit_hash_id().equals(commit.getCommit_hash_id())); //checks that correct value was inserted
        verify(commitRepository).save(commit); //checks that repo is called
    }

    @Test
    public void testReturnListOfAllCommits(){
        List<CommitModel> commitsList = new ArrayList<>();
        commitsList.add(new CommitModel());

        given(commitRepository.findAll()).willReturn(commitsList);

        List<CommitModel> expectedList = commitsService.findAll();
        assertEquals(expectedList, commitsList);
        verify(commitRepository).findAll();
    }

}
