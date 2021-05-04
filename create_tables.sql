CREATE TABLE producer(
    id SERIAL PRIMARY KEY, 
    username VARCHAR(30) NOT NULL, 
    producer_password BYTEA NOT NULL
);

CREATE TABLE producer_notification(
    id SERIAL PRIMARY KEY,
    producer_id INT REFERENCES producer(id),
    notification_text VARCHAR(1000),
    viewed BOOLEAN
);

CREATE TABLE genre(
    id SERIAL PRIMARY KEY,
    genre_name VARCHAR(30)
);

CREATE TABLE types(
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(30)
);

CREATE TABLE production(
    id INT PRIMARY KEY,
    own_production_id VARCHAR(30),
    production_name VARCHAR(50) NOT NULL,
    year SMALLINT,
    genre_id INT REFERENCES genre(id),
    type_id INT REFERENCES types(id),
    producer_id INT REFERENCES producer(id)
);

CREATE TABLE production_approval(
    id SERIAL PRIMARY KEY,
    own_production_id VARCHAR(30),
    production_name VARCHAR(50) NOT NULL,
    year SMALLINT,
    genre_id INT REFERENCES genre(id),
    type_id INT REFERENCES types(id),
    producer_id INT REFERENCES producer(id)
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
    production_id INT REFERENCES production(id),
    rightsholder_id INT REFERENCES rightsholder(id)
);

CREATE TABLE appears_in_approval(
    production_id INT REFERENCES production(id),
    rightsholder_id INT REFERENCES rightsholder(id),
    PRIMARY KEY (production_id, rightsholder_id)
);

CREATE TABLE title(
    id SERIAL PRIMARY KEY,
    title VARCHAR(30)
);

CREATE TABLE role(
    id SERIAL PRIMARY KEY,
    appears_in_id INT REFERENCES appears_in(id),
    title_id INT REFERENCES title(id)
);

CREATE TABLE role_approval(
    id SERIAL PRIMARY KEY,
    appears_in_id INT,
    title_id INT
);

CREATE TABLE rolename(
    role_id INT PRIMARY KEY REFERENCES role(id),
    rolename VARCHAR(50)
);

CREATE TABLE rolename_approval(
    role_id SERIAL PRIMARY KEY,
    rolename VARCHAR(50)
);

CREATE TABLE administrator(
    id SERIAL PRIMARY KEY,
    username VARCHAR(30),
    administrator_password BYTEA
);

CREATE TABLE approval_notification(
    id SERIAL PRIMARY KEY,
    notification_text VARCHAR(1000),
    production_id INT REFERENCES production(id)
    approval_status_id INT REFERENCES approval_status(id)
);

CREATE TABLE approval_status(
	id SERIAL PRIMARY KEY,
	status VARCHAR(12)
);

CREATE TABLE not_viewed(
    administrator_id INT REFERENCES administrator(id),
    notification_id INT REFERENCES administrator_notification(id)
);

--INSERT DATA

INSERT INTO title(title) VALUES ('Billedkunstnere'),('Billed- og lydredigering'),('Casting'),('Colourgrading'),('Dirigenter'),('Drone'),('Dukkefører'),('Dukkeskaber'),('Fortæller'),('Fotografer'),('Forlæg'),('Grafiske designere'),('Indtalere'),('Kapelmester'),('Klipper'),('Koncept'),('Konsulent'),('Kor'),('Koreografi'),('Lyd/tonemester'),('Lydredigering'),('Lys'),('Medvirkende'),('Musikalsk arrangement'),('Orkester/band'),('Oversættere'),('Producent'),('Produktionskoordinator/leder'),('Programansvarlige'),('Redaktion/tilrettelæggelse'),('Redaktøren'),('Rekvisitør'),('Scenografer'),('Scripter/producerassistent'),('Special effects'),('Sponsorer'),('Tegnefilm/animation'),('Tekstere'),('Tekst og musik'),('Uhonoreret indsats');
INSERT INTO approval_status(status) VALUES ('Waiting'), ('Approved'), ('Not Approved');
