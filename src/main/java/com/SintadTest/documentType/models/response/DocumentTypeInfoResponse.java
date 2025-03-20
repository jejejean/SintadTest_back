package com.SintadTest.documentType.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentTypeInfoResponse {
    private Long idDocumentType;
    private String name;
}

