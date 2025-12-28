SELECT AVG(speed)
FROM PC
JOIN Product pr ON pr.model = PC.model
WHERE pr.maker = 'A';