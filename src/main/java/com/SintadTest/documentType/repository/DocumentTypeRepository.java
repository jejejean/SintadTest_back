package com.SintadTest.documentType.repository;

import com.SintadTest.documentType.models.entity.DocumentType;
import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    @Query("SELECT COUNT(d) > 0 FROM DocumentType d WHERE d.code = :code")
    boolean existsByCode(@Param("code") String code);

    @Query("SELECT new com.SintadTest.documentType.models.response.DocumentTypeInfoResponse(ta.idDocumentType, ta.name) " +
            "FROM DocumentType ta WHERE ta.state = true " +
            "ORDER BY ta.name ASC")
    List<DocumentTypeInfoResponse> findAllByStateTrue();

    @Query("SELECT new com.SintadTest.documentType.models.response.DocumentTypeResponse(d.idDocumentType, d.code,d.name,d.description, d.state) " +
            "FROM DocumentType d " +
            "ORDER BY d.idDocumentType DESC")
    List<DocumentTypeResponse> findAllDocuments();

    @Query("SELECT d.code FROM DocumentType d ORDER BY d.code DESC LIMIT 1")
    Optional<String> findLastNumDocument();
}
