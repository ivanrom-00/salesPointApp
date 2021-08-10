CREATE DATABASE miTiendita
GO
USE miTiendita
GO

CREATE TABLE cantidadProductos 
	(ven_id INT NOT NULL, 
	prod_id INT NOT NULL, 
	cant_cantidad INT NOT NULL, 
	cant_subTotal MONEY NOT NULL, 
	PRIMARY KEY (ven_id, prod_id));
GO

CREATE TABLE producto 
	(prod_id INT IDENTITY PRIMARY KEY NOT NULL, 
	prod_nombre VARCHAR(255) NOT NULL, 
	prod_precio MONEY NOT NULL, 
	prod_inventario INT NOT NULL, 
	ses_id BIGINT NOT NULL);
GO

CREATE TABLE roles 
	(rol_id INT IDENTITY PRIMARY KEY NOT NULL, 
	rol_nombre VARCHAR(255) NOT NULL, 
	rol_permiso BIT NOT NULL, 
	rol_fcreacion DATETIME NOT NULL, 
	ses_id BIGINT NOT NULL);
GO

CREATE TABLE sesiones 
	(ses_id BIGINT IDENTITY PRIMARY KEY NOT NULL,
	ses_inicio DATETIME NOT NULL, 
	ses_fin DATETIME NULL, 
	ses_ultima_accion DATETIME NULL, 
	usr_id INT NOT NULL);
GO

CREATE TABLE usuarios 
	(usr_id INT IDENTITY PRIMARY KEY NOT NULL, 
	usr_login VARCHAR(32) NOT NULL, 
	usr_pwd CHAR(32) NOT NULL, 
	ses_id BIGINT NOT NULL, 
	rol_id INT NOT NULL);
GO

CREATE TABLE venta 
	(ven_id INT IDENTITY PRIMARY KEY NOT NULL, 
	ven_total MONEY NULL, 
	ven_fecha DATETIME NULL, 
	ses_id BIGINT NOT NULL);
GO


ALTER TABLE cantidadProductos ADD CONSTRAINT FKventa_id FOREIGN KEY (ven_id) REFERENCES venta (ven_id);
GO

ALTER TABLE cantidadProductos ADD CONSTRAINT FKproducto_id FOREIGN KEY (prod_id) REFERENCES producto (prod_id);
GO

ALTER TABLE producto ADD CONSTRAINT sesionCreaProducto FOREIGN KEY (ses_id) REFERENCES sesiones (ses_id);
GO

ALTER TABLE roles ADD CONSTRAINT sesionCreaRoles FOREIGN KEY (ses_id) REFERENCES sesiones (ses_id);
GO

ALTER TABLE venta ADD CONSTRAINT sesionRealizaVenta FOREIGN KEY (ses_id) REFERENCES sesiones (ses_id);
GO

ALTER TABLE sesiones ADD CONSTRAINT usuarioIniciaSesion FOREIGN KEY (usr_id) REFERENCES usuarios (usr_id);
GO

-- Crear constraints de los usuarios despues de crear al administrador
ALTER TABLE usuarios ADD CONSTRAINT sesionCreaUsuario FOREIGN KEY (ses_id) REFERENCES sesiones (ses_id);
GO
ALTER TABLE usuarios ADD CONSTRAINT ususrioTieneUnRol FOREIGN KEY (rol_id) REFERENCES roles (rol_id);
GO

-- Funcion para obtener el permiso de un Usuario dado con su id
CREATE FUNCTION obtenerPermiso(@id INT)
RETURNS BIT
AS
BEGIN
	DECLARE @permiso BIT;

	SET @permiso = (SELECT rol_permiso
					FROM roles
					WHERE rol_id = (SELECT rol_id
									FROM usuarios
									WHERE usr_id = @id));

	RETURN @permiso;
END;
GO

-- Funcion para obtener el login de un Usuario dado con su id
CREATE FUNCTION obtenerLogin(@id INT)
RETURNS VARCHAR(32)
AS
BEGIN
	DECLARE @log VARCHAR(32);

	SET @log = (SELECT usr_login
					FROM usuarios
					WHERE usr_id = @id)

	RETURN @log;
END;
GO;

-- Funcion para obtener la contraseña de un Usuario dado con su id
CREATE FUNCTION obtenerPassword(@id INT)
RETURNS CHAR(32)
AS
BEGIN
	DECLARE @pass CHAR(32);

	SET @pass = (SELECT usr_pwd
					FROM usuarios
					WHERE usr_id = @id)

	RETURN @pass;
END;
GO;


-- Funcion para obtener el id de Usuario con su login
CREATE FUNCTION obtenerID(@log VARCHAR(32))
RETURNS INT
AS
BEGIN
	DECLARE @id INT;

	SET @id = (SELECT usr_id
					FROM usuarios
					WHERE usr_login = @log)

	RETURN @id;
END;
GO;

-- Procedimiento para iniciar sesion con un login de Usuario
CREATE PROCEDURE procIniciarSesion @log VARCHAR(32), @pass CHAR(32)
AS
BEGIN

	IF  (EXISTS(SELECT usr_login
				FROM usuarios
				WHERE usr_login = @log))
		BEGIN
			DECLARE @id INT;
			SET @id = dbo.obtenerID(@log);

			IF (EXISTS(SELECT usr_pwd
					FROM usuarios
					WHERE usr_id = @id AND usr_pwd = @pass))

					BEGIN
						INSERT INTO sesiones
						(ses_inicio, usr_id)
						VALUES(GETDATE(), @id);
					END
			ELSE
				BEGIN
				RAISERROR('Usuario o contraseña incorrectos', 16, 1);
				END
		END
	ELSE
		BEGIN
		RAISERROR('Usuario o contraseña incorrectos', 16, 1);
		END
END;
GO;

-- Funcion para obtener el id de Sesion actual
CREATE FUNCTION obtenerSesionActual()
RETURNS INT
AS
BEGIN
	DECLARE @id INT;

	SET @id = (SELECT ses_id
			  FROM sesiones
			  WHERE ses_inicio = (SELECT MAX(ses_inicio)
								  FROM sesiones))

	RETURN @id;
END;
GO;

-- Funcion para obtener el id de Usuario actual en la sesion
CREATE FUNCTION obtenerUsuarioActual()
RETURNS INT
AS
BEGIN
	DECLARE @id INT;
	DECLARE @sesion INT;

	SET @sesion = dbo.obtenerSesionActual()

	SET @id = (SELECT usr_id
			  FROM sesiones
			  WHERE ses_id = @sesion)

	RETURN @id;
END;
GO;

-- Procedimiento para insertar en tabla Producto
CREATE PROCEDURE procInsertarProducto @nombre VARCHAR(255), @precio MONEY, @inventario INT
AS
BEGIN
	INSERT INTO producto
	(prod_nombre, prod_precio, prod_inventario, ses_id)
	VALUES(@nombre, @precio, @inventario, dbo.obtenerSesionActual())
END
GO

-- Trigger para comprobar que solo el usuario con rol de Administrador pueda agregar y eliminar productos de la BD
CREATE TRIGGER gestionarProductos
ON producto
INSTEAD OF INSERT, DELETE
AS
BEGIN
	DECLARE @prod_id INT;
	DECLARE @nombre VARCHAR(255);
	DECLARE @precio MONEY;
	DECLARE @inventario INT;
	DECLARE @sesion INT;

	DECLARE @idUsuario INT;
	DECLARE @permiso BIT;

	SET @sesion = dbo.obtenerSesionActual();
	SET @idUsuario = dbo.obtenerUsuarioActual();
	SET @permiso = dbo.obtenerPermiso(@idUsuario);

	-- Comprobar si se intento hacer un INSERT
	IF EXISTS(SELECT *
			  FROM inserted)
	BEGIN
		SELECT @nombre = prod_nombre, @precio = prod_precio, @inventario = prod_inventario
		FROM inserted

		IF(@permiso = 0)
			BEGIN
				INSERT INTO producto
				(prod_nombre, prod_precio, prod_inventario, ses_id)
				VALUES(@nombre, @precio, @inventario, @sesion)

				UPDATE sesiones
				SET ses_ultima_accion = GETDATE()
				WHERE ses_id = @sesion
			END
		ELSE
		BEGIN
			RAISERROR('No tiene permisos para realizar esa accion', 16, 1)
		END

	END


	-- Comprobar si se intento hacer un DELETE
	ELSE IF EXISTS(SELECT *
				   FROM deleted)

		SELECT @prod_id = prod_id
		FROM deleted;

		IF(@permiso = 0)
			BEGIN
				DELETE
				FROM producto
				WHERE (prod_id = @prod_id);
			END

		ELSE
			BEGIN
			RAISERROR('No tiene permisos para realizar esa accion', 16, 1)
			END
END;
GO;

-- Procedimiento para inicializar venta
CREATE PROCEDURE procInicializarVenta
AS
BEGIN
	DECLARE @ses INT;
	SET @ses = dbo.obtenerSesionActual()
	
	INSERT INTO venta
	(ses_id)
	VALUES(@ses);
END
GO



-- Procedimiento para finalizar venta
CREATE PROCEDURE procFinalizarVenta
AS
BEGIN
	
	DECLARE @total MONEY

	SET @total = (SELECT SUM(cant_subTotal) 
				  FROM cantidadProductos 
				  WHERE ven_id = (SELECT MAX(ven_id) 
								  FROM venta))

	UPDATE venta
	SET ven_fecha = GETDATE(), ven_total = @total
	WHERE ven_id = (SELECT MAX(ven_id) FROM venta)

	UPDATE sesiones
	SET ses_ultima_accion = GETDATE()
	WHERE ses_id = (dbo.obtenerSesionActual())
	
END;
GO;

-- Funcion para obtener el inventario de un producto dado su ID
CREATE FUNCTION obtenerInventario(@id INT)
RETURNS INT
AS
BEGIN
	DECLARE @inventario INT;

	SET @inventario = (SELECT prod_inventario
					  FROM producto
					  WHERE prod_id = @id)

	RETURN @inventario
END
GO

-- Funcion para obtener el inventario de un producto dado su ID
CREATE FUNCTION obtenerPrecio(@id INT)
RETURNS MONEY
AS
BEGIN
	DECLARE @precio MONEY;

	SET @precio = (SELECT prod_precio
					  FROM producto
					  WHERE prod_id = @id)

	RETURN @precio
END;
GO;

-- Procedimiento para agregar un producto a la venta
CREATE PROCEDURE procAgregarProductoVenta @prod_id INT, @cant INT
AS
BEGIN

		BEGIN TRY

			DECLARE @existencia INT;
			SET @existencia = dbo.obtenerInventario(@prod_id)

			IF (@existencia >= @cant)
			BEGIN
				DECLARE @ven_id INT;
				SET @ven_id = (SELECT MAX(ven_id)
							  FROM venta)

				DECLARE @subTotal MONEY;
				SET @subTotal = @cant * dbo.obtenerPrecio(@prod_id)

				INSERT INTO cantidadProductos
				(ven_id, prod_id, cant_cantidad, cant_subTotal)
				VALUES(@ven_id, @prod_id, @cant, @subTotal)

				UPDATE producto
				SET prod_inventario = prod_inventario - @cant
				WHERE prod_id = @prod_id

				UPDATE sesiones
				SET ses_ultima_accion = GETDATE()
				WHERE ses_id = (dbo.obtenerSesionActual())


			END

			ELSE
			BEGIN
				RAISERROR('No hay inventario suficiente', 16, 1)
			END

			

		END TRY
	BEGIN CATCH
		RAISERROR('No existe el producto', 16, 1)
	END CATCH
	
END;
GO

-- Procedimiento para crear un Usuario
CREATE PROCEDURE procCrearUsuario @login VARCHAR(32), @pass CHAR(32), @rol INT
AS
BEGIN
	DECLARE @sesion INT;
	SET @sesion = dbo.obtenerSesionActual();

	INSERT INTO usuarios
	(usr_login, usr_pwd, ses_id, rol_id)
	VALUES(@login, @pass, @sesion, @rol)
END;
GO;

-- Procedimiento para promover o degradar un Usuario
CREATE PROCEDURE procModificarUsuario @id INT, @rol INT
AS
BEGIN
	UPDATE usuarios
	SET rol_id = @rol
	WHERE usr_id = @id
END;
GO;

-- Vista para mostrar los productos de la venta actual
CREATE VIEW ventaActual
AS 
SELECT prod.prod_nombre, prod.prod_precio, cant.cant_cantidad, cant_subTotal
FROM producto AS prod, cantidadProductos AS cant
WHERE cant.ven_id = (SELECT MAX(ven_id)
					 FROM venta) AND cant.prod_id = prod.prod_id
GO

-- Vista para mostrar el inventario
CREATE VIEW vistaInventario
AS 
SELECT prod_id, prod_nombre, prod_precio, prod_inventario
FROM producto
GO

-- Funcion para obtener el permiso del Usuario actual
CREATE FUNCTION obtenerPermisoActual()
RETURNS BIT
AS
BEGIN
	DECLARE @id INT;
	SET @id = dbo.obtenerUsuarioActual()
	
	DECLARE @permiso BIT;
	SET @permiso = dbo.obtenerPermiso(@id)

	RETURN @permiso;
END
GO

-- Vista del permiso del usuario actual
CREATE VIEW vistaPermiso
AS
SELECT rol_id
FROM usuarios
WHERE usr_id = (dbo.obtenerUsuarioActual())
GO

-- Procedimiento para actualizar informacion de un producto
CREATE PROCEDURE procActualizaProd @id INT, @nombre VARCHAR(255), @precio MONEY, @inventario INT
AS
BEGIN
	UPDATE producto
	SET prod_nombre = @nombre, prod_precio = @precio, prod_inventario = @inventario
	WHERE prod_id = @id	
END
GO

-- Funcion para obtener el login de usuario segun ID de Sesion
CREATE FUNCTION obtenerLoginConSesion(@ses_id INT)
RETURNS VARCHAR(32)
AS
BEGIN
	DECLARE @usr_id INT
	SET @usr_id = (SELECT usr_id
					FROM sesiones
					WHERE ses_id = @ses_id)

	DECLARE @log VARCHAR(32)
	SET @log = (dbo.obtenerLogin(@usr_id))

	RETURN @log
END
GO

CREATE VIEW historialVentas
AS
SELECT ven_id, ven_fecha, ven_total, V.ses_id, U.usr_login
FROM usuarios AS U, venta AS V
INNER JOIN sesiones AS ses ON (V.ses_id = ses.ses_id)
WHERE U.usr_id = ses.usr_id
GO

-- Procedimiento para Cerrar la Sesion Actual
CREATE PROCEDURE procCerrarSesion
AS
BEGIN

	DECLARE @actual INT
	SET @actual = (dbo.obtenerSesionActual())

	UPDATE sesiones
	SET ses_fin = GETDATE()
	WHERE ses_id = @actual
END
GO

-- Procedimiento para añadir una ultima accion la Sesion Actual
CREATE PROCEDURE procUltimaAccion
AS
BEGIN

	DECLARE @actual INT
	SET @actual = (dbo.obtenerSesionActual())

	UPDATE sesiones
	SET ses_ultima_accion = GETDATE()
	WHERE ses_id = @actual
END
GO

-- Funcion para obtener id de producto segun su nombre
CREATE FUNCTION obtenerIDProducto(@prod VARCHAR(255))
RETURNS INT
AS
BEGIN
	DECLARE @prod_id INT

	SET @prod_id = (SELECT prod_id
					FROM producto
					WHERE prod_nombre = @prod)

	RETURN @prod_id
END
GO

-- Procedimiento para cancelar una venta
CREATE PROCEDURE procCancelarProducto @prod VARCHAR(255)
AS
BEGIN
	DECLARE @ventaActual INT
	SET @ventaActual = (SELECT MAX(ven_id) FROM venta)

	DECLARE @prod_id INT
	SET @prod_id = (dbo.obtenerIDProducto(@prod))

	DELETE FROM cantidadProductos
	WHERE ven_id = @ventaActual AND prod_id = @prod_id

END
GO

-- Procedimiento para cancelar la venta actual
CREATE PROCEDURE procCancelarVenta
AS
BEGIN
	DELETE FROM venta
	WHERE ven_id = (SELECT MAX(ven_id) FROM venta)
END

DROP TRIGGER regresarInventario
GO

-- Trigger para regresar la cantidad que se iba a vender al cancelar una venta
CREATE TRIGGER regresarInventario
ON cantidadProductos
INSTEAD OF DELETE
AS
BEGIN

	DECLARE @ventaActual INT
	SET @ventaActual = (SELECT MAX(ven_id) FROM venta)

	DECLARE @cantidad INT
	SET @cantidad = (SELECT cant_cantidad FROM deleted)

	DECLARE @prod INT;
	SET @prod = (SELECT prod_id FROM deleted)

	UPDATE producto
	SET prod_inventario = prod_inventario + @cantidad
	WHERE prod_id = @prod

	DELETE FROM cantidadProductos
	WHERE ven_id = @ventaActual
END
GO

-- Funcion para obtener la venta actual
CREATE FUNCTION obtenerVentaActual()
RETURNS INT
AS
BEGIN
	DECLARE @ventaActual INT
	SET @ventaActual = (SELECT ven_id
						FROM venta
						WHERE ven_id = (SELECT MAX(ven_id) FROM venta))
	
	RETURN @ventaActual
END
GO

