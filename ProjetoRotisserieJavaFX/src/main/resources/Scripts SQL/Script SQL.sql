CREATE DATABASE Rotisserie;

USE Rotisserie;

CREATE TABLE IF NOT EXISTS Marmita (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(20) NOT NULL,
	max_mistura INT NOT NULL,
	max_guarnicao INT NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Produto (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Bairro (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor_entrega DECIMAL(10,2) NOT NULL,
	status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Motoboy (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(30) NOT NULL,
	valor_diaria DECIMAL(10,2) NOT NULL,
	status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Mensalista (
	id INT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(150) NOT NULL,
    contato VARCHAR(150) NOT NULL,
	conta DECIMAL(10,2) NOT NULL DEFAULT '0',
	status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
	PRIMARY KEY (id),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS Pedido (
	id INT AUTO_INCREMENT NOT NULL,
	id_mensalista INT,
	id_bairro INT,
	nome_cliente VARCHAR(30),
	tipo_pagamento VARCHAR(200) NOT NULL,
	tipo_pedido VARCHAR(30) NOT NULL,
	observacoes VARCHAR(100),
	valor_entrega DECIMAL(10,2),
    endereco VARCHAR(100),
	valor_itens DECIMAL(10,2) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    valor_pago DECIMAL(10,2) NOT NULL DEFAULT 0,
	date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vencimento DATE NOT NULL DEFAULT (CURRENT_DATE),
	status VARCHAR(30) NOT NULL DEFAULT 'FINALIZADO',
	PRIMARY KEY (id),
    CONSTRAINT fk_mensalista FOREIGN KEY (id_mensalista) REFERENCES Mensalista (id),
    CONSTRAINT fk_bairro_pedido FOREIGN KEY (id_bairro) REFERENCES Bairro (id)
);

CREATE TABLE IF NOT EXISTS Marmita_Vendida (
	id INT AUTO_INCREMENT NOT NULL,
    id_pedido INT NOT NULL,
	id_marmita INT NOT NULL,
	detalhes VARCHAR(500) NOT NULL,
    valor_peso DECIMAL(10,2),
    subtotal DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	PRIMARY KEY (id),
    CONSTRAINT fk_pedido_marmita FOREIGN KEY (id_pedido) REFERENCES Pedido (id),
    CONSTRAINT fk_marmita FOREIGN KEY (id_marmita) REFERENCES Marmita (id)
);

CREATE TABLE IF NOT EXISTS Produto_Vendido (
	id INT AUTO_INCREMENT NOT NULL,
    id_pedido INT NOT NULL,
	id_produto INT NOT NULL,
	quantidade INT NOT NULL,
	subtotal DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT fk_pedido_produto FOREIGN KEY (id_pedido) REFERENCES Pedido (id),
    CONSTRAINT fk_produto FOREIGN KEY (id_produto) REFERENCES Produto (id)
);

CREATE TABLE IF NOT EXISTS Desconto_Adicional (
	id INT AUTO_INCREMENT NOT NULL,
    id_pedido INT NOT NULL,
	tipo VARCHAR(15) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(255),
	PRIMARY KEY (id),
    CONSTRAINT fk_pedido_DescontoAdicional FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

CREATE TABLE IF NOT EXISTS Item_Cardapio (
	id INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(150) NOT NULL,
	categoria VARCHAR(30) NOT NULL,
	PRIMARY KEY (id),
    UNIQUE (nome, categoria)
);

CREATE TABLE IF NOT EXISTS Cardapio (
	id INT AUTO_INCREMENT NOT NULL,
    principal_1 VARCHAR(150),
    principal_2 VARCHAR(150),
    mistura_1 VARCHAR(150),
    mistura_2 VARCHAR(150),
    mistura_3 VARCHAR(150),
    mistura_4 VARCHAR(150),
    guarnicao_1 VARCHAR(150),
    guarnicao_2 VARCHAR(150),
    guarnicao_3 VARCHAR(150),
    guarnicao_4 VARCHAR(150),
    salada_1 VARCHAR(150),
    salada_2 VARCHAR(150),
    data_hora DATETIME,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Pagamento (
	id INT AUTO_INCREMENT NOT NULL,
    id_pedido INT NOT NULL,
    tipo_pagamento VARCHAR(30) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(500),
    data DATE NOT NULL DEFAULT (CURRENT_DATE),
	PRIMARY KEY (id),
    FOREIGN KEY (id_pedido) REFERENCES Pedido (id)
);

DELIMITER $$
CREATE PROCEDURE CREATE_MARMITA(_nome VARCHAR(20), _max_mistura INT, _max_guarnicao INT, _valor DECIMAL(10,2))
BEGIN
	INSERT INTO Marmita (nome, max_mistura, max_guarnicao, valor)
    VALUES (_nome, _max_mistura, _max_guarnicao, _valor);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_MARMITA(_id INT, _max_mistura INT, _max_guarnicao INT, _valor DECIMAL(10,2), _status VARCHAR(30))
BEGIN
	UPDATE Marmita
    SET max_mistura = _max_mistura, max_guarnicao = _max_guarnicao, valor = _valor, status = _status
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
CREATE PROCEDURE READ_ALL_MARMITAS()
BEGIN
	SELECT * FROM Marmita;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_MARMITA_VENDIDA(_id_pedido INT, _id_marmita INT, _subtotal DECIMAL(10,2), _detalhes VARCHAR(500), _observacao VARCHAR(100))
BEGIN
	INSERT INTO Marmita_Vendida (id_pedido, id_marmita, subtotal, detalhes, observacao)
    VALUES (_id_pedido, _id_marmita, _subtotal, _detalhes, _observacao);
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
CREATE PROCEDURE READ_ALL_MARMITAS_PEDIDO(_id_pedido INT)
BEGIN
	SELECT M.nome, MV.detalhes, MV.observacao, MV.subtotal FROM Marmita_Vendida AS MV
    JOIN Marmita AS M ON M.id = MV.id_marmita
    WHERE MV.id_pedido = _id_pedido;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PRODUTO(_nome VARCHAR(30), _valor DECIMAL(10,2))
BEGIN
	INSERT INTO Produto (nome, valor)
    VALUES (_nome, _valor);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_PRODUTO(_id INT, _valor DECIMAL(10,2), _status VARCHAR(30))
BEGIN
	UPDATE Produto
    SET valor = _valor, status = _status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_ALL_PRODUTOS()
BEGIN
	SELECT * FROM Produto;
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
CREATE PROCEDURE CREATE_PRODUTO_VENDIDO(_id_pedido INT, _id_produto INT, _quantidade INT, _subtotal DECIMAL(10,2))
BEGIN
	INSERT INTO Produto_Vendido (id_pedido, id_produto, quantidade, subtotal)
    VALUES (_id_pedido, _id_produto, _quantidade, _subtotal);
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
CREATE PROCEDURE READ_ALL_PRODUTOS_PEDIDO(_id_pedido INT)
BEGIN
	SELECT P.nome, PV.quantidade, PV.subtotal FROM Produto_Vendido AS PV
    JOIN Produto AS P ON P.id = PV.id_produto
    WHERE id_pedido = _id_pedido;
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
CREATE PROCEDURE UPDATE_MOTOBOY(_id INT, _valor_diaria DECIMAL(10,2), _status VARCHAR(30))
BEGIN
	UPDATE Motoboy
    SET valor_diaria = _valor_diaria, status = _status
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
CREATE PROCEDURE READ_ALL_MOTOBOYS()
BEGIN
	SELECT * FROM Motoboy;
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
CREATE PROCEDURE UPDATE_BAIRRO(_id INT, _valor_entrega DECIMAL(10,2), _status VARCHAR(30))
BEGIN
	UPDATE Bairro
    SET valor_entrega = _valor_entrega, status = _status
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
CREATE PROCEDURE READ_ALL_BAIRROS()
BEGIN
	SELECT * FROM Bairro;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_BAIRRO_BY_ID(_id INT)
BEGIN
	SELECT * FROM Bairro
    WHERE id = _id;
END $$
DELIMITER ;
    
DELIMITER $$
CREATE PROCEDURE CREATE_MENSALISTA(_nome VARCHAR(30), _contato VARCHAR(150))
BEGIN
	INSERT INTO Mensalista (nome, contato)
    VALUES (_nome, _contato);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_MENSALISTA(_id INT, _status VARCHAR(30), _contato VARCHAR(150))
BEGIN
	UPDATE Mensalista
    SET status = _status, contato = _contato
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
CREATE PROCEDURE READ_ALL_MENSALISTAS()
BEGIN
	SELECT * FROM Mensalista;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PEDIDO(_id_mensalista INT, _id_bairro INT, _nome_cliente VARCHAR(30),
							_tipo_pagamento VARCHAR(30), _tipo_pedido VARCHAR(30), _observacoes VARCHAR(100),
                            _valor_entrega DECIMAL(10,2), _endereco VARCHAR(100), _valor_itens DECIMAL(10,2),
                            _valor_total DECIMAL(10,2), _vencimento DATE)
BEGIN

	DECLARE _status VARCHAR(30);
	DECLARE _valor_pago DECIMAL(10,2);
    
    IF _tipo_pagamento = 'Pagar depois' THEN
		SET _status = "A PAGAR";
        SET _valor_pago = 0;
	ELSE
		SET _status = "PAGO";
        SET _vencimento = (CURRENT_DATE);
        SET _valor_pago = _valor_total;
	END IF;

	INSERT INTO Pedido (id_mensalista, id_bairro, nome_cliente, tipo_pagamento, tipo_pedido,
    observacoes, valor_entrega, endereco, valor_itens, valor_total, valor_pago, vencimento, status)
    
    VALUES (_id_mensalista, _id_bairro, _nome_cliente, _tipo_pagamento, _tipo_pedido,
    _observacoes, _valor_entrega, _endereco, _valor_itens, _valor_total, _valor_pago, _vencimento, _status);
    
    SELECT LAST_INSERT_ID();

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE UPDATE_PEDIDO(_id INT, __status VARCHAR(30))
BEGIN
	UPDATE Pedido
    SET status = _status
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE FINALIZAR_PEDIDO(_id_pedido INT)
BEGIN
	UPDATE Pedido
    SET status = 'PAGO'
    WHERE id = _id_pedido;
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
CREATE PROCEDURE READ_ALL_PEDIDOS()
BEGIN
	SELECT * FROM Pedido
    ORDER BY date_time DESC;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_PEDIDOS_MENSALISTA(_id_mensalista INT)
BEGIN
	SELECT * FROM Pedido
    WHERE id_mensalista = _id_mensalista
    ORDER BY date_time DESC;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_PEDIDOS_BY_DATA(_data DATE)
BEGIN
	SELECT * FROM Pedido
    WHERE DATE(date_time) = _data;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_DESCONTO_ADICIONAL(_id_pedido INT, _tipo VARCHAR(15), _valor DECIMAL(10,2), _observacao VARCHAR(255))
BEGIN
	INSERT INTO DESCONTO_ADICIONAL (id_pedido, tipo, valor, observacao)
    VALUES (_id_pedido, _tipo, _valor, _observacao);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_ALL_DESCONTOS_ADICIONAIS_PEDIDO(_id_pedido INT)
BEGIN
	SELECT * FROM Desconto_Adicional
    WHERE id_pedido = _id_pedido;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_ITEM_CARDAPIO(_nome VARCHAR(150), _categoria VARCHAR(30))
BEGIN
	INSERT INTO Item_Cardapio (nome, categoria)
    VALUES (_nome, _categoria);
    
    SELECT LAST_INSERT_ID() as id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_ITEM_CARDAPIO(_id INT)
BEGIN
	DELETE FROM Item_Cardapio
    WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_ALL_ITENS_CARDAPIO()
BEGIN
	SELECT * FROM Item_Cardapio;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_CARDAPIO(_principal_1 VARCHAR(150), _principal_2 VARCHAR(150),
    _mistura_1 VARCHAR(150), _mistura_2 VARCHAR(150), _mistura_3 VARCHAR(150), _mistura_4 VARCHAR(150),
    _guarnicao_1 VARCHAR(150), _guarnicao_2 VARCHAR(150), _guarnicao_3 VARCHAR(150), _guarnicao_4 VARCHAR(150),
    _salada_1 VARCHAR(150), _salada_2 VARCHAR(150))
BEGIN
	INSERT INTO Cardapio (principal_1, principal_2, mistura_1, mistura_2, mistura_3, mistura_4,
    guarnicao_1, guarnicao_2, guarnicao_3, guarnicao_4, salada_1, salada_2, data_hora)
    
    VALUES (_principal_1, _principal_2, _mistura_1, _mistura_2, _mistura_3, _mistura_4,
    _guarnicao_1, _guarnicao_2, _guarnicao_3, _guarnicao_4, _salada_1, _salada_2, NOW());
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_CARDAPIO()
BEGIN
    SELECT * FROM Cardapio
    ORDER BY data_hora DESC
    LIMIT 1;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_DIARIA(_data DATE)
BEGIN
	SELECT COUNT(*) AS entregas, SUM(valor_entrega) as valorEntregas FROM Pedido
	WHERE DAY(date_time) = DAY(_data) AND tipo_pedido = 'Entrega';
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE CREATE_PAGAMENTO(_id_pedido INT, _tipo_pagamento VARCHAR(30), _valor DECIMAL(10,2), _observacao VARCHAR(500))
BEGIN
	INSERT INTO Pagamento (id_pedido, tipo_pagamento, valor, observacao)
    VALUES (_id_pedido, _tipo_pagamento, _valor, _observacao);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE DELETE_PAGAMENTO(_id_pagamento INT)
BEGIN
	DELETE FROM Pagamento
    WHERE id = _id_pagamento;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE READ_PAGAMENTOS_PEDIDO(_id_pedido INT)
BEGIN
	SELECT * FROM Pagamento
    WHERE id_pedido = _id_pedido;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER SOMA_CONTA_MENSALISTA
AFTER INSERT
ON Pedido
FOR EACH ROW
BEGIN
	IF (NEW.id_mensalista IS NOT NULL AND NEW.status = 'A PAGAR') THEN
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
	IF (OLD.status != 'PAGO' AND NEW.status = 'PAGO') THEN
		UPDATE Mensalista
        SET conta = conta - NEW.valor_total
        WHERE id = NEW.id_mensalista;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER ADICIONA_PAGAMENTO
AFTER INSERT
ON Pagamento
FOR EACH ROW
BEGIN
	UPDATE Pedido
    SET valor_pago =  valor_pago + NEW.valor
    WHERE id = NEW.id_pedido;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER REMOVE_PAGAMENTO
AFTER DELETE
ON Pagamento
FOR EACH ROW
BEGIN
	UPDATE Pedido
    SET valor_pago = valor_pago - OLD.valor
    WHERE id = OLD.id_pedido;
END $$
DELIMITER ;