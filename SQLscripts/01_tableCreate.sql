Drop Table If Exists Superhero;
Drop Table If Exists Power;
Drop Table If Exists Assistant;


Create table Superhero (id serial PRIMARY KEY,
					   Name varchar(50),
						Alias varchar(50),
						Origin varchar(50) 
					   );
			
Create table Assistant (id serial PRIMARY KEY,
					   Name varchar(50)
					   );
				
				
Create table Power (id serial PRIMARY KEY,
				   Name varchar(50),
				   Description text);
			