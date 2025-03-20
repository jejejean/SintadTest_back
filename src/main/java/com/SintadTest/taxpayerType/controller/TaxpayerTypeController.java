package com.SintadTest.taxpayerType.controller;

import com.SintadTest.shared.interfaces.CrudInterface;
import com.SintadTest.taxpayerType.interfaces.TaxpayerTypeInterface;
import com.SintadTest.taxpayerType.models.request.TaxpayerTypeRequest;
import com.SintadTest.taxpayerType.models.response.TaxpayerTypeResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/taxpayer-type")
public class TaxpayerTypeController {

    @Autowired
    private CrudInterface<TaxpayerTypeRequest, TaxpayerTypeResponse> crudInterface;

    @Autowired
    private TaxpayerTypeInterface taxpayerTypeInterface;

    @GetMapping("/all-taxpayer-types")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(crudInterface.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/new-taxpayer-type")
    public ResponseEntity<Object> create(@Valid @RequestBody TaxpayerTypeRequest taxpayerTypeRequest) {
        return new ResponseEntity<>(crudInterface.create(taxpayerTypeRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/update-taxpayer-type/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody TaxpayerTypeRequest taxpayerTypeRequest) {
        return new ResponseEntity<>(crudInterface.update(id, taxpayerTypeRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/delete-taxpayer-type/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.delete(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all-taxpayer-by-state")
    public ResponseEntity<Object> findAllByState() {
        return new ResponseEntity<>(taxpayerTypeInterface.findAllByStateTrue(), HttpStatus.OK);
    }
}
