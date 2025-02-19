-- Create BOOK table
CREATE TABLE IF NOT EXISTS BOOK (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(255) NOT NULL
);

-- Create CUSTOMER table
CREATE TABLE IF NOT EXISTS CUSTOMER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    loyalty_points INT DEFAULT 0 NOT NULL
);

-- Create ORDER table
CREATE TABLE IF NOT EXISTS ORDERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)
);

-- Create BOOK_ITEM table (a join table between ORDER and BOOK)
CREATE TABLE IF NOT EXISTS ORDER_ITEM (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orders_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (orders_id) REFERENCES ORDERS(id),
    FOREIGN KEY (book_id) REFERENCES BOOK(id)
);

-- Adding sample books to the database
INSERT INTO BOOK (title, author, price, type) VALUES ('The Catcher in the Rye', 'J.D. Salinger', 10.99, 'REGULAR');
INSERT INTO BOOK (title, author, price, type) VALUES ('1984', 'George Orwell', 8.99, 'NEW_RELEASE');
INSERT INTO BOOK (title, author, price, type) VALUES ('To Kill a Mockingbird', 'Harper Lee', 12.99, 'OLD_EDITION');

-- Insert sample customers into the CUSTOMER table
INSERT INTO CUSTOMER (name, loyalty_points) VALUES
    ('John Doe', 5),
    ('Jane Smith', 15),
    ('Emily Johnson', 0),
    ('Michael Brown', 20);
