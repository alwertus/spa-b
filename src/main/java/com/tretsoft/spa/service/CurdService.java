package com.tretsoft.spa.service;

import java.util.List;

public interface CurdService<POJO> {
    List<POJO> getAll();
    POJO create(POJO obj);
    POJO update(POJO obj);
    void delete(Long id);
}