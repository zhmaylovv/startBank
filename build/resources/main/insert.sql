INSERT INTO clients (fio)
VALUES
    ('Иванов Иван Иванович'),
    ('Петров Петр Петрович'),
    ('Сидоров Сидор Сидорович');

INSERT INTO accounts (account_number, balance, client_id)
VALUES
    (12345678900987654321, 100500, 1),
    (12345678900987654322, 100500, 2),
    (12345678900987654323, 100500, 3),
    (12345678900987654324, 100500, 1),
    (12345678900987654325, 100500, 2);

INSERT INTO cards(card_number, account_id)
VALUES
    (1111222233334441, 1),
    (1111222233334442, 1),
    (1111222233334443, 3),
    (1111222233334444, 4),
    (1111222233334445, 5),
    (1111222233334446, 2);


INSERT INTO contractors (name)
VALUES
('Рога и копыта'),
('Орден джедаев'),
('Фонд помощи не прошедшим стажировку в сбере');

INSERT INTO contractor_accounts (account_number, balance, contractor_id)
VALUES
(12345678901234567890, 200500, 1),
(12345678901234567891, 200500, 2),
(12345678901234567892, 200500, 3);



