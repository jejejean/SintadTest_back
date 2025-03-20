package com.SintadTest.documentType.models.response;

import com.SintadTest.shared.interfaces.IHandleResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeResponse implements IHandleResponse {
    private Long idDocumentType;
    private String code;
    private String name;
    private String description;
    private Boolean state;
}
