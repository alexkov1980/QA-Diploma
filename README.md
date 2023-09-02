## 1 Установка и запуск
## Запускаем контейнеры из файла docker-compose.yml командой в терминале:
### docker-compose up
## 2 Запускаем SUT командой в терминале:
## для MySQL:
### java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
## для PostgreSQL:
### java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
## 3 Запускаем авто-тесты командой в терминале:
## для MySQL:
### ./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"
## для PostgreSQL:
### ./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app"
