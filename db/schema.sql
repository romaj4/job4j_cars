CREATE TABLE if NOT EXISTS engine (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE if NOT EXISTS car (
    id SERIAL PRIMARY KEY,
    brand varchar NOT NULL,
    model varchar NOT NULL,
    engine_id int NOT NULL  references engine(id)
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