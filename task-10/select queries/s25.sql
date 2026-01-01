SELECT DISTINCT p.maker
FROM Product p
JOIN PC pc ON p.model = pc.model

WHERE pc.ram = (
    SELECT MIN(ram) FROM PC
  )
  AND pc.speed = (
    SELECT MAX(speed) FROM PC 
	WHERE ram = (SELECT MIN(ram) FROM PC)
  );
