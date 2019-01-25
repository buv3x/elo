CREATE TABLE elo.person
(
    name varchar(42) NOT NULL,
    surname varchar(42) NOT NULL,
    id int NOT NULL AUTO_INCREMENT,
    gender tinyint(1) NOT NULL,
    PRIMARY KEY (id)
)