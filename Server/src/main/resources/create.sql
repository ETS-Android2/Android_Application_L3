CREATE TABLE semester (
    id INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
);

CREATE TABLE ue (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(250),
    category INT,
    FOREIGN KEY (category) references category(id)
);

CREATE TABLE semester_ue (
    id_semester INT,
    id_ue INT,
    FOREIGN KEY (id_semester) references semester(id),
    FOREIGN KEY (id_ue) references ue(id)
);
