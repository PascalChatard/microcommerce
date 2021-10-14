DROP TABLE IF EXISTS product;

CREATE TABLE product (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  NOM VARCHAR(250) NOT NULL,
  prix INT ,
  prix_achat INT 
);

INSERT INTO product (NOM, PRIX, PRIX_ACHAT) VALUES
  ('Ordinateur portable' , 350, 120),
  ('Aspirateur Robot' , 500, 200),
  ('Table de Ping Pong' , 750, 400);