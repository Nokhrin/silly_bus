#!/bin/env bash

THIS_DIR=$(dirname $(readlink -f $0))
echo "переходим в каталог проекта $THIS_DIR"
cd $THIS_DIR

##################

OUT_DIR=target/classes
SRC_DIR=src
DOWNLOAD_DIR=target/lib
JAR_FILE=target/app.jar
MAIN_CLASS=pop.lesson04.ScannerInput

# каталог под скомпилированные классы
[ ! -e "$OUT_DIR" ] && mkdir -p "$OUT_DIR"

# каталог для скаченных библиоте
[ ! -e "$DOWNLOAD_DIR" ] && mkdir -p "$DOWNLOAD_DIR"

################

# $1 - файл
# $2 - url
_download_lib() {
    [ ! -e "$1" ] && (
        echo "download $2"
        curl $2 > $1
        if [[ "$?" != "0" ]] ; then
            echo "не смог скачать файл"
            exit 1
        fi
    )
}

_download_dependencies() {
    _download_lib $DOWNLOAD_DIR/slf4j-api-1.7.36.jar https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar
    _download_lib $DOWNLOAD_DIR/jcommander-1.82.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
    _download_lib $DOWNLOAD_DIR/testng-7.7.1.jar https://repo1.maven.org/maven2/org/testng/testng/7.7.1/testng-7.7.1.jar
}

_build() {
    echo "сборка проекта"

    echo "компиляция"
    # Найти все .java файлы и передать их javac
    find "$SRC_DIR" -name "*.java" -print0 | xargs -0 javac -cp "$DOWNLOAD_DIR/*" -d "$OUT_DIR"

    if [[ "$?" != "0" ]] ; then
        echo "ошибка компиляции"
        exit 1
    fi

    echo "сборка jar"
    jar --create --file $JAR_FILE -C $OUT_DIR .
    if [[ "$?" != "0" ]] ; then
        echo "ошибка сборки jar"
        exit 1
    fi

    echo "указние main class $MAIN_CLASS"
    jar --update --file $JAR_FILE --main-class $MAIN_CLASS
    if [[ "$?" != "0" ]] ; then
        echo "ошибка сборки jar"
        exit 1
    fi
}

_download_dependencies
_build
