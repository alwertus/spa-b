package com.tretsoft.spa.old.feeding.repo;

import com.tretsoft.spa.old.feeding.model.FeedingUserOptions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FeedingOptionsRepository extends CrudRepository<FeedingUserOptions, Long> {

    Optional<FeedingUserOptions> findByUserId(Long userId);

}