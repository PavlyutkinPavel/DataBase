# 1. Триггеры:

### 1. **Триггер для логирования создания нового поста в форуме:**

   Этот триггер добавляет запись в таблицу логов при создании нового поста в форуме.

   ```sql
  CREATE OR REPLACE FUNCTION log_new_forum_post()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO log_table (event_type, event_description, event_timestamp)
    VALUES ('New Forum Post', 'New post created with title: ' || NEW.post_text, NOW());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER forum_post_creation_log
AFTER INSERT ON db_university."post"
FOR EACH ROW
EXECUTE PROCEDURE log_new_forum_post();
   ```

### 2. **Триггер для логирования создания нового форума:**

   Этот триггер добавляет запись в таблицу логов при создании нового форума.

   ```sql
CREATE OR REPLACE FUNCTION log_new_forum()
RETURNS TRIGGER AS $$
BEGIN
     INSERT INTO db_university.log_table (event_type, event_description, event_timestamp)
     VALUES ('New', 'New line created with title: ' || NEW.title, NOW());
     RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER forum_creation_log
AFTER INSERT ON db_university."forum"
FOR EACH ROW
EXECUTE PROCEDURE log_new_forum();
   ```

### 3. **Триггер для автоматического обновления статуса футбольного клуба при добавлении новости:**

   Этот триггер изменяет статус футбольного клуба на "Популярен" при добавлении новости о клубе.

   ```sql
   CREATE OR REPLACE FUNCTION update_club_status()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE db_university."football_club"
    SET status = 'Popular'
    WHERE id = NEW.football_club_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_club_status_trigger
AFTER INSERT ON db_university."news"
FOR EACH ROW
EXECUTE PROCEDURE update_club_status();
   ```

### 4. **Триггер для автоматического обновления количества побед при добавлении результатов матча:**

   Этот триггер увеличивает количество побед футбольного клуба при добавлении результатов матча, если клуб является победителем.

   ```sql
   CREATE OR REPLACE FUNCTION update_club_wins()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE db_university."football_club"
    SET wins = wins + 1
    WHERE id = NEW.winner_id;

    RETURN NEW;
END;

$$ LANGUAGE plpgsql;

CREATE TRIGGER update_club_wins_trigger
AFTER INSERT ON db_university."match_results"
FOR EACH ROW
EXECUTE PROCEDURE update_club_wins();

   ```

### 5. **Триггер для автоматического уменьшения количества доступных билетов при покупке:**

   Этот триггер уменьшает количество доступных билетов при покупке билетов на матч.

   ```sql
   CREATE OR REPLACE FUNCTION reduce_available_tickets()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.match_id IS NOT NULL THEN
        UPDATE db_university.match_schedule
        SET available_tickets = available_tickets - 1
        WHERE id = NEW.match_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reduce_available_tickets_trigger
AFTER INSERT ON db_university."shop"
FOR EACH ROW
EXECUTE PROCEDURE reduce_available_tickets();

   ```

### 6. **Триггер для автоматического добавления нового пользователя в группу фан-клуба при регистрации:**

  Этот триггер добавляет пользователя в группу фан-клуба при успешной регистрации.
  
   ```sql
   CREATE OR REPLACE FUNCTION add_to_fan_club()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.club_id IS NOT NULL THEN
        INSERT INTO db_university."fan_club_members" (user_id, club_id)
        VALUES (NEW.id, NEW.club_id);
    ELSE
        -- Если club_id не указан, можно использовать значение по умолчанию, например, 1
        INSERT INTO db_university."fan_club_members" (user_id, club_id)
        VALUES (NEW.id, 1);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Код создания триггера add_to_fan_club_trigger
CREATE TRIGGER add_to_fan_club_trigger
AFTER INSERT ON db_university."users"
FOR EACH ROW
EXECUTE PROCEDURE add_to_fan_club();
   ```

# 2.Процедуры:

### 1. **Добавление нового пользователя:**

   ```sql
   CREATE OR REPLACE FUNCTION create_new_user(
    p_username VARCHAR(30),
    p_email VARCHAR,
    p_password VARCHAR,
    p_role VARCHAR(20)
)
RETURNS VOID
AS $$
BEGIN
    INSERT INTO db_university.users (username, email, password, role)
    VALUES (p_username, p_email, p_password, p_role);
END;
$$ LANGUAGE plpgsql;

   ```
   Вызов процедуры:
```sql
SELECT create_new_user('НовыйПользователь', 'новыйпользователь@example.com', 'пароль123', 'обычный_пользователь');
   ```


### 2. **Добавление новости:**

   ```sql
   CREATE OR REPLACE FUNCTION add_news(
    IN p_title VARCHAR(50),
    IN p_news_text TEXT,
    IN p_publication_date DATE,
    IN p_user_id INTEGER,
    IN p_football_club_id INTEGER
)
RETURNS VOID
AS $$
BEGIN
    INSERT INTO db_university.news (title, news_text, publication_date, user_id, football_club_id)
    VALUES (p_title, p_news_text, p_publication_date, p_user_id, p_football_club_id);
END;
$$ LANGUAGE plpgsql;

   ```

Вызов процедуры:
```sql
SELECT add_news('Новость 1', 'Текст новости', '2023-01-01', 1, 1);
   ```

### 3. **Процедура для добавления нового тренера:**

   ```sql
CREATE OR REPLACE FUNCTION add_coach(
    IN p_coach_name VARCHAR(50),
    IN p_biography VARCHAR,
    IN p_achievements VARCHAR,
    IN p_football_club_id INTEGER
)
RETURNS VOID
AS $$
BEGIN
    INSERT INTO db_university.coaches (coach_name, biography, achievements, football_club_id)
    VALUES (p_coach_name, p_biography, p_achievements, p_football_club_id);
END;
$$ LANGUAGE plpgsql;

   ```
Вызов процедуры:
```sql
SELECT add_coach('Новый тренер', 'Биография тренера', 'Достижения тренера', 1);
   ```

### 4. **Процедура для обновления информации о матче:**

   ```sql
  CREATE OR REPLACE FUNCTION update_match_info(
    IN p_match_id BIGINT,
    IN p_final_score VARCHAR(10),
    IN p_description VARCHAR
)
RETURNS VOID
AS $$
BEGIN
    UPDATE db_university.match_results
    SET final_score = p_final_score, description = p_description
    WHERE id = p_match_id;
END;
$$ LANGUAGE plpgsql;
   ```
Вызов процедуры:
```sql
SELECT update_match_info(1, '2-1', 'Захватывающий матч!');
   ```

### 5. **Обновление информации о футбольном клубе:**


   ```sql
CREATE OR REPLACE FUNCTION update_football_club_info(
    IN p_club_id INTEGER,
    IN p_new_achievements VARCHAR
)
RETURNS VOID
AS $$
BEGIN
    UPDATE db_university.football_club
    SET achievements = p_new_achievements
    WHERE id = p_club_id;
END;
$$ LANGUAGE plpgsql;

   ```
Вызов процедуры:
```sql
SELECT update_football_club_info(1, 'Новые достижения клуба');
   ```

### 6. **Удаление новости по её идентификатору:**


   ```sql
   CREATE OR REPLACE FUNCTION delete_news_by_id(p_news_id INTEGER)
RETURNS VOID
AS $$
BEGIN
    DELETE FROM db_university.news
    WHERE id = p_news_id;
END;
$$ LANGUAGE plpgsql;

   ```
Вызов процедуры:
```sql
SELECT delete_news_by_id(1);
   ```

