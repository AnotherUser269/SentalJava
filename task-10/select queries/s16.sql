SELECT DISTINCT p1.code, p2.code, p1.speed, p1.ram
FROM PC p1
JOIN PC p2 ON p1.ram = p2.ram AND p1.speed = p2.speed AND p1.code < p2.code

