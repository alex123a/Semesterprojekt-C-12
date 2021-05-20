    INSERT INTO title(title) VALUES ('Billedkunstner'),('Billed- og lydredigering'),('Casting'),('Colourgrading'),('Dirigent'),('Drone'),('Dukkeføre'),('Dukkeskaber'),('Fortæller'),('Fotograf'),('Forlæg'),('Grafiske designer'),('Indtaler'),('Kapelmester'),('Klipper'),('Koncept'),('Konsulent'),('Kor'),('Koreografi'),('Lyd/tonemester'),('Lydredigering'),('Lys'),('Medvirkende'),('Musikalsk arrangement'),('Orkester/band'),('Oversætter'),('Producent'),('Produktionskoordinator/leder'),('Programansvarlig'),('Redaktion/tilrettelæggelse'),('Redaktør'),('Rekvisitør'),('Scenograf'),('Scripter/producerassistent'),('Special effects'),('Sponsorer'),('Tegnefilm/animation'),('Tekstere'),('Tekst og musik'),('Uhonoreret indsats');
INSERT INTO category(category_name) VALUES ('Serier'),('Film'),('Reality'),('Underholdning'),('Comedy'),('Dokumentar'),('Rejser og Eventyr'),('Livsstil'),('Magasiner');
INSERT INTO genre(genre_name) VALUES ('Action'),('Børn'),('Dokumentar'),('Drama'),('Familie'),('Gyser'),('Komedie'),('Romantik'),('Thriller');
INSERT INTO approval_status(status) VALUES ('Waiting'), ('Approved'), ('Not Approved');

INSERT INTO users(username, user_password)
VALUES ('admin','54f60aa9191cff3e169ab4c8faa374e94e3972d4653a3cb334f57c9599356258992ece1ae9358babe156612457bf193aa26f14f4ba1a82d722ee42d9c14586f19a906219b5ac298d7ff96cc942ebd4da'),
       ('badehotellet','47d37272f20eab4375e3a90b783e92a57cb850ce9669315799db5fa30f80d60741691f81a2926c4a1d1e4b526567566b2b1bb141e1828ade001629aa1bbd335c8a09568167630099a09f98200c9d852c'),
       ('zulu','aeb11271c42897fabb40c47a3f34e181db33b91bac984469138a11507176cfd441d2d3f78a477f8c7577dfb9e8f558f20d55ba13ae0e13606050c3aab88e8485040aced92cf9bda86d9ce3e94f82bb86');

INSERT INTO producer(id)
VALUES (2),
       (3);

INSERT INTO administrator(id)
VALUES (1);

INSERT INTO production (id, own_production_id, production_name, year, genre_id, category_id, producer_id)
VALUES (1, 'ID0001', 'Badehotellet: Det taler vi ikke om', 2021, 7, 1, 2),
       (2, 'ID0002', 'Badehotellet: Chokolade og flødeskum', 2021, 7, 1, 2),
       (3,'ID003', 'Diamantfamilien: Hvem har trusserne på?',2018,null,3,3);

INSERT INTO rightsholder(id, first_name, last_name, rightsholder_description)
VALUES(1,'Amalie', 'Dollerup', 'Amalie Mathisson Dollerup (født 15. april 1986) er en dansk skuespillerinde, der er uddannet fra skuespillerskolen ved Aarhus Teater i 2010. Hun blev kendt fra tv-serien Strisser på Samsø, og har siden medvirket i flere film og tv-serier, heriblandt Badehotellet.'),
       (2,'Bodil', 'Jørgensen', 'Bodil Jørgensen (født 3. marts 1961 i Vejle) er en dansk skuespiller opvokset i Vejen. Kendt fra en masse danske film.'),
       (3,'Lars', 'Ranthe', 'Lars Kristian Ranthe (født 26. august 1969) er en dansk skuespiller. LarsRanthe begyndte sin skuespilkarriere i starten af 1980''erne i filmen Gummi Tarzan (1981) og tv-serien Crash: Truslen fra det sorte hul (1984), før han i 1995 blev optaget på Odense Teater, hvor han dimitterede fra som skuespiller i 1998.'),
       (4,'Jens Jacob', 'Tychsen', 'Jens Jacob Tychsen (født 19. november 1975 i Aarhus) er en dansk skuespiller. Han er uddannet fra Skuespillerskolen ved Aarhus Teater i 1998 og har siden medvirket i en række forestillinger på teatre over hele landet og diverse film og serier.'),
        (5,'Anne Louise','Hassing','Anne Louise Hassing Nielsen (født 17. september 1967 i Horsens) er en dansk skuespillerinde, der er uddannet fra Statens Teaterskole i 1997.'),
      (6,'Anette','Støvelbæk','Anette Støvelbæk (født 27. juli 1967) er en dansk skuespiller. Hun fik sit gennembrud med rollen som Olympia i Lone Scherfigs film Italiensk for begyndere. På tv har hun medvirket i TV 2-serien Forsvar, og som Frk. Toft i DR-serien Krøniken.'),
      (7,'Cecilie','Stenspil','Cecilie Maria Stenspil (født 22. oktober 1979 i Glostrup) er en dansk skuespiller uddannet fra Skuespillerskolen ved Odense Teater i 2006. Hun har haft hovedroller i en stor række forestillinger på flere danske teatre.'),
      (8,'Peter Hesse','Overgaard','Peter Hesse Overgaard, der er af gøglerslægt, rejste i 1971 til USA for at arbejde på amatørbasis med teater. Han vendte tilbage et par år efter og blev uddannet på Team Teatret i Herning. Efter afsluttet skuespilleruddannelse i 1977 var han i Vancouver i Canada for at medvirke i en Brecht-forestilling. I årene 1978 til 1981 var han medlem af teatergruppen Dueslaget og begyndte i 1982 at arbejde freelance på en række teatre i København, bl.a. Fiolteatret, Husets Teater, Bådteatret, Det Danske Teater, Det Ny Teater, Cafe Teatret og Mammut Teatret.'),
      (9,'Birthe','Neumann','Birthe Neumann er datter af overlærer Svend E. Frederiksen og hustru Betty Neumann Nielsen. Hun blev uddannet fra Statens Teaterskole i 1972 og blev straks derefter engageret på Det Kongelige Teater, hvor hun har medvirket i mange forskellige forestillinger. Hun har spillet Miranda i "Stormen", Leonora i "Julestuen", moderen i "Syng så, Baby", hustruen Marianne i "Scener fra et ægteskab", den onde søster i "To Søstre", Bélise i "Det kvindelige selskab", Madam Rust i "Sparekassen".'),
      (10,'Sonja','Oppenhagen','Sonja Oppenhagen Behrend er datter af forlagsleder Lüche Oppenhagen Behrend (død 1986) og hustru sekretær Dagmar Kragh (09-01-1920 - 08-11-2015). Hun blev optaget på Det Kgl. Teaters Balletskole som 9-årig og videreførte uddannelsen fra 15 til 18 år gammel hos balletpædagogen Edite Frandsen. Hun fik dog problemer med fødderne, så hun måtte holde op igen. Seerne kunne opleve hende i julen 1964, da hun juleaften havde titelpartiet i Hans Brenaas TV- ballet "Den Lille Pige Med Svovlstikkerne".'),
      (11,'Merete','Mærkedahl','Merete Damgaard Mærkedahl, der er opvokset i Nors mellem Thisted og Hanstholm, blev uddannet på Skuespillerskolen ved Aarhus Teater 2005-2009. Hun har på Århus Teater medvirkert i "Teaterkoncert med sange af Tom Waits" (2009), "Loveplay" (2008), "Kejserens Nye Klæder" (2007) og "Peter Pan" (2004). Fra august 2009 var hun ansat i ensemblet på Aalborg Teater. I 2009 medvirkede Merete Mærkedahl desuden i radiospillet "Reservatet". I 2018 fik hun prisen som "Årets revytalent". Sammen med Thomas Evers Poulsen vandt hun i 2020 trofæet i årets "Vild med dans".'),
      (12,'Ulla Vejby','Kristensen','Ulla Vejby Kristensen voksede op på familiens gård i Klovborg lidt uden for Horsens. Hun tog studentereksamen fra Tørring Gymnasium og flyttede efter gymnasiet til Aarhus og blev uddannet på Skuespillerskolen ved Aarhus Teater 2008-2012. Hun fik i 2013 sin første tv-rolle i "Badehotellet" som stuepigen Edith. Ulla Vejby Kristensen blev i oktober 2013 gift med Michael Lysgaard.'),
      (13,'Mia','Højgaard','Mia Helene Højgaard gik på Det Kongelige Teaters Balletskole i Odense indtil 9. klasse og fravalgte herefter dansekarrieren for at satse på skuespillet, da hun fik en birolle i filmen "Kapgang". Mia Helene Højgaard medvirker i 4. sæson af TV2''s succesfulde tv-serie "Badehotellet", hvor hun spiller stuepigen Fies rebelske lillesøster Ane.'),
      (14,'Lucia Vinde','Dirchsen','Lucia Vinde Dirchsen blev uddannet som skuespiller på Den Danske Scenekunstskole i København i 2019. Under sin uddannelse har hun spillet med i "Richard III" i rollerne som Lady Anne, Richmond, Stanley m.fl. Hun har også medvirket i kortfilmene "Imens" og "Hvad hvis alle farver var blå?". I 2019/2020 medvirker hun i "Hobbitten" og "Richard’ III" på Det Kongelige Teater.'),
      (15,'Sigurd Holmen','le Dous','Sigurd Holmen Le Dous, der er søn af radioværten Lars le Dous, blev uddannet på Skuespillerskolen ved Aarhus Teater i 2009. Han blev rigtig kendt i rollen som den homoseksuelle reklametegner Philip Dupont i tv-serien "Badehotellet".'),
       (16,'Mikael','Zilmer','Indspilningsleder på flere film.'),
       (17,'Katja','Kofoed-Hansen','Indspilningsleder for TV2'),
       (18,'Caroline Bille','Frandsen','Har medvirket i flere film og blandt andet serien badehotellet.'),
       (19,'Michael Bille','Frandsen','Michael Bille Frandsen blev uddannet som producer på Den Danske Filmskole i årene 1993 til 1997.'),
       (20,'Carsten','Søsted','Carsten Søsted har endvidere været filmklipper på tv-serierne "Arvingerne" og "Dicte". Han har også i 2008 instrueret dokumentarfilmen "...Og det var Danmark".'),
       (21,'Søren','Sætter-Larsen','Søren Sætter-Lassen er søn af grosserer Ib Sætter-Lassen (24-05-1928 - 23-08-1978) og hustru designer Karin Granov (17-05-1933 - 04-01-2014). Han gik på Tranegaardsskolen i Gentofte samt på Gentofte Statsskole, hvor han tog studentereksamen i 1973. Han blev uddannet på Skuespillerskolen ved Odense Teater 1975-1978 og var ansat på samme teater til 1982. Han spillede her Konstantin i "Mågen" i 1981 og var i 1982 medstifter af Grønnegårds Teatret.'),
       (22,'Lisa','Werlinder','Lisa Werlinder har studeret ved Det Kongelige Musik Konservatorium i Stockholm (1995), og er uddannet fra Teaterskolen ved Luleå, Sverige (2000). Lisa Werlinder har adskillige teaterproduktioner på den nationale og kongelige scene i Stockholm bag sig, senest "Mary Stuart" iscenesat af Ingmar Bergman. I Danmark har man kunne opleve Lisa Werlinder i den prisbelønnede Bille August film "En sang for Martin" fra 2001.'),
       (23,'Katerina','Pitzner','Katerina Pitzner er en af de få kvinder i verden, der har tilladelse til at handle direkte på verdens største diamantbørser. Katerina har også sin egen blog i Dagbladet Børsen.'),
       (24,'Elvira','Pitzner','Elvira er datter af diamanthandler Katerina Pitzner og forretningsmanden Hans Helmgaard Kristiansen. Hun er født i Danmark, men er opvokset i Sverige de første fem år af sit liv. I 2017 medvirkede Elvira i programmet ‘Pigerne’, og siden 2018 har hun medvirket i tre sæsoner af TV2 Zulu-serien ‘Diamantfamilien sammen med sin mor og søstre. I 2020 blev hun aktuel med Dplay-programmet ‘Bare Elvira’.'),
       (25,'Thalia','Pitzner','Elvira er datter af diamanthandler Katerina Pitzner og forretningsmanden Hans Helmgaard Kristiansen. Hun er født i Danmark, men er opvokset i Sverige de første fem år af sit liv. Hun har medvirket i diamantfamilien og stjerner i trøjen. '),
       (26,'Ophelia','Pitzner','Ophelia Pitzner er det sjove indslaf i "Diamantfamilien", når hun kommer med one liners fyldt med sort humor.'),
       (27,'Peter','Skovgaard','Peter skovgaard er redaktør for Pineapple Entertainment der laver indhold til TV2'),
       (28,'Ole','Rahbek','Ole Rahbek er producent for Pineapple Entertainment der laver indhold til TV2');

INSERT INTO appears_in(id, production_id, rightsholder_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 1, 5),
       (6, 1, 6),
       (7, 1, 7),
       (8, 1, 8),
       (9, 1, 9),
       (10, 1, 10),
       (11, 1, 11),
       (12, 1, 12),
       (13, 1, 13),
       (14, 1, 14),
       (15, 1, 15),
       (16, 1, 16),
       (17, 1, 17),
       (18, 1, 18),
       (19, 1, 19),
       (20, 1, 20),
       (21, 2, 1),
       (22, 2, 2),
       (23, 2, 3),
       (24, 2, 4),
       (25, 2, 5),
       (26, 2, 6),
       (27, 2, 7),
       (28, 2, 8),
       (29, 2, 9),
       (30, 2, 10),
       (31, 2, 11),
       (32, 2, 12),
       (33, 2, 13),
       (34, 2, 14),
       (35, 2, 15),
       (36, 2, 16),
       (37, 2, 17),
       (38, 2, 18),
       (39, 2, 19),
       (40, 2, 20),
       (41, 2, 21),
       (42, 2, 22),
       (43, 3, 23),
       (44, 3, 24),
       (45, 3, 25),
       (46, 3, 26),
       (47, 3, 27),
       (48, 3, 28);


INSERT INTO role (id, appears_in_id, title_id)
VALUES (1, 1, 23),
       (2, 2, 23),
       (3, 3, 23),
       (4, 4, 23),
       (5, 5, 23),
       (6, 6, 23),
       (7, 7, 23),
       (8, 8, 23),
       (9, 9, 23),
       (10, 10, 23),
       (11, 11, 23),
       (12, 12, 23),
       (13, 13, 23),
       (14, 14, 23),
       (15, 15, 23),
       (16, 16, 31),
       (17, 17, 31),
       (18, 18, 34),
       (19, 19, 27),
       (20, 20, 15),
       (21, 21, 23),
       (22, 22, 23),
       (23, 23, 23),
       (24, 24, 23),
       (25, 25, 23),
       (26, 26, 23),
       (27, 27, 31),
       (28, 28, 27);

INSERT INTO rolename (role_id, rolename)
VALUES (1, 'Amanda Berggren'),
       (2, 'Molly Andersen'),
        (3, 'Georg Madsen'),
       (4, 'Edward Weise'),
       (5, 'Therese Madsen'),
       (6, 'Alice Frigh'),
       (7, 'Helene Weyse'),
       (8, 'Hjalmer Aurland'),
       (9, 'Olga Fjeldsø'),
       (10, 'Lydia Vetterstrøm'),
       (11, 'Otilia'),
       (12, 'Edith Marie Jensen'),
       (13, 'Ane Kjær'),
       (14, 'Bertha Frigh'),
       (15, 'Philip Dupont'),
       (21, 'August Molin'),
       (22, 'Alma Molin');

