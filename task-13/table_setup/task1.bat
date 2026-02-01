@echo off
set "FILE_DML=.\DML.sql"
set "FILE_DDL=.\DDL.sql"

echo Creating database...
psql -U postgres -h localhost -p 5432 -c "CREATE DATABASE task11;"

echo Running DDL.sql ...
psql -U postgres -h localhost -p 5432 -d task11 -f "%FILE_DDL%"

echo Running DML.sql ...
psql -U postgres -h localhost -p 5432 -d task11 -f "%FILE_DML%"

pause
