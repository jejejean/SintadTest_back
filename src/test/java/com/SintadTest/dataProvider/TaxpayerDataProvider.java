package com.SintadTest.dataProvider;

import com.SintadTest.taxpayerType.models.entity.TaxpayerType;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TaxpayerDataProvider {

    public static TaxpayerTypeRequest createTaxpayerTypeRequest() {
        return TaxpayerTypeRequest.builder()
                .name("Persona Jurídica")
                .state(true)
                .build();
    }

    public static TaxpayerType createTaxpayerTypeEntity() {
        return TaxpayerType.builder()
                .idTaxpayerType(1L)
                .name("Persona Jurídica")
                .state(true)
                .build();
    }

    public static TaxpayerTypeResponse createTaxpayerTypeResponse() {
        return TaxpayerTypeResponse.builder()
                .idTaxpayerType(1L)
                .name("Persona Jurídica")
                .state(true)
                .build();
    }

    public static TaxpayerTypeInfoResponse createTaxpayerTypeInfoResponse() {
        return TaxpayerTypeInfoResponse.builder()
                .idTaxpayerType(2L)
                .name("Persona Natural")
                .build();
    }
    // controller
    public static TaxpayerTypeRequest createTaxpayerTypeRequest(Long id, String name, boolean state) {
        return TaxpayerTypeRequest.builder()
                .idTaxpayerType(id)
                .name(name)
                .state(state)
                .build();
    }

    public static TaxpayerTypeResponse createTaxpayerTypeResponse(Long id, String name, boolean state) {
        return TaxpayerTypeResponse.builder()
                .idTaxpayerType(id)
                .name(name)
                .state(state)
                .build();
    }

    public static List<TaxpayerTypeResponse> createTaxpayerTypeResponseList() {
        return Arrays.asList(
                createTaxpayerTypeResponse(1L, "Persona Jurídica", true),
                createTaxpayerTypeResponse(2L, "Persona Natural", true)
        );
    }

    public static List<TaxpayerTypeInfoResponse> createTaxpayerTypeInfoResponseList() {
        return Arrays.asList(
                new TaxpayerTypeInfoResponse(1L, "Persona Jurídica"),
                new TaxpayerTypeInfoResponse(2L, "Persona Natural")
        );
    }

    public static Optional<TaxpayerTypeResponse> createOptionalTaxpayerTypeResponse(Long id, String name, boolean state) {
        return Optional.of(createTaxpayerTypeResponse(id, name, state));
    }
}
