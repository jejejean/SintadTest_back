package com.SintadTest.taxpayerType.service;

import com.SintadTest.exceptions.BadRequestException;
import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.shared.constants.ExceptionMessages;
import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.shared.interfaces.MapperConverter;
import com.SintadTest.taxpayerType.interfaces.TaxpayerTypeInterface;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import com.SintadTest.taxpayerType.repository.TaxpayerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxpayerTypeServiceImpl implements CrudInterface<TaxpayerTypeRequest, TaxpayerTypeResponse>, TaxpayerTypeInterface {

    @Autowired
    private TaxpayerTypeRepository taxpayerTypeRepository;

    @Autowired
    private MapperConverter<TaxpayerTypeRequest, TaxpayerTypeResponse, TaxpayerType> mapperConverter;

    @Override
    public List<TaxpayerTypeResponse> findAll() {
        List<TaxpayerTypeResponse> taxpayerList = taxpayerTypeRepository.findAllTaxpayers();
        if (taxpayerList.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.TAXPAYERS_TYPES_NOT_FOUND);
        }
        return taxpayerList;
    }

    @Override
    public Optional<TaxpayerTypeResponse> findById(Long id) {
        Optional<TaxpayerType> taxpayerType = taxpayerTypeRepository.findById(id);
        if (taxpayerType.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id));
        }
        return Optional.of(mapperConverter.mapEntityToDto(taxpayerType.get()));
    }

    @Override
    public TaxpayerTypeResponse create(TaxpayerTypeRequest request) {
        if (taxpayerTypeRepository.existsByName(request.getName())) {
            throw new BadRequestException(String.format(ExceptionMessages.TAXPAYER_TYPE_ALREADY_EXISTS, request.getName()));
        }
        TaxpayerType taxpayerType = mapperConverter.mapDtoToEntity(request);
        taxpayerTypeRepository.save(taxpayerType);
        return mapperConverter.mapEntityToDto(taxpayerType);
    }

    @Override
    public TaxpayerTypeResponse update(Long id, TaxpayerTypeRequest request) {
        TaxpayerType taxpayerType = taxpayerTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id)));

        taxpayerType.setName(request.getName());
        taxpayerType.setState(request.getState());

        taxpayerTypeRepository.save(taxpayerType);
        return mapperConverter.mapEntityToDto(taxpayerType);
    }

    @Override
    public String delete(Long id) {
        TaxpayerType taxpayerType = taxpayerTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, id)));

        taxpayerTypeRepository.delete(taxpayerType);
        return String.format(ExceptionMessages.TAXPAYER_TYPE_DELETE, id);
    }

    @Override
    public List<TaxpayerTypeInfoResponse> findAllByStateTrue() {
        List<TaxpayerTypeInfoResponse> taxpayerTypes = taxpayerTypeRepository.findAllByStateTrue();
        if (taxpayerTypes.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.TAXPAYERS_TYPES_WITH_STATUS_TRUE_NOT_FOUND);
        }
        return taxpayerTypes;
    }
}
