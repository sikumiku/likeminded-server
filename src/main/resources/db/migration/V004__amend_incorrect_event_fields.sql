ALTER TABLE event
RENAME COLUMN opentopublic TO open_to_public;

ALTER TABLE event
RENAME COLUMN unlimitedparticipants TO unlimited_participants;

ALTER TABLE event
RENAME COLUMN maxparticipants TO max_participants;

ALTER TABLE event
RENAME COLUMN organizeruserid TO organizer_user_id;
