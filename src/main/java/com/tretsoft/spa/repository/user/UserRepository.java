package com.tretsoft.spa.repository.user;

import com.tretsoft.spa.model.user.SpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SpaUser, Long> {

    @Query("select t from SpaUser t where UPPER(t.login) = UPPER(:login) ")
    Optional<SpaUser> findByLogin(@Param("login") String login);

    Optional<SpaUser> findByEmailConfirmKey(String emailConfirmKey);

}
