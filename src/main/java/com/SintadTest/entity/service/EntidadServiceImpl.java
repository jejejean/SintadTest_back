package com.SintadTest.entity.service;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.repository.DocumentTypeRepository;
import com.SintadTest.entity.interfaces.EntidadMapperConverter;
import com.SintadTest.entity.models.entity.Entidad;
import com.SintadTest.entity.models.request.EntidadRequest;
import com.SintadTest.entity.models.response.EntidadResponse;
import com.SintadTest.entity.repository.EntidadRepository;
import com.SintadTest.exceptions.NotFoundException;
import com.SintadTest.shared.constants.ExceptionMessages;
import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.repository.TaxpayerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadServiceImpl implements CrudInterface<EntidadRequest, EntidadResponse> {

    @Autowired
    private EntidadRepository entidadRepository;

    @Autowired
    private EntidadMapperConverter entidadMapperConverter;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private TaxpayerTypeRepository taxpayerTypeRepository;

    @Override
    public List<EntidadResponse> findAll() {
        if (entidadRepository.findAllEntities().isEmpty()) {
            throw new NotFoundException(ExceptionMessages.ENTITIES_NOT_FOUND);
        }
        return entidadRepository.findAllEntities();
    }

    @Override
    public Optional<EntidadResponse> findById(Long id) {
        Optional<Entidad> entity = entidadRepository.findById(id);
        if (entity.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.ENTITY_WITH_ID_NOT_FOUND, id));
        }
        return Optional.of(entidadMapperConverter.mapEntityToDto(entity.get()));
    }

    @Override
    public EntidadResponse create(EntidadRequest request) {
        DocumentType documentType = documentTypeRepository.findById(request.getDocumentType())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, request.getDocumentType())));

        TaxpayerType taxpayerType = null;
        if (request.getTaxpayerType() != null) {
            taxpayerType = taxpayerTypeRepository.findById(request.getTaxpayerType())
                    .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, request.getTaxpayerType())));
        }

        Entidad entity = entidadMapperConverter.mapDtoToEntity(request, documentType, taxpayerType);


        entidadRepository.save(entity);
        return entidadMapperConverter.mapEntityToDto(entity);
    }

    @Override
    public EntidadResponse update(Long id, EntidadRequest request) {
        Entidad entity = entidadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.ENTITY_WITH_ID_NOT_FOUND, id)));

        DocumentType documentType = documentTypeRepository.findById(request.getDocumentType())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.DOCUMENT_TYPE_WITH_ID_NOT_FOUND, request.getDocumentType())));

        TaxpayerType taxpayerType = null;
        if (request.getTaxpayerType() != null) {
            taxpayerType = taxpayerTypeRepository.findById(request.getTaxpayerType())
                    .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.TAXPAYER_TYPE_WITH_ID_NOT_FOUND, request.getTaxpayerType())));
        }

        entity.setNumDocument(request.getNumDocument());
        entity.setCompanyName(request.getCompanyName());
        entity.setTradeName(request.getTradeName());
        entity.setAddress(request.getAddress());
        entity.setPhone(request.getPhone());
        entity.setState(request.getState());
        entity.setDocumentType(documentType);
        entity.setTaxpayerType(taxpayerType);

        entidadRepository.save(entity);
        return entidadMapperConverter.mapEntityToDto(entity);
    }

    @Override
    public String delete(Long id) {
        Entidad entity = entidadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.ENTITY_WITH_ID_NOT_FOUND, id)));

        entidadRepository.delete(entity);
        return String.format(ExceptionMessages.ENTITY_DELETE, id);
    }
}
