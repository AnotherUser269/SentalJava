DROP TABLE IF EXISTS PC;
DROP TABLE IF EXISTS Laptop;
DROP TABLE IF EXISTS Printer;
DROP TABLE IF EXISTS Product;

create table Product (
	maker varchar(10),
	model varchar(50) PRIMARY KEY,
	type varchar(50)
);

create table PC (
	code int PRIMARY KEY,
	model varchar(50),
	speed smallint,
	ram smallint,
	hd real,
	cd varchar(10),
	price money NULL,

	FOREIGN KEY (model) REFERENCES Product(model)
);

create table Laptop (
	code int PRIMARY KEY,
	model varchar(50),
	speed smallint,
	ram smallint,
	hd real,
	screen smallint,
	price money NULL,

	FOREIGN KEY (model) REFERENCES Product(model)
);

create table Printer (
	code int PRIMARY KEY,
	model varchar(50),
	color char(1),
	type varchar(10),
	price money NULL,

	FOREIGN KEY (model) REFERENCES Product(model)
);


