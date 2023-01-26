DROP TABLE IF EXISTS superhero_power;
DROP TABLE IF EXISTS Assistant;
DROP TABLE IF EXISTS Superhero;
DROP TABLE IF EXISTS Power;



CREATE TABLE Superhero (id serial PRIMARY KEY,
                        Name VARCHAR(50),
                        Alias VARCHAR(50),
                        Origin VARCHAR(50)
);

CREATE TABLE Assistant (id serial PRIMARY KEY,
                        Name VARCHAR(50)
);


CREATE TABLE Power (id serial PRIMARY KEY,
                    Name VARCHAR(50),
                    Description text);
			