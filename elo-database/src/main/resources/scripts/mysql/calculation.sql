CREATE TABLE elo.calculation
(
    result int NOT NULL,
    id int NOT NULL AUTO_INCREMENT,
    rating double NOT NULL,
    change_rating double NOT NULL,
    type tinyint(1),
    calculator tinyint(1) NOT NULL,
    factor int,
    total_factor int,
    PRIMARY KEY (id),
    FOREIGN KEY (result) REFERENCES elo.result (id)
)