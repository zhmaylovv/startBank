DROP TABLE IF EXISTS clients, accounts, cards, contragent,
    contragent_accounts ;

CREATE TABLE IF NOT EXISTS clients(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
fio VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS accounts(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_number DECIMAL UNIQUE ,
balance DECIMAL(15, 2),
client_id INT NOT NULL references clients(id)
);

CREATE TABLE IF NOT EXISTS cards(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
card_number BIGINT UNIQUE ,
account_id INT NOT NULL references accounts(id)
);

CREATE TABLE IF NOT EXISTS contractors(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS contractor_accounts(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_number DECIMAL UNIQUE ,
balance DECIMAL(15, 2),
contractor_id INT NOT NULL references contractors(id)
);