TRUNCATE TABLE spa_user_roles, spa_user, spa_role RESTART IDENTITY cascade ;

-- password = test
INSERT INTO spa_user (id, created, email, email_confirm_key, last_login, login, password, status, updated)
VALUES (1, now(), 'user1@mail.com', null, null, 'user1', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (2, now(), 'user2@mail.com', null, null, 'user2', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (3, now(), 'user3@mail.com', null, null, 'userForCreate', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (4, now(), 'user4@mail.com', null, null, 'userForDel', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (5, now(), 'user5@mail.com', null, null, 'userForUpdate', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (6, now(), 'user6@mail.com', null, null, 'userForReport', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null);

INSERT INTO spa_role (id, created, is_default, name) VALUES (1, now(), false, 'OWNER'),
                                                            (2, now(), false, 'ADMIN'),
                                                            (3, now(), true, 'USER'),
                                                            (4, now(), true, 'PAGE_INFO'),
                                                            (5, now(), false, 'PAGE_PHONE'),
                                                            (6, now(), false, 'PAGE_FEEDING'),
                                                            (7, now(), true, 'PAGE_DOINGS');

INSERT INTO spa_user_roles (spa_user_id, roles_id)
    select u.id, r.id
    from spa_user u, spa_role r
    where r.is_default = true;