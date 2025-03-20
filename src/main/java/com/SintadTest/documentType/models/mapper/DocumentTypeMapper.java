package com.SintadTest.documentType.models.mapper;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.shared.interfaces.MapperConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentTypeMapper implements MapperConverter<DocumentTypeRequest, DocumentTypeResponse, DocumentType> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DocumentTypeResponse mapEntityToDto(DocumentType entity) {
        return modelMapper.map(entity, DocumentTypeResponse.class);
    }

    @Override
    public DocumentType mapDtoToEntity(DocumentTypeRequest request) {
        DocumentType documentType = modelMapper.map(request, DocumentType.class);
        documentType.setIdDocumentType(null);
        return documentType;
    }
}
