drop database if exists idb;
create database idb;
use idb;

create table item (
	id 		int				primary key		auto_increment,
    name	varchar(50)		not null,
    sellin	int				not null,
    qaulity	int				not null
);

CREATE USER idb_user@localhost IDENTIFIED BY 'windows';
GRANT SELECT, INSERT, DELETE, UPDATE ON idb.* TO idb_user@localhost;