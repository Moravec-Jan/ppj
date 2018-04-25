CREATE TABLE country
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE town
(
	id INT NOT NULL,
    name VARCHAR(60) NOT NULL,
    country_id INT NOT NULL,
    PRIMARY KEY (id)

);
ALTER TABLE town ADD FOREIGN KEY (country_id) REFERENCES country (id);
CREATE INDEX fk_town_country_id ON town (country_id);