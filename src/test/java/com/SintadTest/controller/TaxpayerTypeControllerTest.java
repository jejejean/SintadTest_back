package com.SintadTest.controller;

import com.SintadTest.dataProvider.TaxpayerDataProvider;
import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.taxpayerType.controller.TaxpayerTypeController;
import com.SintadTest.taxpayerType.interfaces.TaxpayerTypeInterface;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

@WebMvcTest(TaxpayerTypeController.class)
@ExtendWith(SpringExtension.class)
public class TaxpayerTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudInterface<TaxpayerTypeRequest, TaxpayerTypeResponse> crudInterface;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaxpayerTypeInterface taxpayerTypeInterface;

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testCreateTaxpayerType() throws Exception {
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest(null, "Persona Jurídica", true);
        TaxpayerTypeResponse response = TaxpayerDataProvider.createTaxpayerTypeResponse(1L, "Persona Jurídica", true);

        when(crudInterface.create(any(TaxpayerTypeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/taxpayer-type/new-taxpayer-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)) // Convierte el objeto a JSON
                        .with(csrf())) // Evita el error 403 por CSRF
                .andExpect(status().isCreated()) // HTTP 201
                .andExpect(jsonPath("$.idTaxpayerType").value(1))
                .andExpect(jsonPath("$.name").value("Persona Jurídica"))
                .andExpect(jsonPath("$.state").value(true));

        verify(crudInterface, times(1)).create(any(TaxpayerTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindAllTaxpayerTypes() throws Exception {
        List<TaxpayerTypeResponse> responseList = TaxpayerDataProvider.createTaxpayerTypeResponseList();

        when(crudInterface.findAll()).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/taxpayer-type/all-taxpayer-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$.length()").value(2)) // Verifica que hay 2 elementos
                .andExpect(jsonPath("$[0].idTaxpayerType").value(1))
                .andExpect(jsonPath("$[0].name").value("Persona Jurídica"))
                .andExpect(jsonPath("$[0].state").value(true))
                .andExpect(jsonPath("$[1].idTaxpayerType").value(2))
                .andExpect(jsonPath("$[1].name").value("Persona Natural"))
                .andExpect(jsonPath("$[1].state").value(true));

        verify(crudInterface, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindTaxpayerTypeById() throws Exception {
        Long id = 1L;
        Optional<TaxpayerTypeResponse> response = TaxpayerDataProvider.createOptionalTaxpayerTypeResponse(id, "Persona Jurídica", true);

        when(crudInterface.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/taxpayer-type/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$.idTaxpayerType").value(id))
                .andExpect(jsonPath("$.name").value("Persona Jurídica"))
                .andExpect(jsonPath("$.state").value(true));

        verify(crudInterface, times(1)).findById(id);
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testUpdateTaxpayerType() throws Exception {
        Long id = 1L;
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest(id, "Nueva Persona", true);
        TaxpayerTypeResponse response = TaxpayerDataProvider.createTaxpayerTypeResponse(id, "Nueva Persona", true);

        when(crudInterface.update(eq(id), any(TaxpayerTypeRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/taxpayer-type/update-taxpayer-type/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTaxpayerType").value(id))
                .andExpect(jsonPath("$.name").value("Nueva Persona"))
                .andExpect(jsonPath("$.state").value(true));

        verify(crudInterface, times(1)).update(eq(id), any(TaxpayerTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testDeleteTaxpayerType() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/v1/taxpayer-type/delete-taxpayer-type/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());

        verify(crudInterface, times(1)).delete(id);
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindAllByState() throws Exception {
        List<TaxpayerTypeInfoResponse> responseList = TaxpayerDataProvider.createTaxpayerTypeInfoResponseList();

        when(taxpayerTypeInterface.findAllByStateTrue()).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/taxpayer-type/all-taxpayer-by-state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idTaxpayerType").value(1))
                .andExpect(jsonPath("$[0].name").value("Persona Jurídica"))
                .andExpect(jsonPath("$[1].idTaxpayerType").value(2))
                .andExpect(jsonPath("$[1].name").value("Persona Natural"));

        verify(taxpayerTypeInterface, times(1)).findAllByStateTrue();
    }

}