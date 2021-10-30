CREATE TABLE IF NOT EXISTS patient (
    patient_id INT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    dob DATE NOT NULL,
    sex CHAR(1) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(12),
    PRIMARY KEY (patient_id),
    UNIQUE (first_name , last_name)
);
