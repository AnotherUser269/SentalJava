SELECT maker
FROM Product pr

WHERE 450 <= ALL (
	SELECT speed
	FROM PC p
)
