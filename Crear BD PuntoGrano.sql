CREATE SCHEMA puntograno;
USE puntograno;

/* CREAMOS TABLAS */
CREATE TABLE pedido (
	id_pedido CHAR(36) NOT NULL,
    fecha_hora DATETIME,
    estado ENUM('En preparacion','Listo para retiro','Entregado'),
    rut_cliente VARCHAR(12) NOT NULL,
    PRIMARY KEY(id_pedido)
) ENGINE = INNODB;

CREATE TABLE producto (
    id_producto CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL,
    disponible BOOLEAN,
    PRIMARY KEY (id_producto)
)  ENGINE=INNODB;

CREATE TABLE detalle_pedido (
    id_pedido CHAR(36) NOT NULL,
    id_producto CHAR(36) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario INT NOT NULL,
    PRIMARY KEY (id_pedido , id_producto)
)  ENGINE=INNODB;

CREATE TABLE recompensa (
    id_recompensa CHAR(36) NOT NULL,
    id_producto CHAR(36) NOT NULL UNIQUE,
    costo_puntos INT NOT NULL,
    disponible BOOLEAN,
    PRIMARY KEY (id_recompensa)
)  ENGINE=INNODB;

CREATE TABLE cliente (
    rut_cliente VARCHAR(12) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(50),
    fecha_registro DATE,
    PRIMARY KEY (rut_cliente)
)  ENGINE=INNODB;

CREATE TABLE tarjeta_fidelidad (
    id_tarjeta CHAR(36) NOT NULL,
    puntos_acumulados INT NOT NULL,
    fecha_emision DATE,
    rut_cliente VARCHAR(12) NOT NULL UNIQUE,
    PRIMARY KEY (id_tarjeta)
)  ENGINE=INNODB;

/* ESTABLECEMOS RELACIONES CON LLAVES FORANEAS */
ALTER TABLE pedido ADD CONSTRAINT fk_pedido_rut_cliente FOREIGN KEY (rut_cliente) REFERENCES cliente(rut_cliente);
ALTER TABLE detalle_pedido ADD CONSTRAINT fk_detalle_pedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido);
ALTER TABLE detalle_pedido ADD CONSTRAINT fk_detalle_pedido_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto);
ALTER TABLE recompensa ADD CONSTRAINT fk_recompensa_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto);
ALTER TABLE tarjeta_fidelidad ADD CONSTRAINT fk_tarjeta_fidelidad_rut_cliente FOREIGN KEY (rut_cliente) REFERENCES cliente(rut_cliente);