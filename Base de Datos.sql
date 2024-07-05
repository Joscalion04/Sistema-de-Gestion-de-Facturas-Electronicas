
create table Usuario (
    credencial varchar(30) not null,
    contrasenha varchar(100) not null,
    rol varchar(3) not null,
    estado_cuenta int,
    
    constraint pkCredencial primary key (credencial),
    constraint chkRol check (rol = 'ADM' or rol = 'CLI'),
    constraint chkEstado check (estado_cuenta between 0 and 1)
);

-- PROVEEDORES

create table Proveedor(
	identificador varchar(30) not null,
    nombre varchar(50),
    correo varchar(50),
    telefono varchar(9),
    
    constraint pkProveedor primary key (identificador),
    constraint fkProveedor foreign key (identificador) references Usuario (credencial)
);

-- PRODUCTOS

create table Producto(
	codigo_producto int auto_increment,
	identificador_proveedor varchar(30),
    descripcion varchar(120) not null,
    precio float not null,
    
    constraint pkProducto primary key (codigo_producto),
    constraint fkProducto foreign key (identificador_proveedor) references Proveedor (identificador)
);

-- CLIENTES

create table Cliente(
	identificacion varchar(30),
    nombre varchar(50)  not null,
    correo varchar(50),
    telefono varchar(9) ,
    
    constraint pkCliente primary key (identificacion)
);

-- RELACIÓN: PROVEEDOR-CLIENTE

CREATE TABLE Registra
(
	identificador_proveedor varchar(30),
	identificador_cliente varchar(30),
	CONSTRAINT pkRegistra PRIMARY KEY (identificador_proveedor, identificador_cliente),
	CONSTRAINT fk1Registra FOREIGN KEY (identificador_proveedor) REFERENCES Proveedor (identificador),
	CONSTRAINT fk2Registra FOREIGN KEY (identificador_cliente) REFERENCES Cliente (identificacion)
);

-- FACTURAS

create table Factura
(
    identificacion_cliente varchar(30),
    fecha_emision date not null,
    num_factura int auto_increment,
    
    constraint pkFactura primary key (num_factura),
    constraint fkFactura foreign key (identificacion_cliente) references Cliente (identificacion)
);

-- RELACIÓN: PRODUCTO-FACTURA

create table Compone
(
	codigo_producto int,
	num_factura int,
    cantidad_producto int not null,
    
    constraint pkCompone primary key (num_factura, codigo_producto),
    constraint fk1Compone foreign key (codigo_producto) references Producto (codigo_producto),
	constraint fk2Compone foreign key (num_factura) references Factura (num_factura)
);