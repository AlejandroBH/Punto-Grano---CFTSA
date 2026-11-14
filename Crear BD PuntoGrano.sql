DROP SCHEMA IF EXISTS puntograno;
CREATE SCHEMA puntograno;
USE puntograno;

/* -------------------------------------------------------------- */
/* 1: CREAMOS TABLAS (CON RESPECTIVAS RESTRICCIONES Y RELACIONES) */
/* -------------------------------------------------------------- */
CREATE TABLE pedido (
	id_pedido CHAR(36) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado ENUM('En preparacion','Listo para retiro','Entregado') NOT NULL,
    id_cliente CHAR(36) NOT NULL,
    PRIMARY KEY(id_pedido)
) ENGINE = INNODB;

CREATE TABLE producto (
    id_producto CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio INT NOT NULL CHECK(precio > 0),
    stock INT NOT NULL DEFAULT 0 CHECK(stock >= 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_producto)
)  ENGINE=INNODB;

CREATE TABLE detalle_pedido (
    id_pedido CHAR(36) NOT NULL,
    id_producto CHAR(36) NOT NULL,
    cantidad INT NOT NULL CHECK(cantidad > 0),
    precio_unitario INT NOT NULL CHECK(precio_unitario > 0),
    PRIMARY KEY (id_pedido , id_producto)
)  ENGINE=INNODB;

CREATE TABLE recompensa (
    id_recompensa CHAR(36) NOT NULL,
    id_producto CHAR(36) NOT NULL UNIQUE,
    costo_puntos INT NOT NULL CHECK(costo_puntos > 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_recompensa)
)  ENGINE=INNODB;

CREATE TABLE cliente (
	id_cliente CHAR(36) NOT NULL,
    rut_cliente VARCHAR(12) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(50),
    fecha_registro DATE NOT NULL,
    PRIMARY KEY (id_cliente)
)  ENGINE=INNODB;

CREATE TABLE tarjeta_fidelidad (
    id_tarjeta CHAR(36) NOT NULL,
    puntos_acumulados INT NOT NULL DEFAULT 0 CHECK(puntos_acumulados >= 0),
    fecha_emision DATE NOT NULL,
    id_cliente CHAR(36) NOT NULL UNIQUE,
    PRIMARY KEY (id_tarjeta)
)  ENGINE=INNODB;

/* ESTABLECEMOS RELACIONES CON LLAVES FORANEAS */
ALTER TABLE pedido ADD CONSTRAINT fk_pedido_id_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);
ALTER TABLE detalle_pedido ADD CONSTRAINT fk_detalle_pedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido);
ALTER TABLE detalle_pedido ADD CONSTRAINT fk_detalle_pedido_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto);
ALTER TABLE recompensa ADD CONSTRAINT fk_recompensa_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto);
ALTER TABLE tarjeta_fidelidad ADD CONSTRAINT fk_tarjeta_fidelidad_id_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente);

/* ------------------------------------- */
/* 2: INSERTAMOS DATOS EN LAS TABLAS     */
/* ------------------------------------- */

INSERT INTO cliente (id_cliente, rut_cliente, nombre, telefono, email, fecha_registro) VALUES
('550e8400-e29b-41d4-a716-446655440001', '12.123.456-7', 'Francisco', '+56987465432', 'francisco@email.com', '2026-09-01'),
('550e8400-e29b-41d4-a716-446655440002', '14.246.654-8', 'Javier', '+56975239514', 'javier@email.com', '2026-09-10'),
('550e8400-e29b-41d4-a716-446655440003', '21.346.754-2', 'Fernanda', '+56965326497', 'fernanda@email.com', '2026-10-05'),
('550e8400-e29b-41d4-a716-446655440004', '18.853.423-7', 'Camila', '+56985236542', 'camila@email.com', '2026-10-18');

INSERT INTO tarjeta_fidelidad (id_tarjeta, puntos_acumulados, fecha_emision, id_cliente) VALUES
('660e8400-e29b-41d4-a716-446655440011', 120, '2026-09-01', '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440012', 45, '2026-09-10', '550e8400-e29b-41d4-a716-446655440002'),
('660e8400-e29b-41d4-a716-446655440013', 310, '2026-10-05', '550e8400-e29b-41d4-a716-446655440003'),
('660e8400-e29b-41d4-a716-446655440014', 15, '2026-10-18', '550e8400-e29b-41d4-a716-446655440004');

INSERT INTO producto (id_producto, nombre, categoria, precio, stock, disponible) VALUES
('770e8400-e29b-41d4-a716-446655440021', 'Café expreso', 'Cafeteria', 4500, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440022', 'Muffin o Galleta', 'Pasteleria', 3200, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440023', 'Café del día', 'Cafeteria', 3500, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440024', 'Bolsa de Café en Grano', 'Granos', 7500, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440025', 'Capuccino grande', 'Cafeteria', 5700, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440026', 'Bombilla Mate', 'Accesorios', 3800, 10, TRUE),
('770e8400-e29b-41d4-a716-446655440027', 'Moledor de Café', 'Accesorios', 4200, 10, TRUE);

INSERT INTO recompensa (id_recompensa, id_producto, costo_puntos, disponible) VALUES
('880e8400-e29b-41d4-a716-446655440031', '770e8400-e29b-41d4-a716-446655440022', 25, TRUE),   /* Muffin */
('880e8400-e29b-41d4-a716-446655440032', '770e8400-e29b-41d4-a716-446655440023', 50, TRUE),   /* Café del día */
('880e8400-e29b-41d4-a716-446655440033', '770e8400-e29b-41d4-a716-446655440024', 100, TRUE);  /* Bolsa de Café */

INSERT INTO pedido (id_pedido, fecha_hora, estado, id_cliente) VALUES
('990e8400-e29b-41d4-a716-446655440041', '2026-11-10 09:15:00', 'Entregado', '550e8400-e29b-41d4-a716-446655440001'),
('990e8400-e29b-41d4-a716-446655440042', '2026-11-10 10:30:00', 'En preparacion', '550e8400-e29b-41d4-a716-446655440002'),
('990e8400-e29b-41d4-a716-446655440043', '2026-11-11 16:45:00', 'Listo para retiro', '550e8400-e29b-41d4-a716-446655440003');

INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario) VALUES
('990e8400-e29b-41d4-a716-446655440041', '770e8400-e29b-41d4-a716-446655440025', 2, 5700),
('990e8400-e29b-41d4-a716-446655440041', '770e8400-e29b-41d4-a716-446655440022', 1, 3200),
('990e8400-e29b-41d4-a716-446655440042', '770e8400-e29b-41d4-a716-446655440021', 1, 4500),
('990e8400-e29b-41d4-a716-446655440043', '770e8400-e29b-41d4-a716-446655440024', 1, 7500);

/* ---------------------------------------------------- */
/* 3: CONSULTAS DE VERIFICACION (SELECT, WHERE, JOIN)   */
/* ---------------------------------------------------- */

/* Consulta SELECT basico: visualizar todo el catalogo */
SELECT id_producto, nombre, categoria, precio, stock, disponible FROM producto;

/* Consulta SELECT con WHERE con condicion: devuelve productos de Cafeteria con precio menor a $4000 */
SELECT id_producto, nombre, precio, stock FROM producto 
WHERE categoria = 'Cafeteria' AND precio < 4000;

/* Consulta INNER JOIN: detalle completo del pedido, cliente y productos */
SELECT 
    pedido.id_pedido,
    pedido.estado,
    cliente.nombre AS nombre_cliente,
    producto.nombre AS nombre_producto,
    detalle_pedido.cantidad,
    detalle_pedido.precio_unitario,
    (detalle_pedido.cantidad * detalle_pedido.precio_unitario) AS subtotal
FROM pedido
INNER JOIN cliente ON pedido.id_cliente = cliente.id_cliente
INNER JOIN detalle_pedido ON pedido.id_pedido = detalle_pedido.id_pedido
INNER JOIN producto ON detalle_pedido.id_producto = producto.id_producto;

/* ---------------------------------------------------- */
/* 4: ACTUALIZACION Y ELIMINACION (UPDATE Y DELETE)     */
/* ---------------------------------------------------- */

/* Demostracion de UPDATE con verificacion (cambio de estado del pedido de Javier) */
SELECT id_pedido, estado FROM pedido WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440042'; /* ANTES */
UPDATE pedido SET estado = 'Entregado' WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440042'; /* CAMBIA ESTADO A: Entregado */
SELECT id_pedido, estado FROM pedido WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440042'; /* DESPUES */

/* Demostracion de DELETE con verificacion (eliminando un detalle de Francisco) */
SELECT * FROM detalle_pedido WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440041' AND id_producto = '770e8400-e29b-41d4-a716-446655440022'; /* ANTES */
DELETE FROM detalle_pedido WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440041' AND id_producto = '770e8400-e29b-41d4-a716-446655440022'; /* ELIMINAMOS DETALLE */
SELECT * FROM detalle_pedido WHERE id_pedido = '990e8400-e29b-41d4-a716-446655440041' AND id_producto = '770e8400-e29b-41d4-a716-446655440022'; /* DESPUES */

/* ---------------------------------------------------- */
/* 5: TRANSACCIONES CONTROLADAS (COMMIT Y ROLLBACK)     */
/* ---------------------------------------------------- */

/* Transaccion confirmada (COMMIT): Francisco canjea el Muffin por 25 puntos */

/* Verificacion ANTES: Muestra 120 pts y 10 de stock en una sola fila */
SELECT 
    c.nombre AS cliente,
    t.puntos_acumulados,
    p.nombre AS producto,
    p.stock
FROM cliente c
INNER JOIN tarjeta_fidelidad t ON c.id_cliente = t.id_cliente
INNER JOIN producto p ON p.id_producto = '770e8400-e29b-41d4-a716-446655440022'
WHERE c.id_cliente = '550e8400-e29b-41d4-a716-446655440001';

START TRANSACTION;
UPDATE tarjeta_fidelidad SET puntos_acumulados = puntos_acumulados - 25 WHERE id_cliente = '550e8400-e29b-41d4-a716-446655440001';
UPDATE producto SET stock = stock - 1 WHERE id_producto = '770e8400-e29b-41d4-a716-446655440022';
COMMIT;

/* Verificacion DESPUES: Muestra 95 pts y 9 de stock en una sola fila */
SELECT 
    c.nombre AS cliente,
    t.puntos_acumulados,
    p.nombre AS producto,
    p.stock
FROM cliente c
INNER JOIN tarjeta_fidelidad t ON c.id_cliente = t.id_cliente
INNER JOIN producto p ON p.id_producto = '770e8400-e29b-41d4-a716-446655440022'
WHERE c.id_cliente = '550e8400-e29b-41d4-a716-446655440001';

/* Transaccion revertida (ROLLBACK): Fernanda intenta canjear Bolsa de Cafe pero se arrepiente */
SELECT puntos_acumulados FROM tarjeta_fidelidad WHERE id_cliente = '550e8400-e29b-41d4-a716-446655440003'; /* ANTES: 310 pts */

START TRANSACTION;
UPDATE tarjeta_fidelidad SET puntos_acumulados = puntos_acumulados - 100 WHERE id_cliente = '550e8400-e29b-41d4-a716-446655440003';
SELECT puntos_acumulados FROM tarjeta_fidelidad WHERE id_cliente = '550e8400-e29b-41d4-a716-446655440003'; /* ESTADO TEMPORAL: 210 pts */
ROLLBACK;

SELECT puntos_acumulados FROM tarjeta_fidelidad WHERE id_cliente = '550e8400-e29b-41d4-a716-446655440003'; /* DESPUES: Vuelve a 310 pts */

/* ---------------------------------------------------- */
/* 6: PRUEBAS DE ESTRUCTURA (ALTER, TRUNCATE, DROP)     */
/* ---------------------------------------------------- */

CREATE TABLE promocion_practica (
    id_promo INT PRIMARY KEY,
    descripcion VARCHAR(50)
) ENGINE = INNODB;

ALTER TABLE promocion_practica ADD COLUMN descuento INT NOT NULL;
INSERT INTO promocion_practica (id_promo, descripcion, descuento) VALUES (1, 'Promo Verano', 15), (2, 'Promo Invierno', 20);
SELECT * FROM promocion_practica;

TRUNCATE TABLE promocion_practica;
SELECT * FROM promocion_practica;

DROP TABLE promocion_practica;