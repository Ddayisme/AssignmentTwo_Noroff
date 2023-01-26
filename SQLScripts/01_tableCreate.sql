DROP TABLE If Exists Superhero;
DROP TABLE If Exists Power;
DROP TABLE If Exists Assistant;


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
			