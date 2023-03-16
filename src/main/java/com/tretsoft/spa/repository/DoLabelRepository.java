package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.doings.DoLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoLabelRepository extends JpaRepository<DoLabel, Long> {

    List<DoLabel> findAllByUser(SpaUser user);

}
