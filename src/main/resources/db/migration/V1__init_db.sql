CREATE TABLE IF NOT EXISTS images (
   id bigint NOT NULL AUTO_INCREMENT,
   public_id varchar(255) DEFAULT NULL,
   url varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
 );

CREATE TABLE IF NOT EXISTS users (
   id bigint NOT NULL AUTO_INCREMENT,
   display_name varchar(255),
   password varchar(255),
   username varchar(255),
   image_id bigint,
   role varchar(255),
   PRIMARY KEY (id),
   FOREIGN KEY (image_id) REFERENCES images (id)
 );

CREATE TABLE IF NOT EXISTS posts (
   id bigint NOT NULL AUTO_INCREMENT,
   text text,
   title varchar(255),
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
 );

CREATE TABLE IF NOT EXISTS comments (
   id bigint NOT NULL AUTO_INCREMENT,
   text text,
   post_id bigint NOT NULL,
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
   FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
 );

CREATE TABLE IF NOT EXISTS likes (
   id bigint NOT NULL AUTO_INCREMENT,
   post_id bigint NOT NULL,
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
   FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
 );
