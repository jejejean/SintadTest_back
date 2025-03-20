package com.SintadTest.taxpayerType.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxpayerTypeInfoResponse {
    private Long idTaxpayerType;
    private String name;
}
