DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (userid, description, calories, datetime) VALUES
  (100000, 'breakfast', 500, make_timestamp(2017, 11, 22, 9,0,0.0)),
  (100000, 'dinner', 1000, make_timestamp(2017, 11, 22, 12,0,0.0)),
  (100001, 'lunch', 1500, TIMESTAMP '2017-11-22 10:0:0.0');