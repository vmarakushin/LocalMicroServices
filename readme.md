# LocalMicroServices
## Version: 7.0
### Основные изменения:
- Дополнена документация Swagger
- Использован моно репозиторий для удобства
- Реализованы паттерны: api gateway, service discovery, external configuration
- Для api gateway настроен circuit breaker
### Запуск
- Все необходимое лежит в CUSTOM_RESOURCES
- Mvn clean install используйте только в дефолтном профиле
- Поднимите среду с помощью скрипта build_docker_compose.ps1
- Запустите сервисы с помощью start-services.ps1
- Съешьте 1 (а лучше 2) кусочка пиццы, пока гейтвэй найдет сервисы