SELECT AVG(speed)
FROM Laptop
WHERE price > cast(1000*75 AS money)
