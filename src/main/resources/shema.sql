DROP TABLE IF EXISTS book

CREATE TABLE book (
  id INT AUTO_INCREMENT PRMIARY KEY,
  title VARCHAR (250),
  author VARCHAR (250),
  description VARCHAR (2500),
  image_url VARCHAR (250),
  available TINYINT,
  genre VARCHAR (250),
  year INT,
  amount INT
);