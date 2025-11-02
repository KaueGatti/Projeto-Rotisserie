CREATE DATABASE Rotisserie;

USE Rotisserie;

CREATE TABLE IF NOT EXISTS Marmita (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(20) NOT NULL,
	max_mistura INT NOT NULL,
	max_guarnicao INT NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    _status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Produto (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
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
	conta DECIMAL(10,2) NOT NULL DEFAULT '0',
	endereco VARCHAR(100),
	_status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    CONSTRAINT fk_bairro_mensalista FOREIGN KEY (id_bairro) REFERENCES Bairro (id)
);

CREATE TABLE IF NOT EXISTS Pedido (
	id INT AUTO_INCREMENT NOT NULL,
	id_mensalista INT,
	id_bairro INT,
	id_motoboy INT,
	nome_cliente VARCHAR(30),
	tipo_pagamento VARCHAR(200) NOT NULL,
	tipo_pedido VARCHAR(30) NOT NULL,
	observacoes VARCHAR(100),
	valor_entrega DECIMAL(10,2),
	valor_total DECIMAL(10,2) NOT NULL,
    valor_pago DECIMAL(10,2) NOT NULL,
	endereco VARCHAR(100),
	date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vencimento DATE NOT NULL DEFAULT (CURRENT_DATE),
	_status VARCHAR(30) NOT NULL DEFAULT 'FINALIZADO',
	PRIMARY KEY (id),
    CONSTRAINT fk_mensalista FOREIGN KEY (id_mensalista) REFERENCES Mensalista (id),
    CONSTRAINT fk_bairro_pedido FOREIGN KEY (id_bairro) REFERENCES Bairro (id),
    CONSTRAINT fk_motoboy FOREIGN KEY (id_motoboy) REFERENCES Motoboy (id)
);

CREATE TABLE IF NOT EXISTS Pagamento(
	id INT NOT NULL AUTO_INCREMENT,
    id_pedido INT NOT NULL,
	_data DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(500),
    PRIMARY KEY (id),
    CONSTRAINT fk_pedido_pagamento FOREIGN KEY (id) REFERENCES Pedido (id)
);

CREATE TABLE IF NOT EXISTS Marmita_Vendida (
	id INT AUTO_INCREMENT NOT NULL,
	id_marmita INT,
	id_pedido INT NOT NULL,
	detalhes VARCHAR(500) NOT NULL,
    valor_peso DECIMAL(10,2),
    subtotal DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	PRIMARY KEY (id),
    CONSTRAINT fk_marmita FOREIGN KEY (id_marmita) REFERENCES Marmita (id),
    CONSTRAINT fk_pedido_marmita FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

CREATE TABLE IF NOT EXISTS Produto_Vendido (
	id INT AUTO_INCREMENT NOT NULL,
	id_produto INT NOT NULL,
	id_pedido INT NOT NULL,
	quantidade INT NOT NULL,
	subtotal DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT fk_produto FOREIGN KEY (id_produto) REFERENCES Produto (id),
    CONSTRAINT fk_pedido_produto FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

DELIMITER $$
CREATE PROCEDURE CREATE_MARMITA(_nome VARCHAR(20), _max_mistura INT, _max_guarnicao INT, _valor DECIMAL(10,2))
BEGIN
	INSERT INTO Marmita (nome, max_mistura, max_guarnicao, valor)
    VALUES (_nome, _max_mistura, _max_guarnicao, _valor);
END $$
DELIMITER ;

CALL CREATE_MARMITA('Teste', 2, 3, 20.00);

DELIMITER $$
CREATE PROCEDURE UPDATE_MARMITA(_id INT, _valor DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Marmita
    SET valor = _valor, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_MARMITA(_id INT)
BEGIN
	DELETE FROM Marmita
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_MARMITA_VENDIDA(_id_marmita INT, _id_pedido INT, _subtotal DECIMAL(10,2), _detalhes VARCHAR(100), _observacao VARCHAR(100))
BEGIN
	INSERT INTO Marmita_Vendida (id_marmita, id_pedido, subtotal, detalhes, observacao)
    VALUES (_id_marmita, _id_pedido, _subtotal, _detalhes, _observacao);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_MARMITA_VENDIDA(_id INT)
BEGIN
	DELETE FROM Marmita_Vendida
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PRODUTO(_descricao VARCHAR(30), _valor DECIMAL(10,2))
BEGIN
	INSERT INTO Produto (descricao, valor)
    VALUES (_descricao, _valor);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_PRODUTO(_id INT, _valor DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Produto
    SET valor = _valor, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_PRODUTO(_id INT)
BEGIN
	DELETE FROM Produto
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PRODUTO_VENDIDO(_id_produto INT, _id_pedido INT, _quantidade INT, _subtotal DECIMAL(10,2))
BEGIN
	INSERT INTO Produto_Vendido (id_produto, id_pedido, quantidade, subtotal)
    VALUES (_id_produto, _id_pedido, _quantidade, _subtotal);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_PRODUTO_VENDIDO(_id INT)
BEGIN
	DELETE FROM Produto_Vendido
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_MOTOBOY(_nome VARCHAR(30), _valor_diaria DECIMAL(10,2))
BEGIN
	INSERT INTO Motoboy (nome, valor_diaria)
    VALUES (_nome, _valor_diaria);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_MOTOBOY(_id INT, _valor_diaria DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Motoboy
    SET valor_diaria = _valor_diaria, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_MOTOBOY(_id INT)
BEGIN
	DELETE FROM Motoboy
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_BAIRRO(_nome VARCHAR(30), _valor_entrega DECIMAL(10,2))
BEGIN
	INSERT INTO Bairro (nome, valor_entrega)
    VALUES (_nome, _valor_entrega);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_BAIRRO(_id INT, _valor_entrega DECIMAL(10,2), __status VARCHAR(30))
BEGIN
	UPDATE Bairro
    SET valor_entrega = _valor_entrega, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_BAIRRO(_id INT)
BEGIN
	DELETE FROM Bairro
    WHERE id = _id;
END $$
DELIMITER ;
    
DELIMITER $$
CREATE PROCEDURE CREATE_MENSALISTA(_id_bairro INT, _nome VARCHAR(30), _CPF VARCHAR(14), _endereco VARCHAR(100))
BEGIN
	INSERT INTO Mensalista (id_bairro, nome, CPF, endereco)
    VALUES (_id_bairro, _nome, _CPF, _endereco);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_MENSALISTA(_id INT, _endereco VARCHAR(100), __status VARCHAR(30))
BEGIN
	UPDATE Mensalista
    SET endereco = _endereco, _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_MENSALISTA(_id INT)
BEGIN
	DELETE FROM Mensalista
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PEDIDO(_id_mensalista INT, _id_bairro INT, _id_motoboy INT,
							_nome_cliente VARCHAR(30), _tipo_pagamento VARCHAR(30), _tipo_pedido VARCHAR(30),
							_observacoes VARCHAR(100), _valor_entrega DECIMAL(10,2), _valor_total DECIMAL(10,2),
							_endereco VARCHAR(100))
BEGIN
	INSERT INTO Pedido (id_mensalista, id_bairro, id_motoboy, nome_cliente, tipo_pagamento, tipo_pedido, observacoes, valor_entrega, valor_total, endereco)
    VALUES (_id_mensalista, _id_bairro, _id_motoboy, _nome_cliente, _tipo_pagamento, _tipo_pedido, _observacoes, _valor_entrega, _valor_total, _endereco);
    SELECT LAST_INSERT_ID();
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_PEDIDO(_id INT, __status VARCHAR(30))
BEGIN
	UPDATE Pedido
    SET _status = __status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_PEDIDO(_id INT)
BEGIN
	DELETE FROM Pedido
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE FILTER_PEDIDO_DINAMICO (nome_cliente VARCHAR(100), tipo_pedido VARCHAR(30), date_time_inicio TIMESTAMP, date_time_fim TIMESTAMP, _status VARCHAR(30), orderBy VARCHAR(20), isDesc BOOLEAN)
BEGIN
	SET @sql = CONCAT('SELECT * FROM Pedido WHERE nome_cliente LIKE \'', nome_cliente, '\'');
    
    IF tipo_pedido IS NOT NULL THEN
		SET @sql = CONCAT(@sql, ' AND Pedido.tipo_pedido = ', QUOTE(tipo_pedido));
	END IF;
    
    IF (date_time_inicio IS NOT NULL AND date_time_fim IS NOT NULL) THEN
		SET @sql = CONCAT(@sql, ' AND Pedido.date_time BETWEEN \'', date_time_inicio, '\' AND \'',date_time_fim, '\'');
	ELSEIF date_time_inicio IS NOT NULL THEN
		SET @sql = CONCAT(@sql, ' AND Pedido.date_time >= \'', date_time_inicio, '\'');
	ELSEIF date_time_fim IS NOT NULL THEN
		SET @sql = CONCAT(@sql, ' AND Pedido.date_time <= \'', date_time_fim, '\'');
	END IF;
    
    IF _status IS NOT NULL THEN
		SET @sql = CONCAT(@sql, ' AND Pedido._status = ', QUOTE(_status));
    END IF;
    
    IF orderBy IS NOT NULL THEN
		SET @sql = CONCAT(@sql, ' ORDER BY ', orderBy);
		IF isDesc THEN
			SET @sql = CONCAT(@sql, ' DESC');
		END IF;
	END IF;
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER SOMA_CONTA_MENSALISTA
AFTER INSERT
ON Pedido
FOR EACH ROW
BEGIN
	IF (NEW.id_mensalista IS NOT NULL AND NEW._status != 'PAGO') THEN
		UPDATE Mensalista
        SET conta = conta + NEW.valor_total
        WHERE id = NEW.id_mensalista;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER SUBTRAI_CONTA_MENSALISTA
AFTER UPDATE
ON Pedido
FOR EACH ROW
BEGIN
	IF (OLD._status != 'PAGO' AND NEW._status = 'PAGO') THEN
		UPDATE Mensalista
        SET conta = conta - NEW.valor_total
        WHERE id = NEW.id_mensalista;
	END IF;
END $$
DELIMITER ;