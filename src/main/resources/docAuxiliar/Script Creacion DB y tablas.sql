CREATE DATABASE clubNautico;

CREATE TABLE socio (
	id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50),
    direccion VARCHAR(50),
    telefono INT,
    email VARCHAR(50)
  );

CREATE TABLE patron (
	id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(10),
    nombre VARCHAR(50),
    apellido1 VARCHAR(50),
    apellido2 VARCHAR(50),
    direccion VARCHAR(50),
    telefono INT,
    email VARCHAR(50),
    idSocio INT
);

CREATE TABLE barco (
	id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(50),
    nombre VARCHAR(50),
    num_amarre INT,
    cuota DECIMAL(6,2),
    idSocio INT NOT NULL,
    FOREIGN KEY (idSocio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE salida (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora TIME,
    destino VARCHAR(50),
    idBarco INT NOT NULL,
    idPatron INT NOT NULL,
    FOREIGN KEY (idBarco) REFERENCES barco(id) ON DELETE CASCADE,
    FOREIGN KEY (idPatron) REFERENCES patron(id)
);

DROP TABLE socio;

DROP DATABASE clubNautico;