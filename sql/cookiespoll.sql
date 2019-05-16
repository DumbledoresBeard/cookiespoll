DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE users(
cookieId SERIAL PRIMARY KEY,
login VARCHAR (30) UNIQUE NOT NULL,
name VARCHAR (20),
role VARCHAR (20)
);


CREATE TABLE cookies(
cookieId SERIAL PRIMARY KEY,
name VARCHAR (30) UNIQUE NOT NULL,
description VARCHAR (150),
file_data BYTEA,
cookie_adding_status VARCHAR (20),
rating REAL,
user_id INT,
FOREiGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE cookie_user_rating(
user_id int,
cookie_id int,
rating int,
PRIMARY KEY (user_id, cookie_id),
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (cookie_id) REFERENCES cookies(id) ON DELETE CASCADE
);
