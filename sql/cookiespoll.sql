DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE users(
id VARCHAR(150) PRIMARY KEY,
login VARCHAR (30) UNIQUE NOT NULL,
name VARCHAR (20),
role VARCHAR (20)
);

CREATE TABLE cookies(
id SERIAL PRIMARY KEY,
name VARCHAR (30) UNIQUE NOT NULL,
description VARCHAR (150),
file_data BYTEA,
cookie_adding_status VARCHAR (20),
rating REAL,
user_id VARCHAR(150),
FOREiGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE cookie_user_rating(
user_id VARCHAR(150),
cookie_id int,
rating int,
PRIMARY KEY (user_id, cookie_id),
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (cookie_id) REFERENCES cookies(id) ON DELETE CASCADE
);

CREATE TABLE admins(
id SERIAL PRIMARY KEY,
login VARCHAR (30) UNIQUE NOT NULL
);
