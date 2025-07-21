<<<<<<< HEAD
CREATE DATABASE Rotisseire;

USE Rotisseire;

CREATE TABLE IF NOT EXISTS Marmita (
	id INT AUTO_INCREMENT NOT NULL,
	tamanho VARCHAR(20) NOT NULL UNIQUE,
	max_mistura INT NOT NULL,
	max_guarnicao INT NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    _status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Marmita_Vendida (
	id INT AUTO_INCREMENT NOT NULL,
	id_marmita INT NOT NULL,
	id_pedido INT NOT NULL,
	detalhes VARCHAR(100) NOT NULL,
	observacao VARCHAR(100),
	PRIMARY KEY (id),
    CONSTRAINT fk_marmita FOREIGN KEY (id_marmita) REFERENCES Marmita (id),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

CREATE TABLE IF NOT EXISTS Produto (
	id INT AUTO_INCREMENT NOT NULL,
	descricao VARCHAR(30) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Produto_Vendido (
	id INT AUTO_INCREMENT NOT NULL,
	id_produto INT NOT NULL,
	id_pedido INT NOT NULL,
	quantidade INT NOT NULL,
	subtotal DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT fk_produto FOREIGN KEY (id_produto) REFERENCES Produto (id),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

CREATE TABLE IF NOT EXISTS Bairro (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor_entrega DECIMAL(10,2) NOT NULL,
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Motoboy (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor_diaria DECIMAL(10,2) NOT NULL,
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Mensalista (
	id INT AUTO_INCREMENT NOT NULL,
	id_bairro INT,
	nome VARCHAR(30) NOT NULL,
	CPF VARCHAR(14) NOT NULL UNIQUE,
	conta DECIMAL(10,2) NOT NULL DEFAULT '0',
	endereco VARCHAR(100),
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    CONSTRAINT fk_bairro FOREIGN KEY (id_bairro) REFERENCES Bairro (id)
);

CREATE TABLE IF NOT EXISTS Pedido (
	id INT AUTO_INCREMENT NOT NULL,
	id_mensalista INT,
	id_bairro INT,
	id_motoboy INT,
	nome_cliente VARCHAR(30),
	tipo_pagamento VARCHAR(30) NOT NULL,
	tipo_pedido VARCHAR(30) NOT NULL,
	observacoes VARCHAR(100),
	valor_entrega DECIMAL(10,2),
	valor_total DECIMAL(10,2) NOT NULL,
	endereco VARCHAR(100),
	horario DATETIME NOT NULL,
	_data DATE NOT NULL,
	_status VARCHAR(30) NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT fk_mensalista FOREIGN KEY (id_mensalista) REFERENCES Mensalista (id),
    CONSTRAINT fk_bairro FOREIGN KEY (id_bairro) REFERENCES Bairro (id),
    CONSTRAINT fk_motoboy FOREIGN KEY (id_motoboy) REFERENCES Motoboy (id)
);

DELIMITER $$
CREATE PROCEDURE create_marmita(_tamanho VARCHAR(20), _max_mistura INT, _max_guarnicao INT, _valor DECIMAL(10,2))
BEGIN
	INSERT INTO Marmita (tamanho, max_mistura, max_guarnicao, valor)
    VALUES (_tamanho, _max_mistura, _max_guarnicao, _valor);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_marmita(_id INT, _valor DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Marmita
    SET valor = _valor, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_marmita(_id INT)
BEGIN
	DELETE FROM Marmita
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE create_marmita_vendida(_id_marmita INT, _id_pedido INT, _detalhes VARCHAR(100), _observacao VARCHAR(100))
BEGIN
	INSERT INTO Marmita_Vendida (id_marmita, id_pedido, detalhes, observacao)
    VALUES (_id_marmita, _id_pedido, _detalhes, _observacao);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_marmita_vendida(_id INT)
BEGIN
	DELETE FROM Marmita_Vendida
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE create_pedido(_id_mensalista INT, _id_bairro INT, _id_motoboy INT,
							_nome_cliente VARCHAR(30), _tipo_pagamento VARCHAR(30), _tipo_pedido VARCHAR(30),
							_observacoes VARCHAR(100), _valor_entrega DECIMAL(10,2), _valor_total DECIMAL(10,2),
							_endereco VARCHAR(100), _horario DATETIME, __data DATE, __status VARCHAR(30))
BEGIN
	INSERT INTO Pedido (id_mensalista, id_bairro, id_motoboy, nome_cliente, tipo_pagamento, tipo_pedido, observacoes, valor_entrega, valor_total, endereco, horario, _data, _status)
    VALUES (_id_mensalista, _id_bairro, _id_motoboy, _nome_cliente, _tipo_pagamento, _tipo_pedido, _observacoes, _valor_entrega, _valor_total, _endereco, _horario, __data, __status);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_pedido(_id INT, __status VARCHAR(30))
BEGIN
	UPDATE Pedido
    SET _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_pedido(_id INT)
BEGIN
	DELETE FROM Pedido
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE create_motoboy(_nome VARCHAR(30), _valor_diaria DECIMAL(10,2))
BEGIN
	INSERT INTO Motoboy (nome, valor_diaria)
    VALUES (_nome, _valor_diaria);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_motoboy(_id INT, _valor_diaria DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Motoboy
    SET valor_diaria = _valor_diaria, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_motoboy(_id INT)
BEGIN
	DELETE FROM Motoboy
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE create_bairro(_nome VARCHAR(30), _valor_entrega DECIMAL(10,2))
BEGIN
	INSERT INTO Bairro (nome, valor_entrega)
    VALUES (_nome, _valor_entrega);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_bairro(_id INT, _valor_entrega DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Bairro
    SET valor_entrega = _valor_entrega, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_bairro(_id INT)
BEGIN
	DELETE FROM Bairro
    WHERE id = _id;
END $$
DELIMITER ;
    
DELIMITER $$
CREATE PROCEDURE create_mensalista(_id_bairro INT, _nome VARCHAR(30), _CPF VARCHAR(14), _endereco VARCHAR(100))
BEGIN
	INSERT INTO Mensalista (id_bairro, nome, CPF, endereco)
    VALUES (_id_bairro, _nome, _CPF, _endereco);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_marmita(_id INT, _endereco VARCHAR(100), __status VARCHAR(30))
BEGIN
	UPDATE Mensalista
    SET endereco = _endereco
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_mensalista(_id INT)
BEGIN
	DELETE FROM Mensalista
    WHERE id = _id;
END $$
DELIMITER ;
    
DELIMITER $$
CREATE PROCEDURE create_produto_vendido(_id_produto INT, _id_pedido INT, _quantidade INT, _subtotal DECIMAL(10,2))
BEGIN
	INSERT INTO Produto_Vendido (id_produto, id_pedido, quantidade, subtotal)
    VALUES (_id_produto, _id_pedido, _quantidade, _subtotal);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE delete_produto_vendido(_id INT)
BEGIN
	DELETE FROM Produto_Vendido
    WHERE id = _id;
END $$
DELIMITER ;
