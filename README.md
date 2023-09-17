# Дипломная работа профессии «Тестировщик ПО»
## Документы
### [План автоматизации](docs/Plan.md)
### [Отчет по итогам тестирования](docs/Report.md)
### [Отчет по итогам автоматизации](docs/Summary.md)

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
Сервис будет доступен в браузере по адресу: http://localhost:8080/ <br>
## 4 Генерируем отчёт по итогам тестирования с помощью Allure. Отчёт автоматически откроется в браузере с помощью команды в терминале:
### ./gradlew allureServe
После генерации и работы с отчётом, останавливаем работу allureServe в терминале сочетанием клавиш CTRL + C и подтверждаем действие в терминале вводом Y. <br>

Если необходимо перезапустить контейнеры, приложение или авто-тесты, нужно остановить работу сервисов в терминале сочетанием клавиш CTRL + C и перезапустить их, повторив шаги 1-3. <br>


