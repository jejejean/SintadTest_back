package com.SintadTest.service;

import com.SintadTest.dataProvider.DocumentDataProvider;
import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.documentType.repository.DocumentTypeRepository;
import com.SintadTest.documentType.service.DocumentTypeServiceImpl;
import com.SintadTest.exceptions.BadRequestException;
import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.shared.constants.ExceptionMessages;
import com.SintadTest.shared.interfaces.MapperConverter;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class DocumentTypeServiveImplTest {

    @Mock
    private DocumentTypeRepository documentTypeRepository;

    @InjectMocks
    private DocumentTypeServiceImpl documentTypeService;

    @Mock
    private MapperConverter<DocumentTypeRequest, DocumentTypeResponse, DocumentType> mapperConverter;


    @Test
    void createDocumentSuccess() {
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest();
        DocumentType entity = DocumentDataProvider.createDocumentTypeEntity();
        DocumentTypeResponse response = DocumentDataProvider.createDocumentTypeResponse();

        when(mapperConverter.mapDtoToEntity(request)).thenReturn(entity);
        when(documentTypeRepository.save(entity)).thenReturn(entity);
        when(mapperConverter.mapEntityToDto(entity)).thenReturn(response);

        DocumentTypeResponse documentTypeResponse = documentTypeService.create(request);

        assertNotNull(documentTypeResponse);
        assertEquals(response.getIdDocumentType(), documentTypeResponse.getIdDocumentType());
        assertEquals(response.getCode(), documentTypeResponse.getCode());
        assertEquals(response.getName(), documentTypeResponse.getName());
        assertEquals(response.getDescription(), documentTypeResponse.getDescription());
        assertEquals(response.getState(), documentTypeResponse.getState());

        verify(mapperConverter, times(1)).mapDtoToEntity(request);
        verify(documentTypeRepository, times(1)).save(entity);
        verify(mapperConverter, times(1)).mapEntityToDto(entity);
    }

    @Test
    void createDocumentWhenCodeAlreadyExists() {
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest();

        when(documentTypeRepository.existsByCode(request.getCode())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                documentTypeService.create(request));

        assertEquals(String.format(ExceptionMessages.DOCUMENT_TYPE_ALREADY_EXISTS, request.getCode()), exception.getMessage());

        verify(documentTypeRepository, times(1)).existsByCode(request.getCode());
        verify(mapperConverter, never()).mapDtoToEntity(any());
        verify(documentTypeRepository, never()).save(any());
    }

    @Test
    void findAllDocumentWhenExist() {
        List<DocumentTypeResponse> expectedList = List.of(DocumentDataProvider.createDocumentTypeResponse());
        when(documentTypeRepository.findAllDocuments()).thenReturn(expectedList);

        List<DocumentTypeResponse> actualList = documentTypeService.findAll();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getIdDocumentType(), actualList.get(0).getIdDocumentType());

        verify(documentTypeRepository, times(1)).findAllDocuments();
    }

    @Test
    void findAllDocumentWhenNotExist() {
        when(documentTypeRepository.findAllDocuments()).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                documentTypeService.findAll());

        assertEquals(ExceptionMessages.DOCUMENTS_TYPES_NOT_FOUND, exception.getMessage());
        verify(documentTypeRepository, times(1)).findAllDocuments();
    }

    @Test
    void findByIdDocuemntWhenExist() {
        DocumentType entity = DocumentDataProvider.createDocumentTypeEntity();
        DocumentTypeResponse expected = DocumentDataProvider.createDocumentTypeResponse();

        when(documentTypeRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(mapperConverter.mapEntityToDto(entity)).thenReturn(expected);

        Optional<DocumentTypeResponse> actualResponse = documentTypeService.findById(anyLong());

        assertTrue(actualResponse.isPresent());
        assertEquals(expected.getIdDocumentType(), actualResponse.get().getIdDocumentType());
        assertEquals(expected.getCode(), actualResponse.get().getCode());
        assertEquals(expected.getName(), actualResponse.get().getName());
        assertEquals(expected.getDescription(), actualResponse.get().getDescription());
        assertEquals(expected.getState(), actualResponse.get().getState());

        verify(documentTypeRepository, times(1)).findById(anyLong());
        verify(mapperConverter, times(1)).mapEntityToDto(entity);
    }

    @Test
    void findByIdDocumentWhenNotExist() {
        Long id = 99L;
        when(documentTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                documentTypeService.findById(id));

        assertEquals(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(documentTypeRepository, times(1)).findById(id);
        verifyNoInteractions(mapperConverter);
    }

    @Test
    void updateDocumentSuccess() {
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest();
        DocumentType entity = DocumentDataProvider.createDocumentTypeEntity();
        DocumentTypeResponse response = DocumentDataProvider.createDocumentTypeResponse();

        when(documentTypeRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(documentTypeRepository.save(any(DocumentType.class))).thenReturn(entity);
        when(mapperConverter.mapEntityToDto(entity)).thenReturn(response);

        DocumentTypeResponse documentTypeResponse = documentTypeService.update(anyLong(), request);

        assertNotNull(documentTypeResponse);
        assertEquals(response.getIdDocumentType(), documentTypeResponse.getIdDocumentType());
        assertEquals(response.getCode(), documentTypeResponse.getCode());
        assertEquals(response.getName(), documentTypeResponse.getName());
        assertEquals(response.getDescription(), documentTypeResponse.getDescription());
        assertEquals(response.getState(), documentTypeResponse.getState());

        verify(documentTypeRepository, times(1)).findById(anyLong());
        verify(mapperConverter, times(1)).mapEntityToDto(entity);
        verify(documentTypeRepository, times(1)).save(entity);
    }

    @Test
    void updateDocumentWhenNotExist() {
        Long id = 99L;
        DocumentTypeRequest request = DocumentDataProvider.createDocumentTypeRequest();

        when(documentTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                documentTypeService.update(id, request));

        assertEquals(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(documentTypeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(documentTypeRepository);
        verifyNoInteractions(mapperConverter);
    }

    @Test
    void deleteDocumentSuccess() {
        Long id = 1L;
        DocumentType entity = DocumentDataProvider.createDocumentTypeEntity();
        String expectedMessage = String.format(ExceptionMessages.DOCUMENT_TYPE_DELETE, id);

        when(documentTypeRepository.findById(id)).thenReturn(Optional.of(entity));

        String message = documentTypeService.delete(id);

        assertNotNull(message);
        assertEquals(expectedMessage, message);

        verify(documentTypeRepository, times(1)).findById(id);
        verify(documentTypeRepository, times(1)).delete(entity);
    }

    @Test
    void findAllDocumentsWhenNotExist() {
        Long id = 99L;

        when(documentTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception= assertThrows(NotFoundException.class, () ->
                documentTypeService.delete(id));

        assertEquals(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(documentTypeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(documentTypeRepository);
    }

    @Test
    void findAllByStateTrueWhenExist() {
        List<DocumentTypeInfoResponse> expectedList = List.of(DocumentDataProvider.crateDocumentTypeInfoResponse());

        when(documentTypeRepository.findAllByStateTrue()).thenReturn(expectedList);

        List<DocumentTypeInfoResponse> actualList = documentTypeService.findAllByStateTrue();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getIdDocumentType(), actualList.get(0).getIdDocumentType());
        assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());

        verify(documentTypeRepository, times(1)).findAllByStateTrue();
    }

    @Test
    void findAllByStateFalseWhenExist() {
        when(documentTypeRepository.findAllByStateTrue()).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                documentTypeService.findAllByStateTrue());

        assertEquals(ExceptionMessages.DOCUMENTS_TYPES_WITH_STATUS_TRUE_NOT_FOUND, exception.getMessage());
        verify(documentTypeRepository, times(1)).findAllByStateTrue();
    }


    @Test
    void getNextNumber_ShouldReturnGeneratedNumber() {
        when(documentTypeRepository.findLastNumDocument()).thenReturn(Optional.of("0002-25"));

        String nextNumber = documentTypeService.getNextNumber();

        assertNotNull(nextNumber);
        assertEquals("0003-25", nextNumber);

        verify(documentTypeRepository, times(1)).findLastNumDocument();
    }
}

