CREATE TABLE user(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    google_id VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) NOT NULL UNIQUE ,
    locale VARCHAR(15),
    image_url VARCHAR(200),
    last_visit VARCHAR(50)
);

CREATE TABLE user_role (
    user_id INT NOT NULL,
    roles VARCHAR(255),
    CONSTRAINT FK_User_Roles foreign key (user_id) references user(id)
);

INSERT INTO user(email) VALUES ('medvik9@gmail.com');

INSERT INTO user_role(user_id, roles) VALUES (1, 'SCOPE_ADMIN');
