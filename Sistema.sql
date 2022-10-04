CREATE DATABASE IF NOT EXISTS sistema;
use sistema;

create table if not exists usuario(
id int primary key auto_increment not null,
login varchar(50),
senha varchar(50)
);

create table if not exists material(
	id int primary key auto_increment not null,
    idServico int,
    nomeMaterial varchar(50),
    nomeObra varchar(50),
    quantidade int
);

create table if not exists funcionario(
	id int primary key not null auto_increment,
	nome varchar(255),
	cpf varchar(30) not null,
	cargo varchar(50),
	rua varchar(100),
	bairro varchar(100),
	numero varchar(30),
	cidade varchar(50),
	telefone varchar(30),
	email varchar(50),
	salario varchar(30),
	dataContrato varchar(20)
);

create table if not exists servico(
	codigo int primary key auto_increment not null,
	tipoServico varchar(50),
	statusObra varchar(50),
	rua varchar(100),
	bairro varchar(100),
	numero varchar(30),
	cidade varchar(50),
	descricao varchar(200),
	preco varchar(50),
	dataObra varchar(20),
	codcliente varchar(50),
	dataTermino varchar(20)
);

create table if not exists cliente(
	codcliente int primary key not null auto_increment,
	nome varchar(255),
	tipopessoa char(20),
	cpf varchar(15) not null,
	cnpj varchar(18),
	rua varchar(100),
	bairro varchar(100),
	numero varchar(30),
	cidade varchar(50),
	telefone varchar(20),
	email varchar(50)
);