#!/bin/env bash

echo "удаление предудыщей сборки"
rm -rf target/*

./build_without_maven.sh
if [[ "$?" != "0" ]] ; then
    echo "ошибка сборки jar"
    exit 1
fi

echo "запуск"
java -jar target/app.jar
