DROP TABLE IF EXISTS LUMBER, TIMBER, ORDERS, TASK;

CREATE TABLE LUMBER (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  LAGER VARCHAR(30),
  description varchar(50),
finishing varchar(30),
wood_type varchar(20),
quality varchar(20),
size integer,
width integer,
length integer,
quantity integer,
reserved_quantity integer,
delivered_quantity integer,
all_reserved BIT,
all_delivered BIT
);

CREATE TABLE TIMBER (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  FESTMETER DOUBLE NOT NULL,
  AMOUNT INT NOT NULL,
  LENGTH INT NOT NULL,
  QUALITY VARCHAR (10),
  DIAMETER INT NOT NULL,
  PRICE INT NOT NULL,
  LAST_EDITED TIMESTAMP
);

CREATE TABLE ORDERS (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ORDERDATE TIMESTAMP NOT NULL,
  ISPAID BIT DEFAULT 0,
  DELETED BIT DEFAULT 0
);

CREATE TABLE TASK (
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

INSERT INTO ORDERS (ID, ORDERDATE) VALUES
(1, now()),
(2, now()),
(3, now()),
(4, now()),
(5, now()),
(6, now());

INSERT INTO TASK (ID, ORDERID, DESCRIPTION, FINISHING, WOOD_TYPE, QUALITY, SIZE, WIDTH, LENGTH, QUANTITY, PRODUCED_QUANTITY, DONE) VALUES
(1, 1, 'Latten', 'Prismiert', 'Ta', 'I/III', 22, 48, 3000, 10, 10, 1),
(2, 1, 'Staffel', 'Prismiert', 'Ta', 'II/IV', 22, 48, 2000, 20, 20, 1),
(3, 2, 'Kantholz', 'Prismiert', 'Fi/Ta', 'I/III', 22, 48, 5000, 30, 30, 1),
(4, 3, 'Latten', 'Prismiert', 'Ta', 'I/III', 22, 48, 3000, 10, 10, 1),
(5, 3, 'Staffel', 'Prismiert', 'Ta', 'II/IV', 22, 48, 2000, 20, 20, 1);

INSERT INTO TIMBER(ID, FESTMETER, AMOUNT, LENGTH, QUALITY, DIAMETER, PRICE, LAST_EDITED) VALUES
(1, 21.28, 3, 3500, 'B', 240, 100, now()),
(2, 203.35, 2, 4000, 'Bb', 370, 150, now());

INSERT INTO LUMBER(LAGER,description,finishing,wood_type,quality,size,
width,length,quantity,reserved_quantity,delivered_quantity,
all_reserved,all_delivered) VALUES
('Lager1', 'Latten', 'Prismiert','Ta', 'I/III', 22,48,3000,40,0,0,0,0),
('Lager1', 'Staffel', 'Prismiert','Ta', 'II/IV', 22,48,2000,40,0,0,0,0),
('Lager1', 'Kantholz', 'Prismiert','Fi/Ta', 'I/III', 22,48,5000,40,30,0,0,0),
('Lager1', 'Schnittholz','Prismiert','Ta','S10/CE/TS', 22,48,1000,40,0,0,0,0);