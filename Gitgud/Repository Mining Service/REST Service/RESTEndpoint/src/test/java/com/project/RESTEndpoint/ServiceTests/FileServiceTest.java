package com.project.RESTEndpoint.ServiceTests;

import com.project.RESTEndpoint.Model.FileModel;
import com.project.RESTEndpoint.Repository.FileRepository;
import com.project.RESTEndpoint.Service.FileService;
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
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Test
    public void testWhenFileSavedReturnCorrectFile(){
        FileModel fileModel = new FileModel(0, "test file name", 1);

        when(fileRepository.save(ArgumentMatchers.any(FileModel.class))).thenReturn(fileModel);

        FileModel created = fileService.saveFile(fileModel);
        assertThat(created.getFile_id() == (fileModel.getFile_id()));
        verify(fileRepository).save(fileModel);
    }

    @Test
    public void testReturnListOfFiles(){
        List<FileModel> fileModelList = new ArrayList<>();
        fileModelList.add(new FileModel());

        given(fileRepository.findAll()).willReturn(fileModelList);

        List<FileModel> expectedList = fileService.findAll();
        assertEquals(expectedList,fileModelList);
        verify(fileRepository).findAll();
    }

}
