CREATE TABLE elo.result
(
	id int NOT NULL AUTO_INCREMENT,
    person int NOT NULL,
    competition int NOT NULL,
    result int NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (person, competition),
    FOREIGN KEY (competition) REFERENCES elo.competition (id),
    FOREIGN KEY (person) REFERENCES elo.person (id)
)