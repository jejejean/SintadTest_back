package com.SintadTest.taxpayerType.models.response;

import com.SintadTest.shared.interfaces.IHandleResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxpayerTypeResponse implements IHandleResponse {
    private Long idTaxpayerType;
    private String name;
    private Boolean state;
}
