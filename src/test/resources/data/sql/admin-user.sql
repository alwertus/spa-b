TRUNCATE TABLE spa_user_roles, spa_user, spa_role RESTART IDENTITY cascade ;

-- password = test
INSERT INTO spa_user (id, created, email, email_confirm_key, last_login, login, password, status, updated)
VALUES (1, now(), 'user1@mail.com', null, null, 'common-user1', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (2, now(), 'user2@mail.com', null, null, 'common-user2', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (3, now(), 'user3@mail.com', null, null, 'userForCreate', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (4, now(), 'user4@mail.com', null, null, 'userForDel', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (5, now(), 'user5@mail.com', null, null, 'userForUpdate', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (6, now(), 'user6@mail.com', null, null, 'userForReport', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (7, now(), 'user7@mail.com', null, null, 'admin-user1', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null),
       (8, now(), 'user8@mail.com', null, null, 'admin-owner1', '$2a$12$yygDQh2UOxu0o2Qp8LGWyOiLBumhyAa1H/sDsW69Ma6pPvQvmPep.', 0, null);

INSERT INTO spa_role (id, created, is_default, name, priority) VALUES (1, now(), false, 'OWNER', 0),
                                                            (2, now(), false, 'ADMIN', 1),
                                                            (3, now(), true, 'USER', 2),
                                                            (4, now(), true, 'PAGE_INFO', 2),
                                                            (5, now(), false, 'PAGE_PHONE', 2),
                                                            (6, now(), false, 'PAGE_FEEDING', 2),
                                                            (7, now(), true, 'PAGE_DOINGS', 2),
                                                            (8, now(), true, 'PAGE_CASH', 2);

INSERT INTO spa_user_roles (spa_user_id, roles_id)
    select u.id, r.id
    from spa_user u, spa_role r
    where r.is_default = true;

-- add role ADMIN to user admin*
INSERT INTO spa_user_roles (spa_user_id, roles_id)
    select u.id, r.id
    from spa_user u, spa_role r
    where r.name = 'ADMIN' and u.login like 'admin%';

INSERT INTO spa_user_roles (spa_user_id, roles_id)
    select u.id, r.id
    from spa_user u, spa_role r
    where r.name = 'OWNER' and u.login like 'admin-owner%';