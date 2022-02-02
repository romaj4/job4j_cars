CREATE TABLE if NOT EXISTS engine (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE if NOT EXISTS cars (
    id SERIAL PRIMARY KEY,
    brand varchar NOT NULL,
    model varchar NOT NULL,
    engine_id int NOT NULL references engine(id),
    carBodies_id int NOT NULL references carBodies(id)
);

CREATE TABLE if NOT EXISTS driver (
    id SERIAL PRIMARY KEY,
    name varchar not null
);

CREATE TABLE if NOT EXISTS history_owner (
    id SERIAL PRIMARY KEY,
    driver_id int not null references driver(id),
    car_id int not null references car(id)
);

CREATE TABLE IF NOT EXISTS ads (
    id serial PRIMARY KEY,
    created timestamp,
    description text,
    is_sold boolean NOT NULL,
    car_id NOT NULL REFERENCES car(id),
    user_id NOT NULL REFERENCES users(id),
    photo_id NOT NULL REFERENCES photo(id)
);

create table if not exists carBody (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE if NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email varchar NOT NULL UNIQUE,
    name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS photo (
    id serial primary key,
    path text
);