INSERT INTO users (username, password, enabled, role)
    (SELECT 'student', '$2a$10$ZDL88fenkKKgGpQ4zEmjeeiJQBH2.t7ypo6NHULMvEgZXfC9Zf9nK', '1', 'STUDENT'
        WHERE NOT EXISTS(SELECT * FROM users)) union all
    (SELECT 'teacher', '$2a$10$ZDL88fenkKKgGpQ4zEmjeeiJQBH2.t7ypo6NHULMvEgZXfC9Zf9nK', '1', 'TEACHER'
        WHERE NOT EXISTS(SELECT * FROM users)) union all
    (SELECT 'powerUser', '$2a$10$ZDL88fenkKKgGpQ4zEmjeeiJQBH2.t7ypo6NHULMvEgZXfC9Zf9nK', '1', 'POWER_USER'
        WHERE NOT EXISTS(SELECT * FROM users))