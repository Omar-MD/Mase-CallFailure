-- This file is run when the application is being tested
-- It will be used to populate a H2 database

DROP TABLE IF EXISTS accounts;
CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Insert test account
INSERT INTO accounts (role, username, password) VALUES
    ('Admin', 'admin', 'password');