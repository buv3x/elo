CREATE TABLE elo.competition
(
    name varchar(142) NOT NULL,
    id int NOT NULL AUTO_INCREMENT,
    gender tinyint(1) NOT NULL,
    date date NOT NULL,
    type tinyint(1) NOT NULL,
    level tinyint(1) NOT NULL,
    PRIMARY KEY (id)
)