CREATE TABLE `customer`
(
    `id`       int          NOT NULL AUTO_INCREMENT,
    `email`    varchar(45)  NOT NULL,
    `password` varchar(200) NOT NULL,
    `role`     varchar(45)  NOT NULL,
    primary key (`id`)
);


create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);


SELECT  * FROM authorities a

SELECT * FROM users u

INSERT IGNORE INTO `users` VALUES ('user', '{noop}12345', 1);
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');

INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$DTzG57.5qOiRdyQrtelWx.Vu5ySjiC8Nd6b2Hp.IzsMtNVasd6prW', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');

CREATE TABLE `customer`(
                           `id` int NOT NULL AUTO_INCREMENT,
                           `email` varchar(45) NOT NULL,
                           `password` varchar(200) NOT NULL,
                           `role` varchar(45) NOT NULL,
                           primary key (`id`)
);

insert into `customer` (email, password ,role ) VALUES ('marcos@marcos.com', '{noop}12345', 'read');
insert into `customer` (email, password ,role ) VALUES ('admin@admin.com', '{noop}12345', 'read');

alter