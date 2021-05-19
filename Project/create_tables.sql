CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    user_password CHAR(160) NOT NULL
);

CREATE TABLE producer(
    id SERIAL PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE producer_notification(
    id SERIAL PRIMARY KEY,
    producer_id INT REFERENCES producer(id) ON DELETE CASCADE,
    notification_text VARCHAR(1000) NOT NULL,
    viewed BOOLEAN NOT NULL,
    production_id INT REFERENCES production(id) ON DELETE CASCADE
);

CREATE TABLE genre(
    id SERIAL PRIMARY KEY,
    genre_name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE category(
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE production(
    id INT PRIMARY KEY,
    own_production_id VARCHAR(30) UNIQUE,
    production_name VARCHAR(50) NOT NULL,
    year SMALLINT,
    genre_id INT REFERENCES genre(id),
    category_id INT REFERENCES category(id),
    producer_id INT REFERENCES producer(id) NOT NULL ON DELETE CASCADE
);

CREATE TABLE production_approval(
    id SERIAL PRIMARY KEY,
    own_production_id VARCHAR(30) UNIQUE,
    production_name VARCHAR(50) NOT NULL,
    year SMALLINT,
    genre_id INT REFERENCES genre(id),
    category_id INT REFERENCES category(id),
    producer_id INT REFERENCES producer(id) NOT NULL ON DELETE CASCADE
);

CREATE TABLE rightsholder(
    id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    rightsholder_description VARCHAR(1000)
);

CREATE TABLE rightsholder_approval(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    rightsholder_description VARCHAR(1000)
);

CREATE TABLE appears_in(
    id SERIAL PRIMARY KEY,
    production_id INT REFERENCES production(id) NOT NULL ON DELETE CASCADE,
    rightsholder_id INT REFERENCES rightsholder(id) NOT NULL ON DELETE CASCADE
);

CREATE TABLE appears_in_approval(
    production_id INT REFERENCES production(id) ON DELETE CASCADE,
    rightsholder_id INT REFERENCES rightsholder(id) ON DELETE CASCADE,
    PRIMARY KEY (production_id, rightsholder_id)
);

CREATE TABLE title(
    id SERIAL PRIMARY KEY,
    title VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE role(
    id SERIAL PRIMARY KEY,
    appears_in_id INT REFERENCES appears_in(id) NOT NULL ON DELETE CASCADE,
    title_id INT REFERENCES title(id) NOT NULL
);

CREATE TABLE role_approval(
    id SERIAL PRIMARY KEY,
    appears_in_id INT NOT NULL,
    title_id INT NOT NULL
);

CREATE TABLE rolename(
    role_id INT PRIMARY KEY REFERENCES role(id) ON DELETE CASCADE,
    rolename VARCHAR(50) NOT NULL
);

CREATE TABLE rolename_approval(
    role_id SERIAL PRIMARY KEY,
    rolename VARCHAR(50)
);

CREATE TABLE administrator(
    id SERIAL PRIMARY KEY REFERENCES users(id) ON DELETE CASECADE
);

CREATE TABLE approval_status(
    id SERIAL PRIMARY KEY,
    status VARCHAR(12) UNIQUE NOT NULL
);

CREATE TABLE administrator_notification(
    id SERIAL PRIMARY KEY,
    notification_text VARCHAR(1000) NOT NULL,
    production_id INT REFERENCES production(id) NOT NULL ON DELETE CASCADE,
    approval_status_id INT REFERENCES approval_status(id) NOT NULL
);

CREATE TABLE approved(
    notification_id INT REFERENCES administrator_notification(id),
    approved_time TIMESTAMP NOT NULL,
    approved_by INT REFERENCES administrator(id) ON DELETE CASCADE
);

--INSERT DATA

INSERT INTO title(title) VALUES ('Billedkunstnere'),('Billed- og lydredigering'),('Casting'),('Colourgrading'),('Dirigenter'),('Drone'),('Dukkefører'),('Dukkeskaber'),('Fortæller'),('Fotografer'),('Forlæg'),('Grafiske designere'),('Indtalere'),('Kapelmester'),('Klipper'),('Koncept'),('Konsulent'),('Kor'),('Koreografi'),('Lyd/tonemester'),('Lydredigering'),('Lys'),('Medvirkende'),('Musikalsk arrangement'),('Orkester/band'),('Oversættere'),('Producent'),('Produktionskoordinator/leder'),('Programansvarlige'),('Redaktion/tilrettelæggelse'),('Redaktøren'),('Rekvisitør'),('Scenografer'),('Scripter/producerassistent'),('Special effects'),('Sponsorer'),('Tegnefilm/animation'),('Tekstere'),('Tekst og musik'),('Uhonoreret indsats');
INSERT INTO category(category_name) VALUES ('Serier'),('Film'),('Reality'),('Underholdning'),('Comedy'),('Dokumentar'),('Rejser og Eventyr'),('Livsstil'),('Magasiner');
INSERT INTO genre(genre_name) VALUES ('Action'),('Børnefilm'),('Dokumentar'),('Drama'),('Familiefilm'),('Gyser'),('Komedie'),('Romantik'),('Thriller');
INSERT INTO approval_status(status) VALUES ('Waiting'), ('Approved'), ('Not Approved');
