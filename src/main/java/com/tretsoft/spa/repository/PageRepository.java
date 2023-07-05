package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.info.Page;
import com.tretsoft.spa.model.info.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

    @Query("select p from Page p where p.space = :space")
    List<Page> findAllBySpace(Space space);

    List<Page> findAllByParent(Page parent);

}
