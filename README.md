# Условия домашних заданий по Software Design

## [Домашнее задание 1.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw1)
1. Цель - получить практический опыт применения динамических проверок в коде (assertions)

2. Необходимо реализовать структуру даннхы LRUCache на хешмапе и двусвязном списке. При реализации необходимо самостоятельно продумать возможные проверки pre/post-условий и инвариантов класса. Придуманные проверки необходимо добавить в код реализации в виде assertions. Класс необходимо покрыть тестами.

3. Указания:
   * Использовать LinkedHashMap напрямую нельзя.

## [Домашнее задание 2.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw2)
1. Цель - получить практический опыт реализации модульных тестов и тестов, использующих mock-объекты.

2. Необходимо реализовать компонент, который будет вычислять частоту появления твитов с определенным хештегом за последние несколько часов.

3. Для выполнения лабораторной необходимо использовать twitter api или api любойдругой социальной сети.

4. На входе компонент должен принимать:
   * Хештег, по которому будет идти поиск.
   * N - число часов, за которое необходимо построить диаграмму твитов (1 <= N <= 24)

5. На выходе нужно выдать массив из N целых чисел - каждое число в массиве определяет число твитов в соответствующий час.

6. Указания:
   * При выполнении лабораторной рекомендуется применять SOLID-принципы.
   * Кол должен быть покрыт тестами (в том числе mock-тестами и тестами с StubServer)

## [Домашнее задание 3.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw3)
1. Цель - получить практический опыт применения техник рефакторинга.

2. [Скачайте приложение.](https://github.com/akirakozov/software-design/tree/master/java/refactoring)

3. Приложение представляет собой простой web-server, который хранит информацию о товарах и их цене.

4. Поддержаны такие методы:
   * [http://localhost:8081/get-products](http://localhost:8081/get-products) - посмотреть все товары в базе
   * [http://localhost:8081/add-product?name=iphone6&price=300](http://localhost:8081/add-product?name=iphone6&price=300) - добавить новый товар
   * [http://localhost:8081/query?command=sum](http://localhost:8081/query?command=sum) - выполнить некоторый запрос с данными в базе

5. Необходимо отрефакторить этот код (логика методов не должна измениться), например:
   * убрать дублирование
   * выделить отдельный слой работы с БД
   * выделить отдельный слой формирования html-ответа
   * и тд
   
## [Домашнее задание 4.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw4)
1. Цель - получить практический опыт применения паттерна MVC (Model-View-Controller).

2. Напишите небольшое веб-приложение для работы со списками дел. Приложение должно позволять:
   * Просматривать списки дел и дела в них
   * Добавлять/удалять списки дел
   * Добавлять дела
   * Отмечать дела, как выполненные

3. Рекомендации:
   * Использовать средства spring-framework для DI (dependency injection)

## [Домашнее задание 5.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw5)
1. цель - получить практический опыт применения структурного паттерна bridge.

2. Необходимо реализовать простой визуализатор графов, используя два различных графических API. Способ визуализации графа можно выбрать самостоятельно (например, рисовать вершины по кругу). Приложение должно поддерживать две реализации графов: на спиках ребер и матрице смежностей.

3. Примечание:
   * Выбор API и реализация графа должны задаваться через аргументы командной строки при запуске приложения
   * В качестве drawing api можно использовать java.awt и javafx
   * Можно использовать любой язык и любые api для рисования (главное, чтобы они были принципиально разные)

## [Домашнее задание 6.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw6)
1. Цель - получить практический опыт применения паттернов поведения visitor и state.

2. Необходимо реализовать калькулятор, который умеет преобразовывать простые арифметические выражения в обратную польскую запись (ОПЗ) и вычислять их. Пример выражения:
> (23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2  

3. Выражение может содержать скобки, пробельные сиволы, цифры и 4 операции: +, -, *, /

4. Для вычиления выражения его необходимо сначала разбить на токены:
   * По одному токену на каждую скобку и операцию
   * Токен для целых чисел
Например:
> (30 + 2) / 8 -> LEFT NUMBER(30) PLUS NUMBER(2) RIGHT DIV NUMBER(8)

5. Далее токены преобразуются к ОПЗ, которая уже не содержит скобок и может быть легко вычислена с помощью стека.

6. Схема работы калькулятора:
   * Входной набор данных разбирается на отдельные токены Tokenizer'ом
   * ParseVisitor обходит все полученные токены и преобразует их обратно к польской записи
   * Затем токены печатаются PrintVisitor'ом
   * Значение выражения вычисляется CalcVisitor'ом
   
7. Visitor'ы могут использовать стеки и другие структуры данных, чтобы накапливать в себе промежуточные результаты.

8. Tokenizer проще всего реализовать в виде конечного автомата, который считывает по одному из символов из входного потока и преобразует их в токены. Сам автомат необходимо реализовать, используя паттер State.

9. В итоге необходимо реализовать программу, которая с консоли считывает входное выражение и выводит в консоль сначала выражение преобразованное в обратную польскую нотации, а затем вычиленное значение выражения. Если было введено некорректное выражение, необходимо вывести ошибку.

## [Домашнее задание 7.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw7)
1. Цель - получить практический опыт использования аспектов и  АОП.

2. Написать профайлер для вашего пиложения. Профайлер должен быть реализован в аспектно-ориентированной парадигме. Профайлер должен позволять:
   * Выбирать package, классы которого требуется профилировать
   * Подсчитывать сколько раз был вызван каждый метод
   * Подсчитывать среднее и суммарное время исполнения метода
   * Плюсом будет удобная визуализация результатов профилирования (например в древовидной структуре)

3. Указания:
   * Для java можно использовать spring aop или aspectJ
   * Можно использовать и другие AOP фреймворки (в том числе для других языков)

## [Домашнее задание 8.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw8)
1. Цель - получить практический опыт применения паттерна Clock при реализации тестов.

2. Необходимо реализовать интерфейс EventsStatistic, который считает события, происходящие в системе.

3. Реализация должна хранить статистику ровно за последний час и подсчитывать, сколько событий каждого типа произошло в минуту.

4. EventsStatistic:
   * incEvent(String name) - инкрементит число событий name
   * getEventStatisticByName(String name) - выдает rpm (request per minute) события name за последний час
   * getAllEventStatistic() - выдает rpm всех произошедших событий за прошедший час
   * printStatistic() - выводит в консоль rpm всех произошедших событий

5. Реализацию EventStatistic необходимо покрыть тестами, использя паттерн Clock, рассмотренный на лекции. Тесты не должны использовать sleep'ы и должны выполняться быстро.
	

## [Домашнее задание 9.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw9)
1. Цель - получить практический опыт применения акторов.

2. Необходимо реализовать поисковой агрегатор, который по запросу пользователя собирает top 5 ответов через API известных поисковиков и выдает их пользователю. Например, делает запрос в Google, Яндекс, Bing и возвращает 15 ответов (должно быть ясно, какой ответ от какой поисковой системы). Реальное API можно не использовать, а реализовать StubServer, который будет отдавать результаты в удобном формате (json, xml, protobuf и тд).

2. Архитектура приложения:
   * На каждый запрос создается master-actor, который будет собирать результаты от поисковиков.
   * Master-actor для каждого поисковика создает child-actor, которому посылает исходный "поисковый запрос".
   * Master-actor устанавливает себе receive timeout, сколько времени он будет ждать ответы от child-actor.
   * Child-actor делает запрос в соответствующий поисковый сервис и отправляет его результат в master-actor.
   * Master-actor при получении каждого ответа сохраняет их у себя, если получил все 3 ответа или прошло время таймаута, то отправялет собранный агрегированный результат на дальнейшую обработку.
   * Master-actor должен умирать после того, как вернул агрегированный результат.

3. Указания:
   * В stub-server необходимо реализовать возможность залипания, чтобы можно было проверить сценарий, когда master-actor не дождался ответа от всех поисковиков.

## [Домашнее задание 10.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw10)
1. Цель - получить практический опыт реализации "реактивных" приложений.

2. Необходимо реализовать веб-сервис для просмотра каталога товаров. В сервисе можно регистрировать новых пользователей и добавлять товары. Пользователи при регистрации указывают, в какой валюте они хотят видеть товары (доллары, евро, рубли).

3. Указания:
   * Веб-сервис должен быть полностью "реактивным" (все взаимодействие должно быть асинхронным и не блокирующим).
   * В запросах можно не проверять авторизацию пользователей, а просто указывать id пользователя.
   * Данные можно хранить в mongo и использовать "реактивный" mongo driver, в качестве http-сервиса можно использовать rxnetty-http.
   * Можно использовать и другие реактивные библиотеки и фреймворки.

## [Домашнее задание 11.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw11)
1. Цель - получить практический опыт реализации event-sourcing системы и применения принципов CQRS.

2. Необходимо реализовать информационную систему для фитнес-центра. Клиенты фитнес-центра могут приобретать и продлевать абонементы. При входе и выходе из фитнес-центра, клиент обязан приложить абонемент для прохода. Система позволяет строить различные отчеты на базе статистики собранной в ходе работы сервиса.

3. Система состоит из трёх частей:
   * Веб сервис для менеджера, возволяет: просматривать информацию об абонементах (по номеру), выдавать и продлевать абонементы.
   * Сервис отчетов, позволяет: просмотреть статистику посещений по дням, считать среднюю частоту и длительность посещений.
   * Модуль входа/выхода: пускает клиента, если у него действующий абонемент, сохраняет в базе время входа/выхода клиента.
   
4. Указания:
   * Система должна быть построена на базе event-sourcing подхода.
   * Команды и запросы должны строго разделяться (CQRS-принцип).
   * Сами события должны храниться в персистентном хранилище.
   * Сервис для менеджера и модуль входа/выхода не имеют отдельной базы, и при запросе вытаскивают все события об абонементе из хранилища событий, агрегируют их на лету.
   * Сервич отчетов, необорот, при старте вычисляет статистику и сохраняет ее в памяти, обновляя новыми событиями (но можно и хранить в отдельном персистентном хранилище).
   
## [Домашнее задание 12.](https://github.com/KramerKonstantin/Software_Design/tree/master/hw12)
1. Цель - получить практический опыт реализации интеграционных тестов с использованием docker и testcontainers.

2. Необходимо реализовать информационную систему для торговли на бирже. Для простоты, будем считать, что биржа просто хранит некоторый объем акций для каждой компании, у которых может динамически меняться цена. Любой пользователь может купить/продать необходимое ему число акций на бирже по текущей цене.

3. Система состоит из двух частей:
   * Эмулятор биржи, позволяет: добавлять новые компании и их акции, узнавать текущую цену акций и их количество на бирже, покупать акции компаний по текущей цене, динамически менять курс акций (механизм обновления курса может быть любой).
   * Личный кабинет пользователя, позволяет: заводить новых пользователей (заходить можно просто по id), добавлять денежные средства на счет пользователя, просматривать акции пользователя, их количество и текущую стоимость, просмотреть, сколько сейчас суммарно у пользователя средств, если считать все акции по текущей стоимости, покупать/продавать акции на бирже.

4. Указания:
   * Должна быть возможность упаковать эмулятор биржи в docker-контейнер.
   * Для личного кабинета необходимо написать интеграционные с биржей тесты, которые будут использовать фреймворк testcontainers или его аналог. Интеграционные тесты будут запускать эмулятор биржи в docker и ходить в него.

