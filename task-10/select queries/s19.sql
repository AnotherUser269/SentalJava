SELECT pr.maker, AVG(lp.screen)
FROM Product pr
JOIN Laptop lp ON lp.model = pr.model
GROUP BY pr.maker;