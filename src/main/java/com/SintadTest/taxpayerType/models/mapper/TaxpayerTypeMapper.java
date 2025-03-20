package com.SintadTest.taxpayerType.models.mapper;

import com.SintadTest.shared.interfaces.MapperConverter;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxpayerTypeMapper implements MapperConverter<TaxpayerTypeRequest, TaxpayerTypeResponse, TaxpayerType> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaxpayerTypeResponse mapEntityToDto(TaxpayerType entity) {
        return modelMapper.map(entity, TaxpayerTypeResponse.class);
    }

    @Override
    public TaxpayerType mapDtoToEntity(TaxpayerTypeRequest request) {
        TaxpayerType taxpayerType = modelMapper.map(request, TaxpayerType.class);
        taxpayerType.setIdTaxpayerType(null);
        return taxpayerType;
    }
}
