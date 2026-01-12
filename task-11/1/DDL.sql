DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Requests;

CREATE TABLE Books (
	id INTEGER PRIMARY KEY,
	title VARCHAR(200),
	author VARCHAR(100),
	description TEXT,
	timeStamp BIGINT,
	price NUMERIC(10, 2),
	status VARCHAR(15)
);

CREATE TABLE Orders (
	id INTEGER PRIMARY KEY,
	bookId INTEGER,
	startTime BIGINT,
	phoneNumber VARCHAR(25),
	deliveryPrice NUMERIC(10, 2),
	status VARCHAR(15),
	completionTime BIGINT,

	FOREIGN KEY (bookId) REFERENCES Books(id)
);

CREATE TABLE Requests (
	id INTEGER PRIMARY KEY,
	bookTitle VARCHAR(200),
	status VARCHAR(15)
)