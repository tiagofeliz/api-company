package br.com.tiagofeliz.company.controller;

import br.com.tiagofeliz.company.model.dto.DepartmentDto;
import br.com.tiagofeliz.company.service.impl.DepartmentServiceImpl;
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
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentServiceImpl departmentService;

    @Test
    public void testGETIndexShouldReturnOkStatus() throws Exception {
        given(departmentService.findAll()).willReturn(Arrays.asList(new DepartmentDto()));
        this.mockMvc.perform(get("/department")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETIndexShouldReturnNoContentStatusWhenHasNoEmployees() throws Exception {
        given(departmentService.findAll()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get("/department")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGETShowShouldReturnOkStatus() throws Exception {
        given(departmentService.findById(1L)).willReturn(Optional.of(new DepartmentDto()));
        this.mockMvc.perform(get("/department/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETShowShouldReturnNotFoundStatusWhenEmployeeIdIsInvalid() throws Exception {
        given(departmentService.findById(1L)).willReturn(Optional.empty());
        this.mockMvc.perform(get("/department/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // TODO Test POST and PUT methods

}
