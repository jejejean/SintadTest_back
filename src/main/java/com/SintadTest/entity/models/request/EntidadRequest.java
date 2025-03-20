package com.SintadTest.entity.models.request;

import com.SintadTest.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntidadRequest implements IHandleRequest {
    private Long idEntity;
    private String numDocument;
    private String companyName;
    private String tradeName;
    private String address;
    private String phone;
    private Boolean state;
    private Long taxpayerType;
    private Long documentType;
}
