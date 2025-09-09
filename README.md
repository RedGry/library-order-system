# Getting Started

## Требования
- Java 1.8
- Maven 3.9+
- PostgreSQL 14+ (или совместимая СУБД)

## Настройки
Перед запуском нужно указать параметры подключения к БД.   
Например, через файл `.env`, если хотите запустить на своей БД или используйте по умолчанию для запуска с образом бд из `docker-compose.yaml`.

Пример `.env` файла:

```dotenv
DB_HOST=localhost
DB_PORT=5432
DB_NAME=postgres
DB_USERNAME=testuser
DB_PASSWORD=testpassword
```

Остальные параметры можно настроить в файлах: `src/main/resources/*`, либо их не трогать.

Команда для запуска docker-compose с БД: `docker-compose up -d`.  
P.S.: Проверьте, что у вас при запуске на занят порт `5432`, иначе БД не будет работать.

## Сборка
1. `mvn clean package`
2. `java -jar target/library-0.0.1-SNAPSHOT.jar`

Приложение поднимется на порту 8081 по умолчанию.

Swagger: `http://{host}:8081/swagger-ui/index.html#/`

## Тесты
`mvn test`