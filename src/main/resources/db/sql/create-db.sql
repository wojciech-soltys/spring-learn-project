CREATE TABLE users (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  firstName VARCHAR(30),
  lastName VARCHAR(30),
  email  VARCHAR(50)
);

CREATE TABLE auditorium (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name VARCHAR(30),
  numberOfSeats BIGINT
);

CREATE TABLE vipSeats(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    auditoriumId INTEGER,
    numberOfSeat BIGINT
);

ALTER TABLE vipSeats ADD FOREIGN KEY (auditoriumId) REFERENCES auditorium(id);

ALTER TABLE vipSeats ADD CONSTRAINT VIP_SEATS_UNIQUE UNIQUE(auditoriumId,numberOfSeat);

CREATE TABLE events(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(30),
    basePrice DOUBLE,
    rating VARCHAR(5) check (rating in ('LOW', 'MID', 'HIGH'))
);

CREATE TABLE eventAirDates(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    eventId   INTEGER,
    airDate    TIMESTAMP,
    auditoriumId INTEGER
);

ALTER TABLE eventAirDates ADD FOREIGN KEY (eventId) REFERENCES events(id);
ALTER TABLE eventAirDates ADD FOREIGN KEY (auditoriumId) REFERENCES auditorium(id);
ALTER TABLE eventAirDates ADD CONSTRAINT EVENT_AIR_DATES_UNIQUE UNIQUE(airDate,auditoriumId);