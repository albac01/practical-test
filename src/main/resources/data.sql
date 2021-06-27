DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Candidate;
DROP TABLE IF EXISTS Position;

CREATE TABLE Candidate (
	id VARCHAR(12) PRIMARY KEY,
	name VARCHAR(256),
	lastname VARCHAR(256),
	address VARCHAR(256),
	cellphone VARCHAR(30),
	cityname VARCHAR(256)
);

CREATE TABLE Position (
	id INT PRIMARY KEY,
	name VARCHAR(256)
);

CREATE TABLE Employee (
	id VARCHAR(12) PRIMARY KEY,
	person VARCHAR(12),
	position INT,
	salary INT
);