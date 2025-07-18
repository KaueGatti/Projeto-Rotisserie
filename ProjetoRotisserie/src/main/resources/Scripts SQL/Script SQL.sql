create database Rotisserie;

use Rotisserie;

create table pedidos(
	id int not null auto_increment primary key,
	nome_cliente varchar(30) not null,
	valor_total decimal (7,2) not null,
	valor_entrega decimal (4,2) not null,
	tipo varchar(20) not null,
    data_pedido date not null,
    horario_pedido time not null
);

create table marmitas_cadastradas(
	cod int not null auto_increment primary key,
	descricao varchar(30) not null,
   	tamanho varchar(10) not null,
  	valor double not null
);

create table marmitas_vendidas(
	id int not null auto_increment primary key,
	descricao varchar(30) not null,
   	tamanho varchar(10) not null,
    mistura_1 varchar(15),
    mistura_2 varchar(15),
    mistura_3 varchar(15),
    guarnicao_1 varchar(15),
    guarnicao_2 varchar(15),
    guarnicao_3 varchar(15),
    salada varchar(15),
	cod_marmita int not null,
   	id_pedido int not null,
	constraint fk_cod_MC foreign key (cod_marmita) references marmitas_cadastradas (cod),
	constraint fk_id_pedido foreign key (id_pedido) references pedidos (id)
);

create table bairro(
	id int not null primary key auto_increment,
  	nome varchar(30) not null,
   	valor_entrega double not null
);