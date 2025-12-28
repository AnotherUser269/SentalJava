SELECT type, l.model, l.speed 
FROM Product p
JOIN Laptop l ON l.speed < (
	SELECT min(speed)
	FROM PC
) AND p.model = l.model
WHERE type = 'Laptop'
