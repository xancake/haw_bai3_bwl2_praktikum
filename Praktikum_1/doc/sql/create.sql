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

CREATE TABLE Statistic(
  id INTEGER PRIMARY KEY,
  produkt INTEGER NOT NULL,
  wert INTEGER NOT NULL,
  bezeichnung VARCHAR(50) NOT NULL,
  FOREIGN KEY(produkt) REFERENCES Produkt
);

CREATE TABLE Bestellung(
  id INTEGER PRIMARY KEY,
  datum DATE NOT NULL
);

CREATE TABLE Bestellposition(
  bestellung INTEGER NOT NULL,
  produkt INTEGER NOT NULL,
  menge INTEGER NOT NULL CHECK(menge > 0),
  PRIMARY KEY (bestellung, produkt),
  FOREIGN KEY (bestellung)
    REFERENCES Bestellung,
  FOREIGN KEY (produkt)
    REFERENCES Produkt
);

CREATE SEQUENCE Bestellung_seq
  START WITH 1000
  INCREMENT BY 1;
  
CREATE TABLE Analytics(
  website VARCHAR(50) NOT NULL,
  aufrufe INTEGER NOT NULL CHECK(aufrufe >= 0)
);