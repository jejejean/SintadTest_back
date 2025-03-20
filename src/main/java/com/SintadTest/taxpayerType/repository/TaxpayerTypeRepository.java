package com.SintadTest.taxpayerType.repository;

import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxpayerTypeRepository extends JpaRepository<TaxpayerType, Long> {
    @Query("SELECT COUNT(d) > 0 FROM TaxpayerType d WHERE d.name = :name")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT new com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse(ta.idTaxpayerType, ta.name) " +
            "FROM TaxpayerType ta WHERE ta.state = true " +
            "ORDER BY ta.name ASC")
    List<TaxpayerTypeInfoResponse> findAllByStateTrue();

    @Query("SELECT new com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse(ta.idTaxpayerType, ta.name, ta.state) " +
            "FROM TaxpayerType ta " +
            "ORDER BY ta.idTaxpayerType DESC")
    List<TaxpayerTypeResponse> findAllTaxpayers();
}
