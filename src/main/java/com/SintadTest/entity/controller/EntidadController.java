package com.SintadTest.entity.controller;

import com.SintadTest.entity.models.request.EntidadRequest;
import com.SintadTest.entity.models.response.EntidadResponse;
import com.SintadTest.shared.interfaces.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/entity")
public class EntidadController {

    @Autowired
    private CrudInterface<EntidadRequest, EntidadResponse> crudInterface;

    @GetMapping("/all-entities")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(crudInterface.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/new-entity")
    public ResponseEntity<Object> create(@RequestBody EntidadRequest entidadRequest) {
        return new ResponseEntity<>(crudInterface.create(entidadRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PutMapping("/update-entity/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody EntidadRequest entidadRequest) {
        return new ResponseEntity<>(crudInterface.update(id, entidadRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/delete-entity/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return new ResponseEntity<>(crudInterface.delete(id),HttpStatus.NO_CONTENT);
    }

}
