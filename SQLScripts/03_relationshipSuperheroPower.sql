DROP TABLE IF EXISTS superhero_power;

Create Table superhero_power();
Alter table superhero_power
    add column superhero_id int REFERENCES superhero(id),
    add column power_id int References power(id)