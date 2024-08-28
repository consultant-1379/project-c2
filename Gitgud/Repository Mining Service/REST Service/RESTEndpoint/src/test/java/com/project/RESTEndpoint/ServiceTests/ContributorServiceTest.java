package com.project.RESTEndpoint.ServiceTests;

import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Repository.ContributorRepository;
import com.project.RESTEndpoint.Service.ContributorsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContributorServiceTest {

    @Mock
    private ContributorRepository contributorRepository;;

    @InjectMocks
    private ContributorsService contributorsService;

    @Test
    public void whenContributorSavedReturnContributor(){
        ContributorModel contributorModel = new ContributorModel("test@email.com", "Test Name");

        when(contributorRepository.save(ArgumentMatchers.any(ContributorModel.class))).thenReturn(contributorModel);

        ContributorModel created = contributorsService.saveContributor(contributorModel);
        assertThat(created.getContributor_email().equals(contributorModel.getContributor_email()));
        verify(contributorRepository).save(contributorModel);

    }

    @Test
    public void testReturnListOfContributors(){
        List<ContributorModel> contributorModelList = new ArrayList<>();
        contributorModelList.add(new ContributorModel());

        given(contributorRepository.findAll()).willReturn(contributorModelList);

        List<ContributorModel> expectedList = contributorsService.findAll();
        assertEquals(expectedList, contributorModelList);
        verify(contributorRepository).findAll();

    }


}
