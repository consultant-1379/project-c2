package com.project.RESTEndpoint.ControllerTests;

import com.project.RESTEndpoint.Controller.FileController;
import com.project.RESTEndpoint.Model.CommitModel;
import com.project.RESTEndpoint.Model.FileModel;
import com.project.RESTEndpoint.Service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@WebMvcTest(FileController.class)
public class FileModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void createFileWhenPOSTMethodCalled() throws Exception {
        FileModel fileModel = new FileModel(0, "test file", 10);

        given(fileService.saveFile(fileModel)).willReturn(fileModel);

        mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJson(fileModel)))
                .andExpect(status().isCreated());

    }

    @Test
    public void listAllCommitsWhenGETMethodCalled() throws Exception{
        FileModel fileModel = new FileModel(0, "test file", 10);

        List<FileModel> allFiles = Arrays.asList(fileModel);
        given(fileService.findAll()).willReturn(allFiles);

        mockMvc.perform(get("/file")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

   }

}
