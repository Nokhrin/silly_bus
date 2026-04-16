## Архитектура
![Архитектура - диаграмма](https://username.github.io/repo/architecture.png)
[Архитектура - исходник](docs/architecture.xml)



## Локальный рендеринг диаграмм

Для предпросмотра изменений в диаграммах без пуша в репозиторий:

### Требования
- Установленный [Docker](https://docs.docker.com/get-docker/)

### Команда
```shell
# Из корня проекта:
docker run --rm \
  -v $(pwd):/data \
  rlespinasse/drawio-export \
  --output /data/docs/generated \
  --input /data/docs \
  --format png \
  --format svg \
  --crop
```

### Проверка
```shell
xdg-open docs/generated/architecture.png
```