ALTER TABLE event
    ADD COLUMN description TEXT NOT NULL DEFAULT '',
    ADD COLUMN unlimitedParticipants BOOL,
    ADD COLUMN maxParticipants INT,
    ADD COLUMN organizerUserId INT NOT NULL DEFAULT '0';

ALTER TABLE event
  RENAME COLUMN public TO openToPublic;
