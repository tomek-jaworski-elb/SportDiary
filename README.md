### Uruchomienie aplikacji

Serwis WWW </br>
http://localhost:8080/ </br>
Dane logowania:</br>
`user:user`</br>
`admin:admin`


Serwis REST</br>
http://localhost:8080/api/

------------------------------

### Uruchomienie kontenera mySql

Uruchomienie kontenera mySql 

`docker run -p 0.0.0.0:3307:3306 --name hibernate-isa -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=highschool -d mysql`

Restart kontenera:

`docker restart hibernate-isa `

--------------------------
### Docker-compose

`docker-compose up -d`

`docker-compose restart`

`docker-compose stop`

`docker-compose rm`

`docker-compose kill`

-------------------------

