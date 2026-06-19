-- Seed-Daten NUR fuer den lokalen H2-Test (wird ueber run.bat geladen).
-- Auf eurem MySQL wird das NICHT ausgefuehrt (laeuft nur bei eingebetteter H2-DB).
-- Passwort fuer ALLE drei Test-User = "password" (frisch erzeugter, gueltiger BCrypt-Hash).
-- (Der Original-Hash aus eurem DB-Skript gehoerte zu keinem bekannten Passwort.)
-- MERGE statt INSERT -> idempotent, vertraegt Neustarts.
MERGE INTO users (username, password, email, user_description, role) KEY(username)
VALUES ('Samvel',    '$2a$10$jxgcwAZz5PVEwePUdtHXlu36Fm2U2dKjcY3dkXrUI8u8Df4EKeeMu', 'samvel@voxera.de',    'Developer', 'USER');
MERGE INTO users (username, password, email, user_description, role) KEY(username)
VALUES ('Dustin',    '$2a$10$jxgcwAZz5PVEwePUdtHXlu36Fm2U2dKjcY3dkXrUI8u8Df4EKeeMu', 'dustin@voxera.de',    'Developer', 'USER');
MERGE INTO users (username, password, email, user_description, role) KEY(username)
VALUES ('Vladyslav', '$2a$10$jxgcwAZz5PVEwePUdtHXlu36Fm2U2dKjcY3dkXrUI8u8Df4EKeeMu', 'vladyslav@voxera.de', 'Developer', 'USER');
