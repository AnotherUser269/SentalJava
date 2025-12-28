SELECT model
FROM PC p

WHERE p.price = (
	SELECT max(price)
	FROM PC
)

UNION ALL 

SELECT model
FROM Laptop l

WHERE l.price = (
	SELECT max(price)
	FROM Laptop
)

UNION ALL 

SELECT model
FROM Printer pr

WHERE pr.price = (
	SELECT max(price)
	FROM Printer
)
