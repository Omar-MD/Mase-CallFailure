DROP TABLE IF EXISTS accounts;
CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ACC_TYPE VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Insert test account
INSERT INTO accounts (ACC_TYPE, username, password) VALUES
    ('Admin', 'admin', 'password');

-- SELECT * FROM accounts;