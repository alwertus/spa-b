TRUNCATE TABLE do_task_list, do_label_list RESTART IDENTITY cascade ;

-- create tasks
insert into do_task_list (name, start_date, user_id)
values ('Work', now(), (select id from spa_user where login = 'user1')),
       ('Sleep', null, (select id from spa_user where login = 'user1')),
       ('Watch video', now(), (select id from spa_user where login = 'user2')),
       ('taskDel1', now(), (select id from spa_user where login = 'userForDel'));

insert into do_task_list (id, name, start_date, user_id)
values (1000, 'taskForUpdate', now(), (select id from spa_user where login = 'userForUpdate')),
       (1001, 'taskForAddLabel', now(), (select id from spa_user where login = 'userForUpdate')),
       (1002, 'taskForAddLabel2', now(), (select id from spa_user where login = 'userForUpdate'));


-- create labels
insert into do_label_list (name, color, user_id)
values ('Useful', null, (select id from spa_user where login = 'user1')),
       ('Rest', '#FF0000', (select id from spa_user where login = 'user1')),
       ('Routines', '#0000FF', (select id from spa_user where login = 'user1')),
       ('labelForDelete', '#0000FF', (select id from spa_user where login = 'userForDel')),
       ('labelForInsert', '#0000FF', (select id from spa_user where login = 'userForUpdate'));

insert into do_label_list (id, name, color, user_id)
values (1000, 'OldValue', '#000000', (select id from spa_user where login = 'userForUpdate')),
       (1001, 'LabelToAdd', null, (select id from spa_user where login = 'userForUpdate'));


-- add label to taks
insert into do_task_label (task_id, label_id)
values ((select id from do_task_list where name='Work'), (select id from do_label_list where name='Useful')),
       ((select id from do_task_list where name='Sleep'), (select id from do_label_list where name='Rest'));