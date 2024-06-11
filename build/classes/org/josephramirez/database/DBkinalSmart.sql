 drop database if exists DBkinalSmart;
create database DBkinalSmart;
use DBkinalSmart;
set global time_zone = '-6:00';

-- CLIENTES --
create table Clientes(
	codigoCliente int not null,
    nitCliente varchar(10),
    nombresClientes varchar (50),
    apellidosClientes varchar (50),
    direccionCliente varchar (150),
    telefonoCliente varchar (8),
    correoCliente varchar (45),
    primary key Pk_codigoCliente(codigoCliente)
);
-- --------------------------------------------------------
delimiter $$
create procedure sp_agregar_Cliente(in _codigoCliente int, in _nitCliente varchar(10), in _nombresClientes varchar (50), in _apellidosClientes varchar (50),
in _direccionCliente varchar (150), in _telefonoCliente varchar (8), in _correoCliente varchar (45))
begin 
	insert into Clientes (Clientes.codigoCliente,Clientes.nitCliente,Clientes.nombresClientes,Clientes.apellidosClientes,Clientes.direccionCliente,Clientes.telefonoCliente,Clientes.correoCliente)
    values (_codigoCliente,_nitCLiente,_nombresClientes,_apellidosClientes,_direccionCliente,_telefonoCliente,_correoCliente);
end $$
delimiter ;

call sp_agregar_Cliente(1,'1234567890','Joseph Moises','Ramirez Gaitan','zona 3 Guatemala','53387832','jramirez@kinal.edu.gt');
call sp_agregar_Cliente(2,'4234567890','Krystel Alejandra','Ramirez Pedroza','zona 6 Guatemala','48137846','aramirez@kinal.edu.gt');
-- ----------------------------------------------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarClientes ()
    Begin 
		select
        C.codigoCliente,
        C.nitCliente,
        C.nombresClientes,
        C.apellidosClientes,
        C.direccionCliente,
        C.telefonoCliente,
        C.correoCliente
        from Clientes C;
	End $$
Delimiter ;

call sp_ListarClientes ();
-- ------------------------------------------
delimiter $$
create procedure sp_buscar_Clientes (in _codigoCliente int)
begin
	select * from Clientes where Clientes.codigoCliente =_codigoCliente;
end $$
delimiter ;

call sp_buscar_Clientes(1);
-- ----------------------------------------------
delimiter $$
 create procedure sp_actualizar_Clientes (in _codigoCliente int, in _nitCliente varchar (10), in _nombresClientes varchar (50), in _apellidosClientes varchar (50), in _direccionCliente varchar (150), in _telefonoCliente varchar (8), in _correoCliente varchar (45) )
 begin
	Update Clientes
    set Clientes.nitCliente = _nitCLiente,
		Clientes.nombresClientes = _nombresClientes,
        Clientes.apellidosClientes = _apellidosClientes,
        Clientes.direccionCliente = _direccionCliente,
        Clientes.telefonoCliente = _telefonoCliente,
        Clientes.correoCliente = _correoCliente
        where
        Clientes.codigoCliente = _codigoCliente;
 end $$
 delimiter ; 
 
call sp_actualizar_Clientes(2,'8234567890','Alejandra Sofia','Ramirez Quintanilla','zona 18 Guatemala','48634486','squintanilla@kinal.edu.gt');
-- ----------------------------------+----------------------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_Cliente (in _codigoCliente int)
 begin
	delete from Clientes
    where Clientes.codigoCliente = _codigoCliente;
 end $$
 delimiter ;

-- call sp_eliminar_Cliente (2);
-- -----------------------------------------------------------------------------------------------------

 -- --------------------------- CARGO EMPLEADO ---------------------------------------------------------
 create table CargoEmpleado(
 codigoCargoEmpleado int not null,
 nombreCargoEmpleado varchar (45),
 descripcionCargo varchar (45),
 primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
 );
 -- ----------------------------------------------------------------------------------------------------
 delimiter $$
create procedure sp_agregar_CargoEmpleado(in _codigoCargoEmpleado int, in _nombreCargoEmpleado varchar (45), in _descripcionCargo varchar (45))
begin 
	insert into CargoEmpleado (CargoEmpleado.codigoCargoEmpleado,CargoEmpleado.nombreCargoEmpleado,CargoEmpleado.descripcionCargo)
    values (_codigoCargoEmpleado,_nombreCargoEmpleado,_descripcionCargo);
end $$
delimiter ;

call sp_agregar_CargoEmpleado(1,'Gerente','Ser lider');
call sp_agregar_CargoEmpleado(2,'Supervisor','Supervisar a los trabajadores');
-- ---------------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarCargoEmpleado ()
    Begin 
		select
		C.codigoCargoEmpleado,
        C.nombreCargoEmpleado,
        C.descripcionCargo
        from CargoEmpleado C;
	End $$
Delimiter ;

call sp_ListarCargoEmpleado ();
-- --------------------------------------------------------------
delimiter $$
create procedure sp_buscar_CargoEmpleado (in _codigoCargoEmpleado int)
begin
	select * from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado =_codigoCargoEmpleado;
end $$
delimiter ;

call sp_buscar_CargoEmpleado(1);
-- ------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_CargoEmpleado (in _codigoCargoEmpleado int, in _nombreCargoEmpleado varchar (45), in _descripcionCargo varchar (45))
 begin
	Update CargoEmpleado
    set CargoEmpleado.nombreCargoEmpleado = _nombreCargoEmpleado,
		CargoEmpleado.descripcionCargo = _descripcionCargo 
		where
        CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
 end $$
 delimiter ; 
 
 call sp_actualizar_CargoEmpleado(1,'Ingeniero','Encargado de todos');
 -- ------------------------------------------------------------------------
 delimiter $$
 create procedure sp_eliminar_CargoEmpleado (in _codigoCargoEmpleado int)
 begin
	delete from CargoEmpleado
    where CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
 end $$
 delimiter ;

-- call sp_eliminar_CargoEmpleado(2);
-- --------------------------------------------------------------------------

-- ---------------------- COMPRAS--------------------------------------------
create table Compras
(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar ( 60),
    totalDocumento decimal (10,2),
	primary key PK_numeroDocumento(numeroDocumento)
);
-- -----------------------------------------------------------------------
 delimiter $$
create procedure sp_agregar_Compras(in _numeroDocumento int, in _fechaDocumento date, in _descripcion varchar (60),in _totalDocumento decimal (10,2))
begin 
	insert into Compras (Compras.numeroDocumento,Compras.fechaDocumento,Compras.descripcion,Compras.totalDocumento)
    values (_numeroDocumento,_fechaDocumento,_descripcion,_totalDocumento);
end $$
delimiter ;

call sp_agregar_Compras(1,'2002-05-14','Leche en polvo',54.02);
call sp_agregar_Compras(2,'2004-05-06','Carne de res',24.50);
-- ------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarCompras ()
    Begin 
		select
		C.numeroDocumento,
        C.fechaDocumento,
        C.descripcion,
        C.totalDocumento
        from Compras C;
	End $$
Delimiter ;

call sp_ListarCompras ();
-- -----------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_Compra (in _numeroDocumento int)
begin
	select * from Compras where Compras.numeroDocumento =_numeroDocumento;
end $$
delimiter ;

call sp_buscar_Compra(1);
-- ------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_Compras (in _numeroDocumento int, in _fechaDocumento date, in _descripcion varchar (60),in _totalDocumento decimal (10,2))
 begin
	Update Compras
    set Compras.fechaDocumento = _fechaDocumento,
		Compras.descripcion = _descripcion,
        Compras.totalDocumento = _totalDocumento
		where
        Compras.numeroDocumento = _numeroDocumento;
 end $$
 delimiter ; 
 
 call sp_actualizar_Compras(1,'2024-06-06','1 Lb de Manzanas',15.25);
 -- -------------------------------------------------------------------
 delimiter $$
 create procedure sp_eliminar_Compras (in _numeroDocumento int)
 begin
	delete from Compras
    where Compras.numeroDocumento = _numeroDocumento;
 end $$
 delimiter ;

-- call sp_eliminar_Compras(2);
-- -------------------------------------------------------------------

-- -----------------------TIPO PRODUCTO--------------------------------
create table tipoProducto
(
	codigoTipoProducto int not null,
    descripcion varchar (45),
    primary key PK_codigoTipoProducto(codigoTipoProducto)
);
-- --------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_TipoProducto(in _codigoTipoProducto int, in _descripcion varchar (45))
begin 
	insert into tipoProducto (tipoProducto.codigoTipoProducto,tipoProducto.descripcion)
    values (_codigoTipoProducto,_descripcion);
end $$
delimiter ;

call sp_agregar_TipoProducto(1,'Lacteos');
call sp_agregar_TipoProducto(2,'Verduras');
call sp_agregar_TipoProducto(3,'Frutas');
call sp_agregar_TipoProducto(4,'Ropa');
call sp_agregar_TipoProducto(5,'Carne');

-- ---------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarTipoProducto ()
    Begin 
		select
		t.codigoTipoProducto,
        t.descripcion
        from tipoProducto t;
	End $$
Delimiter ;

call sp_ListarTipoProducto ();
-- --------------------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE sp_buscar_TipoProducto (IN _codigoTipoProducto INT)
BEGIN
    SELECT descripcion, codigoTipoProducto
    FROM tipoProducto
    WHERE tipoProducto.codigoTipoProducto = _codigoTipoProducto;
END $$
DELIMITER ;


-- --------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_tipoProducto (in _codigoTipoProducto int, in _descripcion varchar (45))
 begin
	Update tipoProducto
    set tipoProducto.descripcion = _descripcion
		where
        tipoProducto.codigoTipoProducto = _codigoTipoProducto;
 end $$
 delimiter ; 
 
 call sp_actualizar_tipoProducto(1,'FRUTOS');
 -- ------------------------------------------------------------------------
 delimiter $$
 create procedure sp_eliminar_tipoProducto (in _codigoTipoProducto int)
 begin
	delete from tipoProducto
    where tipoProducto.codigoTipoProducto = _codigoTipoProducto;
 end $$
 delimiter ;

-- call sp_eliminar_tipoProducto(2);
-- ------------------------------------------------------------------------

-- ------------------------------- PROVEEDORES --------------------------------------------
create table Proveedores
(
	codigoProveedor int not null,
    nitProveedor varchar (10),
    nombresProveedor varchar (60),
    apellidosProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor(codigoProveedor)
);

delimiter $$
create procedure sp_agregar_Poveedor(in _codigoProveedor int, in _nitProveedor varchar(10), in _nombresProveedor varchar (60), in _apellidosProveedor varchar (60),
in _direccionProveedor varchar (150), in _razonSocial varchar (60), in _contactoPrinicpal varchar (100), in _paginaWeb varchar (50))
begin 
	insert into Proveedores (Proveedores.codigoProveedor,Proveedores.nitProveedor,Proveedores.nombresProveedor,Proveedores.apellidosProveedor,Proveedores.direccionProveedor,Proveedores.razonSocial,Proveedores.contactoPrincipal,Proveedores.paginaWeb)
    values (_codigoProveedor,_nitProveedor,_nombresProveedor,_apellidosProveedor,_direccionProveedor,_razonSocial,_contactoPrinicpal,_paginaWeb);
end $$
delimiter ;

call sp_agregar_Poveedor(1,'1234567890','Krystel Alejandra','Ramirez Pedroza','zona 7 Guatemala','Proveedora De Medicina','krystel@gmail.com','http.Krustels.com');
call sp_agregar_Poveedor(2,'4896513857','DOris Patricia','Mejia Gaitan','zona 6 Guatemala','Proveedora De Ropa','Doris@gmail.com','http.Doris.com');
call sp_agregar_Poveedor(3,'4896513857','Fernanda Patricia','Mejia Gaitan','zona 4 Guatemala','Proveedora De Coca','Doris@gmail.com','http.Doris.com');
call sp_agregar_Poveedor(4,'4896513857','Alejandro Fernando','Mejia Gaitan','zona 7 Guatemala','Proveedora De Verdura','Doris@gmail.com','http.Doris.com');
call sp_agregar_Poveedor(5,'4896513857','Gustavo Ignacio','Mejia Gaitan','zona 8 Guatemala','Proveedora De FRUTA','Doris@gmail.com','http.Doris.com');


-- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarProveedores ()
    Begin 
		select
        P.codigoProveedor,
        P.nitProveedor,
        P.nombresProveedor,
        P.apellidosProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
        from Proveedores P;
	End $$
Delimiter ;

call sp_ListarProveedores ();gg
-- ---------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_Proveedores (in _codigoProveedor int)
begin
	select * from Proveedores where Proveedores.codigoProveedor =_codigoProveedor;
end $$
delimiter ;

call sp_buscar_Proveedores(1);
-- ---------------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_Proveedores (in _codigoProveedor int, in _nitProveedor varchar (10), in _nombresProveedor varchar (60), in _apellidosProveedor varchar (60), in _direccionProveedor varchar (150),
 in _razonSocial varchar (60), in _contactoPrinicpal varchar (100), in _paginaWeb varchar (50)  )
 begin
	Update Proveedores
    set Proveedores.nitProveedor = _nitProveedor,
		Proveedores.nombresProveedor = _nombresProveedor,
        Proveedores.apellidosProveedor = _apellidosProveedor,
        Proveedores.direccionProveedor = _direccionProveedor,
        Proveedores.razonSocial = _razonSocial,
        Proveedores.contactoPrincipal = _contactoPrinicpal,
        Proveedores.paginaWeb = _paginaWeb
        where
        Proveedores.codigoProveedor = _codigoProveedor;
 end $$
 delimiter ; 
 
  call sp_actualizar_Proveedores(2,'4896513857','Doris Fernanda','Mejia Gaitan','zona 6 Guatemala','Proveedora De Ropa','Doris@gmail.com','http.Doris.com');
-- ----------------------------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_Proveedores (in _codigoProveedor int)
 begin
	delete from Proveedores
    where Proveedores.codigoProveedor = _codigoCliente;
 end $$
 delimiter ;

-- call sp_eliminar_Proveedores (2);

-- --------------------------------------------------

-- --------------------- EMPLEADOS -----------------------------
create table Empleado
(
	codigoEmpleado int not null,
    nombresEmpleado varchar (50),
    apellidosEmpleado varchar (50),
    sueldo decimal (10,2),
    direccion varchar (150),
    turno varchar (15),
    codigoCargoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint  FK_Empleado_cargoEmpleado  foreign key (codigoCargoEmpleado) references cargoEmpleado (codigoCargoEmpleado)
);
-- -------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_Empleado(in _codigoEmpleado int, in _nombresEmpleado varchar (50) , in _apellidosEmpleado varchar (50), in _sueldo decimal (10,2),
in _direccion varchar (150), in _turno varchar (15), in _codigoCargoEmpleado int)
begin 
	insert into Empleado (Empleado.codigoEmpleado,Empleado.nombresEmpleado,Empleado.apellidosEmpleado,Empleado.sueldo,Empleado.direccion,Empleado.turno,Empleado.codigoCargoEmpleado)
    values (_codigoEmpleado,_nombresEmpleado,_apellidosEmpleado,_sueldo,_direccion,_turno,_codigoCargoEmpleado);
end $$
delimiter ;

call sp_agregar_Empleado(1,'Joseph Moises','Ramirez Gaitan',3500.00,'Zona 3 Guatemala','Matutina',1);
-- ------------------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarEmpleado ()
    Begin 
		select
        E.codigoEmpleado,
        E.nombresEmpleado,
        E.apellidosEmpleado,
        E.sueldo,
        E.direccion,
        E.turno,
        E.codigoCargoEmpleado
        from Empleado E;
	End $$
Delimiter ;

call sp_ListarEmpleado ();
-- --------------------------------------------
delimiter $$
create procedure sp_buscar_Empleado (in _codigoEmpleado int)
begin
	select * from Empleado where Empleado.codigoEmpleado =_codigoEmpleado;
end $$
delimiter ;

call sp_buscar_Empleado(1);
-- ------------------------------------------------
delimiter $$
 create procedure sp_actualizar_Empleado (in _codigoEmpleado int, in _nombresEmpleado varchar (50) , in _apellidosEmpleado varchar (50), in _sueldo decimal (10,2),
in _direccion varchar (150), in _turno varchar (15), in _codigoCargoEmpleado int )
 begin
	Update Empleado
    set Empleado.nombresEmpleado = _nombresEmpleado,
		Empleado.apellidosEmpleado = _apellidosEmpleado,
        Empleado.sueldo = _sueldo,
        Empleado.direccion = _direccion,
        Empleado.turno = _turno,
        Empleado.codigoCargoEmpleado = _codigoCargoEmpleado
        where
        Empleado.codigoEmpleado = _codigoEmpleado;
 end $$
 delimiter ; 

call sp_actualizar_Empleado(1,'Jose David','Quintanilla Molina',3500.00,'Zona 3 Guatemala','Matutina',1);
-- ----------------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_Empleado (in _codigoEmpleado int)
 begin
	delete from Empleado
    where Empleado.codigoEmpleado = _codigoEmpleado;
 end $$
 delimiter ;

-- call sp_eliminar_Empleado (2);
-- ----------------------------- FACTURA --------------------------------------------
create table Factura
(
	numeroFactura int not null,
    estado varchar (50),
    totalFactura decimal (10,2),
    fechaFactura varchar (45),
    codigoCliente int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_Factura_Clientes foreign key (codigoCliente) references Clientes (codigoCliente),
    constraint FK_Factura_Empleado foreign key (codigoEmpleado) references Empleado (codigoEmpleado)
);
-- -------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_Factura(in _numeroFactura int, in _estado varchar (50) , in _totalFactura decimal (10,2), in _fechaFactura varchar (45),
in _codigoCliente int, in _codigoEmpleado int)
begin 
	insert into Factura (Factura.numeroFactura,Factura.estado,Factura.totalFactura,Factura.fechaFactura,Factura.codigoCliente,Factura.codigoEmpleado)
    values (_numeroFactura,_estado,_totalFactura,_fechaFactura,_codigoCliente,_codigoEmpleado);
end $$
delimiter ;

call sp_agregar_Factura(1,'activa',45.50,'2024-06-23',1,1);
-- ---------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarFactura ()
    Begin 
		select
        F.numeroFactura,
        F.estado,
        F.totalFactura,
        F.fechaFactura,
        F.codigoCliente,
        F.codigoEmpleado
        from Factura F;
	End $$
Delimiter ;

call sp_ListarFactura ();
-- ------------------------------------------
-- --------------------------------------------
delimiter $$
create procedure sp_buscar_Factura (in _numeroFactura int)
begin
	select * from Factura where Factura.numeroFactura =_numeroFactura;
end $$
delimiter ;

call sp_buscar_Factura(1);
-- ------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_Factura (in _numeroFactura int, in _estado varchar (50) , in _totalFactura decimal (10,2), in _fechaFactura varchar (45),
in _codigoCliente int, in _codigoEmpleado int)
 begin
	Update Factura
    set Factura.estado = _estado,
		Factura.totalFactura = _totalFactura,
        Factura.fechaFactura = _fechaFactura,
        Factura.codigoCliente = _codigoCliente,
        Factura.codigoEmpleado = _codigoEmpleado
        where
        Factura.numeroFactura = _numeroFactura;
 end $$
 delimiter ; 

call sp_actualizar_Factura(1,'Permitida',45.50,'2024-06-23',1,1);
-- --------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_Factura (in _numeroFactura int)
 begin
	delete from Factura
    where Factura.numeroFactura = _numeroFactura;
 end $$
 delimiter ;

-- call sp_eliminar_Factura (2);
-- ------------------------------------------

create table EmailProveedor
(
	codigoEmailProveedor int not null,
    emailProveedor varchar (50),
    descripcion varchar (100),
    codigoProveedor int not null,
    primary key PK_codigoEmailProveedor(codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key (codigoProveedor) references Proveedores (codigoProveedor)
);
-- ---------------------------------------------
delimiter $$
create procedure sp_agregar_EmailProveedor(in _codigoEmailProveedor int, in _emailProveedor varchar (50), in _descripcion varchar (100),in  _codigoProveedor int)
begin 
	insert into EmailProveedor (EmailProveedor.codigoEmailProveedor,EmailProveedor.emailProveedor,EmailProveedor.descripcion,EmailProveedor.codigoProveedor)
    values (_codigoEmailProveedor,_emailProveedor,_descripcion,_codigoProveedor);
end $$
delimiter ;

call sp_agregar_EmailProveedor(1,'jose@gmail.com','Contacto Principal',1);
-- ------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarEmailProveedor ()
    Begin 
		select
        L.codigoEmailProveedor,
        L.emailProveedor,
        L.descripcion,
        L.codigoProveedor
        from EmailProveedor L;
	End $$
Delimiter ;

call sp_ListarEmailProveedor ();
-- -----------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_EmailProveedor (in _codigoEmailProveedor int)
begin
	select * from EmailProveedor where EmailProveedor.codigoEmailProveedor =_codigoEmailProveedor;
end $$
delimiter ;

call sp_buscar_EmailProveedor(1);
-- ------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_EmailProveedor (in _codigoEmailProveedor int, 
 in _emailProveedor varchar (50), in _descripcion varchar (100),in  _codigoProveedor int)
 begin
	Update EmailProveedor
    set 
		EmailProveedor.emailProveedor = _emailProveedor,
        EmailProveedor.descripcion = _descripcion,
        EmailProveedor.codigoProveedor = _codigoProveedor
        where
        EmailProveedor.codigoEmailProveedor = _codigoEmailProveedor;
 end $$
 delimiter ; 
 
 call sp_actualizar_EmailProveedor(1,'Abner@gmail.com','Contacto Principal',1);
 -- -----------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_EmailProveedor (in _codigoEmailProveedor int)
 begin
	delete from EmailProveedor
    where EmailProveedor.codigoEmailProveedor = _codigoEmailProveedor;
 end $$
 delimiter ;

-- call sp_eliminar_EmailProveedor (2);
-- ------------------------------------------------------------------------------------

-- ---------------------------------------------------------------------------------------
create table TelefonoProveedor
(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar (8),
    numeroSecundario varchar (8),
    observaciones varchar (45),
    codigoProveedor int not null,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key (codigoProveedor) references Proveedores (codigoProveedor)
);
-- ---------------------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_TelefonoProveedor(in _codigoTelefonoProveedor int, in _numeroPrincipal varchar (8), in _numeroSecundario varchar (8), in  _observaciones varchar (45),
in _codigoProveedor int)
begin 
	insert into TelefonoProveedor (TelefonoProveedor.codigoTelefonoProveedor,TelefonoProveedor.numeroPrincipal,TelefonoProveedor.numeroSecundario,TelefonoProveedor.observaciones,TelefonoProveedor.codigoProveedor)
    values (_codigoTelefonoProveedor,_numeroPrincipal,_numeroSecundario,_observaciones,_codigoProveedor);
end $$
delimiter ;

call sp_agregar_TelefonoProveedor(1,'79857894','78964856','Ocupado',1);
-- ---------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarTelefonoProveedor()
    Begin 
		select
        T.codigoTelefonoProveedor,
        T.numeroPrincipal,
        T.numeroSecundario,
        T.observaciones,
        T.codigoProveedor
        from TelefonoProveedor T;
	End $$
Delimiter ;

call sp_ListarTelefonoProveedor();
-- ---------------------------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_TelefonoProveedor (in _codigoTelefonoProveedor int)
begin
	select * from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor =_codigoTelefonoProveedor;
end $$
delimiter ;

call sp_buscar_TelefonoProveedor(1);
-- ---------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_TelefonoProveedor (in _codigoTelefonoProveedor int, in _numeroPrincipal varchar (8), in _numeroSecundario varchar (8), in  _observaciones varchar (45),
in _codigoProveedor int)
 begin
	Update TelefonoProveedor
    set TelefonoProveedor.numeroPrincipal = _numeroPrincipal,
		TelefonoProveedor.numeroSecundario = _numeroSecundario,
        TelefonoProveedor.observaciones = _observaciones,
        TelefonoProveedor.codigoProveedor = _codigoProveedor
        where
        TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
 end $$
 delimiter ; 
 
 call sp_actualizar_TelefonoProveedor(1,'48936984','48392658','Dispobile',1);
-- ---------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_TelefonoProveedor (in _codigoTelefonoProveedor int)
 begin
	delete from TelefonoProveedor
    where TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
 end $$
 delimiter ;

-- call sp_eliminar_TelefonoProveedor (2);
-- ---------------------------------------------------------------------------------------

-- ------------------------------- PRODUCTOS --------------------------------------------------------
CREATE TABLE Productos (
    codigoProducto VARCHAR(15),
    descripcionProducto VARCHAR(45),
    precioUnitario DECIMAL(10,2),
    precioDocena DECIMAL(10,2),
    precioMayor DECIMAL(10,2),
    imagenProducto VARCHAR(45),
    existencia INT NOT NULL,
    codigoTipoProducto INT NOT NULL,
    codigoProveedor INT NOT NULL,
    PRIMARY KEY (codigoProducto),
    CONSTRAINT FK_TipoProducto_Productos FOREIGN KEY (codigoTipoProducto) REFERENCES TipoProducto (codigoTipoProducto),
    CONSTRAINT FK_Proveedores_Productos FOREIGN KEY (codigoProveedor) REFERENCES Proveedores (codigoProveedor)
);

-- -------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_Productos(in _codigoProducto varchar (15), in _descripcionProducto varchar (45), in _precioUnitario decimal (10,2),
 in  _precioDocena decimal (10,2), in _precioMayor decimal (10,2), in _imagenProducto varchar (45), in _existencia int, in _codigoTipoProducto int, in _codigoProveedor int)
begin 
	insert into Productos (Productos.codigoProducto,Productos.descripcionProducto,Productos.precioUnitario,Productos.precioDocena,Productos.precioMayor,
    Productos.imagenProducto,Productos.existencia,Productos.codigoTipoProducto,Productos.codigoProveedor)
    values (_codigoProducto,_descripcionProducto,_precioUnitario,_precioDocena,_precioMayor,_imagenProducto,_existencia,_codigoTipoProducto,_codigoProveedor);
end $$
delimiter ;

call sp_agregar_Productos('XXX','Lacteos',15.80,10.30,8.00,'Imagen',1,1,1);
call sp_agregar_Productos('AAA','Verduras',20.80,10.30,8.00,'Imagen',2,2,2);
call sp_agregar_Productos('BBB','Frutas',25.80,10.30,8.00,'Imagen',3,3,3);
call sp_agregar_Productos('CCC','Ropa',10.80,10.30,8.00,'Imagen',4,4,4);
call sp_agregar_Productos('DDD','Carnes',28.80,10.30,8.00,'Imagen',5,5,5);
-- ----------------------------------------------------------------------
SELECT Productos.codigoProducto, TipoProducto.codigoTipoProducto FROM Productos
INNER JOIN TipoProducto ON Productos.codigoProducto=TipoProducto.codigoTipoProducto
ORDER BY Productos.codigoProducto;
-- -----------------------------------------------------------------------
SELECT Productos.codigoProducto,Productos.precioUnitario,Productos.existencia,TipoProducto.codigoTipoProducto 
FROM Productos LEFT JOIN TipoProducto
ON Productos.codigoProducto = TipoProducto.codigoTipoProducto
ORDER BY Productos.codigoProducto;
-- --------------------------------------------------------------------------------------------
SELECT TipoProducto.codigoTipoProducto, Productos.codigoProducto
FROM Productos RIGHT JOIN TipoProducto
ON Productos.codigoProducto=TipoProducto.codigoTipoProducto
ORDER BY TipoProducto.codigoTipoProducto;
-- --------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarProductos()
    Begin 
		select
        L.codigoProducto,
        L.descripcionProducto,
        L.precioUnitario,
        L.precioDocena,
        L.precioMayor,
        L.imagenProducto,
        L.existencia,
        L.codigoTipoProducto,
        L.codigoProveedor
        from Productos L;
	End $$
Delimiter ;

call sp_ListarProductos();
-- -------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_Productos (in _codigoProducto varchar (15))
begin
	select * from Productos where Productos.codigoProducto =_codigoProducto;
end $$
delimiter ;

call sp_buscar_Productos('XXX');
-- -------------------------------------------------------------------------------------------

delimiter $$
 create procedure sp_actualizar_Productos (in _codigoProducto varchar (15), in _descripcionProducto varchar (45), in _precioUnitario decimal (10,2),
 in  _precioDocena decimal (10,2), in _precioMayor decimal (10,2), in _imagenProducto varchar (45), in _existencia int, in _codigoTipoProducto int, in _codigoProveedor int)
 begin
	Update Productos
    set 
		Productos.descripcionProducto = _descripcionProducto,
        Productos.precioUnitario = _precioUnitario,
        Productos.precioDocena = _precioDocena,
        Productos.precioMayor = _precioMayor,
        Productos.imagenProducto = _imagenProducto,
        Productos.existencia = _existencia,
        Productos.codigoTipoProducto = _codigoTipoProducto,
        Productos.codigoProveedor = _codigoProveedor
        where
        Productos.codigoProducto = _codigoProducto;
 end $$
 delimiter ; 
 
call sp_actualizar_Productos('XXX','Verduras',15.80,10.30,8.00,'Imagen',1,1,1);
-- -------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_eliminar_Productos (in _codigoProducto varchar (15))
 begin
	delete from Productos
    where Productos.codigoProducto = _codigoProducto;
 end $$
 delimiter ;

-- call sp_eliminar_TelefonoProveedor (2);
-- -------------------------------------------------------------------------------------------

-- -------------------------------------------------------------------------------------------
create table DetalleCompra
(
	codigoDetalleCompra int not null,
    costoUnitario decimal (10,2),
    cantidad int not null,
    codigoProducto varchar (15),
    numeroDocumento int not null,
    primary key PK_codigoDetalleCompra (codigoDetalleCompra),
    constraint FK_Productos_DetalleCompra foreign key (codigoProducto) references Productos (codigoProducto),
    constraint FK_Compras_DetalleCompra foreign key (numeroDocumento) references Compras (numeroDocumento)
);
-- ------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_DetalleCompra(in _codigoDetalleCompra int, in _costoUnitario decimal (10,2), in _cantidad int, in  _codigoProducto varchar (15), in _numeroDocumento int)
begin 
	insert into DetalleCompra (DetalleCompra.codigoDetalleCompra,DetalleCompra.costoUnitario,DetalleCompra.cantidad,DetalleCompra.codigoProducto,DetalleCompra.numeroDocumento)
    values (_codigoDetalleCompra,_costoUnitario,_cantidad,_codigoProducto,_numeroDocumento);
end $$
delimiter ;

call sp_agregar_DetalleCompra(1,50.00,2,'XXX',1);
-- -----------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarDetalleCompra()
    Begin 
		select
        D.codigoDetalleCompra,
        D.costoUnitario,
        D.cantidad,
        D.codigoProducto,
        D.numeroDocumento
        from DetalleCompra D;
	End $$
Delimiter ;

call sp_ListarDetalleCompra();
-- ------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_DetalleCompra (in _codigoDetalleCompra int)
begin
	select * from DetalleCompra where DetalleCompra.codigoDetalleCompra =_codigoDetalleCompra;
end $$
delimiter ;

call sp_buscar_DetalleCompra(1);

-- ------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_DetalleCompra (in _codigoDetalleCompra int, in _costoUnitario decimal (10,2), in _cantidad int, in  _codigoProducto varchar (15), in _numeroDocumento int)
 begin
	Update DetalleCompra
    set DetalleCompra.costoUnitario = _costoUnitario,
		DetalleCompra.cantidad = _cantidad,
        DetalleCompra.codigoProducto = _codigoProducto,
        DetalleCompra.numeroDocumento = _numeroDocumento
        where
        DetalleCompra.codigoDetalleCompra = _codigoDetalleCompra;
 end $$
 delimiter ; 
 
 call sp_actualizar_DetalleCompra(1,35.00,2,'XXX',1);
-- ------------------------------------------------------------------------------------------

delimiter $$
 create procedure sp_eliminar_DetalleCompra (in _codigoDetalleCompra int)
 begin
	delete from DetalleCompra
    where DetalleCompra.codigoDetalleCompra = _codigoDetalleCompra;
 end $$
 delimiter ;

-- call sp_eliminar_DetalleCompra (2);
-- ------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------------------

create table DetalleFactura
(
	codigoDetalleFactura int not null,
    precioUnitario decimal (10,2),
    cantidad int not null,
	numeroFactura int not null,
	codigoProducto varchar (15),
    primary key codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_Factura_DetalleFactura foreign key (numeroFactura) references Factura (numeroFactura),
    constraint FK_Productos_DetalleFactura foreign key (codigoProducto) references Productos (codigoProducto)
    
);
-- ------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_agregar_DetalleFactura(in _codigoDetalleFactura int, in _precioUnitario decimal (10,2), in _cantidad int, in  _numeroFactura int, in _codigoProducto varchar (15))
begin 
	insert into DetalleFactura (DetalleFactura.codigoDetalleFactura,DetalleFactura.precioUnitario,DetalleFactura.cantidad,DetalleFactura.numeroFactura,DetalleFactura.codigoProducto)
    values (_codigoDetalleFactura,_precioUnitario,_cantidad,_numeroFactura,_codigoProducto);
end $$
delimiter ;

call sp_agregar_DetalleFactura(1,35.20,1,1,'XXX');
-- -------------------------------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarDetalleFactura()
    Begin 
		select
        D.codigoDetalleFactura,
        D.precioUnitario,
        D.cantidad,
        D.numeroFactura,
        D.codigoProducto
        from DetalleFactura D;
	End $$
Delimiter ;

call sp_ListarDetalleFactura();
-- -------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_buscar_DetalleFactura(in _codigoDetalleFactura int)
begin
	select * from DetalleFactura where DetalleFactura.codigoDetalleFactura =_codigoDetalleFactura;
end $$
delimiter ;

call sp_buscar_DetalleFactura(1);
-- ----------------------------------------------------------------------------------------------
delimiter $$
 create procedure sp_actualizar_DetalleFactura (in _codigoDetalleFactura int, in _precioUnitario decimal (10,2), in _cantidad int, in  _numeroFactura int, in _codigoProducto varchar (15))
 begin
	Update DetalleFactura
    set DetalleFactura.precioUnitario = _precioUnitario,
		DetalleFactura.cantidad = _cantidad,
        DetalleFactura.numeroFactura = _numeroFactura,
        DetalleFactura.codigoProducto = _codigoProducto
        where
        DetalleFactura.codigoDetalleFactura = _codigoDetalleFactura;
 end $$
 delimiter ; 
 
 call sp_actualizar_DetalleFactura(1,50.20,1,1,'XXX');
 -- ---------------------------------------------------------------------
 delimiter $$
 create procedure sp_eliminar_DetalleFactura (in _codigoDetalleFactura int)
 begin
	delete from DetalleFactura
    where DetalleFactura.codigoDetalleFactura = _codigoDetalleFactura;
 end $$
 delimiter ;

-- call sp_eliminar_DetalleFactura (2);