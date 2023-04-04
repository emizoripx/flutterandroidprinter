package utils;



// shared preference for saving state of lock (ON | OFF)
public final class PrefUtils {
    private static final String PREF_LOCK_MODE = "pref_lock_mode";
    public static final int BITMAP_REQ_WIDTH = 360;
    public static final int BITMAP_REQ_HEIGHT = 360;

    public static final int BITMAP_REQ_WIDTH_LOGO = 150;
    public static final int BITMAP_REQ_HEIGHT_LOGO = 150;

    public static final int PRINTER_80_MM_DEFAULT_CHARS = 48;
    public static final int PRINTER_55_MM_DEFAULT_CHARS = 32;

    public static final int PRINTER_80_MM = 1;
    public static final int PRINTER_55_MM = 2;

    public static final int FONT_A = 1;
    public static final int FONT_B = 2;

    public static final int PRINTER_WIFI_LAN = 1;
    public static final int PRINTER_BLUETOOTH = 2;
    public static final int PRINTER_USB = 3;
    //fragmentos
    public static final int FRAGMENT_PRINCIPAL = 1;
    public static final int FRAGMENT_LIST_TRANSACTION = 2;
    public static final int FRAGMENT_LIST_PEDIDOS = 3;
    public static final int FRAGMENT_LIST_BUSINESS_INVITATION = 4;
    public static final int FRAGMENT_DETAIL_BUSINESS = 5;
    public static final int FRAGMENT_AM_PRODUCTO = 6;
    public static final int FRAGMENT_POS_CARRITO = 7;
    public static final int FRAGMENT_LIST_LOCATION = 8;
    public static final int FRAGMENT_LIST_USER = 9;
    public static final int FRAGMENT_AM_UNIDAD = 10;
    public static final int FRAGMENT_ASIGNAR_CATEGORIA = 11;
    public static final int FRAGMENT_AM_CATEGORIA = 12;
    public static final int FRAGMENT_LIST_UNIDAD = 13;
    public static final int FRAGMENT_LIST_CATEGORIA = 14;
    public static final int FRAGMENT_INVENTORY = 15;
    public static final int FRAGMENT_LIST_PRODUCT = 16;
    public static final int FRAGMENT_LIST_REVIEW = 17;
    public static final int FRAGMENT_VENDIS_PEDIDOS = 18;
    public static final int FRAGMENT_TU_NEGOCIO = 19;
    public static final int FRAGMENT_DETALLE_PEDIDO = 20;
    public static final int FRAGMENT_LIST_DELIVERYBOY = 21;
    public static final int FRAGMENT_VIEW_BUSINESS = 22;
    public static final int FRAGMENT_AM_PRODUCTO_P = 23;
    public static final int FRAGMENT_PRECIOS_PEDIDOS = 24;
    public static final int FRAGMENT_LIST_DELIVERYBOY_A = 25;
    public static final int FRAGMENT_SETTINGS = 26;
    public static final int FRAGMENT_LIST_PRINTERS = 27;
    public static final int FRAGMENT_AM_PRINTER = 28;
    public static final int FRAGMENT_LIST_CLIENTS = 29;
    public static final int FRAGMENT_CREATE_CLIENT = 30;
    public static final int FRAGMENT_SEE_TRANSACTION = 31;
    public static final int FRAGMENT_CASH_REGISTER = 32;
    public static final int FRAGMENT_FINAL_POS_VENTA = 33;
    public static final int FRAGMENT_POS_COBRAR = 34;
    public static final int FRAGMENT_ENVIAR_SMS = 35;
    public static final int FRAGMENT_SETTINGS_BUSINESS = 36;
    public static final int FRAGMENT_INFO_CLIENT = 37;
    public static final int FRAGMENT_AM_LOCATION = 38;
    public static final int FRAGMENT_AM_VARIATION = 39;
    public static final int FRAGMENT_LOGINPIN = 50;
    public static final int FRAGMENT_LIST_ORDERS = 60;
    public static final int FRAGMENT_INFO_CASH_REGISTER = 61;
    public static final int FRAGMENT_STOCK_ADJUSTMENT = 62;
    public static final int FRAGMENT_DETAIL_CLIENT = 63;
    public static final int FRAGMENT_PRODUCT_CATEGORY = 64;
    public static final int FRAGMENT_WEB_VERSION = 65;
    public static final int FRAGMENT_INFO_CASH_REGISTER_PRO = 66;
    public static final int FRAGMENT_FILTRO_TRANSACCION = 67;
    public static final int FRAGMENT_NO_REGISTER_PRODUCT = 68;
    public static final int FRAGMENT_SEE_TRANSACTION_PDF = 69;
//    public static final int FRAGMENT_ = 6;
    //fragmentos

    //preferencias para debugger
    public static final String HOST_HTTP_NAME = "pref_datos_http";


    /*// return state of lock
    public static boolean isKioskModeActive(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_LOCK_MODE, false);
    }

    // change lock
    public static void setKioskModeActive(final boolean active, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_LOCK_MODE, active).commit();
    }*/


    public static final String CONTENT_INICIO = "co_in";// vista inicio
    public static final String CONTENT_BIENVENIDO = "co_we";// vista bienvenidos
    public static final String CONTENT_REGISTER = "co_reg";// vista regitro
    public static final String CONTENT_LOGIN = "co_lo";// vista login
    public static final String CONTENT_VENTA_POS = "co_vpos";// vsita venta pos
    public static final String CONTENT_VENTA_DETALLE = "co_vdet";//vista detalle de la venta
    public static final String CONTENT_VENTA_COBRAR = "co_vcob";// vista cobrar
    public static final String CONTENT_VENTA_REALIZADA = "co_vhec"; // vista venta final
    public static final String CONTENT_VENTA_SMS = "co_vsms";//vista enviar sms
    public static final String CONTENT_VENTA_DOCUMENTO = "co_vdoc";// vista ver pdf
    public static final String CONTENT_VENTA_PRODUCTO = "co_vpro";// vista venta de producto(vista en comabio productos)
    public static final String CONTENT_VENTA_PRODUCTO_DESCUENTO = "co_vprodesc";// venta de producto con descuento
    public static final String CONTENT_DIALOG_PEDIDO = "co_diped";// vista dialogo pedido
    public static final String CONTENT_DIALOG_MENSAJE = "co_dmen";// vista dialogo mensaje
    public static final String CONTENT_VENTA_RAPIDA = "co_vr";// venta rapida
    public static final String CONTENT_DIALOG_CANTIDAD = "co_dcant";// vista dialogo cantidad
    public static final String CONTENT_SCANNER = "co_scan";// vista scanner
    public static final String CONTENT_MENU = "co_m";// vista menu
    public static final String CONTENT_HEADER = "co_h";// vista header
    public static final String CONTENT_SELECCION_EMPRESA = "co_lbuss";// vista lista business
    public static final String CONTENT_NUEVA_EMPRESA = "co_nbuss";// vista nueva empresa
    public static final String CONTENT_EDITAR_EMPRESA = "co_ebuss";// vista edicion de empresa
    public static final String CONTENT_PERFIL_USUARIO = "co_puser";// vista perfil de usuario
    public static final String CONTENT_RESET_PASSWORD = "co_rpass";// vista reset password
    public static final String CONTENT_LISTA_PEDIDO = "co_lped";// lista pedido
    public static final String CONTENT_FILTRO = "co_fit";//vista filtro
    public static final String CONTENT_EDITAR_TRANSACCION = "co_etrans";// vista editar transaccion
    public static final String CONTENT_INVENTARIO = "co_inv";// vista inventario
    public static final String CONTENT_LISTA_PRODUCTO = "co_lpro";// vista lista producto
    public static final String CONTENT_NUEVO_PRODUCTO = "co_npro";// vista nuevo producto
    public static final String CONTENT_SELECCIONAR_CATEGORIA = "co_scat"; // vista seleccionar categoria
    public static final String CONTENT_NUEVA_CATEGORIA = "co_ncat";// vista nueva categoria
    public static final String CONTENT_NUEVA_VARIANTE = "co_nvar";// vista neuva variante
    public static final String CONTENT_STOCK = "co_stock";//vista estock
    public static final String CONTENT_EDITAR_VARIANTE = "co_evar";// vista editar variante
    public static final String CONTENT_SELECCIONAR_UNIDAD = "co_suni";// vista seleccionar unidad
    public static final String CONTENT_EDITAR_PRODUCTO = "co_epro";// vista editar producto
//    public static final String CONTENT_EDITAR_NEGOCIO = "co_ebuss";// vista editar empresa
    public static final String CONTENT_LISTA_CATEGORIA = "co_lcat";// vista lista de categoria
    public static final String CONTENT_EDITAR_CATEGORIA = "co_ecat";// vista editar categoria
    public static final String CONTENT_ASIGNAR_PRODUCTOS = "co_asiprod";// vista asignar productos a categoria
    public static final String CONTENT_LISTA_UNIDAD = "co_luni";// vista lista unidad
    public static final String CONTENT_NUEVA_UNIDAD = "co_nuni";// vista nueva unidad
    public static final String CONTENT_EDITAR_UNIDAD = "co_euni";// vista editar unidad
    public static final String CONTENT_LISTA_TRANSACCION = "co_ltrans";// vista listar transacciones
    public static final String CONTENT_EDITAR_PAGO = "co_epay";// vista editar pago
    public static final String CONTENT_LISTA_CLIENTE = "co_lcli";// vista lista clientes
    public static final String CONTENT_NUEVO_CLIENTE = "co_ncli";// vista lista clientes
    public static final String CONTENT_INFORMACION_CLIENTE = "co_icli";// vista info cliente
    public static final String CONTENT_EDITAR_CLIENTE = "co_ecli";// vista editar cliente
    public static final String CONTENT_CAJA = "co_caj";// vista caja
    public static final String CONTENT_DIALOG_APERTURA_CAJA = "co_dop_caj"; // vista dialogo vista
    public static final String CONTENT_DIALOG_CIERRE_CAJA = "co_dclcaj";// vista dialogo de cierre de caja
    public static final String CONTENT_DIALOG_NUEVO_MOVIMIENTO_CAJA = "co_dnmocaj";// vista dialogo agregar movimiento de caja
    public static final String CONTENT_LISTA_USUARIO = "co_lus";// vista lista de usuarios o colaboradores
    public static final String CONTENT_EDITAR_USUARIO = "co_euse";// vista editar usuario o colaborador
    public static final String CONTENT_AGREGAR_USUARIO = "co_nuse";// vista nuevo nuevo usuario
//    public static final String CONTENT_EDITAR_INVITACION = "co_euse";// vista
    public static final String CONTENT_CONFIGURACION = "co_set";// vista de configuraciones
    public static final String CONTENT_EDITAR_TIENDA = "co_etie";// vista editar tienda
    public static final String CONTENT_LISTA_IMPRESORA = "co_lpri";// vista lista de immpresoras
    public static final String CONTENT_NUEVA_IMPRESORA = "co_npri";// vista nueva impresora
    public static final String CONTENT_EDITAR_IMPRESORA = "co_epri";// vista editar impresora
    public static final String CONTENT_LECTOR_TARJETA = "co_lectar";// vista lector de tarjeta
    public static final String CONTENT_LISTA_SUCURSAL = "co_lsuc";// vista lista de sucursal
    public static final String CONTENT_NUEVA_SUCURSAL = "co_nsuc";// vista nuevo sucursal
    public static final String CONTENT_EDITAR_SUCURSAL = "co_esuc";// vista editar sucursal
    public static final String CONTENT_DIALOG_CERRAR_SESION = "co_dcloses";// vista dialogo cerrar sesion

    public static final String CONTENT_DIALOG_CLIENTE = "co_dcli";// vista buscar dialogo cliente por codigo
    public static final String CONTENT_DIALOG_MENSAJE_CONDICION = "co_dmencon";// vista dialogo mensaje con condicion
    public static final String CONTENT_SELECCION_USUARIO = "co_suse";// vista seleccionar de usuario
    public static final String CONTENT_DETALLE_PEDIDO = "co_detail_order";// vista detalle de orden
    public static final String CONTENT_LISTA_REPARTIDORES = "co_ldelboy";// vista lista de repartidores
    public static final String CONTENT_AGREGAR_REPARTIDOR = "co_ndelboy";// vista nuevo repartidor
    public static final String CONTENT_ACTIVAR_PUBLISH = "co_actpubl";// vista activar publicar negocio en vendis pedidos
//    public static final String CONTENT_NUEVO_VARIANTE = "co_nvar";// vista nueva variante
    public static final String CONTENT_CASH_REGISTER = "co_cashreg";// vista de detalle de cajas


    /**
     * Repeat a String as many times you need.
     *
     * @param i - Number of Repeating the String.
     * @param s - The String wich you want repeated.
     * @return The string n - times.
     */
    public static String repeate(int i, String s) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++)
            sb.append(s);
        return sb.toString();
    }
}
