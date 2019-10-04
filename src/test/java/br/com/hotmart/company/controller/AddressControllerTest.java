package br.com.hotmart.company.controller;

import br.com.hotmart.company.config.exception.ResourceNotFoundException;
import br.com.hotmart.company.model.dto.AddressDto;
import br.com.hotmart.company.model.form.AddressForm;
import br.com.hotmart.company.service.impl.AddressServiceImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddressServiceImpl addressService;

    private AddressForm addressForm;

    private String addressJson;

    @Before
    public void setup() throws JsonProcessingException {
        this.addressForm = new AddressForm();
        this.addressForm.setStreet("Rua Minas Gerais");
        this.addressForm.setCity("Guanambi");
        this.addressForm.setZipCode("46430-000");
        this.addressForm.setUf("BA");
        this.addressForm.setCountry("Brasil");
        this.addressJson = this.mapper.writeValueAsString(this.addressForm);
    }

    @Test
    public void testGETIndexShouldReturnOkStatus() throws Exception {
        given(addressService.findAll()).willReturn(Arrays.asList(new AddressDto()));
        this.mockMvc.perform(get("/address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETIndexShouldReturnNoContentStatusWhenHasNoEmployees() throws Exception {
        given(addressService.findAll()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get("/address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGETShowShouldReturnOkStatus() throws Exception {
        given(addressService.findById(1L)).willReturn(Optional.of(new AddressDto()));
        this.mockMvc.perform(get("/address/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGETShowShouldReturnNotFoundStatusWhenEmployeeIdIsInvalid() throws Exception {
        given(addressService.findById(1L)).willReturn(Optional.empty());
        this.mockMvc.perform(get("/address/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPUTUpdateShouldThrowAExceptionWhenAddressIdIsInvalid() throws Exception {
        given(addressService.update(this.addressForm.toEntity(), 1L)).willThrow(ResourceNotFoundException.class);
        this.mockMvc.perform(put("/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.addressJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPUTUpdateShouldReturnOkWhenAddressIsUpdated() throws Exception {
        given(addressService.update(this.addressForm.toEntity(), 1L)).willReturn(new AddressDto(this.addressForm.toEntity()));
        this.mockMvc.perform(put("/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.addressJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
