INSERT INTO production (id, own_production_id, production_name, year, genre_id, category_id, producer_id)
VALUES (1, 'ID0001', 'Badehotellet', 2021, 7, 1, 1),
       (2, 'ID0002', 'hvem vil være millionær', 1998, NULL, 4, 1);

INSERT INTO rightsholder(id, first_name, last_name, rightsholder_description)
VALUES(1, 'John', 'Smith', 'En meget sej beskrivelse'),
       (2, 'Seje', 'Reje', 'En meget sej reje'),
       (3, 'Janik', 'Sunke', 'Janik er en kvinde på 30 år'),
       (4, 'Hans', 'Pilgaard', 'En eller anden beskrivelse');


INSERT INTO appears_in(id, production_id, rightsholder_id)
VALUES (1, 1, 1),
       (2, 1, 3),
       (3, 2, 2),
       (4, 2, 4);

INSERT INTO role (id, appears_in_id, title_id)
VALUES (1, 1, 23),
       (2, 2, 23),
       (3, 3, 29),
       (4, 4, 23);

INSERT INTO rolename (role_id, rolename)
VALUES (1, 'hr. Smithies'),
       (2, 'Frøken Petersen');

