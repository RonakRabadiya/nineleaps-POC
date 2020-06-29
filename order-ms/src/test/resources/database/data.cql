CREATE KEYSPACE IF NOT EXISTS test
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    };

CREATE TYPE IF NOT EXISTS test.orderitem (  productid uuid, quantity int, price decimal );

CREATE TABLE IF NOT EXISTS test."order" ( id uuid, date timestamp, customer_name varchar, customer_email varchar,
customer_address varchar, items set<FROZEN <orderitem>>, total decimal, primary key (id) );

