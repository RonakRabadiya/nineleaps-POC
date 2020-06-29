CREATE KEYSPACE IF NOT EXISTS test
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    };

DROP TABLE IF EXISTS test.product ;

DROP TABLE IF EXISTS test.product_by_supplier ;

CREATE TABLE test.product ( id uuid, name varchar, price decimal, description varchar, supplierId uuid, primary key
    (id,supplierId) );

CREATE TABLE  IF NOT EXISTS test.product_by_supplier (
         id uuid,
         supplierid uuid,
         description text,
         name text,
         price decimal,
         PRIMARY KEY (supplierid)
) ;

INSERT INTO test.product (id , supplierid , name , price, description ) VALUES ( 9028f749-3fab-4ed0-837e-318891b70591,
9128f749-3fab-4ed0-837e-318891b70591,'product-1',100.00,'this is product 1');

INSERT INTO test.product (id , supplierid , name , price, description ) VALUES ( 9028f749-3fab-4ed0-837e-318891b70592,
9128f749-3fab-4ed0-837e-318891b70592,'product-2',200.00,'this is product 2');