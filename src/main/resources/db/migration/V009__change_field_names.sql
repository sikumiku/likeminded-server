ALTER TABLE event
  RENAME COLUMN photo_file_path TO image_base_64;

ALTER TABLE event_image
  RENAME COLUMN image_path TO image_base_64;

ALTER TABLE game
  RENAME COLUMN image_file_path TO image_base_64;

ALTER TABLE "group"
  RENAME COLUMN image_file_path TO image_base_64;

ALTER TABLE "user"
  RENAME COLUMN profile_img_file_path TO image_base_64;

ALTER TABLE stock_profile_image
  RENAME COLUMN file_path TO image_base_64;