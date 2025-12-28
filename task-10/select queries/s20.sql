SELECT pr.maker, COUNT(DISTINCT p.model)
FROM Product pr
JOIN PC p ON p.model = pr.model
GROUP BY pr.maker
HAVING COUNT(DISTINCT p.model) >= 3;