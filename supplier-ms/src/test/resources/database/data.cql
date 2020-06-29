CREATE KEYSPACE IF NOT EXISTS test
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    };


CREATE TABLE IF NOT EXISTS test.supplier ( id uuid, name varchar, email varchar, Primary key (id));

CREATE TABLE IF NOT EXISTS test.supplier_by_email(
	id uuid ,
    email text,
    name text,
    PRIMARY KEY (email)
);

INSERT INTO test.supplier (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70591,'ronak1','ronak1@gmail.com');
INSERT INTO test.supplier (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70592,'ronak2','ronak2@gmail.com');
INSERT INTO test.supplier (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70593,'ronak3','ronak3@gmail.com');

INSERT INTO test.supplier_by_email (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70591,'ronak1','ronak1@gmail.com');
INSERT INTO test.supplier_by_email (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70592,'ronak2','ronak2@gmail.com');
INSERT INTO test.supplier_by_email (id , name , email ) VALUES ( 9128f749-3fab-4ed0-837e-318891b70593,'ronak3','ronak3@gmail.com');