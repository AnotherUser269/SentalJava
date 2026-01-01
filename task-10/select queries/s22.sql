SELECT speed, AVG( cast(price AS numeric) )
FROM PC p
GROUP BY speed
HAVING p.speed > 600