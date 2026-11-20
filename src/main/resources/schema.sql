--Crée la table salles
CREATE TABLE salles
(
    nom      NVARCHAR(25) PRIMARY KEY,
    capacite int default 5,
);

--Crée la table reservations
CREATE TABLE reservations
(
    id          BIGINT IDENTITY PRIMARY KEY,
    nom_employe NVARCHAR(250) NOT NULL,
    date        DATE          NOT NULL,
    heure_debut TIME          NOT NULL,
    heure_fin   TIME          NOT NULL,
    duree       BIGINT default 0,
    nom_salle   NVARCHAR(25)  NOT NULL,
    CONSTRAINT chk_heure_valide CHECK (heure_debut < heure_fin)
);

ALTER TABLE reservations
    add constraint FK_reservations_salles foreign key (nom_salle) references salles (nom);


INSERT INTO salles(nom, capacite)
VALUES ('Niveau01_S1', 15);
INSERT INTO salles(nom, capacite)
VALUES ('Niveau01_S2', 25);
INSERT INTO salles(nom, capacite)
VALUES ('Niveau02_S1', 50);
INSERT INTO salles(nom, capacite)
VALUES ('Niveau02_S2', 30);
INSERT INTO salles(nom, capacite)
VALUES ('Niveau03_S1', 5);
INSERT INTO salles(nom, capacite)
VALUES ('Niveau03_S2', 10);


INSERT INTO reservations(date, heure_debut, heure_fin, nom_salle, duree, nom_employe)
values ('2025-09-15', '10:00', '13:00', 'Niveau01_S1', '180', 'Anne La Rousse');