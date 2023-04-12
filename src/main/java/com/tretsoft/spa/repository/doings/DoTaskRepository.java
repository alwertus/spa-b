package com.tretsoft.spa.repository.doings;

import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.model.doings.DoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoTaskRepository extends JpaRepository<DoTask, Long> {

    List<DoTask> findAllByUser(SpaUser user);

}
