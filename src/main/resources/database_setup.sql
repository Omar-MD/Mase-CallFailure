USE mase_group_project;

DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Insert Admin account
INSERT INTO accounts (username, password, role) VALUES
    ('admin', 'password', 'SYSTEM_ADMINISTRATOR'),
    ('customer', 'password', 'CUSTOMER_SERVICE_REP');
