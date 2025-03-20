package com.SintadTest.entity.models.response;

import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.shared.interfaces.IHandleResponse;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadResponse implements IHandleResponse {
    private Long idEntity;
    private String numDocument;
    private String companyName;
    private String tradeName;
    private String address;
    private String phone;
    private Boolean state;
    private TaxpayerTypeResponse taxpayerType;
    private DocumentTypeResponse documentType;
}
