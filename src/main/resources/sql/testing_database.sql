
-- Insert test accounts
INSERT INTO accounts (username, password, role) VALUES 
	('admin', '$2a$10$5l3n0SFJYpUImCvooXLuJOXYGCftlXOkvU1Bm9TsBhIbanoWdZWyO', 'SYSTEM_ADMINISTRATOR'),
	('support', '$2a$10$5l3n0SFJYpUImCvooXLuJOXYGCftlXOkvU1Bm9TsBhIbanoWdZWyO', 'SUPPORT_ENGINEER'),
    ('customer', '$2a$10$5l3n0SFJYpUImCvooXLuJOXYGCftlXOkvU1Bm9TsBhIbanoWdZWyO', 'CUSTOMER_SERVICE_REP'),
    ('engineer', '$2a$10$5l3n0SFJYpUImCvooXLuJOXYGCftlXOkvU1Bm9TsBhIbanoWdZWyO', 'NETWORK_ENGINEER');    