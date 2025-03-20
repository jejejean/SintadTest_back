package com.SintadTest.documentType.controller;

import com.SintadTest.documentType.models.interfaces.DocumentTypeInterface;
import com.SintadTest.documentType.models.request.DocumentTypeRequest;
import com.SintadTest.documentType.models.response.DocumentTypeResponse;
import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.shared.interfaces.NumberGeneratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/document-type")
public class DocumentTypeController {

    @Autowired
    private CrudInterface<DocumentTypeRequest, DocumentTypeResponse> crudInterface;

    @Autowired
    private DocumentTypeInterface documentTypeInterface;

    @Autowired
    @Qualifier("documentTypeServiceImpl")
    private NumberGeneratorService numberGeneratorService;

    @GetMapping("/all-document-types")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(crudInterface.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('' + @rolesConfig.getAdminRole())")
    @PostMapping("/new-document-type")
    public ResponseEntity<Object> create(@Valid @RequestBody DocumentTypeRequest documentTypeRequest) {
        return new ResponseEntity<>(crudInterface.create(documentTypeRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('' + @rolesConfig.getAdminRole())")
    @PutMapping("/update-document-type/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody DocumentTypeRequest documentTypeRequest) {
        return new ResponseEntity<>(crudInterface.update(id, documentTypeRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('' + @rolesConfig.getAdminRole())")
    @DeleteMapping("/delete-document-type/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.delete(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all-document-by-state")
    public ResponseEntity<Object> findAllByState() {
        return new ResponseEntity<>(documentTypeInterface.findAllByStateTrue(), HttpStatus.OK);
    }

    @GetMapping("/next-number")
    public ResponseEntity<String> getNextSaleNumber() {
        return new ResponseEntity<>(numberGeneratorService.getNextNumber(),HttpStatus.OK);
    }
}
