create table user (
    id integer not null auto_increment,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    user_role varchar(255),
    primary key (id)
);

INSERT INTO user (email, first_name, last_name, password, user_role)
VALUES ('medvik9@gmail.com', 'Andrii', 'Vedmid', '$2a$10$Xq9NWUnTZQ3gXw2TO38Tq.dyvUmPn69ZIz//y6O8SSNhcJndRCzES', 'ADMIN');