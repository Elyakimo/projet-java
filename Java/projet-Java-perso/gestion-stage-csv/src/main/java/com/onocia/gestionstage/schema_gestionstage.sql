-- ============================================================
-- Projet : Gestionnaire de stages
-- Script de création du schéma PostgreSQL
-- ============================================================

-- Création de la base de données (à exécuter une seule fois, hors transaction)
-- CREATE DATABASE gestionstage;
-- \c gestionstage

-- ============================================================
-- Table ORGANISATION
-- Représente les entreprises/structures d'accueil
-- ============================================================
CREATE TABLE organisation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nom_entreprise VARCHAR(255),
    mail_entreprise VARCHAR(255),
    telephone_entreprise INTEGER NOT NULL,
    adresse_entreprise VARCHAR(255)
);

-- ============================================================
-- Table STAGIAIRE
-- Représente l'étudiant effectuant le stage
-- ============================================================
CREATE TABLE stagiaire (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    telephone_etudiant INTEGER,
    mail_etudiant VARCHAR(255) NOT NULL,
    adresse_etudiant VARCHAR(255),
    classe VARCHAR(255) NOT NULL
);

-- ============================================================
-- Table TUTEUR
-- Représente le tuteur en entreprise, rattaché à une organisation
-- ============================================================
CREATE TABLE tuteur (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nom_tuteur VARCHAR(255) NOT NULL,
    prenom_tuteur VARCHAR(255) NOT NULL,
    numero_tuteur INTEGER NOT NULL,
    id_entreprise UUID,
    CONSTRAINT fk_tuteur_organisation
        FOREIGN KEY (id_entreprise)
        REFERENCES organisation(id)
        ON DELETE RESTRICT
);

-- ============================================================
-- Type énuméré pour le statut d'un stage
-- (mêmes valeurs que l'enum StatutStage côté Java)
-- ============================================================
CREATE TYPE statut_stage_enum AS ENUM (
    'CANDIDATURE',
    'EN_COURS',
    'TERMINE',
    'REFUSE'
);

-- ============================================================
-- Table STAGE
-- Table centrale reliant organisation, tuteur et stagiaire
-- ============================================================
CREATE TABLE stage (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    statut_stage statut_stage_enum NOT NULL,
    date_debut TIMESTAMP NOT NULL,
    date_fin TIMESTAMP NOT NULL,
    id_entreprise UUID,
    id_tuteur UUID,
    id_stagiaire UUID,
    CONSTRAINT fk_stage_organisation
        FOREIGN KEY (id_entreprise)
        REFERENCES organisation(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_stage_tuteur
        FOREIGN KEY (id_tuteur)
        REFERENCES tuteur(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_stage_stagiaire
        FOREIGN KEY (id_stagiaire)
        REFERENCES stagiaire(id)
        ON DELETE RESTRICT
);

-- ============================================================
-- Requête de consultation (exemple) : vue consolidée d'un stage
-- ============================================================
-- SELECT s.statut_stage, s.date_debut, s.date_fin,
--        o.nom_entreprise, t.nom_tuteur, st.nom AS nom_stagiaire
-- FROM stage s
-- JOIN organisation o ON s.id_entreprise = o.id
-- JOIN tuteur t ON s.id_tuteur = t.id
-- JOIN stagiaire st ON s.id_stagiaire = st.id;
