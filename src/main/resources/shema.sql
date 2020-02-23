drop table IF EXISTS library.user2book;
drop table IF EXISTS library.books;
drop table IF EXISTS library.authorities;
drop table IF EXISTS library.users;

CREATE TABLE library.users (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(50) NOT NULL,
  name VARCHAR(50),
  last_name VARCHAR(50),
  email VARCHAR(50),
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  book_limit INT,
  is_admin TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE library.authorities (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (id) REFERENCES library.users(id)
);

CREATE TABLE library.books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR (250),
  author VARCHAR (250),
  description VARCHAR (2500),
  image_url VARCHAR (250),
  available TINYINT,
  genre VARCHAR (250),
  year INT,
  amount INT
);

CREATE TABLE library.user2book (
  id INT NOT NULL AUTO_INCREMENT,
  book_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (book_id) REFERENCES library.books(id),
  FOREIGN KEY (user_id) REFERENCES library.users(id)
);

CREATE UNIQUE INDEX ix_auth_login on library.authorities (login,authority);
