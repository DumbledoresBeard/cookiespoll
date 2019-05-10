DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE users (
id SERIAL PRIMARY KEY,
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
rating INT,
user_id INT,
FOREiGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)
