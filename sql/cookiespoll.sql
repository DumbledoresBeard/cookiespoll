DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE users (
id VARCHAR(150) PRIMARY KEY,
login VARCHAR (30) UNIQUE NOT NULL,
name VARCHAR (20),
role VARCHAR (20)
);


CREATE TABLE cookies (
id SERIAL PRIMARY KEY,
name VARCHAR (30) UNIQUE NOT NULL,
description VARCHAR (150),
file_data BYTEA,
cookie_adding_status VARCHAR (20),
<<<<<<< HEAD
rating numeric(5,2),
user_id VARCHAR(150),
=======
rating NUMERIC(5,2),
user_id INT,
>>>>>>> origin/feature/cookie-poll
FOREiGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)

CREATE TABLE cookie_user_rating (
user_id VARCHAR(150),
cookie_id int,
rating int,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (cookie_id) REFERENCES cookie(id) ON DELETE CASCADE
)
