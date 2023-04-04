//package utils;
//
//import androidx.annotation.NonNull;
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.Ignore;
//import androidx.room.PrimaryKey;
//import androidx.room.TypeConverters;
//
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.UUID;
//
//import vendis.preventa.converter.IntegerListConverter;
//
//@Entity(tableName = "printers")
//public class Impresora implements Serializable {
//
//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    @SerializedName("id")
//    @Expose
//    private int id;
//    @SerializedName("nombre")
//    @Expose
//    private String nombre;
//    @SerializedName("ip")
//    @Expose
//    private String ip;
//    @SerializedName("puerto")
//    @Expose
//    private int puerto;
//    @TypeConverters(IntegerListConverter.class)
//    @SerializedName("tipo_documentos")
//    @Expose
//    private List<Integer> tipoDocumentos = null;// tipo de documentos ejempo los tipos son 1: nota de entrega, 2: pedido, 3: factura, 4: pagos
//    @TypeConverters(IntegerListConverter.class)
//    @SerializedName("categorias")
//    @Expose
//    private List<Integer> categorias = null;// ids de las categorias que se imprimiran
//    @SerializedName("estado")
//    @Expose
//    private int estado;//estado actual, ejemplo: 0 inactivo, 1 activo
//    @SerializedName("tipo_impresora")
//    @Expose
//    private int tipoImpresora;// tipo 1: wifi/red, 2: bluetooth, 3: Usb
//    @SerializedName("uuid")
//    @Expose
//    @Ignore
//    private UUID uuid;
//    @SerializedName("device_address")
//    @Expose
//    private String deviceAddress;
//    @SerializedName("tipo_papel")
//    @Expose
//    private int tipoPapel;// para nosotros sera de 80mm y 55mm
//    @SerializedName("cantidad_caracteres_linea")
//    @Expose
//    private int cantidadCaracteresLinea;
//    @SerializedName("printer_cierre_caja")
//    @Expose
//    private Integer printerCierreCaja;
//    @SerializedName("abrir_caja")
//    @Expose
//    private Integer abrirCaja;// flag para abrir caja/ 1: abrir, 2:no abrir
//    @SerializedName("tipo_fuente")
//    @Expose
//    //2020
//    private Integer tipoFuente;// FONT A, FONT B
//    @SerializedName("code_page")
//    @Expose
//    private String codePage;// LATIN 3, PC437, PC775
//    @SerializedName("no_imprimir_texto_abrir_cajero")
//    @Expose
//    private Boolean noImprimirTextoAbrirCajero;// si para solo abrir cajero de dinero y no imprimir nada;
//    @SerializedName("beep_printer")
//    @Expose
//    private Boolean beepPrinter;
//    //2020 02
//    @SerializedName("model_printer")
//    @Expose
//    private String modelPrinter;// Zebra, otros
//
//    //usb
//    @SerializedName("vendor_usb")
//    @Expose
//    private int vendorUsb;
//    @SerializedName("product_id_usb")
//    @Expose
//    private int productIdUsb;
//    @SerializedName("device_id_usb")
//    @Expose
//    private int deviceIdUsb;
//    @SerializedName("device_name_usb")
//    @Expose
//    private String deviceNameUsb;
//    //usb
//
//    @SerializedName("lines_below")
//    @Expose
//    private Integer linesBelow;
//
//    private int esLocal = 0;// 0 no esta integrada, 1: sunmi V2, 2: PAX, 3: mobiprint MP3,
//
//    public Impresora() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getEstado() {
//        return estado;
//    }
//
//    public void setEstado(int estado) {
//        this.estado = estado;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public int getPuerto() {
//        return puerto;
//    }
//
//    public void setPuerto(int puerto) {
//        this.puerto = puerto;
//    }
//
//    public List<Integer> getTipoDocumentos() {
//        return tipoDocumentos;
//    }
//
//    public void setTipoDocumentos(List<Integer> tipoDocumentos) {
//        this.tipoDocumentos = tipoDocumentos;
//    }
//
//    public List<Integer> getCategorias() {
//        return categorias;
//    }
//
//    public void setCategorias(List<Integer> categorias) {
//        this.categorias = categorias;
//    }
//
//    public int getTipoImpresora() {
//        return tipoImpresora;
//    }
//
//    public void setTipoImpresora(int tipoImpresora) {
//        this.tipoImpresora = tipoImpresora;
//    }
//
//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }
//
//    public String getDeviceAddress() {
//        return deviceAddress;
//    }
//
//    public void setDeviceAddress(String deviceAddress) {
//        this.deviceAddress = deviceAddress;
//    }
//
//    public int getTipoPapel() {
//        return tipoPapel;
//    }
//
//    public void setTipoPapel(int tipoPapel) {
//        this.tipoPapel = tipoPapel;
//    }
//
//    public int getCantidadCaracteresLinea() {
//        return cantidadCaracteresLinea;
//    }
//
//    public void setCantidadCaracteresLinea(int cantidadCaracteresLinea) {
//        this.cantidadCaracteresLinea = cantidadCaracteresLinea;
//    }
//
//    public int getVendorUsb() {
//        return vendorUsb;
//    }
//
//    public void setVendorUsb(int vendorUsb) {
//        this.vendorUsb = vendorUsb;
//    }
//
//    public int getProductIdUsb() {
//        return productIdUsb;
//    }
//
//    public void setProductIdUsb(int productIdUsb) {
//        this.productIdUsb = productIdUsb;
//    }
//
//    public int getDeviceIdUsb() {
//        return deviceIdUsb;
//    }
//
//    public void setDeviceIdUsb(int deviceIdUsb) {
//        this.deviceIdUsb = deviceIdUsb;
//    }
//
//    public String getDeviceNameUsb() {
//        return deviceNameUsb;
//    }
//
//    public void setDeviceNameUsb(String deviceNameUsb) {
//        this.deviceNameUsb = deviceNameUsb;
//    }
//
//    public Integer getPrinterCierreCaja() {
//        return printerCierreCaja;
//    }
//
//    public void setPrinterCierreCaja(Integer printerCierreCaja) {
//        this.printerCierreCaja = printerCierreCaja;
//    }
//
//    public Integer getAbrirCaja() {
//        return abrirCaja;
//    }
//
//    public void setAbrirCaja(Integer abrirCaja) {
//        this.abrirCaja = abrirCaja;
//    }
//
//    public Integer getTipoFuente() {
//        return tipoFuente;
//    }
//
//    public void setTipoFuente(Integer tipoFuente) {
//        this.tipoFuente = tipoFuente;
//    }
//
//    public String getCodePage() {
//        return codePage;
//    }
//
//    public void setCodePage(String codePage) {
//        this.codePage = codePage;
//    }
//
//    public Boolean getNoImprimirTextoAbrirCajero() {
//        return noImprimirTextoAbrirCajero;
//    }
//
//    public void setNoImprimirTextoAbrirCajero(Boolean noImprimirTextoAbrirCajero) {
//        this.noImprimirTextoAbrirCajero = noImprimirTextoAbrirCajero;
//    }
//
//    public Boolean getBeepPrinter() {
//        return beepPrinter;
//    }
//
//    public void setBeepPrinter(Boolean beepPrinter) {
//        this.beepPrinter = beepPrinter;
//    }
//
//    public String getModelPrinter() {
//        return modelPrinter;
//    }
//
//    public void setModelPrinter(String modelPrinter) {
//        this.modelPrinter = modelPrinter;
//    }
//
//    public int getEsLocal() {
//        return esLocal;
//    }
//
//    public void setEsLocal(int esLocal) {
//        this.esLocal = esLocal;
//    }
//
//    public Integer getLinesBelow() {
//        return linesBelow;
//    }
//
//    public void setLinesBelow(Integer linesBelow) {
//        this.linesBelow = linesBelow;
//    }
//
//    @Override
//    public String toString() {
//        return new Gson().toJson(this);
//    }
//}
