SELECT speed, AVG( cast(price AS numeric) )
FROM PC p
GROUP BY speed