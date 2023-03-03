package com.tretsoft.spa.service;

import java.util.List;

public interface CurdService<T> {
    List<T> getAll();
    T create(T obj);
    T update(T obj);
    void delete(Long id);
}