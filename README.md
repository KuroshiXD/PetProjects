# Как развернуть все на Docker. (**Для папки BankDeposits**)

## 1. Создаем файл *.jar
### *Я пытался залить файл .jar, но он превышает объем 25мб, даже в архиве, поэтому нужно будет создать сборку проекта с помощью Maven Wrapper, такой командой в корне репозитория*
```
./mvnw clean package 
```
### *Затем можно протестрировать следующей командой*
```
java -jar target/BankDeposits-0.0.1-SNAPSHOT.jar
```
### *после успешного запуска можно проверить Api с помощью Postmen, файлы с тестами запросов находятся в src/test/json, их только сперва нужно импортировать в Postman*

## 2. Нужно создать докер контейнер для PostgreSQl и самого приложения (app)
### *Сперва создадим и запустим контейнер для PostgreSQl следующей командой в терминале*
```
docker run --name postgres-container -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=postgres -p 5432:5432 -d postgres:17
```
### _после этого создаем контейнер для приложения, прежде создав Dockerfile, docher-compose.yml (эти 2 уже есть) и *.jar файлы_
```
docker build -t bankdeposits-app . 
```
## 3. После того как 2 контейнера созданы, запускаем контейнеры которые определенный в файле docker-compose
```
docker-compose up 
```
### *или же можно можно сделать так, чтобы пересобрать образы перед запуском*
```
docker-compose up --build
```
## 4. Тесты через Postman только приложение и БД развернуты на Docker
### *Можно протестировать файлами json из папки test, только нужно будет их импортировать в постман*
