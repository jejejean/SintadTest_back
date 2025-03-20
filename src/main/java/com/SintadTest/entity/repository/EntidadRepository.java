package com.SintadTest.entity.repository;

import com.SintadTest.entity.models.entity.Entidad;
import com.SintadTest.entity.models.response.EntidadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Long> {

    @Query("SELECT new com.SintadTest.entity.models.response.EntidadResponse(e.idEntity, e.numDocument, e.companyName, e.tradeName, e.address, e.phone, e.state, " +
            "new com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse(t.idTaxpayerType, t.name, t.state), " +
            "new com.SintadTest.documentType.models.response.DocumentTypeResponse(d.idDocumentType, d.code, d.name, d.description, d.state)) " +
            "FROM Entidad e " +
            "JOIN e.taxpayerType t " +
            "JOIN e.documentType d " +
            "ORDER BY e.idEntity DESC")
    List<EntidadResponse> findAllEntities();


    @Query("SELECT e.numDocument FROM Entidad e ORDER BY e.numDocument DESC LIMIT 1")
    Optional<String> findLastNumDocument();

}

