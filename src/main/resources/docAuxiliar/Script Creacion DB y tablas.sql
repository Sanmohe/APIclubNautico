CREATE DATABASE clubNautico;
USE clubNautico;

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
    dni VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50),
    direccion VARCHAR(50),
    telefono INT,
    email VARCHAR(50)
);

CREATE TABLE barco (
	matricula VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50),
    num_amarre INT,
    cuota DECIMAL(6,2)
);

CREATE TABLE salida (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora TIME,
    destino VARCHAR(50)
);

DROP TABLE barco;

DROP DATABASE clubNautico;