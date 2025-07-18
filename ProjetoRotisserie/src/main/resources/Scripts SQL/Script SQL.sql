CREATE DATABASE Rotisserie;

USE Rotisserie;

CREATE TABLE pedidos(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nome_cliente VARCHAR(30) NOT NULL,
	valor_total DECIMAL (7,2) NOT NULL,
	valor_entrega DECIMAL (4,2) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
    data_pedido DATE NOT NULL,
    horario_pedido TIME NOT NULL
);

CREATE TABLE marmitas_cadastradas(
	cod INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	descricao VARCHAR(30) NOT NULL,
   	tamanho VARCHAR(10) NOT NULL,
  	valor DOUBLE NOT NULL
);

CREATE TABLE marmitas_vendidas(
	id_marmita_vendida INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	detalhes VARCHAR(30) NOT NULL,
    observacao VARCHAR(30),
	id_marmita INT NOT NULL,
   	id_pedido INT NOT NULL,
	CONSTRAINT fk_cod_MC FOREIGN KEY (cod_marmita) REFERENCES marmitas_cadastradas (cod),
	CONSTRAINT fk_id_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos (id)
);

CREATE TABLE bairro(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  	nome VARCHAR(30) NOT NULL,
   	valor_entrega DOUBLE NOT NULL
);