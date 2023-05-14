TRUNCATE TABLE cash_wallet, cash_wallet_cell, cash_product, cash_operation RESTART IDENTITY cascade;

-- create wallet
insert into cash_wallet (id, hidden, name, currency_id, user_id)
values (1000, false, 'WALLET1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'user1')),
       (1001, false, 'WALLET2', (select id from currency where name = 'RUB'), (select id from spa_user where login = 'user1')),
       (1002, false, 'DELME1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel')),
       (1003, false, 'DELME2', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForDel')),
       (1004, false, 'ADD1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForCreate')),
       (1005, false, 'UPDWALLET1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForUpdate')),
       (1006, false, 'wallet-for-product-1', (select id from currency where name = 'USD'), (select id from spa_user where login = 'userForCreate')),
       (1007, false, 'wallet-for-product-2', (select id from currency where name = 'KGS'), (select id from spa_user where login = 'userForCreate'));

-- create wallet-cell
insert into cash_wallet_cell (id, hidden, icon, name, notes, wallet_id)
values (1000, false, 'icon1', 'cell1', null, 1000),
       (1001, false, 'icon2', 'cell2', null, 1000),
       (1002, false, 'icon3', 'cell3', null, 1000),
       (1003, false, 'icon4', 'cell4', 'note11', 1005),
       (1004, false, 'icon4', 'del1', 'note', 1003),
       (1005, false, 'icon4', 'del2', 'note', 1003),
       (1006, false, 'i1', 'cell-usd-1', null, 1006),
       (1007, false, 'i2', 'cell-kgs-1', null, 1007),
       (1008, false, 'icon5', 'cellUpdate2', 'note5', 1005);

-- product list
insert into cash_product (id, grade, name, default_wallet_cell_dst_id, default_wallet_cell_src_id, user_id)
values (1000, 5, 'product1', 1001, null, (select id from spa_user where login = 'user1')),
       (1001, 4, 'product2', null, 1000, (select id from spa_user where login = 'user1')),
       (1002, 0, 'update product 1', null, null, (select id from spa_user where login = 'userForUpdate')),
       (1003, 0, 'del1', null, null, (select id from spa_user where login = 'userForDel')),
       (1004, 0, 'del2', null, null, (select id from spa_user where login = 'userForDel')),
       (1005, 0, 'product-operation 1', null, null, (select id from spa_user where login = 'userForCreate')),
       (1006, 0, 'update product 2', null, null, (select id from spa_user where login = 'userForUpdate')),
       (1007, 0, 'update product 3', null, null, (select id from spa_user where login = 'userForUpdate'));

insert into cash_operation (id, complete_date, composite_sum, created, is_autofill, notes, rate, sum, transfer_fee, product_id, wallet_cell_dst_id, wallet_cell_src_id)
values (1000, null, null, now(), false, null, 1, 100, 0, 1002, 1003, null),
       (1001, null, null, now(), false, null, 1, 100, 0, 1003, 1005, null),
       (1002, null, null, now(), false, null, 1, 100, 0, 1003, 1005, null);