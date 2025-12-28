SELECT maker, price 
FROM Product pr

JOIN Printer p ON p.model = pr.model
WHERE p.color = 'y' AND p.price = (
	SELECT min(price)
	FROM Printer
)