uruchomienie kontenera mySql

`docker run -p 0.0.0.0:3307:3306 --name hibernate-isa -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=highschool -d mysql`