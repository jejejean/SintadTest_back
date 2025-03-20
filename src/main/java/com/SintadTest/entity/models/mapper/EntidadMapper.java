package com.SintadTest.entity.models.mapper;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.entity.interfaces.EntidadMapperConverter;
import com.SintadTest.entity.models.entity.Entidad;
import com.SintadTest.entity.models.request.EntidadRequest;
import com.SintadTest.entity.models.response.EntidadResponse;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntidadMapper implements EntidadMapperConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Entidad mapDtoToEntity(EntidadRequest request, DocumentType documentType, TaxpayerType taxpayerType) {
        Entidad entidad = modelMapper.map(request, Entidad.class);
        entidad.setIdEntity(null);
        entidad.setDocumentType(documentType);
        entidad.setTaxpayerType(taxpayerType);
        return entidad;
    }

    @Override
    public EntidadResponse mapEntityToDto(Entidad entidad) {
        EntidadResponse entidadResponse = modelMapper.map(entidad, EntidadResponse.class);
        return entidadResponse;
    }

}
