DROP TABLE Lumber IF EXISTS;
DROP TABLE Timber IF EXISTS;
DROP TABLE Orders IF EXISTS;
DROP TABLE Task IF EXISTS;
DROP TABLE ASSIGNMENT IF EXISTS;

CREATE TABLE TIMBER (
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  FESTMETER DOUBLE NOT NULL,
  AMOUNT INT NOT NULL,
  LENGTH INT NOT NULL,
  QUALITY VARCHAR (10),
  DIAMETER INT NOT NULL,
  PRICE INT NOT NULL,
  LAST_EDITED TIMESTAMP
);

create table LUMBER(
ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
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

create table Orders (
ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
customer_name varchar(50) NOT NULL,
customer_address varchar(50) NOT NULL,
customer_uid varchar(10) NOT NULL,
order_date datetime NOT NULL,
isPaidFlag boolean NOT NULL,
isDoneFlag boolean NOT NULL
);


CREATE TABLE TASK (
  ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
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
  SUM INT NOT NULL,
  DONE BIT DEFAULT 0,
  DELETED BIT DEFAULT 0,
  FOREIGN KEY (ORDERID) REFERENCES ORDERS(ID)
);


create table ASSIGNMENT(
ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
creation_date Datetime NOT NULL,
amount integer NOT NULL,
box_ID integer REFERENCES TIMBER(ID),
isDone boolean NOT NULL
);



INSERT INTO TIMBER(festmeter,amount,length, quality,diameter,price,last_edited) VALUES
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now()),
( 21.28,7,3500, 'CX', 220,50,now());

INSERT INTO LUMBER(lager,description,finishing,wood_type,quality,size,
width,length,quantity,reserved_quantity,all_reserved,all_delivered) VALUES
('Lager1', 'Latten', 'Prismiert','Ta', 'I/III', 22,48,3000,40,50,1,0),
('Lager1', 'Staffel', 'Prismiert','Ta', 'II/IV', 22,48,2000,40,42,1,0),
('Lager1', 'Kantholz', 'Prismiert','Fi/Ta', 'I/III', 22,48,5000,100,50,1,1),
('Lager1', 'Schnittholz','Prismiert','Ta','S10/CE/TS', 22,48,1000,40,42,1,0),
('Lager1', 'Latten', 'roh-SW','Fi', 'I/III', 22,48,3000,40,50,1,0),
('Lager1', 'Staffel', 'roh-SW','Ta', 'S10/CE/TS', 22,48,6000,40,42,1,0),
('Lager1', 'Kantholz', 'roh-SW','Fi', 'II/IV', 22,48,2500,40,50,1,0),
('Lager1', 'Schnittholz', 'roh-SW','Fi/Ta', 'I/III', 22,48,2100,50,42,1,0),
('Lager1', 'Latten', 'Prismiert','Ta', 'I/III', 22,48,1500,40,50,1,0),
('Lager1', 'Latten', 'roh','Ta', 'S10/CE/TS', 22,48,3000,40,60,0,1);

INSERT INTO ORDERS(customer_name, customer_address,customer_uid, order_date, isPaidFlag, isDoneFlag) VALUES
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,0),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,1),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,0),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,1),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,0),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,1),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,0),
('Herr Fischer', 'Ausstellungstrasse 7/9',12345,now(),0,1);


INSERT INTO task(orderid,description,finishing,wood_type,quality,size,
width,length,quantity,produced_quantity,sum,done,deleted) VALUES
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0),
(2,'Latten','Prismiert','Ta', 'I/III', 22,48,3000,40,50,25,1,0);

INSERT INTO ASSIGNMENT(creation_date, amount,box_ID, isDone) VALUES
(now(),2,3,0),
(now(),5,2,1),
(now(),5,6,0),
(now(),8,4,1),
(now(),2,3,0),
(now(),8,2,1),
(now(),9,3,1),
(now(),7,4,0),
(now(),6,3,0),
(now(),4,2,1);