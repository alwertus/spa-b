package com.tretsoft.spa.repository.doings;

import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.model.doings.DoLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoLabelRepository extends JpaRepository<DoLabel, Long> {

    List<DoLabel> findAllByUser(SpaUser user);

}
