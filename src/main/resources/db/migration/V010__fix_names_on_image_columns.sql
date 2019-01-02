ALTER TABLE event
  RENAME COLUMN image_base_64 TO image_base64;

ALTER TABLE event_image
  RENAME COLUMN image_base_64 TO image_base64;

ALTER TABLE game
  RENAME COLUMN image_base_64 TO image_base64;

ALTER TABLE "group"
  RENAME COLUMN image_base_64 TO image_base64;

ALTER TABLE "user"
  RENAME COLUMN image_base_64 TO image_base64;

ALTER TABLE stock_profile_image
  RENAME COLUMN image_base_64 TO image_base64;