# 1.Создать пул запросов для сложной выборки из базы данных.

### Выборка информации о форумах и связанных с ними пользователях:
Этот запрос объединяет таблицы "forum" и "users", чтобы получить информацию о форумах и их создателях.

    SELECT f.id AS forum_id, f.title AS forum_title, u.username AS user_creator
    FROM db_university."forum" f
    LEFT JOIN db_university."users" u ON f.user_id = u.id;

### Выборка списка новостей с комментариями:
В этом запросе мы выбираем новости и связанные с ними комментарии из таблиц "news" и "post".

    SELECT n.id AS news_id, n.title AS news_title, p.post_text AS comment_text
    FROM db_university."news" n
    LEFT JOIN db_university."post" p ON n.id = p.user_id;

### Выборка футбольных клубов и числа игроков и тренеров в каждом клубе:
Этот запрос объединяет таблицы "football_club", "players" и "coaches", чтобы подсчитать количество игроков и тренеров в каждом футбольном клубе.

    SELECT fc.club_name, COUNT(p.id) AS num_players, COUNT(c.id) AS num_coaches
    FROM db_university."football_club" fc
    LEFT JOIN db_university."players" p ON fc.id = p.football_club_id
    LEFT JOIN db_university."coaches" c ON fc.id = c.football_club_id
    GROUP BY fc.club_name;

### Выборка матчей, в которых участвует конкретный футбольный клуб:
Этот запрос выбирает информацию о матчах, в которых участвует футбольный клуб с определенным идентификатором.

    SELECT ms.id AS match_id, ms.match_date, ms.match_location
    FROM db_university."match_schedule" ms
    WHERE ms.home_team_id = 1 OR ms.away_team_id = 1;

### Выборка последних 5 новостей, опубликованных для определенного футбольного клуба:
Этот запрос выбирает пять последних новостей, опубликованных для определенного футбольного клуба.

    SELECT n.id AS news_id, n.title AS news_title, n.publication_date
    FROM db_university."news" n
    WHERE n.football_club_id = 1
    ORDER BY n.publication_date DESC
    LIMIT 5;

### Выборка пользователей с определенной ролью:
Этот запрос выбирает всех пользователей с ролью "admin".

    SELECT * FROM db_university."users"
    WHERE role = 'admin';

### Выборка футбольных клубов, у которых нет стадиона:
Этот запрос находит футбольные клубы, у которых нет связанного стадиона.

    SELECT fc.club_name
    FROM db_university."football_club" fc
    LEFT JOIN db_university."stadiums" s ON fc.id = s.football_club_id
    WHERE s.id IS NULL;

### Выборка игроков, у которых номер меньше 10 и которые играют за определенный футбольный клуб:
Этот запрос выбирает игроков с номерами меньше 10, играющих за определенный футбольный клуб.

    SELECT p.player_name, p.player_number
    FROM db_university."players" p
    WHERE p.player_number < 10 AND p.football_club_id = 1;

### Выборка тренеров, чьи имена начинаются с определенной буквы:
Этот запрос находит тренеров, имена которых начинаются с определенной буквы.

    SELECT c.coach_name
    FROM db_university."coaches" c
    WHERE c.coach_name LIKE 'A%';

### Выборка матчей, проведенных в определенном месяце:
Этот запрос выбирает матчи, которые были проведены в определенном месяце.

    SELECT ms.id AS match_id, ms.match_date, ms.match_location
    FROM db_university."match_schedule" ms
    WHERE EXTRACT(MONTH FROM ms.match_date) = 7; -- Июль

### Выборка количества комментариев к каждой новости:
Этот запрос подсчитывает количество комментариев к каждой новости.

    SELECT n.id AS news_id, n.title AS news_title, COUNT(p.id) AS num_comments
    FROM db_university."news" n
    LEFT JOIN db_university."post" p ON n.id = p.user_id
    GROUP BY n.id, n.title;

### Выборка пользователей, у которых не указан адрес электронной почты:
Этот запрос находит пользователей, у которых не заполнено поле "email".

    SELECT * FROM db_university."users"
    WHERE email IS NULL;

# 2.Создать пул запросов для получения представлений в базе данных.

### 1) INNER JOIN (Внутреннее соединение) для получения информации о футбольных клубах и их игроках:
Этот запрос объединяет таблицы "football_club" и "players", чтобы получить информацию о футбольных клубах и их игроках. В результате будут включены только те строки, где есть соответствующие записи в обеих таблицах.

    SELECT fc.club_name, p.player_name
    FROM db_university."football_club" fc
    INNER JOIN db_university."players" p ON fc.id = p.football_club_id;

### 2) LEFT JOIN (Левое соединение) для получения информации о футбольных клубах и их тренерах:
Этот запрос объединяет таблицы "football_club" и "coaches" с использованием LEFT JOIN, чтобы получить информацию о футбольных клубах и их тренерах. Результат будет включать все футбольные клубы и их тренеров, даже если у некоторых клубов нет тренеров.

    SELECT fc.club_name, c.coach_name
    FROM db_university."football_club" fc
    LEFT JOIN db_university."coaches" c ON fc.id = c.football_club_id;

### 3) RIGHT JOIN (Правое соединение) для получения информации о футбольных клубах и матчах:
Этот запрос объединяет таблицы "football_club" и "match_schedule" с использованием RIGHT JOIN, чтобы получить информацию о футбольных клубах и матчах. Результат будет включать все матчи и соответствующие им футбольные клубы, даже если у некоторых клубов нет матчей.

    SELECT fc.club_name, ms.match_date, ms.match_location
    FROM db_university."football_club" fc
    RIGHT JOIN db_university."match_schedule" ms ON fc.id = ms.home_team_id;

### 4)FULL JOIN (Полное соединение) для получения информации о пользователях и комментариях:
Этот запрос объединяет таблицы "users" и "post" с использованием FULL JOIN, чтобы получить информацию о пользователях и их комментариях. Результат будет включать всех пользователей и все комментарии, даже если у некоторых пользователей нет комментариев, и наоборот.

    SELECT u.username, p.post_text
    FROM db_university."users" u
    FULL JOIN db_university."post" p ON u.id = p.user_id;

### 5)CROSS JOIN (Кросс-соединение) для получения всех возможных комбинаций пользователей и футбольных клубов:
Этот запрос использует CROSS JOIN, чтобы создать комбинации всех пользователей и футбольных клубов. В результате будет получено представление, которое содержит все возможные сочетания пользователей и клубов.

    SELECT u.username, fc.club_name
    FROM db_university."users" u
    CROSS JOIN db_university."football_club" fc;

### 6) SELF JOIN (Самосоединение) для получения информации о связанных пользователях:
Этот запрос использует SELF JOIN на таблице "users", чтобы найти связанных пользователей, например, тех, у которых совпадает адрес электронной почты.

    SELECT u1.username AS user1, u2.username AS user2
    FROM db_university."users" u1
    JOIN db_university."users" u2 ON u1.email = u2.email
    WHERE u1.id < u2.id; -- Исключает дубликаты

### 7) OUTER JOIN (в данном случае, LEFT OUTER JOIN) для получения информации о футбольных клубах и их тренерах, включая футбольные клубы без тренеров:
Этот запрос использует LEFT OUTER JOIN для включения всех футбольных клубов (независимо от наличия у них тренеров) в результат выборки, а также связанных с ними тренеров, если они есть.

    SELECT fc.club_name, c.coach_name
    FROM db_university."football_club" fc
    LEFT OUTER JOIN db_university."coaches" c ON fc.id = c.football_club_id;



# 3.Создать пул запросов для получения сгруппированных данных.

### 1) GROUP BY + Агрегирующие функции:
Этот запрос группирует новости по футбольным клубам и выводит сумму числа новостей для каждого клуба.

    SELECT football_club_id, COUNT(*) AS num_news
    FROM db_university."news"
    GROUP BY football_club_id;

### 2) PARTITION OVER + Оконные функции:
Этот запрос использует оконную функцию для расчета средней длины биографий тренеров внутри каждого футбольного клуба.

    SELECT id, coach_name, biography, AVG(LENGTH(biography)) OVER (PARTITION BY football_club_id) AS avg_biography_length
    FROM db_university."coaches";

### 3) PARTITION и PARTITION OVER:
В этом запросе мы используем оконную функцию ROW_NUMBER(), которая нумерует тренеров внутри каждого футбольного клуба (football_club_id) в алфавитном порядке по их именам (coach_name). PARTITION BY football_club_id определяет разделение результатов на группы по football_club_id, и каждая группа нумеруется отдельно.
    
    SELECT
      football_club_id,
      coach_name,
      biography,
      ROW_NUMBER() OVER (PARTITION BY football_club_id ORDER BY coach_name) AS coach_rank
    FROM
      db_university."coaches";

### 4) HAVING:
Этот запрос группирует новости по футбольным клубам и выводит только те клубы, у которых более 5 новостей.

    SELECT football_club_id, COUNT(*) AS num_news
    FROM db_university."news"
    GROUP BY football_club_id
    HAVING COUNT(*) > 5;

### 5) UNION:
Этот запрос объединяет результаты двух запросов: список пользователей с ролью "admin" и список пользователей с ролью "editor".

    SELECT id, username, role FROM db_university."users" WHERE role = 'admin'
    UNION
    SELECT id, username, role FROM db_university."users" WHERE role = 'editor';

# 4. Создать пул запросов, необходимых для сложных операций над данными в БД.

### 1) EXISTS:
Этот запрос проверяет наличие новостей, опубликованных для футбольного клуба с определенным ID, и выводит сообщение, если новости существуют.

    IF EXISTS (SELECT 1 FROM db_university."news" WHERE football_club_id = 1) THEN
        RAISE NOTICE 'Новости существуют для футбольного клуба с ID 1.';
    ELSE
        RAISE NOTICE 'Новости отсутствуют для футбольного клуба с ID 1.';
    END IF;
    
### 2) INSERT INTO SELECT:
Этот запрос вставляет данные из одной таблицы в другую. Например, он может использоваться для копирования новостей определенного футбольного клуба в архивную таблицу.

    INSERT INTO db_university."news_archive" (id, title, news_text, publication_date, user_id, football_club_id)
    SELECT id, title, news_text, publication_date, user_id, football_club_id
    FROM db_university."news"
    WHERE football_club_id = 1;
    
### 3) CASE:
Этот запрос использует оператор CASE для создания вычисляемого столбца, который определяет, является ли число голосов "положительным", "отрицательным" или "нейтральным" на основе определенного порога.

    SELECT
        id,
        comment_text,
        votes,
        CASE
            WHEN votes > 10 THEN 'положительный'
            WHEN votes < -10 THEN 'отрицательный'
            ELSE 'нейтральный'
        END AS sentiment
    FROM db_university."comments";
    
### 4) EXPLAIN:
Этот запрос использует оператор EXPLAIN для анализа выполнения определенного запроса и отображения плана выполнения запроса. Это полезно для оптимизации производительности запросов.

    EXPLAIN SELECT id, username FROM db_university."users" 
    WHERE role = 'admin';


