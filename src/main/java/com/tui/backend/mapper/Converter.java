package com.tui.backend.mapper;

public interface Converter<T, R> {

    T toDto(R entity);

}