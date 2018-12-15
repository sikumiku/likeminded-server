-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2018-12-15 10:37:35.527

-- tables
-- Table: address
CREATE TABLE address (
  id  SERIAL PRIMARY KEY NOT NULL,
  address_line varchar(45)  NULL,
  city varchar(45)  NOT NULL,
  postcode varchar(10)  NOT NULL,
  countrycode varchar(3)  NOT NULL,
  user_id int  NULL,
  event_id int  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: category
CREATE TABLE category (
  id  SERIAL PRIMARY KEY NOT NULL,
  name varchar(45)  NOT NULL,
  icon_file_path text  NOT NULL,
  description text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: event
CREATE TABLE event (
  id  SERIAL PRIMARY KEY NOT NULL,
  name varchar(45)  NOT NULL,
  public boolean  NOT NULL,
  photo_file_path text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL,
  user_id int  NOT NULL
);

-- Table: event_category
CREATE TABLE event_category (
  id  SERIAL PRIMARY KEY NOT NULL,
  event_id int  NOT NULL,
  category_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: event_image
CREATE TABLE event_image (
  id  SERIAL PRIMARY KEY NOT NULL,
  image_name varchar(45)  NOT NULL,
  image_path varchar(100)  NOT NULL,
  event_id int  NOT NULL
);

-- Table: event_time
CREATE TABLE event_time (
  id  SERIAL PRIMARY KEY NOT NULL,
  start_time smallint  NOT NULL,
  end_time smallint  NOT NULL,
  start_date date  NOT NULL,
  end_date date  NOT NULL,
  event_id int  NOT NULL
);

-- Table: favorite_game
CREATE TABLE favorite_game (
  id  SERIAL PRIMARY KEY NOT NULL,
  user_id int  NOT NULL,
  game_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: game
CREATE TABLE game (
  id  SERIAL PRIMARY KEY NOT NULL,
  name varchar(45)  NOT NULL,
  description text  NULL,
  image_file_path text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: game_category
CREATE TABLE game_category (
  id  SERIAL PRIMARY KEY NOT NULL,
  category_id int  NOT NULL,
  game_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: group
CREATE TABLE "group" (
  id  SERIAL PRIMARY KEY NOT NULL,
  name varchar(45)  NOT NULL,
  description text  NULL,
  image_file_path text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: group_category
CREATE TABLE group_category (
  id  SERIAL PRIMARY KEY NOT NULL,
  group_id int  NOT NULL,
  category_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: role
CREATE TABLE role (
  id  SERIAL PRIMARY KEY NOT NULL,
  name text  NOT NULL,
  description text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: stock_profile_image
CREATE TABLE stock_profile_image (
  id  SERIAL PRIMARY KEY NOT NULL,
  name varchar(45)  NOT NULL,
  file_path text  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: user
CREATE TABLE "user" (
  id  SERIAL PRIMARY KEY NOT NULL,
  username varchar(20)  NOT NULL,
  firstname varchar(45)  NULL,
  lastname varchar(45)  NULL,
  birthday date  NULL,
  profile_img_file_path text  NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL,
  email varchar(100)  NOT NULL,
  password varchar(45)  NOT NULL
);

-- Table: user_event
CREATE TABLE user_event (
  id  SERIAL PRIMARY KEY NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL,
  user_id int  NOT NULL,
  event_id int  NOT NULL
);

-- Table: user_group
CREATE TABLE user_group (
  id  SERIAL PRIMARY KEY NOT NULL,
  user_id int  NOT NULL,
  group_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- Table: user_log
CREATE TABLE user_log (
  id  SERIAL PRIMARY KEY NOT NULL,
  query_string varchar(200)  NOT NULL,
  request_method varchar(10)  NOT NULL,
  request_name varchar(45)  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL,
  user_id int  NOT NULL
);

-- Table: user_role
CREATE TABLE user_role (
  role_id int  NOT NULL,
  user_id int  NOT NULL
);

-- Table: user_stock_profile_image
CREATE TABLE user_stock_profile_image (
  id  SERIAL PRIMARY KEY NOT NULL,
  user_id int  NOT NULL,
  stock_profile_image_id int  NOT NULL,
  active boolean  NOT NULL,
  created_time timestamp  NOT NULL,
  updated_time timestamp  NOT NULL
);

-- foreign keys
-- Reference: address_event (table: address)
ALTER TABLE address ADD CONSTRAINT address_event
FOREIGN KEY (event_id)
REFERENCES event (id)
;

-- Reference: address_user (table: address)
ALTER TABLE address ADD CONSTRAINT address_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: event_category_category (table: event_category)
ALTER TABLE event_category ADD CONSTRAINT event_category_category
FOREIGN KEY (category_id)
REFERENCES category (id)
;

-- Reference: event_category_event (table: event_category)
ALTER TABLE event_category ADD CONSTRAINT event_category_event
FOREIGN KEY (event_id)
REFERENCES event (id)
;

-- Reference: event_image_event (table: event_image)
ALTER TABLE event_image ADD CONSTRAINT event_image_event
FOREIGN KEY (event_id)
REFERENCES event (id)
;

-- Reference: event_time_event (table: event_time)
ALTER TABLE event_time ADD CONSTRAINT event_time_event
FOREIGN KEY (event_id)
REFERENCES event (id)
;

-- Reference: event_user (table: event)
ALTER TABLE event ADD CONSTRAINT event_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: favorite_game_game (table: favorite_game)
ALTER TABLE favorite_game ADD CONSTRAINT favorite_game_game
FOREIGN KEY (game_id)
REFERENCES game (id)
;

-- Reference: favorite_game_user (table: favorite_game)
ALTER TABLE favorite_game ADD CONSTRAINT favorite_game_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: game_category_category (table: game_category)
ALTER TABLE game_category ADD CONSTRAINT game_category_category
FOREIGN KEY (category_id)
REFERENCES category (id)
;

-- Reference: game_category_game (table: game_category)
ALTER TABLE game_category ADD CONSTRAINT game_category_game
FOREIGN KEY (game_id)
REFERENCES game (id)
;

-- Reference: group_category_category (table: group_category)
ALTER TABLE group_category ADD CONSTRAINT group_category_category
FOREIGN KEY (category_id)
REFERENCES category (id)
;

-- Reference: group_category_group (table: group_category)
ALTER TABLE group_category ADD CONSTRAINT group_category_group
FOREIGN KEY (group_id)
REFERENCES "group" (id)
;

-- Reference: user_event_event (table: user_event)
ALTER TABLE user_event ADD CONSTRAINT user_event_event
FOREIGN KEY (event_id)
REFERENCES event (id)
;

-- Reference: user_event_user (table: user_event)
ALTER TABLE user_event ADD CONSTRAINT user_event_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: user_group_group (table: user_group)
ALTER TABLE user_group ADD CONSTRAINT user_group_group
FOREIGN KEY (group_id)
REFERENCES "group" (id)
;

-- Reference: user_group_user (table: user_group)
ALTER TABLE user_group ADD CONSTRAINT user_group_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: user_log_user (table: user_log)
ALTER TABLE user_log ADD CONSTRAINT user_log_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: user_role_role (table: user_role)
ALTER TABLE user_role ADD CONSTRAINT user_role_role
FOREIGN KEY (role_id)
REFERENCES role (id)
;

-- Reference: user_role_user (table: user_role)
ALTER TABLE user_role ADD CONSTRAINT user_role_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- Reference: user_stock_image_stock_profile_image (table: user_stock_profile_image)
ALTER TABLE user_stock_profile_image ADD CONSTRAINT user_stock_image_stock_profile_image
FOREIGN KEY (stock_profile_image_id)
REFERENCES stock_profile_image (id)
;

-- Reference: user_stock_image_user (table: user_stock_profile_image)
ALTER TABLE user_stock_profile_image ADD CONSTRAINT user_stock_image_user
FOREIGN KEY (user_id)
REFERENCES "user" (id)
;

-- End of file.

