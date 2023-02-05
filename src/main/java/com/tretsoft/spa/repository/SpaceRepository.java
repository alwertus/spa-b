package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.info.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    List<Space> findAllByIsPrivateIsFalseOrIsPrivateIsNull();
    List<Space> findAllByIsPrivateIsTrueAndCreatedByEquals(SpaUser createdBy);
}
