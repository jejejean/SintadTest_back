package com.SintadTest.documentType.service;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.models.interfaces.DocumentTypeInterface;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.documentType.repository.DocumentTypeRepository;
import com.SintadTest.exceptions.BadRequestException;
import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.shared.constants.ExceptionMessages;
import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.shared.interfaces.LastNumberProvider;
import com.SintadTest.shared.interfaces.MapperConverter;
import com.SintadTest.shared.interfaces.NumberGeneratorService;
import com.SintadTest.shared.utils.GenerateNummber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeServiceImpl implements CrudInterface<DocumentTypeRequest, DocumentTypeResponse>, DocumentTypeInterface, NumberGeneratorService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private MapperConverter<DocumentTypeRequest, DocumentTypeResponse, DocumentType> mapperConverter;


    @Override
    public List<DocumentTypeResponse> findAll() {
        List<DocumentTypeResponse> documentTypeResponse =  documentTypeRepository.findAllDocuments();
        if (documentTypeResponse.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.DOCUMENTS_TYPES_NOT_FOUND);
        }
        return documentTypeResponse;
    }

    @Override
    public Optional<DocumentTypeResponse> findById(Long id) {
        Optional<DocumentType> documentType = documentTypeRepository.findById(id);
        if (documentType.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id));
        }
        return Optional.of(mapperConverter.mapEntityToDto(documentType.get()));
    }

    @Override
    public DocumentTypeResponse create(DocumentTypeRequest request) {
        if (documentTypeRepository.existsByCode(request.getCode())) {
            throw new BadRequestException(String.format(ExceptionMessages.DOCUMENT_TYPE_ALREADY_EXISTS,request.getCode()));
        }
        DocumentType documentType = mapperConverter.mapDtoToEntity(request);
        documentTypeRepository.save(documentType);
        return mapperConverter.mapEntityToDto(documentType);
    }

    @Override
    public DocumentTypeResponse update(Long id, DocumentTypeRequest request) {
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id)));

        documentType.setCode(request.getCode());
        documentType.setName(request.getName());
        documentType.setDescription(request.getDescription());
        documentType.setState(request.getState());

        documentTypeRepository.save(documentType);
        return mapperConverter.mapEntityToDto(documentType);
    }

    @Override
    public String delete(Long id) {
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id)));

        documentTypeRepository.delete(documentType);
        return String.format(ExceptionMessages.DOCUMENT_TYPE_DELETE, id);
    }

    @Override
    public List<DocumentTypeInfoResponse> findAllByStateTrue() {
        List<DocumentTypeInfoResponse> documentTypes = documentTypeRepository.findAllByStateTrue();
        if (documentTypes.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.DOCUMENTS_TYPES_WITH_STATUS_TRUE_NOT_FOUND);
        }
        return documentTypes;
    }

    @Override
    public String getNextNumber() {
        LastNumberProvider lastNumberProvider = documentTypeRepository::findLastNumDocument;
        return GenerateNummber.generateNextNumber(lastNumberProvider);
    }
}
