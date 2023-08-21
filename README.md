# Дипломный проект по профессии «Тестировщик ПО»

Цель данного проекта заключена в автоматизации тестирования сервиса по покупке тура.

# Требования для запуска тестов:
## 1. Необходимое окружение.
### Инструменты: IntelliJ IDEA, Git, GitHub, Docker Desktop, Java, MySQL, PostgerSQL.
## 2 Запуск Тестов:
1. Запустить Docker Desktop, предварительно зарегестрироваться на Docker Hub;
1. В IntelliJ IDEA открыть терминал;
2. Выполнить команду: `docker-compose up`;
3. Дождаться сборки контейнера;
1. В IntelliJ IDEA открыть дополнительную вкладку терминала;
2. Выполнить команду для запуска `aqa-shop.jar` для одной из СУБД:
 - **MySQL:** `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
 - **PostgreSQL:** `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
7. В IntelliJ IDEA дважды нажать Ctrl и в командной строке «Run Anything» выполнить одну из команд в зависимости от выбранной СУБД:
- **MySQL:** `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
- **PostgreSQL:** `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
8. Перейти в терминале по сыылке на отчет gradle после завершения всех тестов.
