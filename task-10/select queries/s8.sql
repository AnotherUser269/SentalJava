SELECT maker
FROM Product pr

JOIN PC pc ON pc.model = pr.model

WHERE pr.model NOT IN (
	SELECT model
	FROM Laptop lp
)
