SELECT pc.model, price
FROM PC pc
JOIN Product p ON p.model = pc.model AND p.maker = 'B'

UNION ALL

SELECT lp.model, price
FROM Laptop lp
JOIN Product p ON p.model = lp.model AND p.maker = 'B'

UNION ALL

SELECT pr.model, price
FROM Printer pr
JOIN Product p ON p.model = pr.model AND p.maker = 'B'