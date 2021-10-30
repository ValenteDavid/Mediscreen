CREATE TABLE patient (
    idPatient INT AUTO_INCREMENT NOT NULL,
    firstName VARCHAR(200) NOT NULL,
    lastName VARCHAR(200) NOT NULL,
    dob DATE NOT NULL,
    sex CHAR(1) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(12),
    PRIMARY KEY (idPatient),
    UNIQUE (firstName , lastName)
);
