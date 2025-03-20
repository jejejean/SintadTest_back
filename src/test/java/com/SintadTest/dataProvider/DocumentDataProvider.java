package com.SintadTest.dataProvider;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DocumentDataProvider {

    public static DocumentTypeRequest createDocumentTypeRequest() {
        return DocumentTypeRequest.builder()
                .code("0001")
                .name("DNI")
                .state(true)
                .description("Documento Nacional de Identidad")
                .build();
    }

    public static DocumentType createDocumentTypeEntity() {
        return DocumentType.builder()
                .idDocumentType(1L)
                .code("0001")
                .name("DNI")
                .state(true)
                .description("Documento Nacional de Identidad")
                .build();
    }

    public static DocumentTypeResponse createDocumentTypeResponse() {
        return DocumentTypeResponse.builder()
                .idDocumentType(1L)
                .code("0001")
                .name("DNI")
                .state(true)
                .description("Documento Nacional de Identidad")
                .build();
    }

    public static DocumentTypeInfoResponse crateDocumentTypeInfoResponse() {
        return DocumentTypeInfoResponse.builder()
                .idDocumentType(2L)
                .name("DNI")
                .build();
    }

    // controller
    public static DocumentTypeRequest createDocumentTypeRequest(Long id, String code, String name, boolean state, String description) {
        return DocumentTypeRequest.builder()
                .idDocumentType(id)
                .code(code)
                .name(name)
                .state(state)
                .description(description)
                .build();
    }


    public static DocumentTypeResponse createDocumentTypeResponse(Long id, String code, String name, boolean state, String description) {
        return DocumentTypeResponse.builder()
                .idDocumentType(id)
                .code(code)
                .name(name)
                .state(state)
                .description(description)
                .build();
    }

    public static List<DocumentTypeResponse> createDocumentTypeResponseList() {
        return Arrays.asList(
                createDocumentTypeResponse(1L, "0001", "DNI", true, "Documento Nacional de Identidad"),
                createDocumentTypeResponse(2L, "0002", "RUC", true, "Registro Único de Contribuyentes")
        );
    }

    public static List<DocumentTypeInfoResponse> createDocumentTypeInfoResponseList() {
        return Arrays.asList(
                new DocumentTypeInfoResponse(1L, "DNI"),
                new DocumentTypeInfoResponse(2L, "RUC")
        );
    }

    public static Optional<DocumentTypeResponse> createDocumentTypeOptional(Long id, String code, String name, boolean state, String description) {
        return Optional.of(createDocumentTypeResponse(id, code, name, state, description));
    }
}
