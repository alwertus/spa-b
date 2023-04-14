package com.tretsoft.spa.service;

import java.util.List;

public interface CrudService<POJO> {
    List<POJO> getAll();
    POJO getById(Long id);
    POJO create(POJO obj);
    POJO update(POJO obj);
    void delete(Long id);
}