TRUNCATE TABLE cash_wallet, cash_wallet_cell RESTART IDENTITY cascade;

-- create wallet
insert into cash_wallet (id, hidden, name, currency_id, user_id)
values (1000, false, 'WALLET1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'user1')),
       (1001, false, 'WALLET2', (select id from currency where name = 'RUB'), (select id from spa_user where login = 'user1')),
       (1002, false, 'DELME1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel')),
       (1003, false, 'DELME2', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel')),
       (1004, false, 'ADD1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForCreate')),
       (1005, false, 'UPDWALLET1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForUpdate'));

insert into cash_wallet_cell (id, hidden, icon, name, notes, wallet_id)
values (1000, false, 'icon1', 'cell1', null, 1000),
       (1001, false, 'icon2', 'cell2', null, 1000),
       (1002, false, 'icon3', 'cell3', null, 1000),
       (1003, false, 'icon4', 'cell4', 'note11', 1005),
       (1004, false, 'icon4', 'del1', 'note', 1003),
       (1005, false, 'icon4', 'del2', 'note', 1003);