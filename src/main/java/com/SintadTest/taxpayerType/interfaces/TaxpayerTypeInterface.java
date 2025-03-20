package com.SintadTest.taxpayerType.interfaces;

import com.SintadTest.taxpayerType.models.response.TaxpayerTypeInfoResponse;

import java.util.List;

public interface TaxpayerTypeInterface {
    List<TaxpayerTypeInfoResponse> findAllByStateTrue();
}
