# Установка пакета разработки Java

> установка определенной версии, выбор используемой по умолчанию

### Путь к исполняемому файлу в PATH
which java

### Выбор версии по умолчанию
update-alternatives --config java
update-alternatives --config javac

### Путь к домашней директории JDK - указывать только одну версию, не список
echo $JAVA_HOME

#### Проверка
java --version


## Установка Java в openSUSE Tumbleweed
### Список доступных неустановленных RPM-пакетов Java
zypper search --not-installed-only java*openjdk*devel
### Список установленных RPM-пакетов Java
zypper search --installed-only java*openjdk*devel
### Пример установки: JDK 21
zypper install java-21-openjdk-devel

