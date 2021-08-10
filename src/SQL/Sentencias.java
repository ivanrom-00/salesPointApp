package SQL;

public class Sentencias {
    
    public static final String INICIAR_SESION = "EXEC dbo.procIniciarSesion ";
    public static final String INICIAR_VENTA = "EXEC dbo.procInicializarVenta";
    public static final String AGREGAR_A_VENTA = "EXEC dbo.procAgregarProductoVenta ";
    public static final String TABLA_CARRITO = "SELECT * FROM ventaActual";
    public static final String TABLA_PRODUCTOS = "SELECT * FROM vistaInventario ORDER BY prod_nombre";
    public static final String AGREGAR_PRODUCTO = "EXEC dbo.procInsertarProducto ";
    public static final String ACTUALIZAR_PRODUCTO = "EXEC dbo.procActualizaProd ";
    public static final String TABLA_VENTAS = "SELECT * FROM historialVentas";
    public static final String CERRAR_SESION = "EXEC dbo.procCerrarSesion";
    public static final String ULTIMA_ACCION = "EXEC dbo.procUltimaAccion";
    public static final String CANCELAR_PRODUCTO = "EXEC dbo.procCancelarProducto ";
    public static final String CANCELAR_VENTA = "EXEC dbo.procCancelarVenta";
    public static final String TERMINAR_VENTA = "EXEC dbo.procFinalizarVenta";
    public static final String VENTA_ACTUAL = "(dbo.obtenerVentaActual())";
    public static final String PERMISO_ACTUAL = "SELECT * FROM vistaPermiso";
    
}
