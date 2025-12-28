SELECT pr.maker, MAX(pc.price)
FROM Product pr
JOIN PC pc ON pc.model = pr.model
GROUP BY pr.maker;