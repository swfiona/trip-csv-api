DROP TABLE IF EXISTS Zone;

CREATE TABLE Zone AS SELECT * FROM CSVREAD('classpath:tripdata/taxi+_zone_lookup.csv');

