SELECT DISTINCT maker
FROM Product

WHERE maker IN (
    SELECT pr.maker
    FROM Product pr
    JOIN PC p ON p.model = pr.model
    WHERE p.speed >= 750
)
AND maker IN (
    SELECT pr.maker
    FROM Product pr
    JOIN Laptop l ON l.model = pr.model
    WHERE l.speed >= 750
);