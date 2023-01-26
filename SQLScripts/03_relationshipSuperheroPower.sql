DROP TABLE IF EXISTS superhero_power;

CREATE TABLE superhero_power();
ALTER TABLE superhero_power
ADD COLUMN superhero_id int REFERENCES superhero(id),
ADD COLUMN power_id int REFERENCES power(id);

ALTER TABLE superhero_power
ADD CONSTRAINT PK_Superhero_power PRIMARY KEY (superhero_id, power_id);