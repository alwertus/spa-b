TRUNCATE TABLE cash_wallet RESTART IDENTITY cascade;

-- create wallet
insert into cash_wallet (id, hidden, name, currency_id, user_id)
values (1000, false, 'WALLET1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'user1')),
       (1001, false, 'WALLET2', (select id from currency where name = 'RUB'), (select id from spa_user where login = 'user1')),
       (1002, false, 'DELME1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel')),
       (1003, false, 'DELME2', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel'));
