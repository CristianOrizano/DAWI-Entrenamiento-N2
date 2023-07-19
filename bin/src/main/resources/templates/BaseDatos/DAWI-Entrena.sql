create database Entrena_DAWI;

use  Entrena_DAWI;


create table tb_Ciudad
(
   cod_ciu char(5) not  null primary key,
   nom_ciu varchar(50)
);
create table tb_estado
(
  estado int primary key,
  descripcion varchar(50)
);
insert into tb_estado values(0,'eliminado');
insert into tb_estado values(1,'Activo');

insert into tb_Ciudad values('CI001','Wisconsi');
insert into tb_Ciudad values('CI002','Denver');
insert into tb_Ciudad values('CI003','California');
insert into tb_Ciudad values('CI004','Viugglu');

create table tb_Administrador
(
  cod_adm int primary key auto_increment,
  nombre varchar(50),
  apellido varchar(50),
  Direccion varchar(50),
  telefono int,
  sueldo decimal(10,2),
  sexo varchar(50),
  fecha_nac date,
  cod_ciu char(5),
  estado int,
  constraint fk_esta foreign key(estado) references tb_estado(estado),
  constraint fk_tbA foreign key(cod_ciu) references tb_Ciudad(cod_ciu)
);
alter table tb_Administrador auto_increment=1001;
insert into tb_Administrador values(null,'cristian','Orizano Huyhua','los pinos',98456123,20,'Masculino',curdate(),'CI001',1);
insert into tb_Administrador values(null,'cristian','Orizano momo','los pinos',98456123,300.31,'Masculino',curdate(),'CI001',1);
insert into tb_Administrador values(null,'cristian','Orizano momo','los pinos',98456123,500.22,'Masculino','2020-08-16','CI001',1);
insert into tb_Administrador values(null,'asaff','Orizano momo','los pinos',98456123,500.22,'Masculino','2020-08-16','CI001',1);




select * from  tb_estado;
select * from tb_Administrador;
select * from tb_Ciudad;

