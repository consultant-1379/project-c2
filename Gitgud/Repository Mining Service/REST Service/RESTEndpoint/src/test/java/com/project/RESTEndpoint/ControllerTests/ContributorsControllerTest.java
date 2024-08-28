package com.project.RESTEndpoint.ControllerTests;

import com.project.RESTEndpoint.Controller.ContributorsController;
import com.project.RESTEndpoint.Model.ContributorModel;
import com.project.RESTEndpoint.Service.ContributorsService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContributorsController.class)
public class ContributorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContributorsService contributorsService;

    @Test
    public void createContributorsWhenPOSTCalled() throws Exception{
        ContributorModel contributorModel = new ContributorModel("test@email.com", "test name");

        given(contributorsService.saveContributor(contributorModel)).willReturn(contributorModel);

        mockMvc.perform(post("/contributors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJson(contributorModel)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listAllContributorsWhenGETMethodCalled() throws Exception{
        ContributorModel contributorModel = new ContributorModel("test@email.com", "test name");

        List<ContributorModel> allContributors = Arrays.asList(contributorModel);
        given(contributorsService.findAll()).willReturn(allContributors);

        mockMvc.perform(get("/contributors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
