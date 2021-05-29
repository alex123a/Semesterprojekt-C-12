CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    user_password CHAR(160) NOT NULL
);

CREATE TABLE producer(
    id INTEGER,
	PRIMARY KEY (id),
	FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE genre(
    id SERIAL,
    genre_name VARCHAR(30) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE category(
    id SERIAL,
    category_name VARCHAR(30) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE production(
    id INTEGER,
    own_production_id VARCHAR(30) UNIQUE,
    production_name VARCHAR(50) NOT NULL,
    description VARCHAR(3000),
    year SMALLINT,
    genre_id INTEGER,
    category_id INTEGER,
    producer_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (genre_id) REFERENCES genre(id),
	FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (producer_id) REFERENCES producer(id) ON DELETE CASCADE
);

CREATE TABLE production_approval(
    id SERIAL,
    own_production_id VARCHAR(30) UNIQUE,
    production_name VARCHAR(50) NOT NULL,
    description VARCHAR(3000),
    year SMALLINT,
    genre_id INTEGER,
    category_id INTEGER,
    producer_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (genre_id) REFERENCES genre(id),
	FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (producer_id) REFERENCES producer(id) ON DELETE CASCADE
);

CREATE TABLE rightsholder(
    id INTEGER,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    rightsholder_description VARCHAR(1000),
	PRIMARY KEY (id)
);

CREATE TABLE rightsholder_approval(
    id SERIAL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    rightsholder_description VARCHAR(1000),
	PRIMARY KEY (id)
);

CREATE TABLE appears_in(
    id SERIAL,
    production_id INTEGER NOT NULl,
    rightsholder_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (production_id) REFERENCES production(id) ON DELETE CASCADE,
	FOREIGN KEY (rightsholder_id) REFERENCES rightsholder(id) ON DELETE CASCADE
);

CREATE TABLE appears_in_approval(
	id SERIAL PRIMARY KEY,
        production_id INTEGER,
        rightsholder_id INTEGER
);

CREATE TABLE title(
    id SERIAL,
    title VARCHAR(30) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE role(
    id SERIAL,
    appears_in_id INTEGER NOT NULL,
    title_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (appears_in_id) REFERENCES appears_in(id) ON DELETE CASCADE,
	FOREIGN KEY (title_id) REFERENCES title(id)
);

CREATE TABLE role_approval(
    id SERIAL,
    appears_in_id INTEGER NOT NULL,
    title_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE rolename(
    role_id INTEGER,
    rolename VARCHAR(50) NOT NULL,
	PRIMARY KEY (role_id),
	FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE rolename_approval(
    role_id SERIAL,
    rolename VARCHAR(50),
	PRIMARY KEY (role_id)
);

CREATE TABLE administrator(
    id INTEGER,
	PRIMARY KEY (id),
	FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE approval_status(
    id SERIAL,
    status VARCHAR(12) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE administrator_notification(
    id SERIAL,
    notification_text VARCHAR(1000) NOT NULL,
    production_id INTEGER NOT NULL,
    approval_status_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (approval_status_id) REFERENCES approval_status(id)
);

CREATE TABLE approved(
    notification_id INTEGER,
    approved_time TIMESTAMP NOT NULL,
    approved_by INTEGER,
	FOREIGN KEY (notification_id) REFERENCES administrator_notification(id) ON DELETE CASCADE,
	FOREIGN KEY (approved_by) REFERENCES administrator(id) ON DELETE CASCADE
);

CREATE TABLE producer_notification(
    id SERIAL,
    notification_text VARCHAR(1000) NOT NULL,
    viewed BOOLEAN NOT NULL,
    production_id INTEGER,
	PRIMARY KEY (id)
);

