package br.com.hotmart.company.controller;

import br.com.hotmart.company.model.dto.BudgetDto;
import br.com.hotmart.company.service.impl.BudgetServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetServiceImpl budgetService;

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

    // TODO Test POST and PUT methods

}
