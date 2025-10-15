![eva_result](https://github.com/user-attachments/assets/469b18f3-7704-452c-8db0-45db491df5c3)
This is the project
- Username: Ana, password: a123
- card number: 123, cvv: 123

  SQL script
  create database libraryPro;

use libraryPro;

create schema if not exists account;

CREATE TABLE IF NOT EXISTS account.users (
	user_id INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	 roles VARCHAR(30) NOT NULL,
	PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price INT DEFAULT 0 CHECK (price >= 0),
    tax_percent INT DEFAULT 5 CHECK (tax_percent >= 0),
    quantity INT DEFAULT 10 CHECK (quantity >= 0),
    category_id BIGINT,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS card (
	card_number VARCHAR(255) NOT NULL UNIQUE,
	card_cvv VARCHAR(255) NOT NULL,
	PRIMARY KEY (card_number)
);

-- add 
INSERT INTO categories (name)
VALUES
    ('Electronics'),
    ('Books'),
    ('Clothing');

INSERT INTO products (name, price, tax_percent, quantity, category_id)
VALUES
    ('Smartphone', 699, 8, 50, 1),   -- Electronics
    ('Laptop', 1200, 10, 30, 1),     -- Electronics
    ('Novel - The Great Gatsby', 15, 5, 100, 2),  -- Books
    ('T-Shirt', 25, 5, 200, 3),      -- Clothing
    ('Jeans', 50, 5, 150, 3);        -- Clothing
    
    insert into account.users (username, password) values ("Ana", "a123", "admin");
insert into account.users (username, password) values ("Bob", "a123", "user");
insert into account.users (username, password) values ("Cedrick", "a123", "user");

INSERT INTO card (card_number, card_cvv) VALUES
('4111111111111111', '123'),  -- Visa
('5500000000000004', '456'),  -- MasterCard
('340000000000009',  '1234'), -- American Express
('30000000000004',   '789'),  -- Diners Club
('6011000000000004', '321');

INSERT INTO card (card_number, card_cvv) VALUES
('123', '123');

select * from products;-- Discover



