SELECT model, ram, screen
FROM Laptop
WHERE price > cast(1000*75 AS money)