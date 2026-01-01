SELECT model, speed, hd
FROM PC
WHERE (cd = '12x' OR cd = '24x') AND price < cast(600*75 AS money)
