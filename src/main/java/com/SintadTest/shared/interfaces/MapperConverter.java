package com.SintadTest.shared.interfaces;


import java.util.List;

public interface MapperConverter<R extends IHandleRequest, G extends IHandleResponse, T extends IHandleEntity> {
    G mapEntityToDto(T entity);
    T mapDtoToEntity(R request);
}
