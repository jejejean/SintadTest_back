package com.SintadTest.documentType.models.request;

import com.SintadTest.shared.interfaces.IHandleRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeRequest implements IHandleRequest {
    private Long idDocumentType;
    private String code;
    private String name;
    private String description;
    private Boolean state;
}
