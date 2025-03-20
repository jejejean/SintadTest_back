package com.SintadTest.controller;


import com.SintadTest.dataProvider.DocumentDataProvider;
import com.SintadTest.documentType.controller.DocumentTypeController;
import com.SintadTest.documentType.models.interfaces.DocumentTypeInterface;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.shared.interfaces.CrudInterface;
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

@WebMvcTest(DocumentTypeController.class)
@ExtendWith(SpringExtension.class)
public class DocumentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudInterface<DocumentTypeRequest, DocumentTypeResponse> crudInterface;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DocumentTypeInterface documentTypeInterface;


    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testCreateDocumentType() throws Exception {
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest(null, "0002", "DNI", true, "Documento Nacional de Identidad");
        DocumentTypeResponse response = DocumentDataProvider.createDocumentTypeResponse(1L, "0002", "DNI", true, "Documento Nacional de Identidad");

        when(crudInterface.create(any(DocumentTypeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/document-type/new-document-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)) // Convierte el objeto a JSON
                        .with(csrf())) // Evita el error 403 por CSRF
                .andExpect(status().isCreated()) // HTTP 201
                .andExpect(jsonPath("$.idDocumentType").value(1))
                .andExpect(jsonPath("$.code").value("0002"))
                .andExpect(jsonPath("$.name").value("DNI"))
                .andExpect(jsonPath("$.state").value(true))
                .andExpect(jsonPath("$.description").value("Documento Nacional de Identidad"));

        verify(crudInterface, times(1)).create(any(DocumentTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindAllDocumentTypes() throws Exception {
        List<DocumentTypeResponse> responseList = DocumentDataProvider.createDocumentTypeResponseList();

        when(crudInterface.findAll()).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/document-type/all-document-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDocumentType").value(1))
                .andExpect(jsonPath("$[0].code").value("0001"))
                .andExpect(jsonPath("$[0].name").value("DNI"))
                .andExpect(jsonPath("$[0].state").value(true))
                .andExpect(jsonPath("$[0].description").value("Documento Nacional de Identidad"))
                .andExpect(jsonPath("$[1].idDocumentType").value(2))
                .andExpect(jsonPath("$[1].code").value("0002"))
                .andExpect(jsonPath("$[1].name").value("RUC"))
                .andExpect(jsonPath("$[1].state").value(true))
                .andExpect(jsonPath("$[1].description").value("Registro Único de Contribuyentes"));
        verify(crudInterface, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindDocumentTypeById() throws Exception {
        Long id = 1L;
        Optional<DocumentTypeResponse> response = DocumentDataProvider.createDocumentTypeOptional(id, "0001", "DNI", true, "Documento Nacional de Identidad");

        when(crudInterface.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/document-type/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDocumentType").value(1))
                .andExpect(jsonPath("$.code").value("0001"))
                .andExpect(jsonPath("$.name").value("DNI"))
                .andExpect(jsonPath("$.state").value(true))
                .andExpect(jsonPath("$.description").value("Documento Nacional de Identidad"));
        verify(crudInterface, times(1)).findById(id);
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testUpdateDocumentType() throws Exception {
        Long id = 1L;
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest(id, "0001", "DNI", true, "Documento Nacional de Identidad");
        DocumentTypeResponse response = DocumentDataProvider.createDocumentTypeResponse(id, "0001", "DNI", true, "Documento Nacional de Identidad");

        when(crudInterface.update(eq(id), any(DocumentTypeRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/document-type/update-document-type/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDocumentType").value(1))
                .andExpect(jsonPath("$.code").value("0001"))
                .andExpect(jsonPath("$.name").value("DNI"))
                .andExpect(jsonPath("$.state").value(true))
                .andExpect(jsonPath("$.description").value("Documento Nacional de Identidad"));
        verify(crudInterface, times(1)).update(eq(id), any(DocumentTypeRequest.class));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testDeleteDocumentType() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/document-type/delete-document-type/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());
        verify(crudInterface, times(1)).delete(id);
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRADOR")
    void testFindAllDocumentByState() throws Exception {
        List<DocumentTypeInfoResponse> responseList = DocumentDataProvider.createDocumentTypeInfoResponseList();

        when(documentTypeInterface.findAllByStateTrue()).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/document-type/all-document-by-state")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDocumentType").value(1))
                .andExpect(jsonPath("$[0].name").value("DNI"))
                .andExpect(jsonPath("$[1].idDocumentType").value(2))
                .andExpect(jsonPath("$[1].name").value("RUC"));
        verify(documentTypeInterface, times(1)).findAllByStateTrue();
    }

}
