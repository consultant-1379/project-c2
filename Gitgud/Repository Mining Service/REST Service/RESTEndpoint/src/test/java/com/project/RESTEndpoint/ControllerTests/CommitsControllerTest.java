package com.project.RESTEndpoint.ControllerTests;
import com.project.RESTEndpoint.Controller.CommitsController;
import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Service.CommitsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommitsController.class)
public class CommitsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommitsService commitsService;

    @Test
    public void createCommitWhenPOSTMethodCalled() throws Exception{
        CommitModel commit = new CommitModel("hash", 1,1,new Date(),"test@email.com", 3);

        given(commitsService.saveCommit(commit)).willReturn(commit);

        mockMvc.perform(post("/commits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJson(commit)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listAllCommitsWhenGETMethodCalled() throws Exception{
        CommitModel commit = new CommitModel("hash", 1,1,new Date(),"test@email.com", 3);

        List<CommitModel> allCommits = Arrays.asList(commit);
        given(commitsService.findAll()).willReturn(allCommits);

        mockMvc.perform(get("/commits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
