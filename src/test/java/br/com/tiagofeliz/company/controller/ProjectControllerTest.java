package br.com.tiagofeliz.company.controller;

import br.com.tiagofeliz.company.model.dto.ProjectDto;
import br.com.tiagofeliz.company.service.impl.ProjectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectServiceImpl projectService;

    @Test
    public void testGETIndexShouldReturnOkStatus() throws Exception {
        given(projectService.findAll()).willReturn(Arrays.asList(new ProjectDto()));
        this.mockMvc.perform(get("/project")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETIndexShouldReturnNoContentStatusWhenHasNoEmployees() throws Exception {
        given(projectService.findAll()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get("/project")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGETShowShouldReturnOkStatus() throws Exception {
        given(projectService.findById(1L)).willReturn(Optional.of(new ProjectDto()));
        this.mockMvc.perform(get("/project/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETShowShouldReturnNotFoundStatusWhenEmployeeIdIsInvalid() throws Exception {
        given(projectService.findById(1L)).willReturn(Optional.empty());
        this.mockMvc.perform(get("/project/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // TODO Test POST and PUT methods

}
