CREATE TABLE IF NOT EXISTS tokens (
   id bigint NOT NULL AUTO_INCREMENT,
   token varchar(255),
   token_type varchar(255),
   expired boolean,
   revoked boolean,
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
 );