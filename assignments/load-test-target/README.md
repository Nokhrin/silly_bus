### Настройка Java
```shell
zypper search --installed-only java*openjdk*devel
rpm -ql java-21-openjdk-devel | grep bin/java
```


# Тестируемое приложение
### Сборка
```shell
mvn clean install
```

### Запуск
```shell
JAVA_HOME=/usr/lib64/jvm/java-21-openjdk-21 mvn spring-boot:run
```


### Проверка эндпоинтов
```shell
# Health check (без авторизации)
curl -w '\n' -s http://localhost:8080/api/health

# Эхо с авторизацией
curl -w '\n' -s -u test:test123 http://localhost:8080/api/echo/hello

# POST запрос
curl -w '\n' -s -u test:test123 \
-H "Content-Type: application/json" \
-d '{"value":10,"multiplier":5}' \
http://localhost:8080/api/compute
```

---

# JMeter

## Установка
```shell
cd /opt
sudo wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.6.3.tgz
sudo tar -xzf apache-jmeter-5.6.3.tgz
sudo ln -s /opt/apache-jmeter-5.6.3 /opt/jmeter
echo 'export PATH=$PATH:/opt/jmeter/bin' >> ~/.bashrc
source ~/.bashrc
jmeter --version
```

---

# Запуск теста

```shell
cd /path/to/project
```

## Вручную
```shell
jmeter -n -t jmeter/test-plans/auth-test.jmx -l results.jtl -e -o jmeter/reports/
```

## Скриптом
```shell
./jmeter/scripts/run-tests.sh auth-test.jmx
```

---

# Отладочный запуск

```shell
mvn clean install
JAVA_HOME=/usr/lib64/jvm/java-21-openjdk-21 mvn spring-boot:run
# Проверить логин для каждого пользователя
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"tech_user_1","password":"password"}'
# Ожидается: {"token":"fake-jwt-token-tech_user_1"}
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"tech_user_2","password":"password"}'
# Ожидается: {"token":"fake-jwt-token-tech_user_2"}
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"tech_user_3","password":"password"}'
# Ожидается: {"token":"fake-jwt-token-tech_user_3"}

# Выполнить тест
./jmeter/scripts/run-tests.sh auth-test.jmx
# Ожидается:
# summary =     50 in 00:00:01 =   67.8/s Avg:   161 Min:   131 Max:   196 Err:     0 (0.00%)
```