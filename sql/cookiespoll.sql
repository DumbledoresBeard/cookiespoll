DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE users (
id serial PRIMARY KEY,
login VARCHAR (30) UNIQUE NOT NULL,
password VARCHAR (150),
first_name VARCHAR (20),
last_name VARCHAR (30),
role VARCHAR (20)
)


CREATE TABLE cookie (
id serial PRIMARY KEY,
name VARCHAR (30) UNIQUE NOT NULL,
description VARCHAR (150),
file_data BYTEA,
cookie_adding_status VARCHAR (20),
rating int,
user_id int,
FOREiGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)
