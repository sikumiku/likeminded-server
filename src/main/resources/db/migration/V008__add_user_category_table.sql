CREATE TABLE user_category (
  id SERIAL PRIMARY KEY NOT NULL,
  user_id int  NOT NULL,
  category_id int NOT NULL,
  active boolean NOT NULL,
  created_time timestamp NOT NULL,
  updated_time timestamp NOT NULL
);