SELECT p.maker, l.speed
FROM Laptop l
JOIN Product p ON p.model = l.model AND hd >= 100
