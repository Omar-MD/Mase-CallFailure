USE mase_group_project;

CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Insert test account
INSERT INTO accounts (username, password, role) VALUES
    ('admin', 'password', 'Admin');

-- SELECT * FROM accounts;