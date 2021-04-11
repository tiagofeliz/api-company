package br.com.tiagofeliz.company.controller;

import br.com.tiagofeliz.company.config.exception.ResourceNotFoundException;
import br.com.tiagofeliz.company.model.dto.BudgetDto;
import br.com.tiagofeliz.company.model.form.BudgetForm;
import br.com.tiagofeliz.company.service.impl.BudgetServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetServiceImpl budgetService;

    @Autowired
    private ObjectMapper mapper;

    private BudgetForm budgetForm;

    private String budgetJson;

    @Before
    public void setup() throws JsonProcessingException {
        this.budgetForm = new BudgetForm();
        this.budgetForm.setIdDepartment(1L);
        this.budgetForm.setValue(6000);
        this.budgetForm.setStartDate(LocalDate.of(2019, 1, 1));
        this.budgetForm.setEndDate(LocalDate.of(2019, 1, 1));
        this.budgetJson = this.mapper.writeValueAsString(this.budgetForm);
    }

    @Test
    public void testGETShowShouldReturnOkStatus() throws Exception {
        given(budgetService.findById(1L)).willReturn(Optional.of(new BudgetDto()));
        this.mockMvc.perform(get("/budget/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETShowShouldReturnNotFoundStatusWhenEmployeeIdIsInvalid() throws Exception {
        given(budgetService.findById(1L)).willReturn(Optional.empty());
        this.mockMvc.perform(get("/budget/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPOSTCreateShouldReturnCreatedStatusWhenABudgetIsCreated() throws Exception {
        given(budgetService.create(this.budgetForm.toEntity())).willReturn(new BudgetDto(this.budgetForm.toEntity()));
        this.mockMvc.perform(post("/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.budgetJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPUTUpdateShouldReturnOkWhenBudgetIsUpdated() throws Exception {
        given(budgetService.update(this.budgetForm.toEntity(), 1L)).willReturn(new BudgetDto(this.budgetForm.toEntity()));
        this.mockMvc.perform(put("/budget/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.budgetJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
