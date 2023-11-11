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

   Этот триггер изменяет статус футбольного клуба на "Активен" при добавлении новости о клубе.

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

   Этот триггер уменьшает количество доступных билетов при создании нового матча.

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

### 1. **Процедура для создания нового пользователя:**

   Создайте процедуру, которая позволяет администратору или модератору создать нового пользователя, указав имя пользователя, адрес электронной почты и пароль.

   ```sql
   CREATE OR REPLACE PROCEDURE create_new_user(
       username character varying,
       email character varying,
       password character varying
   ) AS $$
   BEGIN
       INSERT INTO public."users" (username, email, password)
       VALUES (username, email, password);
   END;
   $$ LANGUAGE plpgsql;
   ```

### 2. **Процедура для добавления нового поста:**

   Создайте процедуру, которая позволяет пользователям добавить новый пост с указанием заголовка и описания.

   ```sql
   CREATE OR REPLACE PROCEDURE create_new_post(
       user_id bigint,
       title character varying,
       description character varying
   ) AS $$
   BEGIN
       INSERT INTO public."posts" (title, description, user_id)
       VALUES (title, description, user_id);
   END;
   $$ LANGUAGE plpgsql;
   ```

### 3. **Процедура для получения списка задач для конкретного пользователя:**

   Создайте процедуру, которая принимает идентификатор пользователя и возвращает список его задач.

   ```sql
   CREATE OR REPLACE PROCEDURE get_user_tasks(user_id bigint) AS $$
   BEGIN
       SELECT * FROM public."tasks" WHERE user_id = user_id;
   END;
   $$ LANGUAGE plpgsql;
   ```

### 4. **Процедура для добавления нового отзыва:**

   Создайте процедуру, которая позволяет пользователям добавлять новые отзывы с указанием текста и даты.

   ```sql
   CREATE OR REPLACE PROCEDURE create_new_review(
       user_id bigint,
       description character varying,
       create_time date
   ) AS $$
   BEGIN
       INSERT INTO public."reviews" (description, create_time, user_id)
       VALUES (description, create_time, user_id);
   END;
   $$ LANGUAGE plpgsql;
   ```

### 5. **Процедура для обновления статуса задачи:**

   Создайте процедуру, которая позволяет пользователю обновить статус задачи по ее идентификатору.

   ```sql
   CREATE OR REPLACE PROCEDURE update_task_status(
       task_id bigint,
       new_status integer
   ) AS $$
   BEGIN
       UPDATE public."tasks"
       SET "check" = (new_status = 1)
       WHERE id = task_id;
   END;
   $$ LANGUAGE plpgsql;
   ```
