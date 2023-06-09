# Пора в путешествие

Это мобильное приложение на Android, которое представляет из себя список актуальных дешевых авиаперелетов и детальную информацию о каждом перелете.

## Описание

**Первый экран** представляет из себя список актуальных дешевых авиаперелетов. Каждая ячейка списка содержит:

- Город отправления
- Город прибытия
- Дата отправления
- Дата возвращения
- Цена в рублях
- Иконка “Лайк”

**Второй экран** - это детализация перелета с кнопкой “Лайк”. Второй экран открывается при выборе одной из ячеек первого экрана. Должна быть возможность вернуться к первому экрану назад.

Второй экран содержит данные по выбранному перелету (город отправления, город прибытия, дата отправления, дата возвращения, цена в рублях) и кнопку “Лайк”.

Кнопка лайк имеет два состояния:

1. Перелет нравится
2. Перелет не нравится

## Используемые технологии

- Kotlin
- Retrofit
- Room
- Coroutines

## Использование API

Для получения списка авиаперелетов используется следующий API:

```bash
curl ‘https://vmeste.wildberries.ru/api/avia-service/twirp/aviaapijsonrpcv1.WebAviaService/GetCheap’
-H ‘accept: application/json, text/plain, /’
-H ‘content-type: application/json’
–data-raw ‘{“startLocationCode”:“LED”}’
–compressed
