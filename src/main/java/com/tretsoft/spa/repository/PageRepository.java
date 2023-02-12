package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.info.Page;
import com.tretsoft.spa.model.info.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {

    List<Page> findAllBySpace(Space space);

    List<Page> findAllByParent(Page parent);

}
