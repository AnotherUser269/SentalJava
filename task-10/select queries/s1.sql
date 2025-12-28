SELECT model, speed, hd
FROM PC
GROUP BY code
HAVING price < cast(500*75 AS money);
