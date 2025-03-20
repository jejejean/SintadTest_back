package com.SintadTest.taxpayerType.models.request;

import com.SintadTest.shared.interfaces.IHandleRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxpayerTypeRequest implements IHandleRequest {
    private Long idTaxpayerType;
    private String name;
    private Boolean state;
}
