-- Initializing table accounts --
INSERT INTO tb_accounts (document_number, available_credit_limit) VALUES ('12345678900', 5000.00);

-- Initializing table transactions --
INSERT INTO tb_transactions (account_id, operation_type, amount, event_date) VALUES (1, 'COMPRA_A_VISTA', -50.00, '2020-01-01T10:32:07.7199222');
INSERT INTO tb_transactions (account_id, operation_type, amount, event_date) VALUES (1, 'COMPRA_A_VISTA', -23.50, '2020-01-01T10:48:12.2135875');
INSERT INTO tb_transactions (account_id, operation_type, amount, event_date) VALUES (1, 'COMPRA_A_VISTA', -18.70, '2020-01-02T19:01:23.1458543');
INSERT INTO tb_transactions (account_id, operation_type, amount, event_date) VALUES (1, 'PAGAMENTO', 60.00, '2020-01-05T09:34:18.5893223');

