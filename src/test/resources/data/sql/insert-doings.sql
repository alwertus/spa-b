TRUNCATE TABLE do_task_list, do_label_list, do_log RESTART IDENTITY cascade ;

-- create tasks
insert into do_task_list (name, start_date, user_id)
values ('Work', now(), (select id from spa_user where login = 'user1')),
       ('Sleep', null, (select id from spa_user where login = 'user1')),
       ('Watch video', timestamp '2023-01-02 01:00', (select id from spa_user where login = 'user2')),
       ('taskDel1', now(), (select id from spa_user where login = 'userForDel'));

insert into do_task_list (id, name, start_date, user_id)
values (1000, 'taskForUpdate', now(), (select id from spa_user where login = 'userForUpdate')),
       (1001, 'taskForAddLabel', now(), (select id from spa_user where login = 'userForUpdate')),
       (1002, 'taskForAddLabel2', now(), (select id from spa_user where login = 'userForUpdate')),
       (1100, 'Work', null, 6),
       (1101, 'Sleep', null, 6),
       (1102, 'Video', null, 6),
       (1103, 'Walking', null, 6),
       (1104, 'Learning', null, 6);


-- create labels
insert into do_label_list (name, color, user_id)
values ('Useful', null, (select id from spa_user where login = 'user1')),
       ('Rest', '#FF0000', (select id from spa_user where login = 'user1')),
       ('Routines', '#0000FF', (select id from spa_user where login = 'user1')),
       ('labelForDelete', '#0000FF', (select id from spa_user where login = 'userForDel')),
       ('labelForInsert', '#0000FF', (select id from spa_user where login = 'userForUpdate'));

insert into do_label_list (id, name, color, user_id)
values (1000, 'OldValue', '#000000', (select id from spa_user where login = 'userForUpdate')),
       (1001, 'LabelToAdd', null, (select id from spa_user where login = 'userForUpdate')),
       (1100, 'Routines', null, 6),
       (1101, 'Rest', null, 6),
       (1102, 'Useful', null, 6);


-- add label to taks
insert into do_task_label (task_id, label_id)
values ((select id from do_task_list where name='Work' and user_id = (select id from spa_user where login = 'user1')), (select id from do_label_list where name='Useful' and user_id = (select id from spa_user where login = 'user1'))),
       ((select id from do_task_list where name='Sleep' and user_id = (select id from spa_user where login = 'user1')), (select id from do_label_list where name='Rest' and user_id = (select id from spa_user where login = 'user1'))),
       (1100, 1100),
       (1101, 1100),
       (1102, 1101),
       (1103, 1101),
       (1104, 1102);

-- add log event
insert into do_log (start_date, end_date, task_id)
values (timestamp '2023-01-01 01:00', timestamp '2023-01-01 03:00', (select id from do_task_list where name='Work' and user_id = ((select id from spa_user where login = 'user1')))),
       (timestamp '2023-01-01 03:00', timestamp '2023-01-01 03:15', (select id from do_task_list where name='Sleep' and user_id = ((select id from spa_user where login = 'user1')))),
       (timestamp '2023-01-01 03:15', timestamp '2023-01-01 05:00', (select id from do_task_list where name='Work' and user_id = ((select id from spa_user where login = 'user1'))));

-- log for report
insert into do_log (start_date, end_date, task_id)
values (timestamp '2023-01-01 10:00', timestamp '2023-01-01 19:00', 1100), -- work
       (timestamp '2023-01-02 10:00', timestamp '2023-01-02 19:00', 1100),
       (timestamp '2023-01-03 10:00', timestamp '2023-01-03 19:00', 1100),
       (timestamp '2023-01-04 10:00', timestamp '2023-01-04 19:00', 1100),
       (timestamp '2023-01-05 10:00', timestamp '2023-01-05 19:00', 1100),
       (timestamp '2023-01-01 23:00', timestamp '2023-01-02 08:00', 1101), -- sleep
       (timestamp '2023-01-02 23:00', timestamp '2023-01-03 08:00', 1101),
       (timestamp '2023-01-03 23:00', timestamp '2023-01-04 08:00', 1101),
       (timestamp '2023-01-04 23:00', timestamp '2023-01-05 08:00', 1101),
       (timestamp '2023-01-05 23:00', timestamp '2023-01-06 08:00', 1101),
       (timestamp '2023-01-01 08:00', timestamp '2023-01-01 10:00', 1102), -- video
       (timestamp '2023-01-01 19:00', timestamp '2023-01-01 20:00', 1102),
       (timestamp '2023-01-02 08:00', timestamp '2023-01-02 10:00', 1102),
       (timestamp '2023-01-02 19:00', timestamp '2023-01-02 20:00', 1102),
       (timestamp '2023-01-03 08:00', timestamp '2023-01-03 10:00', 1102),
       (timestamp '2023-01-03 19:00', timestamp '2023-01-03 20:00', 1102),
       (timestamp '2023-01-04 08:00', timestamp '2023-01-04 10:00', 1102),
       (timestamp '2023-01-04 19:00', timestamp '2023-01-04 20:00', 1102),
       (timestamp '2023-01-05 08:00', timestamp '2023-01-05 10:00', 1102),
       (timestamp '2023-01-05 19:00', timestamp '2023-01-05 20:00', 1102),
       (timestamp '2023-01-01 20:00', timestamp '2023-01-01 21:00', 1103), -- walking
       (timestamp '2023-01-02 20:00', timestamp '2023-01-02 21:00', 1103),
       (timestamp '2023-01-03 20:00', timestamp '2023-01-03 21:00', 1103),
       (timestamp '2023-01-04 20:00', timestamp '2023-01-04 21:00', 1103),
       (timestamp '2023-01-05 20:00', timestamp '2023-01-05 21:00', 1103),
       (timestamp '2023-01-01 21:00', timestamp '2023-01-01 23:00', 1104), -- learning
       (timestamp '2023-01-02 21:00', timestamp '2023-01-02 23:00', 1104),
       (timestamp '2023-01-03 21:00', timestamp '2023-01-03 23:00', 1104),
       (timestamp '2023-01-04 21:00', timestamp '2023-01-04 23:00', 1104),
       (timestamp '2023-01-05 21:00', timestamp '2023-01-05 23:00', 1104)
;