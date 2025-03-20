package com.SintadTest.entity.interfaces;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.entity.models.entity.Entidad;
import com.SintadTest.entity.models.request.EntidadRequest;
import com.SintadTest.entity.models.response.EntidadResponse;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;

public interface EntidadMapperConverter {
    Entidad mapDtoToEntity(EntidadRequest request, DocumentType documentType, TaxpayerType taxpayerType);
    EntidadResponse mapEntityToDto(Entidad entidad);
}
