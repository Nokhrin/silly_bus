# Управление зависимостями

## Maven

### установка / проверка
IDE может содержать Maven как плагин
[пример - IDEA](https://www.jetbrains.com/help/idea/maven-support.html)

при этом в системе Maven может отсутствовать

mvn --version
```text

Command 'mvn' not found, but can be installed with:

sudo apt install maven
```

проект будет работать, доступны версии Maven, которые встроены в IDE
можно считать это ограничением


- [установка Maven из бинарного архива](https://maven.apache.org/download.cgi), здесь доступны:
  - архив актуального релиза, контрольная сумма, подпись контрольной суммы
  - pgp ключи разработчика
  - процедура проверки подписи архива релиза
  - ссылка на инструкцию по установке


- загрузить и импортировать ключи разработчиков Apache
curl -o /tmp/apache_keys https://downloads.apache.org/maven/KEYS
gpg --import /tmp/apache_keys

- проверить подпись дистрибутива
gpg --verify apache-maven-3.9.11-bin.tar.gz.asc apache-maven-3.9.11-bin.tar.gz
```
gpg: Signature made Сб 12 июл 2025 23:32:55 +05
gpg:                using RSA key 84789D24DF77A32433CE1F079EB80E92EB2135B1
gpg:                issuer "sjaranowski@apache.org"
gpg: Good signature from "Slawomir Jaranowski <sjaranowski@apache.org>" [unknown]
gpg:                 aka "Slawomir Jaranowski <s.jaranowski@gmail.com>" [unknown]
gpg: WARNING: This key is not certified with a trusted signature!
gpg:          There is no indication that the signature belongs to the owner.
Primary key fingerprint: 8478 9D24 DF77 A324 33CE  1F07 9EB8 0E92 EB21 35B1
```

- проверить контрольную сумму
{ cat apache-maven-3.9.11-bin.tar.gz.sha512 | tr '\n' ' ' ; echo " *apache-maven-3.9.11-bin.tar.gz"; } | sha512sum -c
```
apache-maven-3.9.11-bin.tar.gz: OK
```

### установка

- создать каталог для Maven 
mkdir -p ~/.local/apache-maven
- распаковать
tar xzvf apache-maven-3.9.11-bin.tar.gz -C ~/.local/apache-maven
- добавить bin в PATH - изменить `.bashrc`
vim ~/.bashrc

```text
# Maven
export M2_HOME=/home/xubuser/.local/apache-maven/apache-maven-3.9.11/
export M2=$M2_HOME/bin
export PATH=$M2:$PATH
```

source ~/.bashrc

- проверить
mvn --version
```
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /home/xubuser/.local/apache-maven/apache-maven-3.9.11
Java version: 17.0.15, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-216-generic", arch: "amd64", family: "unix"
```

## работа с зависимостями
- центр управления зависимостями - `pom.xml`
  - править `pom.xml`, без сторонних инструментов
- [актуальная дока о maven central](https://maven.apache.org/repository/index.html)

на примере JUnit5
- в [maven central](https://mvnrepository.com/repos/central) ищещь junit - он выдает варианты
- берешь последний - он генерирует текст который добавляешь в pom.xml
- в IDEA после добавления - варианты
  1 - появится иконка refresh в области кода
  2 - можно нажать syncronize на вкладке maven
  3 - можно запустить maven compile - скачает, а потом 1/2


## Рабочий процесс Maven

1. **Инициализация** проекта
- создать проект Maven 
- определить метаданные проекта и зависимостей в `pom.xml`
2. Вызов процедуры **сборки**
- выполнить `mvn install` или `mvn package`
3. **Проверка зависимостей**
- Maven проверяет наличие зависимостей в локальном репозитории
- При наличии исходников в локальном репозитории Maven установит зависимости
4. Если Maven не найдет зависимости в локальном репозитории, проверит наличие зависимостей в Центральном репозитории
- если найдет, загрузит исходники,
- выполнит установку,
- добавит исходники в локальный репозиторий
5. После успешного разрешения зависимостей Maven выполнит **компиляцию** проекта
6. Упаковка
  - Maven пакует проект в исполняемый файл JVM (JAR/WAR/...)