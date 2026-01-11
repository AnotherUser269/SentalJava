INSERT INTO Books(id, title, author, description, timeStamp, price, status) VALUES
	(1, 'Book1', 'Author1', 'This is a brand new Book1!', 1718476134, 5.00, 'InOrder'),
	(2, 'Book2', 'Author1', 'This is a brand new Book2!', 1709777843, 1.00, 'NotInOrder'),
	(3, 'Book3', 'Author1', 'This is a brand new Book3!', 1731659587, 10.00, 'NotInOrder'),
	(4, 'Book4', 'Author2', 'Cool Book4! Read it.', 1756695401, 10.00, 'NotInOrder'),
	(5, 'Book5', 'Author2', 'Cool Book5! Read it.', 1725042231, 15.00, 'InOrder'),
	(6, 'Book6', 'Author2', 'Cool Book6! Read it.', 1748737085, 9.00, 'InOrder'),
	(7, 'Book7', 'Author3', 'Hey, check my Book7! You will like it!', 1759617643, 4.24, 'InOrder'),
	(8, 'Book8', 'Author3', 'Hey, check my Book8! You will like it!', 1752268832, 4.04, 'InOrder'),
	(9, 'Book9', 'Author3', 'Hey, check my Book9! You will like it!', 1734371970, 4.11, 'NotInOrder'),
	(10, 'Book10', 'Author4', 'No description provided', 1734365488, 3.00, 'InOrder');
	
INSERT INTO Orders(id, bookId, startTime, phoneNumber, deliveryPrice, status, completionTime) VALUES
	(1, 2, 1700000000, '+1-202-555-0101', 5.00, 'New', -1),
	(2, 1, 1700003600, '+1-202-555-0102', 7.50, 'Dismissed', 1712000000),
	(3, 10, 1700007200, '+1-202-555-0103', 10.00, 'Success', 1750000000),
	(4, 4, 1700010800, '+1-202-555-0104', 5.00, 'New', -1),
	(5, 5, 1700014400, '+1-202-555-0105', 12.00, 'Success', 1761200005),
	(6, 6, 1700018000, '+1-202-555-0106', 6.50, 'Dismissed', 1750048006),
	(7, 9, 1700021600, '+1-202-555-0107', 4.00, 'New', -1),
	(8, 8, 1700025200, '+1-202-555-0108', 8.00, 'Dismissed', 1702125205),
	(9, 7, 1700028800, '+1-202-555-0109', 5.50, 'Success', 1712328800),
	(10, 3, 1700032400, '+1-202-555-0110', 0.00, 'New', -1);

INSERT INTO Requests(id, bookTitle, status) VALUES
	(1, 'Book11', 'Opened'),
	(2, 'Book12', 'Opened'),
	(3, 'Book3', 'Closed')