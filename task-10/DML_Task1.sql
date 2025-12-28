-- Product
INSERT INTO Product VALUES
    ('Dell', 'XPS 13 9310', 'Laptop'),
    ('HP', 'LaserJet Pro M404dn', 'Printer'),
    ('Apple', 'MacBook Air M1', 'Laptop'),
    ('Lenovo', 'ThinkPad X1 Carbon Gen 9', 'Laptop'),
    ('ASUS', 'VivoPC R Series', 'PC'),
    ('Canon', 'PIXMA TR8620', 'Printer'),
    ('ASUS', 'ROG Strix G15', 'Laptop'),
    ('HP', 'Omen 30L', 'PC'),
    ('HP', 'Pavilion Gaming Desktop TG01-2023', 'PC'),
    ('Epson', 'EcoTank ET-2720', 'Printer'),
	('HP', '255 G4', 'PC'),
	('HP', 'LJ M1536', 'Printer'),
	('B', 'MagicBook X16', 'Laptop'),
	('B', 'Canon PIXMA MG2541S', 'Printer'),
	('A', 'M-16 3.0 White', 'PC'),
	('A', 'M-16 3.0 Aqua', 'PC'),
	('HP', '256 G4', 'PC');
;

-- PC
INSERT INTO PC VALUES 
	(1000, 'VivoPC R Series', 1400, 8192, 120, '4x', 20000),
	(1001, 'Omen 30L', 3200, 16384, 1000, '16x', 80000),
	(1002, 'Pavilion Gaming Desktop TG01-2023', 3600, 8192, 512, '16x', 55000),
	(1003, '255 G4', 2000, 4096, 500, '12x', 17000),
	(1004, 'M-16 3.0 White', 2500, 8192, 960, '16x', 209418),
	(1005, '256 G4', 2000, 4096, 1000, '12x', 209418),
	(1006, 'M-16 3.0 Aqua', 2500, 16384, 960, '24x', 275019);

-- Laptop
INSERT INTO Laptop VALUES
	(2000, 'XPS 13 9310', 2800, 16384, 512, 13, 120000),
	(2001, 'MacBook Air M1', 3000, 8192, 256, 13, 90000),
	(2002, 'ThinkPad X1 Carbon Gen 9', 2900, 16384, 512, 14, 110000),
	(2003, 'ROG Strix G15', 3600, 16384, 1000, 15, 10000),
	(2004, 'MagicBook X16', 1200, 8192, 512, 16, 39599);

-- Printer
INSERT INTO Printer VALUES 
	(3000, 'LaserJet Pro M404dn', 'n', 'Laser', 25000),
	(3001, 'PIXMA TR8620', 'y', 'Jet', 20000),
	(3002, 'EcoTank ET-2720', 'y', 'Jet', 18000),
	(3003, 'Canon PIXMA MG2541S', 'y', 'Jet', 6534),
	(3004, 'LJ M1536', 'n', 'Laser', 25000);

