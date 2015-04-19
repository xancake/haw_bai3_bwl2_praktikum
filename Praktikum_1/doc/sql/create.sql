DROP TABLE Baugruppe;
DROP TABLE Produkt;

CREATE TABLE Produkt(
  id INTEGER PRIMARY KEY,
  bestand INTEGER NOT NULL CHECK(bestand >= 0),
  preis NUMBER(8, 2) NOT NULL CHECK(preis >= 0.00),
  name VARCHAR(30) NOT NULL,
  bild VARCHAR(200)
);

CREATE TABLE Baugruppe(
  oberteil INTEGER NOT NULL,
  unterteil INTEGER NOT NULL,
  anzahl INTEGER NOT NULL CHECK(anzahl >= 0),
  PRIMARY KEY(oberteil,  unterteil),
  FOREIGN KEY(oberteil) REFERENCES Produkt,
  FOREIGN KEY(unterteil) REFERENCES Produkt
);
