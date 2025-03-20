package com.SintadTest.service;

import com.SintadTest.dataProvider.TaxpayerDataProvider;
import com.SintadTest.exceptions.BadRequestException;
import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.shared.constants.ExceptionMessages;
import com.SintadTest.shared.interfaces.MapperConverter;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import com.SintadTest.taxpayerType.repository.TaxpayerTypeRepository;
import com.SintadTest.taxpayerType.service.TaxpayerTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaxpayerTypeServiceImplTest {

    @Mock
    private TaxpayerTypeRepository taxpayerTypeRepository;

    @InjectMocks
    private TaxpayerTypeServiceImpl taxpayerTypeService;

    @Mock
    private MapperConverter<TaxpayerTypeRequest, TaxpayerTypeResponse, TaxpayerType> mapperConverter;

    @Test
    void createTaxpayerSuccsess() {
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest();
        TaxpayerType entity = TaxpayerDataProvider.createTaxpayerTypeEntity();
        TaxpayerTypeResponse response = TaxpayerDataProvider.createTaxpayerTypeResponse();

        when(mapperConverter.mapDtoToEntity(request)).thenReturn(entity);
        when(taxpayerTypeRepository.save(entity)).thenReturn(entity);
        when(mapperConverter.mapEntityToDto(entity)).thenReturn(response);

        TaxpayerTypeResponse actualResponse = taxpayerTypeService.create(request);

        assertNotNull(actualResponse);
        assertEquals(response.getIdTaxpayerType(), actualResponse.getIdTaxpayerType());
        assertEquals(response.getName(), actualResponse.getName());
        assertEquals(response.getState(), actualResponse.getState());

        verify(mapperConverter, times(1)).mapDtoToEntity(request);
        verify(taxpayerTypeRepository, times(1)).save(entity);
        verify(mapperConverter, times(1)).mapEntityToDto(entity);
    }

    @Test
    void createTaxpayerWhenNameAlreadyExists() {
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest();

        when(taxpayerTypeRepository.existsByName(request.getName())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                taxpayerTypeService.create(request));

        assertEquals(String.format(ExceptionMessages.TAXPAYER_TYPE_ALREADY_EXISTS, request.getName()), exception.getMessage());

        verify(taxpayerTypeRepository, times(1)).existsByName(request.getName());
        verify(mapperConverter, never()).mapDtoToEntity(any());
        verify(taxpayerTypeRepository, never()).save(any());
    }

    @Test
    void findAllTaxpayerWhenExist() {
        List<TaxpayerTypeResponse> expectedList = List.of(TaxpayerDataProvider.createTaxpayerTypeResponse());
        when(taxpayerTypeRepository.findAllTaxpayers()).thenReturn(expectedList);

        List<TaxpayerTypeResponse> actualList = taxpayerTypeService.findAll();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getIdTaxpayerType(), actualList.get(0).getIdTaxpayerType());

        verify(taxpayerTypeRepository, times(1)).findAllTaxpayers();
    }

    @Test
    void findAllTaxpayerEmpty() {
        when(taxpayerTypeRepository.findAllTaxpayers()).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                taxpayerTypeService.findAll());

        assertEquals(ExceptionMessages.TAXPAYERS_TYPES_NOT_FOUND, exception.getMessage());
        verify(taxpayerTypeRepository, times(1)).findAllTaxpayers();
    }

    @Test
    void findByIdTaxpayerWhenExist() {

        TaxpayerType taxpayerType = TaxpayerDataProvider.createTaxpayerTypeEntity();
        TaxpayerTypeResponse expectedResponse = TaxpayerDataProvider.createTaxpayerTypeResponse();

        when(taxpayerTypeRepository.findById(anyLong())).thenReturn(Optional.of(taxpayerType));
        when(mapperConverter.mapEntityToDto(taxpayerType)).thenReturn(expectedResponse);

        Optional<TaxpayerTypeResponse> actualResponse = taxpayerTypeService.findById(anyLong());

        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse.getIdTaxpayerType(), actualResponse.get().getIdTaxpayerType());
        assertEquals(expectedResponse.getName(), actualResponse.get().getName());
        assertEquals(expectedResponse.getState(), actualResponse.get().getState());

        verify(taxpayerTypeRepository, times(1)).findById(anyLong());
        verify(mapperConverter, times(1)).mapEntityToDto(taxpayerType);
    }

    @Test
    void findByIdTaxpayerEmpty() {
        Long id = 99L;
        when(taxpayerTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            taxpayerTypeService.findById(id);
        });

        assertEquals(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(taxpayerTypeRepository, times(1)).findById(id);
        verifyNoInteractions(mapperConverter);
    }

    @Test
    void updateTaxpayerWhenIdExits() {
        TaxpayerType existingEntity = TaxpayerDataProvider.createTaxpayerTypeEntity();
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest();
        TaxpayerTypeResponse expectedResponse = TaxpayerDataProvider.createTaxpayerTypeResponse();

        when(taxpayerTypeRepository.findById(anyLong())).thenReturn(Optional.of(existingEntity));
        when(taxpayerTypeRepository.save(any(TaxpayerType.class))).thenReturn(existingEntity);
        when(mapperConverter.mapEntityToDto(existingEntity)).thenReturn(expectedResponse);

        TaxpayerTypeResponse actualResponse = taxpayerTypeService.update(anyLong(), request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getIdTaxpayerType(), actualResponse.getIdTaxpayerType());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getState(), actualResponse.getState());

        verify(taxpayerTypeRepository, times(1)).findById(anyLong());
        verify(taxpayerTypeRepository, times(1)).save(existingEntity);
        verify(mapperConverter, times(1)).mapEntityToDto(existingEntity);
    }

    @Test
    void updateTaxpayerWhenIdNotExist() {
        Long id = 99L;
        TaxpayerTypeRequest request = TaxpayerDataProvider.createTaxpayerTypeRequest();

        when(taxpayerTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            taxpayerTypeService.update(id, request);
        });

        assertEquals(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(taxpayerTypeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taxpayerTypeRepository);
        verifyNoInteractions(mapperConverter);
    }

    @Test
    void deleteTaxpayerWhenIdExists() {
        Long id = 1L;
        TaxpayerType existingEntity = TaxpayerDataProvider.createTaxpayerTypeEntity();
        String expectedMessage = String.format(ExceptionMessages.TAXPAYER_TYPE_DELETE, id);

        when(taxpayerTypeRepository.findById(id)).thenReturn(Optional.of(existingEntity));

        String actualMessage = taxpayerTypeService.delete(id);

        assertNotNull(actualMessage);
        assertEquals(expectedMessage, actualMessage);

        verify(taxpayerTypeRepository, times(1)).findById(id);
        verify(taxpayerTypeRepository, times(1)).delete(existingEntity);
    }

    @Test
    void deleteTaxpayerWhenIdNotExist() {
        Long id = 99L;

        when(taxpayerTypeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                taxpayerTypeService.delete(id));

        assertEquals(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id), exception.getMessage());

        verify(taxpayerTypeRepository, times(1)).findById(id);
        verifyNoMoreInteractions(taxpayerTypeRepository);
    }


    @Test
    void findAllByStateTrueWhenExist() {
        List<TaxpayerTypeInfoResponse> expectedList = List.of(TaxpayerDataProvider.createTaxpayerTypeInfoResponse());

        when(taxpayerTypeRepository.findAllByStateTrue()).thenReturn(expectedList);

        List<TaxpayerTypeInfoResponse> actualList = taxpayerTypeService.findAllByStateTrue();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getIdTaxpayerType(), actualList.get(0).getIdTaxpayerType());
        assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());

        verify(taxpayerTypeRepository, times(1)).findAllByStateTrue();
    }

    @Test
    void findAllByStateTrueWhenNotExist() {
        when(taxpayerTypeRepository.findAllByStateTrue()).thenReturn(List.of());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                taxpayerTypeService.findAllByStateTrue());

        assertEquals(ExceptionMessages.TAXPAYERS_TYPES_WITH_STATUS_TRUE_NOT_FOUND, exception.getMessage());

        verify(taxpayerTypeRepository, times(1)).findAllByStateTrue();
    }

}
