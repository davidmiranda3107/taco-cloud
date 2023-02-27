create table if not exists Ingredient (
 id varchar(4) not null UNIQUE,
 name varchar(25) not null,
 type varchar(10) not null
);

create table if not exists Taco (
 id identity,
 name varchar(50) not null,
 createdAt timestamp not null
);

create table if not exists Taco_Ingredient (
 taco_id bigint not null,
 ingredients_id varchar(4) not null
);

alter table Taco_Ingredient
 add foreign key (taco_id) references Taco(id);

alter table Taco_Ingredient
 add foreign key (ingredients_id) references Ingredient(id);

create table if not exists Taco_Order (
 id identity,
 Name varchar(50) not null,
 Street varchar(50) not null,
 City varchar(50) not null,
 State varchar(2) not null,
 Zip varchar(10) not null,
 ccNumber varchar(16) not null,
 ccExpiration varchar(5) not null,
 ccCVV varchar(3) not null,
 placedAt timestamp not null
);

create table if not exists Taco_Order_Taco (
 taco_Order_id bigint not null,
 tacos_id bigint not null
);

alter table Taco_Order_Taco
 add foreign key (taco_Order_id) references Taco_Order(id);

alter table Taco_Order_Taco
 add foreign key (tacos_id) references Taco(id);

 create table if not exists Taco_User (
 id identity,
 Username varchar(50) not null,
 Password varchar(100) not null,
 Fullname varchar(50) not null,
 Street varchar(50) not null,
 City varchar(50) not null,
 State varchar(20) not null,
 Zip varchar(10) not null,
 PhoneNumber varchar(15) not null
);

 create sequence TACO_SEQ as bigint start with 1 increment by 50;

 create sequence TACO_ORDER_SEQ as bigint start with 1 increment by 50;

  create sequence TACO_USER_SEQ as bigint start with 1 increment by 50;
 