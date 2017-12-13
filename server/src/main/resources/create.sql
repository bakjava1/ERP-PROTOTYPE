CREATE TABLE IF NOT EXISTS LUMBER (
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
lager varchar(30) NOT NULL,
description varchar(50) NOT NULL,
finishing varchar(30) NOT NULL,
wood_type varchar(20) NOT NULL,
quality varchar(20) NOT NULL,
size integer NOT NULL CHECK(size>0),
width integer NOT NULL CHECK(width>0),
length integer NOT NULL CHECK(length>0),
quantity integer NOT NULL CHECK(quantity>0),
reserved_quantity integer NOT NULL CHECK(reserved_quantity>0),
delivered_quantity integer NOT NULL CHECK(delivered_quantity>0),
all_reserved boolean NOT NULL,
all_delivered boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS TIMBER (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  FESTMETER DOUBLE NOT NULL,
  AMOUNT INT NOT NULL,
  LENGTH INT NOT NULL,
  QUALITY VARCHAR (10),
  DIAMETER INT NOT NULL,
  PRICE INT NOT NULL,
  LAST_EDITED TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ORDERS (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ORDERDATE TIMESTAMP NOT NULL,
  ISPAID BIT DEFAULT 0,
  DELETED BIT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS TASK (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ORDERID INT NOT NULL,
  DESCRIPTION VARCHAR(50),
  FINISHING VARCHAR(15),
  WOOD_TYPE VARCHAR(10),
  QUALITY VARCHAR(10),
  SIZE INT NOT NULL,
  WIDTH INT NOT NULL,
  LENGTH INT NOT NULL,
  QUANTITY INT,
  PRODUCED_QUANTITY INT,
  DONE BIT DEFAULT 0,
  DELETED BIT DEFAULT 0,
  FOREIGN KEY (ORDERID) REFERENCES ORDERS(ID)
);

MERGE INTO ORDERS (ID, ORDERDATE, DELETED) KEY(ID) VALUES(1, '2017-12-03 08:00:00', 0);

MERGE INTO TASK (ID, ORDERID, DESCRIPTION, SIZE, WIDTH, LENGTH, QUANTITY, PRODUCED_QUANTITY, DONE) KEY(ID) VALUES(1, 1, 'for testing purpose only', 24, 48, 3000, 10, 10, 1);
MERGE INTO TASK (ID, ORDERID, DESCRIPTION, SIZE, WIDTH, LENGTH, QUANTITY, PRODUCED_QUANTITY, DONE) KEY(ID) VALUES(1, 1, 'another test', 28, 100, 300, 20, 20, 1);

MERGE INTO TIMBER(ID, FESTMETER, AMOUNT, LENGTH, QUALITY, DIAMETER, PRICE, LAST_EDITED) KEY(ID) VALUES(1, 21.28, 3, 3500, 'B', 240, 100, '2017-12-05 08:00:00');
MERGE INTO TIMBER(ID, FESTMETER, AMOUNT, LENGTH, QUALITY, DIAMETER, PRICE, LAST_EDITED) KEY(ID) VALUES(2, 203.35, 2, 4000, 'Bb', 370, 150, '2017-12-05 08:00:00');


MERGE INTO LUMBER(ID,lager,description,finishing,wood_type,quality,size, width,length,quantity,reserved_quantity,delivered_quantity,
all_reserved,all_delivered) KEY(ID) VALUES(1, 'Lager1', 'Latten', 'Prismiert','Ta', 'I/III', 22,48,3000,40,50,42,1,0);

MERGE INTO LUMBER(ID,lager,description,finishing,wood_type,quality,size, width,length,quantity,reserved_quantity,delivered_quantity,
all_reserved,all_delivered) KEY(ID) VALUES(7,'Lager1', 'Kantholz', 'roh-SW','Fi', 'II/IV', 22,48,2500,40,50,42,1,0);