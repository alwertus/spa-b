package com.tretsoft.spa.repository;

import com.tretsoft.spa.model.SpaUser;
import com.tretsoft.spa.model.doings.DoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface DoLogRepository extends JpaRepository<DoLog, Long> {

    @Query("""
        select l
        from DoLog l
        join DoTask t on t.id = l.task.id
        where t.user = :user and l.startDate >= :startDate and l.endDate <= :endDate
    """)
    List<DoLog> findByUserAndIntervalBetween(@Param("user") SpaUser user,
                                                @Param("startDate") Calendar startDate,
                                                @Param("endDate") Calendar endDate);


    @Query("""
        select l
        from DoLog l
        join DoTask t on t.id = l.task.id
        where t.user = :user
    """)
    List<DoLog> findAllByUser(@Param("user") SpaUser user);


}
