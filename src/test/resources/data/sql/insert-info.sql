TRUNCATE TABLE info_space, info_page RESTART IDENTITY cascade ;

-- create spaces
insert into info_space (id, created, is_private, title, created_by)
values (1000, now(), true, 'Space1', (select id from spa_user where login = 'user1'));

-- create pages
insert into info_page (id, created, html, position, title, updated, created_by, parent_id, space_id, updated_by)
values (1000, now(), 'TEXT TEXT TEXT 1', null, 'Space1-Page1', now(), (select id from spa_user where login = 'user1'), null, 1000, null);