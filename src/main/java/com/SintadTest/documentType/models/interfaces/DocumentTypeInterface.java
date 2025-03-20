package com.SintadTest.documentType.models.interfaces;

import com.SintadTest.documentType.models.response.DocumentTypeInfoResponse;

import java.util.List;

public interface DocumentTypeInterface {
    List<DocumentTypeInfoResponse> findAllByStateTrue();
}
