INSERT INTO users VALUES (1, 'mkyong', 'mkyong', 'mkyong@gmail.com');
INSERT INTO users VALUES (2, 'alex', 'alex', 'alex@yahoo.com');
INSERT INTO users VALUES (3, 'joel', 'joel', 'joel@gmail.com');

INSERT INTO auditorium VALUES (1, 'A' , 100);
INSERT INTO auditorium VALUES (2, 'B' , 200);

INSERT INTO vipSeats VALUES (1,1,1);
INSERT INTO vipSeats VALUES (2,1,2);
INSERT INTO vipSeats VALUES (3,1,3);

INSERT INTO vipSeats VALUES (4,2,4);
INSERT INTO vipSeats VALUES (5,2,5);
INSERT INTO vipSeats VALUES (6,2,6);

INSERT INTO events VALUES (1, 'EVENT', 15.5, 'MID');
INSERT INTO events VALUES (2, 'EVENT2', 17.5, 'HIGH');

INSERT INTO eventAirDates VALUES (1, 1, parsedatetime('30-11-2016 18:30', 'dd-MM-yyyy hh:mm'), 1);
INSERT INTO eventAirDates VALUES (2, 1, parsedatetime('30-11-2016 20:30', 'dd-MM-yyyy hh:mm'), 1);

INSERT INTO eventAirDates VALUES (3, 2, parsedatetime('20-12-2016 20:30', 'dd-MM-yyyy hh:mm'), 2);
INSERT INTO eventAirDates VALUES (4, 2, parsedatetime('21-11-2016 22:30', 'dd-MM-yyyy hh:mm'), 2);