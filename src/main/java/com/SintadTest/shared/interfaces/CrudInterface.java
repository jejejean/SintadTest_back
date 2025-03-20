package com.SintadTest.shared.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T extends IHandleRequest, G extends IHandleResponse> {
    List<G> findAll();
    Optional<G> findById(Long id);
    G create(T request);
    G update(Long id, T request);
    String delete(Long id);
}
