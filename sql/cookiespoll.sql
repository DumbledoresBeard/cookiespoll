DROP DATABASE IF EXISTS cookiespoll;
CREATE DATABASE `cookiespoll`;
USE `cookiespoll`;

CREATE TABLE cookie (
id serial PRIMARY KEY,
name VARCHAR (30) UNIQUE NOT NULL,
description VARCHAR (150),
file_data BYTEA
)