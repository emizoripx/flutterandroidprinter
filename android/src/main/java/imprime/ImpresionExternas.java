//package android.src.main.imprime;
//
//import android.Manifest;
//import android.content.Context;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.hardware.usb.UsbDeviceConnection;
//import android.hardware.usb.UsbEndpoint;
//import android.hardware.usb.UsbInterface;
//import android.os.Environment;
//import android.util.Log;
//
//import androidx.core.app.ActivityCompat;
//
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//import java.io.File;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
////import bo.com.vendis.pos.R;
////import vendis.preventa.model.dominio.request.sells.Payment;
////import vendis.preventa.model.dominio.response.account.Business;
////import vendis.preventa.model.dominio.response.account.User;
////import vendis.preventa.model.dominio.response.caja.CashRegister;
////import vendis.preventa.model.dominio.response.caja.CashRegisterReport;
////import vendis.preventa.model.dominio.response.sells.Contact;
////import vendis.preventa.model.dominio.response.sells.Dosage;
////import vendis.preventa.model.dominio.response.sells.PaymentLine;
////import vendis.preventa.model.dominio.response.sells.Sell;
////import vendis.preventa.model.dominio.response.sells.SellLine;
////import vendis.preventa.model.dominio.response.sucursal.Location;
////import vendis.preventa.model.dominio.utils.AdicionalPayment;
////import vendis.preventa.model.dominio.utils.Impresora;
////import vendis.preventa.restapi.rest.utils.PrefUtils;
////import vendis.preventa.utils.ConfigEmizor;
////import vendis.preventa.utils.LogUtils;
//
//import android.src.main.io.github.escposjava.PrinterService;
//import android.src.main.io.github.escposjava.print.BluetoothPrinter;
//import android.src.main.io.github.escposjava.print.NetworkPrinter;
//import android.src.main.io.github.escposjava.print.Printer;
//import android.src.main.io.github.escposjava.print.UsbPrinter;
////import vendis.preventa.utils.QRCodeHelper;
////import vendis.preventa.utils.utilqr.AndroidBmpUtil;
//
//import android.src.main.utils.LogUtils;
//import android.src.main.utils.PrefUtils;
//
//public class ImpresionExternas extends Thread {
//
//    private String TAG = ImpresionExternas.class.getName();
//
//    //private List<Impresora> listaImp = new ArrayList<>();
//    protected Impresora impresora;
//    protected Sell venta;
//    protected Location sucursal;
//    protected User usuario;
//
//    //agrego omar
//    protected Business empresa;
//    protected Payment pago;
//    protected Context context;
//    //agrego omar
//
//
//    protected int tipoImpresion;
//    protected String fechaPedido;
//    protected String moneda;
//    protected OutputStream outputStream;
//
//    private int DELAY_PRINTING_IMAGE = 0;
//    private int DELAY_PRINTING_NORMAL = 100;
//    private int DELAY_PRINTING_END = 100;
//    private int CANT_LINES_FOR_DELAY = 10;
//
//    // QR Code: Select the model
//    //              Hex     1D      28      6B      04      00      31      41      n1(x32)     n2(x00) - size of model
//    // set n1 [49 x31, model 1] [50 x32, model 2] [51 x33, micro qr code]
//    // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=140
//    byte[] modelQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, (byte) 0x04, (byte) 0x00, (byte) 0x31, (byte) 0x41, (byte) 0x32, (byte) 0x00};
//
//    // QR Code: Set the size of module
//    // Hex      1D      28      6B      03      00      31      43      n
//    // n depends on the printer
//    // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=141
//    // Set the size of module in 5
//    byte[] sizeQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, (byte) 0x03, (byte) 0x00, (byte) 0x31, (byte) 0x43, (byte) 0x05};
//
//    // QR Code: Select the error correction level
//    //          Hex     1D      28      6B      03      00      31      45      n
//    // Set n for error correction [48 x30 -> 7%] [49 x31-> 15%] [50 x32 -> 25%] [51 x33 -> 30%]
//    // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=142
//    // Selects Error correction level M
//    byte[] errorQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, (byte) 0x03, (byte) 0x00, (byte) 0x31, (byte) 0x45, (byte) 0x31};
//
//    // QR Code: Print the symbol data in the symbol storage area
//    // Hex      1D      28      6B      03      00      31      51      m
//    // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=144
//    byte[] printQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, (byte) 0x03, (byte) 0x00, (byte) 0x31, (byte) 0x51, (byte) 0x30};
//
//    protected CashRegisterReport cashRegisterReport;
//    protected CashRegister cashRegister;
//    protected UsbPrinter usbPrinter;
//
//    protected ImprimirAvisoListener imprimirAvisoListener;
//
//    public ImpresionExternas() {
//    }
//
//    public void setImpresora(Impresora impresora) {
//        this.impresora = impresora;
//    }
//
//    public void setVenta(Sell venta) {
//        this.venta = venta;
//    }
//
//    public void setSucursal(Location sucursal) {
//        this.sucursal = sucursal;
//    }
//
//    public void setUsuario(User usuario) {
//        this.usuario = usuario;
//    }
//
//    public void setTipoImpresion(int tipoImpresion) {
//        this.tipoImpresion = tipoImpresion;
//    }
//
//    public void setFechaPedido(String fechaPedido) {
//        this.fechaPedido = fechaPedido;
//    }
//
//    public void setMoneda(String moneda) {
//        this.moneda = moneda;
//    }
//
//    // agrego el omar pago
//    public void setPago(Payment pago) {
//        this.pago = pago;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    public void setEmpresa(Business empresa) {
//        this.empresa = empresa;
//    }
//    // agrego el omar pago
//
//    public void setOutputStream(OutputStream outputStream) {
//        this.outputStream = outputStream;
//    }
//
//    public void setCashRegisterReport(CashRegisterReport cashRegisterReport) {
//        this.cashRegisterReport = cashRegisterReport;
//    }
//
//    public void setCashRegister(CashRegister cashRegister) {
//        this.cashRegister = cashRegister;
//    }
//
//    public void setUsbPrinter(UsbDeviceConnection connection, UsbInterface intrface, UsbEndpoint mEndPoint, Boolean forceClaim) {
//        LogUtils.i(TAG, "setUsbPrinter conection " + connection + " interfa " + intrface + "endpoint " + mEndPoint + " force " + forceClaim);
//        this.usbPrinter = new UsbPrinter(connection, intrface, mEndPoint, forceClaim);
//    }
//
//    public void setImprimirAvisoListener(ImprimirAvisoListener imprimirAvisoListener) {
//        this.imprimirAvisoListener = imprimirAvisoListener;
//    }
//
//    @Override
//    public void run() {
//        LogUtils.i(TAG, "run normal");
//        String linea;
//
//        linea = PrefUtils.repeate(impresora.getCantidadCaracteresLinea(), "-");
//
//        String fecha1 = "";
//
//        if (venta != null) {
//            fecha1 = venta.getTransactionDate().split(" ")[0];
////            fecha1 = fecha1.replace('-', '/');
//        }
//        String logoS3bmp = null;
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            if (empresa != null) {
//                if (empresa.getLogoBmpUrl() != null && empresa.getLogoBmpUrl().length() > 0) {
//                    String databmp[] = empresa.getLogoBmpUrl().split("/");
//                    logoS3bmp = databmp[databmp.length - 1];
//                }
//            }
//        }
////        LogUtils.i(TAG, "LOG " + empresa);
//        LogUtils.i(TAG, "LOG " + logoS3bmp);
//        LogUtils.i(TAG, "LOG " + tipoImpresion);
//
//        if (tipoImpresion != -15) {
//            if (tipoImpresion != 5) {
//                if (tipoImpresion != 6) {
//                    if (impresora.getTipoDocumentos().get(tipoImpresion - 1) == 0) {
//                        if (imprimirAvisoListener != null){
//                            imprimirAvisoListener.terminoDeImprimir();
//                        }
//                        return;
//                    }
//                }
//            }
//        }
//
//        try {
//
//            Printer printer = null;
//            switch (impresora.getTipoImpresora()){
//                case PrefUtils.PRINTER_WIFI_LAN:
//                    LogUtils.i(TAG, "PRINTER ES WIFI");
//                    printer = new NetworkPrinter(impresora.getIp(), impresora.getPuerto());
//                    break;
//                case PrefUtils.PRINTER_BLUETOOTH:
//                    LogUtils.i(TAG, "PRINTER ES BLUETOOTH");
//                    printer = new BluetoothPrinter(outputStream);
//                    break;
//                case PrefUtils.PRINTER_USB:
//                    LogUtils.i(TAG, "PRINTER ES USB");
//                    printer = usbPrinter;
//                    break;
//            }
//
//            if (printer == null){
//                LogUtils.i(TAG, "PRINTER ES IGUAL A NULL");
//                if (imprimirAvisoListener != null){
//                    imprimirAvisoListener.terminoDeImprimir();
//                }
//                return;
//            }
//            PrinterService printerService = new PrinterService(printer);
//
//            if (impresora.getCodePage() != null){
//                printerService.setCharsetText(impresora.getCodePage());
//            }
//
//            printerService.init();
//
//            if (impresora.getNoImprimirTextoAbrirCajero() != null && impresora.getNoImprimirTextoAbrirCajero()){
//                printerService.openCashDrawerPin2();
//                printerService.openCashDrawerPin5();
//                if (impresora.getBeepPrinter())
//                    printerService.beepCorto();
//
//                printerService.close();
//
//                if (imprimirAvisoListener != null){
//                    imprimirAvisoListener.terminoDeImprimir();
//                }
//                return;
//            }
//
//            DecimalFormat decimalFormatTotales = ConfigEmizor.obtenerDecimalFormat();
//            switch (tipoImpresion) {
//                case 1:// impresion de pedido
//                    try {
//                        printerService.setTextAlignCenter();
//                        // probando logo
//                        if (logoS3bmp != null) {
//                            File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + logoS3bmp);
////                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
//
//                            if (imgFile.exists()) {
//
//                                printerService.printImage(imgFile.getAbsolutePath());
//                                // ESPERAMOS 200 MILISEGUNDOS POR LOS DATOS ENVIADOS DE LA IMAGEN
////                                sleep(DELAY_PRINTING_IMAGE);
//                            } else {
//                                LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
//                            }
//                        }
//
////                        // probando logo
////                        printerService.setTextAlignCenter();
////                        // probando logo
////                        File imgFile2 = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
////
////                        if (imgFile2.exists()) {
////
////                            printerService.printImage(imgFile2.getAbsolutePath());
////
////                        } else {
////                            LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
////                        }
////                        // probando logo
//
//                        if (venta.getAdditionalNotes() != null && venta.getAdditionalNotes().length() > 0) {
////                            printerService.setTextSize2H();
////                            printerService.setTextSize2W();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(venta.getAdditionalNotes());
//                        } else {
//                            printerService.setTextAlignCenter();
////                            printerService.setTextSize2H();
////                            printerService.setTextSize2W();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.orden_mayus));
//                        }
//
//                        printerService.setTextSizeNormal();
//                        //printerService.printLn(usuario.getBusiness().getName());
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.printLn(linea);
//
//                        printerService.setTextAlignLeft();
//                        printerService.printLn(context.getString(R.string.no_orden) + venta.getInvoiceNo());
//                        printerService.printLn(context.getString(R.string.impr_fecha) + fecha1 + context.getString(R.string.impr_hora) + venta.getTransactionDate().split(" ")[1]);
//
//                        if (venta.getContact().getBusinessName() != null && venta.getContact().getBusinessName().length() > 0) {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getBusinessName());
//                        } else {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getName());
//                        }
//
//                        printerService.printLn(linea);
//                        DecimalFormat decimalFormat1 = ConfigEmizor.obtenerDecimalFormatPos();
////                        printerService.printLn(context.getString(R.string.cant_concepto));
////                        if (impresora.getTipoPapel() == PrefUtils.PRINTER_55_MM) {
////                            printerService.printLn(context.getString(R.string.concep_precio_sub_total55));
////                        }else{
////                            printerService.printLn(context.getString(R.string.concep_precio_sub_total));
////                        }
//
//                        printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), context.getString(R.string.impr_txt_concepto), context.getString(R.string.impr_txt_cant), context.getString(R.string.impr_txt_precio), context.getString(R.string.impr_txt_subtotal)));
//
//                        printerService.printLn(linea);
//
////                        BigDecimal totalDescuento = new BigDecimal(0);
////                        BigDecimal bigDecimalTotal = new BigDecimal(0);
////                        sleep(DELAY_PRINTING_NORMAL);
//
//                        String fechaActual = null;
//                        double cantidad = 0;
//                        int contadorLineas = 0;
//                        for (SellLine sellLine : venta.getSellLines()) {
//                            String fechaGrupo = "";
//
//                            if (venta.getMidata() != null) {
//                                if (!venta.getMidata().equals(sellLine.getCreatedAt())) {
//                                    continue;
//                                }
//                            } else {
//
//                                if (fechaActual == null) {
//
//                                    fechaActual = sellLine.getCreatedAt();
//                                    fechaGrupo = "";
//
//                                } else if (!fechaActual.equals(sellLine.getCreatedAt())) {
//
//                                    fechaActual = sellLine.getCreatedAt();
//                                    fechaGrupo = fechaActual;
//
//                                }
//
//                            }
//
//                            BigDecimal bigDecimal = new BigDecimal(sellLine.getQuantity()).multiply(new BigDecimal(sellLine.getUnitPriceIncTax()));
//
////                            BigDecimal descPordAux = new BigDecimal(sellLine.getUnitPriceBeforeDiscount());
////
////                            descPordAux = descPordAux.subtract(new BigDecimal(sellLine.getUnitPrice()));
////
////                            descPordAux = descPordAux.multiply(new BigDecimal(sellLine.getQuantity()));
//
////                            totalDescuento = totalDescuento.add(descPordAux);
//
////                            bigDecimalTotal = bigDecimalTotal.add(bigDecimal);
//
//                            cantidad = cantidad + sellLine.getQuantity() * 1.0f;
//                            String nota = "";
//                            if (sellLine.getSellLineNote() != null && sellLine.getSellLineNote().length() > 0) {
//                                nota = "  * " + sellLine.getSellLineNote();
//                            }
//
//                            if (fechaGrupo != null && fechaGrupo.length() > 0)
//                                printerService.printLn(fechaGrupo);
//                            String nombreProducto = "";
//
//                            if (sellLine.getProduct().getType().equals("single")) {
//                                nombreProducto = sellLine.getProduct().getName();
//                            } else {
//                                nombreProducto = sellLine.getProduct().getName() + " - " + sellLine.getVariations().getName();
//                            }
//
//                            if (sellLine.getName() != null){
//                                nombreProducto = sellLine.getName();
//                            }
////                            printerService.printLn((decimalFormat1.format(sellLine.getQuantity()) + "   " + nombreProducto));
////                            printerService.printLn(nombreProducto);
//                            List<String> prodLine = formatearLinea(impresora.getCantidadCaracteresLinea(),nombreProducto);
//                            for (String texto : prodLine) {
//                                printerService.printLn(texto);
//                            }
//                            if (nota.length() > 0) {
////                                printerService.printLn((nota));
//                                List<String> notaProdLine = formatearLinea(impresora.getCantidadCaracteresLinea(),nota);
//                                for (String texto : notaProdLine) {
//                                    printerService.printLn(texto);
//                                }
//                            }
//
//                            printerService.setTextAlignRight();
////                            if (impresora.getTipoPapel() == PrefUtils.PRINTER_55_MM){
////                                printerService.printLn(decimalFormat1.format(sellLine.getQuantity()) + "   " + sellLine.getUnitPriceBeforeDiscount() + "    " + bigDecimal.toString());
////                            }else {
////                                printerService.printLn(decimalFormat1.format(sellLine.getQuantity()) + "    yerre    " + sellLine.getUnitPriceBeforeDiscount() + "      " + bigDecimal.toString());
////                            }
//
//                            printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), "", "", ConfigEmizor.obtenerDecimalFormatPos().format(sellLine.getQuantity()) + "x" + sellLine.getUnitPriceIncTax(), ConfigEmizor.obtenerDecimalFormatPos().format(bigDecimal.doubleValue())));
//                            printerService.setTextAlignLeft();
//                            // contador para las lienas que se envian si es 10 se procedera a para el hilo durante 200 milisegundos
//                            contadorLineas++;
//                            if (contadorLineas == CANT_LINES_FOR_DELAY){
//                                contadorLineas = 0;
//                                sleep(DELAY_PRINTING_NORMAL);
//                            }
//                        }
//
//                        printerService.printLn(linea);
//
//
//                        printerService.printLn(context.getString(R.string.cantidad) + decimalFormat1.format(cantidad));
//
//                        try {
////                            totalDescuento = new BigDecimal(0);
//
////                            if (venta.getTotalBeforeTax() != null){
////                                if (venta.getTotalBeforeTax().length() > 0)
////                                    totalDescuento = new BigDecimal(venta.getTotalBeforeTax()).subtract(new BigDecimal(venta.getFinalTotal()));
////                            }
//                            if (venta == null || venta.getDiscountTotal().equals("0")) {
//                                printerService.setTextAlignRight();
////                                printerService.setTextSize2W();
////                                printerService.setTextSize2H();
//                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(context.getString(R.string.total) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getFinalTotal())));
//                            } else {
//
////                                BigDecimal bigDecimalAmountSubtotal = new BigDecimal(bigDecimalTotal.doubleValue()).add(totalDescuento);
//
//                                printerService.setTextAlignRight();
//                                printerService.printLn(context.getString(R.string.subtotal) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getTotalBeforeTax())));
//
//
//                                printerService.printLn(context.getString(R.string.descuento) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getDiscountTotal())));
//
//                                printerService.setTextSize2W();
//                                printerService.setTextSize2H();
//                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(context.getString(R.string.total) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getFinalTotal())));
//
//
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//
//                        printerService.setTextAlignLeft();
//                        printerService.setTextSizeNormal();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.printLn(linea);
//                        List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                        for (String texto : userLine) {
//                            printerService.printLn(texto);
//                        }
//                        printerService.setTextAlignCenter();
//                        printerService.printLn(context.getString(R.string.gracias_preferencia));
//                        for (int h=0; h<impresora.getLinesBelow(); h++){
//                            printerService.printLn(" ");
//                        }
//
//                        // paramos DELAY_PRINTING_NORMAL milisegundos
////                        sleep(DELAY_PRINTING_NORMAL);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    break;
//                case 2:// impresion de nota de entrega
//                    try{
//                        LogUtils.i(TAG, "PRINTER ES NOTA");
//                        printerService.setTextAlignCenter();
//                        // probando logo
//                        if (logoS3bmp != null) {
//                            File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + logoS3bmp);
////                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
//
//                            if (imgFile.exists()) {
//
//                                printerService.printImage(imgFile.getAbsolutePath());
//                                // esperamos 200 milisegundos a que se procese la imagen
////                                sleep(DELAY_PRINTING_IMAGE);
//                            } else {
//                                LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
//                            }
//                        }
//
//
//
//                        printerService.setTextAlignCenter();
//                        printerService.setTextSize2H();
//                        printerService.setTextSize2W();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        List<String> empresaLine = formatearLineaG1(impresora.getCantidadCaracteresLinea(),empresa.getName(), impresora.getTipoFuente());
//                        for (String texto : empresaLine) {
//                            printerService.printLn(texto);
//                        }
//                        printerService.setTextSizeNormal();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        if (sucursal.getAddress() != null) {
//                            printerService.setTextAlignLeft();
//                            printerService.printLn(sucursal.getAddress());
//                            printerService.setTextAlignCenter();
//                        }
//
//                        if (sucursal.getAlternateNumber() != null) {
//                            printerService.setTextAlignLeft();
//                            printerService.printLn(context.getString(R.string.txt_phone) + " " + sucursal.getAlternateNumber());
//                            printerService.setTextAlignCenter();
//                        }
//
//                        if (sucursal.getMobile() != null) {
//                            printerService.setTextAlignLeft();
//                            printerService.printLn(context.getString(R.string.txt_phone) + " " + sucursal.getMobile());
//                            printerService.setTextAlignCenter();
//                        }
//
//                        printerService.setTextAlignCenter();
//                        printerService.setTextSizeNormal();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.printLn(context.getString(R.string.nota_entrega));
//                        printerService.printLn(linea);
//
//                        printerService.setTextAlignLeft();
//                        printerService.printLn(context.getString(R.string.nro_nota_entrega) + venta.getInvoiceNo());
//                        printerService.printLn(context.getString(R.string.impr_fecha) + venta.getTransactionDate());
//
//                        Contact contact = venta.getContact();
//
//                        if (contact.getContactId() != null && contact.getContactId().length() > 0) {
//                            printerService.printLn(context.getString(R.string.impr_cod_client) + contact.getContactId());
//                        }
//
//                        if (contact.getTaxNumber() != null && contact.getTaxNumber().length() > 0 && empresa.getTaxLabel() != null && empresa.getTaxLabel().length() > 0) {
//                            printerService.printLn(empresa.getTaxLabel() + ": " + contact.getTaxNumber());
//                        }
//
//                        if (venta.getContact().getBusinessName() != null && venta.getContact().getBusinessName().length() > 0) {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getBusinessName());
//                        } else {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getName());
//                        }
//
//                        printerService.printLn(linea);
//                        DecimalFormat decimalFormat1 = ConfigEmizor.obtenerDecimalFormatPos();
//
//                        printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), context.getString(R.string.impr_txt_concepto), context.getString(R.string.impr_txt_cant), context.getString(R.string.impr_txt_precio), context.getString(R.string.impr_txt_subtotal)));
//
//                        printerService.printLn(linea);
//                        // paramos DELAY_PRINTING_NORMAL milisegundos por las anteriores lineas enviadas a ser imprimidas
////                        sleep(DELAY_PRINTING_NORMAL);
//                        int contadorLineas = 0;
////                        BigDecimal totalDescuento = new BigDecimal(0);
//                        for (SellLine sellLine : venta.getSellLines()) {
//                            BigDecimal bigDecimal = new BigDecimal(sellLine.getQuantity()).multiply(new BigDecimal(sellLine.getUnitPriceIncTax()));
//
////                            BigDecimal descPordAux = new BigDecimal(sellLine.getUnitPriceBeforeDiscount());
////
////                            descPordAux = descPordAux.subtract(new BigDecimal(sellLine.getUnitPrice()));
////
////                            descPordAux = descPordAux.multiply(new BigDecimal(sellLine.getQuantity()));
////
////                            totalDescuento = totalDescuento.add(descPordAux);
//
//                            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_EVEN);
//
//                            String nombreProducto = "";
//
//                            if (sellLine.getProduct().getType().equals("single")) {
//                                nombreProducto = sellLine.getProduct().getName();
//                            } else {
//                                nombreProducto = sellLine.getProduct().getName() + " - " + sellLine.getVariations().getName();
//                            }
//
//                            if (sellLine.getName() != null){
//                                nombreProducto = sellLine.getName();
//                            }
//
//                            printerService.printLn(nombreProducto);
//
//                            printerService.setTextAlignRight();
////                            if (impresora.getTipoPapel() == PrefUtils.PRINTER_55_MM){
////                                printerService.printLn(decimalFormat1.format(sellLine.getQuantity()) + "   " + sellLine.getUnitPriceBeforeDiscount() + "    " + bigDecimal.toString());
////                            }else {
////                                printerService.printLn(decimalFormat1.format(sellLine.getQuantity()) + "        " + sellLine.getUnitPriceBeforeDiscount() + "      " + bigDecimal.toString());
////                            }
//                            printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), "", "", ConfigEmizor.obtenerDecimalFormatPos().format(sellLine.getQuantity()) + "x" + sellLine.getUnitPriceIncTax(), ConfigEmizor.obtenerDecimalFormatPos().format(bigDecimal.doubleValue())));
//                            printerService.setTextAlignLeft();
//
//                            // si el contador esta en 10 lineas enviadas se procedera a dar un descanso de 200ml
//                            contadorLineas++;
//                            if (contadorLineas == CANT_LINES_FOR_DELAY){
//                                contadorLineas = 0;
//                                sleep(DELAY_PRINTING_NORMAL);
//                            }
//
//                        }
//
//                        printerService.printLn(linea);
//
////                        totalDescuento = new BigDecimal(0);
////
////                        if (venta.getTotalBeforeTax() != null){
////                            if (venta.getTotalBeforeTax().length() > 0)
////                                totalDescuento = new BigDecimal(venta.getTotalBeforeTax()).subtract(new BigDecimal(venta.getFinalTotal()));
////                        }
//
//                        if (venta.getDiscountTotal() == null || venta.getDiscountTotal().equals("0")) {
//                            printerService.setTextAlignRight();
////                            printerService.setTextSize2W();
////                            printerService.setTextSize2H();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.total) + moneda + " " + venta.getFinalTotal());
//
//                        } else {
////                            BigDecimal bigDecimalAmount = new BigDecimal(venta.getFinalTotal());
////                            BigDecimal bigDecimalAmountSubtotal = new BigDecimal(bigDecimalAmount.doubleValue()).add(totalDescuento);
//                            printerService.setTextAlignRight();
//                            printerService.printLn(context.getString(R.string.subtotal) + moneda + " " + decimalFormat1.format(Double.valueOf(venta.getTotalBeforeTax())));
//
//                            printerService.printLn(context.getString(R.string.descuento) + moneda + " " + decimalFormat1.format(Double.valueOf(venta.getDiscountTotal())));
//                            printerService.setTextAlignRight();
//                            printerService.setTextSize2W();
//                            printerService.setTextSize2H();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.total) + moneda + " " + decimalFormat1.format(Double.valueOf(venta.getFinalTotal())));
//
//                        }
//                        printerService.setTextSizeNormal();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.setTextAlignLeft();
//                        printerService.printLn(linea);
//
//                        List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                        for (String texto : userLine) {
//                            printerService.printLn(texto);
//                        }
//                        printerService.setTextAlignCenter();
//                        printerService.printLn(context.getString(R.string.gracias_preferencia));
//                        for (int h=0; h<impresora.getLinesBelow(); h++){
//                            printerService.printLn(" ");
//                        }
//
//                        boolean estado = procesarListaPagosCard(venta.getPaymentLines(), venta, printerService, linea, "card");
//
//                        if (! estado){
//                            Log.i("FATVE", "pedestado" + estado);
//                        }
//
////                        sleep(DELAY_PRINTING_NORMAL);
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                    break;
//                case 3:// impresion de factura
//                    try {
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        if (venta.getDosage() != null) {
//
//                            // POR TERCEROS
//                            if (venta.getDosage().getTypeThird() == 1) {
//                                printerService.setTextAlignLeft();
//
//                                List<String> thirdLine = formatearLinea(impresora.getCantidadCaracteresLinea(),venta.getDosage().getTypeThirdName());
//                                for (String texto : thirdLine) {
//                                    printerService.printLn(texto);
//                                }
//                                printerService.printLn(linea);
//                            }
//                            printerService.setTextAlignCenter();
//                            if (venta.getDosage().getPrintLogo() == 1 && empresa.getLogo() != null) {
//                                if (logoS3bmp != null) {
//                                    File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + logoS3bmp);
////                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
//
//                                    if (imgFile.exists()) {
//
//                                        printerService.printImage(imgFile.getAbsolutePath());
//
//                                        // esperamos 200 milisegundos por la informacion de la imagen enviada a la impresora
////                                        sleep(DELAY_PRINTING_IMAGE);
//                                    } else {
//                                        LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
//                                    }
//                                }
//                            }
//
//                            if (venta.getDosage().getPrintBusinessName() == 1) {
//                                printerService.setTextSize2H();
//                                printerService.setTextSize2W();
//
//                                List<String> businessLine = formatearLineaG1(impresora.getCantidadCaracteresLinea(),venta.getDosage().getBusinessName(), impresora.getTipoFuente());
//                                for (String texto : businessLine) {
//                                    printerService.printLn(texto);
//                                }
//                                printerService.setTextSizeNormal();
//
//                            }
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            // UNIPERSONAL
//                            if (venta.getDosage().getIsUniper() == 1) {
//
//                                List<String> uniperLine = formatearLinea(impresora.getCantidadCaracteresLinea(),"De: " + venta.getDosage().getUnipersonal());
//                                for (String texto : uniperLine) {
//                                    printerService.printLn(texto);
//                                }
//                            }
//                            //UNIPERSONAL
//
//
//                            Dosage dosage = venta.getDosage();
//                            printerService.printLn(context.getString(R.string.casa_matriz));
//
//                            List<String> addressMatLine = formatearLinea(impresora.getCantidadCaracteresLinea(),dosage.getMainBranchAddress());
//                            for (String texto : addressMatLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            if (dosage.getMainBranchPhone() != null && dosage.getMainBranchPhone().length() > 0)
//                                printerService.printLn(context.getString(R.string.telefono_del_cliente) + " " + dosage.getMainBranchPhone());
//
//                            printerService.printLn(dosage.getMainBranchCity() + "-" + dosage.getMainBranchCountry());
//
//                            if (dosage.getBranchNumber() != null && dosage.getBranchNumber().length() > 0) {
//                                printerService.printLn(context.getString(R.string.sucursal) + dosage.getBranchNumber());
//
//                                List<String> addressSucLine = formatearLinea(impresora.getCantidadCaracteresLinea(),dosage.getBranchAddress());
//                                for (String texto : addressSucLine) {
//                                    printerService.printLn(texto);
//                                }
//                                if (dosage.getBranchPhone() != null && dosage.getBranchPhone().length() > 0) {
//                                    printerService.printLn(context.getString(R.string.telefono_del_cliente) + " " + dosage.getBranchPhone());
//                                }
//                                printerService.printLn(dosage.getBranchCity() + "-" + dosage.getBranchCountry());
//
//                            }
//
//                            if (venta.getDosage() != null && venta.getDosage().getTypeThird() == 1) {
//                                printerService.printLn(context.getString(R.string.facturas_terceros));
//                            } else {
//                                printerService.printLn(context.getString(R.string.texto_de_mesaje_factura));
//                            }
//
//                            printerService.printLn(linea);
//
//                            printerService.setTextAlignLeft();
//
//                            printerService.printLn(context.getString(R.string.nit_espacio) + venta.getDosage().getBusinessNit());
//                            printerService.printLn(context.getString(R.string.factura_espacio) + venta.getDosage().getInvoiceNumber());
//                            printerService.printLn(context.getString(R.string.autorizacion) + venta.getDosage().getNumberAuthorization());
//                            printerService.printLn(linea);
//                            // actividad economica
//                            List<String> actividadLine = formatearLinea(impresora.getCantidadCaracteresLinea(),dosage.getEconomicActivity());
//                            for (String texto : actividadLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            String [] fechahora = venta.getDosage().getInvoiceDate().split(" ");
//
//                            if (fechahora.length == 2) {
//
//                                printerService.printLn(context.getString(R.string.impr_fecha) + fechahora[0] + context.getString(R.string.impr_hora) + fechahora[1]);
//                            }else{
//                                printerService.printLn(context.getString(R.string.impr_fecha) + venta.getDosage().getInvoiceDate());
//                            }
//
//                            printerService.printLn(context.getString(R.string.nit_ci) + venta.getDosage().getContactNit());
//
//                            List<String> clienteLine = formatearLinea(impresora.getCantidadCaracteresLinea(),(context.getString(R.string.razon_social_mayuscula) + venta.getDosage().getContactBusinessName()));
//                            for (String texto : clienteLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            printerService.printLn(linea);
//
//                            DecimalFormat decimalFormat1 = ConfigEmizor.obtenerDecimalFormatPos();
//                            printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), context.getString(R.string.impr_txt_concepto), context.getString(R.string.impr_txt_cant), context.getString(R.string.impr_txt_precio), context.getString(R.string.impr_txt_subtotal)));
//
//                            printerService.printLn(linea);
//
//                            // esperamos 200 ms para que se envien la info
////                            sleep(DELAY_PRINTING_NORMAL);
//
//                            int contadorLineas = 0;
//                            for (SellLine sellLine : venta.getSellLines()) {
//                                BigDecimal bigDecimal = new BigDecimal(sellLine.getQuantity()).multiply(new BigDecimal(sellLine.getUnitPriceIncTax()));
//
//                                bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_EVEN);
//
//                                String nombreProducto = "";
//
//                                if (sellLine.getProduct().getType().equals("single")) {
//                                    nombreProducto = sellLine.getProduct().getName();
//                                } else {
//                                    nombreProducto = sellLine.getProduct().getName() + " - " + sellLine.getVariations().getName();
//                                }
//
//                                if (sellLine.getName() != null){
//                                    nombreProducto = sellLine.getName();
//                                }
//
//                                List<String> productoLine = formatearLinea(impresora.getCantidadCaracteresLinea(),nombreProducto);
//                                for (String texto : productoLine) {
//                                    printerService.printLn(texto);
//                                }
//
//                                printerService.setTextAlignRight();
//
//                                printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), "", "", ConfigEmizor.obtenerDecimalFormatPos().format(sellLine.getQuantity()) + "x" + sellLine.getUnitPriceIncTax(), ConfigEmizor.obtenerDecimalFormatPos().format(bigDecimal.doubleValue())));
//                                printerService.setTextAlignLeft();
//
//                                // if hay mas de 10 lineas enviandas a la impresora
//                                contadorLineas++;
//                                if (contadorLineas == CANT_LINES_FOR_DELAY){
//                                    contadorLineas = 0;
//                                    // esperamos 200 ms para que se envien la info
//                                    sleep(DELAY_PRINTING_NORMAL);
//                                }
//
//                            }
//
//                            if ((venta.getNotes() != null) || (venta.getTerms() != null)) {
//
//                                if ((!venta.getNotes().isEmpty()) || (!venta.getTerms().isEmpty())) {
//
//                                    if (!venta.getNotes().isEmpty()) {
//                                        printerService.printLn(context.getString(R.string.nota_mayuscula) + venta.getNotes());
//                                        List<String> noteLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.nota_mayuscula) + venta.getNotes());
//                                        for (String texto : noteLine) {
//                                            printerService.printLn(texto);
//                                        }
//                                    }
//
//                                    if (!venta.getTerms().isEmpty()) {
////                                        printerService.printLn(context.getString(R.string.terminos) + venta.getTerms());
//                                        List<String> termLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.terminos) + venta.getTerms());
//                                        for (String texto : termLine) {
//                                            printerService.printLn(texto);
//                                        }
//                                    }
//
//                                }
//                            }
//                            printerService.printLn(linea);
//
//
//                            if (venta.getDiscountTotal() == null || venta.getDiscountTotal().equals("0")) {
//                                printerService.setTextAlignRight();
//                                printerService.setTextSize2W();
//                                printerService.setTextSize2H();
//                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.print(context.getString(R.string.total));
//                                printerService.printLn(moneda + " " + venta.getFinalTotal());
//                            } else {
//                                BigDecimal bigDecimalAmount = new BigDecimal(venta.getFinalTotal());
//                                printerService.setTextAlignRight();
////                                printerService.setTextAlignLeft();
//                                printerService.print(context.getString(R.string.subtotal));
////                                printerService.setTextAlignRight();
//                                printerService.printLn(moneda + " " + decimalFormat1.format(Double.valueOf(venta.getTotalBeforeTax())));
//
////                                printerService.setTextAlignLeft();
//                                printerService.print(context.getString(R.string.descuento));
////                                printerService.setTextAlignRight();
//                                printerService.printLn(moneda + " " + decimalFormat1.format(Double.valueOf(venta.getDiscountTotal())));
//                                printerService.setTextAlignRight();
//                                printerService.setTextSize2W();
//                                printerService.setTextSize2H();
//                                printerService.setTextFont(impresora.getTipoFuente());
////                                printerService.setTextAlignLeft();
//                                printerService.print(context.getString(R.string.total) );
//                                printerService.printLn(moneda + " " + decimalFormat1.format(Double.valueOf(venta.getFinalTotal())));
//
//                            }
//                            printerService.setTextSizeNormal();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.setTextAlignLeft();
//                            List<String> literalLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.son) + dosage.getLiteralTotal() + context.getString(R.string.modulo) + " " +dosage.getCurrencyName());
//                            for (String texto : literalLine) {
//                                printerService.printLn(texto);
//                            }
////                            printerService.printLn(context.getString(R.string.son) + dosage.getLiteralTotal() + context.getString(R.string.modulo) + dosage.getCurrencyName());
//
//                            printerService.printLn(linea);
//                            List<String> codeControlLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.codigo_control) + dosage.getControlCode());
//                            for (String texto : codeControlLine) {
//                                printerService.printLn(texto);
//                            }
////                            printerService.printLn(new String((context.getString(R.string.codigo_control) + dosage.getControlCode()).getBytes(Charset.forName("UTF-8")), "ISO-8859-3"));
//                            List<String> fechaLimiteLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.fecha_limit_emision) + dosage.getDeadline());
//                            for (String texto : fechaLimiteLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.usuario) + usuario.getFirstName() + " " + usuario.getLastName());
//                            for (String texto : userLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            // esperamos DELAY_PRINTING_NORMAL ms para que se envien la info
////                            sleep(DELAY_PRINTING_NORMAL);
//
//                            printerService.setTextAlignCenter();
//                            //qr
//                            if (impresora.getModelPrinter().equals("SUNMI(MINI)")) {
//                                try {
//                                    String rutaArchBmp = context.getFilesDir().getAbsolutePath() + "codigoQr.bmp";
//                                    AndroidBmpUtil.save(QRCodeHelper.newInstance(context).setContent(dosage.getQr()).setErrorCorrectionLevel(ErrorCorrectionLevel.Q).setTamanio(384, 200).getQRCOde(), rutaArchBmp);
////                                File imgFileQr = new File(rutaArchBmp);
////                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
//
////                                if (imgFileQr.exists()) {
//
//                                    printerService.printImage(rutaArchBmp);
////                                    sleep(300);
////                                    // esperamos 200 milisegundos por la informacion de la imagen enviada a la impresora
//////                                        sleep(DELAY_PRINTING_IMAGE);
////                                } else {
////                                    LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
////                                }
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
//                            } else {
//                                int store_len = dosage.getQr().length() + 3;
//                                byte store_pL = (byte) (store_len % 256);
//                                byte store_pH = (byte) (store_len / 256);
//
//                                // QR Code: Store the data in the symbol storage area
//                                // Hex      1D      28      6B      pL      pH      31      50      30      d1...dk
//                                // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=143
//                                //                        1D          28          6B         pL          pH  cn(49->x31) fn(80->x50) m(48->x30) d1…dk
//                                byte[] storeQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, store_pL, store_pH, (byte) 0x31, (byte) 0x50, (byte) 0x30};
//
//                                // write() simply appends the data to the buffer
//                                printer.write(modelQR);
//
//                                printer.write(sizeQR);
//                                printer.write(errorQR);
//                                printer.write(storeQR);
//                                printer.write(dosage.getQr().getBytes());
//                                printer.write(printQR);
//                            }
//                            printerService.setTextAlignLeft();
//
//                            //qr
//
//
//
//                            printerService.printLn(linea);
//                            printerService.setTextAlignCenter();
////                            printerService.printLn(dosage.getLaw());
//                            List<String> lawLine = formatearLinea(impresora.getCantidadCaracteresLinea(),dosage.getLaw());
//                            for (String texto : lawLine) {
//                                printerService.printLn(texto);
//                            }
//
////                            printerService.printLn(context.getString(R.string.datos_finales));
//
//                            List<String> redaLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.datos_finales));
//                            for (String texto : redaLine) {
//                                printerService.printLn(texto);
//                            }
//
//                            printerService.printLn(context.getString(R.string.gracias_preferencia));
//                            for (int h=0; h<impresora.getLinesBelow(); h++){
//                                printerService.printLn(" ");
//                            }
//
//                            boolean estado = procesarListaPagosCard(venta.getPaymentLines(), venta, printerService, linea, "card");
//
//                            if (! estado){
//                                Log.i("FATVE", "estado" + estado);
//                            }
//
//                            // esperamos 200 ms para que se envien la info
////                            sleep(DELAY_PRINTING_NORMAL);
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                    break;
//                case 4:// impresion de pago
//                    try {
//
//                        if (pago != null && pago.getRevocatePaymentId() != null && pago.getRevocatePaymentId() > 0) {
//                            if (imprimirAvisoListener != null){
//                                imprimirAvisoListener.terminoDeImprimir();
//                            }
//                            return;
//                        }
//
//                        // metodo de pago de una impresora externa OMAR
//
//                        DecimalFormat decimalFormat = ConfigEmizor.obtenerDecimalFormatPos();
//
//                        // List<String> nombreEmpresa = formatearLineaCadena2(empresa.getName());
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.printLn(empresa.getName());
//
//                        if (pago.getIsReturn() != null && pago.getIsReturn() == 1) {
//                            printerService.setTextAlignCenter();
//                            printerService.printLn(context.getString(R.string.pago_anulado));
//                            printerService.setTextAlignLeft();
//                        }
//
//                        printerService.printLn(linea);
//                        String fecha11 = pago.getPaidOn().split(" ")[0];
////                        fecha11 = fecha11.replace('-', '/');
//
//
//                        printerService.printLn(context.getString(R.string.numero_pago) + pago.getId());
//
//                        printerService.printLn(context.getString(R.string.numero_referencia) + pago.getPaymentRefNo());
//                        switch (pago.getMethod()) {
//                            case "cash":
//                                printerService.printLn(context.getString(R.string.metodo_efectivo));
//                                break;
//                            case "card":
//                                printerService.printLn(context.getString(R.string.metodo_tarjeta));
//                                break;
//                            case "cheque":
//                                printerService.printLn(context.getString(R.string.metodo_cheque));
//                                break;
//                            case "bank_transfer":
//                                printerService.printLn(context.getString(R.string.metodo_transferencia_bancaria));
//                                break;
//                            default:
//                                printerService.printLn(context.getString(R.string.metodo_otro));
//                                break;
//                        }
//                        printerService.printLn(context.getString(R.string.impr_fecha) + fecha11 + context.getString(R.string.impr_hora) + pago.getPaidOn().split(" ")[1]);
//
//                        //List<String> listCliente = formatearLineaCadena();
//                        if (venta.getContact().getBusinessName() != null && venta.getContact().getBusinessName().length() > 0) {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getBusinessName());
//                        } else {
//                            printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getName());
//                        }
//
////                        printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getName());
//                        if (pago.getNote() != null && pago.getNote().length() > 0) {
//
//                            printerService.printLn(context.getString(R.string.texto_de_la_nota) + pago.getNote());
//                        }
//
//                        printerService.printLn(context.getString(R.string.monto_pago) + moneda + " " + decimalFormat.format(pago.getAmount()));
//
//                        if (pago.getExtras() != null) {
//                            if (pago.getExtras().getPagoTarjetaMpos() != null) {
//
//                                printerService.printLn(context.getString(R.string.id) + pago.getExtras().getPagoTarjetaMpos().getId());
//                                printerService.printLn(context.getString(R.string.titular_tarjeta) + pago.getExtras().getPagoTarjetaMpos().getCardHolderName());
//                                printerService.printLn(context.getString(R.string.tarjeta) + pago.getExtras().getPagoTarjetaMpos().getPan());
//                                printerService.printLn(context.getString(R.string.codigo_autorizacion) + pago.getExtras().getPagoTarjetaMpos().getAuthorizationCode());
//                                printerService.printLn(context.getString(R.string.tipo_de_pago) + pago.getExtras().getPagoTarjetaMpos().getPaymentType());
//                                printerService.printLn(context.getString(R.string.total) + pago.getExtras().getPagoTarjetaMpos().getTotal());
//                                printerService.printLn(context.getString(R.string.monto_impuesto) + pago.getExtras().getPagoTarjetaMpos().getTaxAmount());
//                                printerService.printLn(context.getString(R.string.monto_propina) + pago.getExtras().getPagoTarjetaMpos().getTipAmount());
//                            }
//                        }
//                        // esperamos DELAY_PRINTING_NORMAL ms para que se envien la info
////                        sleep(DELAY_PRINTING_NORMAL);
//
//                        printerService.printLn(linea);
//
//                        // detalle"
//
//                        printerService.setTextAlignCenter();
//                        printerService.printLn(context.getString(R.string.detalle_mayuscula));
//                        printerService.setTextAlignLeft();
//
//                        if (venta.getIsSuspend() != null && venta.getIsSuspend() == 1) {
//                            printerService.printLn(context.getString(R.string.orden_numero) + venta.getInvoiceNo());
//                        } else if (venta.getIsSellNote() != null && venta.getIsSellNote() == 1) {
//                            printerService.printLn(context.getString(R.string.nota_entrega_numero) + venta.getInvoiceNo());
//                        } else if (venta.getIsSellNote() != null && venta.getIsSellNote() == 0) {
//                            printerService.printLn(context.getString(R.string.factura_numero) + venta.getInvoiceNo());
//                        }
//
//                        printerService.printLn(linea);
//                        BigDecimal montoTotal = new BigDecimal(venta.getFinalTotal());
//                        BigDecimal montoDebe = new BigDecimal(venta.getTotalDebt());
//                        BigDecimal montoPagado = montoTotal.subtract(montoDebe);
//                        //montoPagado = montoPagado.setScale(2, RoundingMode.HALF_EVEN);
//
//                        printerService.printLn(context.getString(R.string.a_cuenta) + moneda + " " + decimalFormat.format(montoPagado.doubleValue()));
//                        printerService.printLn(context.getString(R.string.saldo) + moneda + " " + decimalFormat.format(montoDebe.doubleValue()));
//                        printerService.printLn(context.getString(R.string.total_dos_puntos) + moneda + " " + decimalFormat.format(Double.valueOf(venta.getFinalTotal())));
//                        printerService.printLn(linea);
////                        printerService.printLn(context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                        List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                        for (String texto : userLine) {
//                            printerService.printLn(texto);
//                        }
//
//                        for (int h=0; h<impresora.getLinesBelow(); h++){
//                            printerService.printLn(" ");
//                        }
//
//                        //  printerService.printLn("pie de pagina");
//
//                        //metodo de pago de una impresora externa OMAR
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    // esperamos 200 ms para que se envien la info
////                    sleep(DELAY_PRINTING_NORMAL);
//                    break;
//                case 5:// impresion de categorias
//                    try {
//
//                        DecimalFormat decimalFormat1 = ConfigEmizor.obtenerDecimalFormatPos();
//
//                        BigDecimal totalDescuento = new BigDecimal(0);
//                        BigDecimal bigDecimalTotal = new BigDecimal(0);
//
//                        List<String> lineas = new ArrayList<>();
//
//                        String fechaActual = null;
//                        double cantidad = 0;
//                        for (SellLine sellLine : venta.getSellLines()) {
//                            String fechaGrupo = "";
//
//                            if (venta.getMidata() != null) {
//                                if (!venta.getMidata().equals(sellLine.getCreatedAt())) {
//                                    continue;
//                                }
//                            } else {
//
//                                if (fechaActual == null) {
//
//                                    fechaActual = sellLine.getCreatedAt();
//                                    fechaGrupo = "";
//
//                                } else if (!fechaActual.equals(sellLine.getCreatedAt())) {
//
//                                    fechaActual = sellLine.getCreatedAt();
//                                    fechaGrupo = fechaActual;
//
//                                }
//
//                            }
//
//                            boolean estadoAprob = false;
//                            LogUtils.i(TAG, "Sell line imp wifi " + sellLine);
//                            for (int i = 0; i< impresora.getCategorias().size(); i++){
//                                if (sellLine.getProduct().getCategoryId()!= null && sellLine.getProduct().getCategoryId().equals(String.valueOf(impresora.getCategorias().get(i))) || impresora.getCategorias().get(i) == -1){
//                                    estadoAprob = true;
//                                    break;
//                                }
//                            }
//
//                            if (estadoAprob) {
//
//                                BigDecimal bigDecimal = new BigDecimal(sellLine.getQuantity()).multiply(new BigDecimal(sellLine.getUnitPriceIncTax()));
//
//                                bigDecimalTotal = bigDecimalTotal.add(bigDecimal);
//
//                                cantidad = cantidad + sellLine.getQuantity() * 1.0f;
//                                String nota = "";
//                                if (sellLine.getSellLineNote() != null && sellLine.getSellLineNote().length() > 0) {
//                                    nota = "  * " + sellLine.getSellLineNote();
//                                }
//
//                                if (fechaGrupo != null && fechaGrupo.length() > 0)
//                                    lineas.add(fechaGrupo);
//                                String nombreProducto = "";
//
//                                if (sellLine.getProduct().getType().equals("single")) {
//                                    nombreProducto = sellLine.getProduct().getName();
//                                } else {
//                                    nombreProducto = sellLine.getProduct().getName() + " - " + sellLine.getVariations().getName();
//                                }
//
//                                if (sellLine.getName() != null){
//                                    nombreProducto = sellLine.getName();
//                                }
//
//                                lineas.add((decimalFormat1.format(sellLine.getQuantity()) + "   " + nombreProducto));
////                                lineas.add(formatearLineaItem(impresora.getCantidadCaracteresLinea(), "", sellLine.getQuantity() + "", sellLine.getUnitPriceBeforeDiscount(), bigDecimal.toString()));
//                                if (nota.length() > 0)
//                                    lineas.add((nota));
//
//                            }
//
//                        }
//
//                        if (lineas.size() > 0) {
//
////                            printerService.init();
//                            if (venta.getAdditionalNotes() != null && venta.getAdditionalNotes().length() > 0) {
//                                printerService.setTextSize2H();
//                                printerService.setTextSize2W();
//                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(venta.getAdditionalNotes());
//                            } else {
//                                printerService.setTextAlignCenter();
//                                printerService.setTextSize2H();
//                                printerService.setTextSize2W();
//                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(context.getString(R.string.orden_mayus));
//                            }
//
//                            printerService.setTextSizeNormal();
//                            printerService.setTextFont(impresora.getTipoFuente());
//                            //printerService.printLn(usuario.getBusiness().getName());
//                            printerService.printLn(linea);
//
//                            printerService.setTextAlignLeft();
//                            printerService.printLn(context.getString(R.string.no_orden) + venta.getInvoiceNo());
//                            printerService.printLn(context.getString(R.string.impr_fecha) + fecha1 + context.getString(R.string.impr_hora) + venta.getTransactionDate().split(" ")[1]);
//
//                            if (venta.getContact().getBusinessName() != null && venta.getContact().getBusinessName().length() > 0) {
//                                printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getBusinessName());
//                            } else {
//                                printerService.printLn(context.getString(R.string.impr_para) + venta.getContact().getName());
//                            }
//                            printerService.printLn(linea);
////                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.cant_concepto));
////                            printerService.printLn(formatearLineaItem(impresora.getCantidadCaracteresLinea(), "Concepto", "Cant.", "Precio", "Subtotal"));
//                            // esperamos 200 ms para que se envien la info
////                            sleep(DELAY_PRINTING_NORMAL);
//                            printerService.printLn(linea);
//                            int contadorLineas = 0;
//                            for (String textoLinea : lineas){
////                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(textoLinea);
//                                // contador de lienar para la impresion
//                                contadorLineas++;
//                                if (contadorLineas == CANT_LINES_FOR_DELAY){
//                                    contadorLineas = 0;
//                                    // esperamos 200 ms para que se envien la info
//                                    sleep(DELAY_PRINTING_NORMAL);
//                                }
//                            }
////                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(linea);
//
////                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.cantidad) + decimalFormat1.format(cantidad));
//
//                            try {
//
//                                totalDescuento = new BigDecimal(0);
//
//                                if (venta.getMidata() == null){
//                                    if (venta.getDiscountTotal() != null && venta.getDiscountTotal().length() > 0) {
//                                        totalDescuento = new BigDecimal(venta.getDiscountTotal());
//                                    } else {
//                                        bigDecimalTotal = new BigDecimal(venta.getFinalTotal());
//                                    }
//                                }
//                                if (totalDescuento.doubleValue() <= 0) {
//                                    printerService.setTextAlignRight();
//                                    printerService.setTextSize2W();
//                                    printerService.setTextSize2H();
//                                    printerService.printLn(context.getString(R.string.total) + moneda + " " + decimalFormatTotales.format(bigDecimalTotal.doubleValue()));
//                                } else {
//
//                                    printerService.setTextAlignRight();
//                                    printerService.setTextFont(impresora.getTipoFuente());
//                                    printerService.printLn(context.getString(R.string.subtotal) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getTotalBeforeTax())));
//                                    printerService.setTextAlignRight();
//                                    printerService.setTextFont(impresora.getTipoFuente());
//                                    printerService.printLn(context.getString(R.string.descuento) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getDiscountTotal())));
//
//                                    printerService.setTextAlignRight();
//                                    printerService.setTextSize2W();
//                                    printerService.setTextSize2H();
//                                    printerService.setTextFont(impresora.getTipoFuente());
//                                    printerService.printLn(context.getString(R.string.total) + moneda + " " + decimalFormatTotales.format(Double.valueOf(venta.getFinalTotal())));
//
//                                }
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                            printerService.setTextSizeNormal();
//                            printerService.setTextFont(impresora.getTipoFuente());
////                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(linea);
////                            printerService.printLn(context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                            List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                            for (String texto : userLine) {
////                                printerService.setTextFont(impresora.getTipoFuente());
//                                printerService.printLn(texto);
//                            }
//                            printerService.setTextAlignCenter();
////                            printerService.setTextFont(impresora.getTipoFuente());
//                            printerService.printLn(context.getString(R.string.gracias_preferencia));
//                            for (int h=0; h<impresora.getLinesBelow(); h++){
//                                printerService.printLn(" ");
//                            }
//
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    // esperamos 200 ms para que se envien la info
////                    sleep(DELAY_PRINTING_NORMAL);
//                    break;
//                case 6:// impresion de cierre de caja
//                    try {
//
//                        LogUtils.i(TAG, "cashRegisterReport FinalPrint in print " + cashRegisterReport);
//                        LogUtils.i(TAG, "cashRegister FinalPrint in print " + cashRegister);
//
//                        DecimalFormat decimalFormat1 = ConfigEmizor.obtenerDecimalFormatPos();
//
//                        List<String> nombreEmpresa = formatearLineaG1(impresora.getCantidadCaracteresLinea(), empresa.getName(), impresora.getTipoFuente());
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.setTextAlignCenter();
//                        printerService.setTextSize2H();
//                        printerService.setTextSize2W();
//
//                        for (String data : nombreEmpresa) {
//                            printerService.printLn(data);
//                        }
//
//                        printerService.setTextSizeNormal();
//                        printerService.setTextFont(impresora.getTipoFuente());
//                        printerService.setTextAlignCenter();
//                        printerService.printLn(context.getString(R.string.text_cc_cierre_caja));
//                        printerService.setTextAlignLeft();
//
//                        printerService.printLn(linea);
//
//                        printerService.printLn(context.getString(R.string.text_cc_sucursal) + " " + sucursal.getName());
//
//                        printerService.printLn(context.getString(R.string.text_cc_nro_referencia) + " " + cashRegister.getId());
//
//                        printerService.printLn(context.getString(R.string.text_cc_fapertura) + " " + cashRegister.getOpenedAt());
//                        printerService.printLn(context.getString(R.string.text_cc_abierta_por) + " " + cashRegister.getOpenedBy());
//
//                        printerService.printLn(context.getString(R.string.text_cc_monto_apertura) + " " + moneda + " " + decimalFormat1.format((cashRegisterReport.getInitialCash())));
//
//                        if (cashRegister.getClosedAt() != null)
//                            printerService.printLn(context.getString(R.string.text_cc_fcierre) + " " + cashRegister.getClosedAt());
//
//                        if (cashRegister.getClosedBy() != null)
//                            printerService.printLn(context.getString(R.string.text_cc_cerrada_por) + " " + cashRegister.getClosedBy());
//
//                        printerService.printLn(context.getString(R.string.text_cc_monto_cierre) + "   " + moneda + " " + decimalFormat1.format(Double.valueOf(cashRegister.getClosingAmount())));
//
//                        if (cashRegister.getClosingNote() != null && cashRegister.getClosingNote().length() > 0) {
//
//                            printerService.printLn(context.getString(R.string.text_cc_nota) + " " + cashRegister.getClosingNote());
//                        }
//                        // esperamos 200 ms para que se envien la info
////                        sleep(DELAY_PRINTING_NORMAL);
//
//                        printerService.printLn(linea);
//
//                        printerService.setTextAlignCenter();
//                        printerService.printLn(context.getString(R.string.text_cc_detalle));
//
//                        printerService.setTextAlignLeft();
//
//                        printerService.printLn(context.getString(R.string.text_cc_todas_las_ventas) + "  " + moneda + " " +decimalFormat1.format((cashRegisterReport.getTotalAllSells())));
//
//                        printerService.printLn(context.getString(R.string.text_cc_total_efec) + "      " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalSellCash())));
//
//                        try {
//
//                            printerService.printLn(context.getString(R.string.text_cc_total_tarj) + "       " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalSellCard())));
//
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }
//
//                        printerService.printLn(context.getString(R.string.text_cc_total_ventas) + "        " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalSell() )));
//
//                        printerService.printLn(context.getString(R.string.text_cc_devolucion_efec) + " " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalRefundCash())));
//                        printerService.printLn(context.getString(R.string.text_cc_devolucion_tarj) + "  " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalRefundCard())));
//                        printerService.printLn(context.getString(R.string.text_cc_total_devo) + "    " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalRefund())));
//
//
//                        printerService.printLn(context.getString(R.string.text_cc_total_ingre) + " " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalMovementsIn())));
//
//                        printerService.printLn(context.getString(R.string.text_cc_total_egre) + "  " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalMovementsOut())));
//
//
//                        printerService.printLn(context.getString(R.string.text_cc_total_efec_caja) + " " + moneda + " " + decimalFormat1.format((cashRegisterReport.getTotalCashExpected())));
//
//
//                        printerService.printLn(linea);
//
////                        printerService.printLn("Preparado por: " + usuario.getFirstName() + " " + usuario.getLastName());
//                        List<String> userLine = formatearLinea(impresora.getCantidadCaracteresLinea(),context.getString(R.string.texto_preparado_por) + usuario.getFirstName() + " " + usuario.getLastName());
//                        for (String texto : userLine) {
//                            printerService.printLn(texto);
//                        }
//
//                        for (int h=0; h<impresora.getLinesBelow(); h++){
//                            printerService.printLn(" ");
//                        }
////                        printerService.printLn("pie de pagina");
//
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    // esperamos 200 ms para que se envien la info
////                    sleep(DELAY_PRINTING_NORMAL);
//                    break;
//                case -15:// impresion de prueba
//
//                    printerService.setTextAlignCenter();
//                    // probando logo
//                    if (logoS3bmp != null) {
//                        File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + logoS3bmp);
////                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());
//
//                        if (imgFile.exists()) {
//
//                            printerService.printImage(imgFile.getAbsolutePath());
//
//                            // esperamos 200 ms para que se envien la info
////                            sleep(DELAY_PRINTING_IMAGE);
//
//                        } else {
//                            LogUtils.i(TAG, "NO EXISTE LOGO EN BMP");
//                        }
//                    }
//
//                    List<String> testprint = formatearLineaG1(impresora.getCantidadCaracteresLinea(), (context.getString(R.string.impre_prueba)), impresora.getTipoFuente());
//
//                    List<String> lineas = formatearLinea(impresora.getCantidadCaracteresLinea(), (context.getString(R.string.prueba_y_descripcion)));
//                    //qr
//                    String textoqr = "Prueba desde Vendis";
////                    String textoqr = "54325364|12|123123123";
//                    int store_len = textoqr.length() + 3;
//                    byte store_pL = (byte) (store_len % 256);
//                    byte store_pH = (byte) (store_len / 256);
//
//                    // QR Code: Store the data in the symbol storage area
//                    // Hex      1D      28      6B      pL      pH      31      50      30      d1...dk
//                    // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=143
//                    //                        1D          28          6B         pL          pH  cn(49->x31) fn(80->x50) m(48->x30) d1…dk
//                    byte[] storeQR = {(byte) 0x1d, (byte) 0x28, (byte) 0x6b, store_pL, store_pH, (byte) 0x31, (byte) 0x50, (byte) 0x30};
//
////                    printerService.setTextFont(impresora.getTipoFuente());
//                    printerService.setTextAlignCenter();
//                    printerService.setTextSize2H();
//                    printerService.setTextSize2W();
//
//                    printerService.setTextFont(impresora.getTipoFuente());
//                    for (String testp : testprint) {
//                        printerService.printLn(testp);
//                    }
//                    printerService.printLn("12345678901234567890123456789012345678901234567890");
//                    printerService.setTextAlignLeft();
//                    printerService.setTextSizeNormal();
//                    printerService.setTextFont(impresora.getTipoFuente());
//                    printerService.printLn("");
//                    printerService.printLn("1234567890123456789012345678901234567890123456789012345678901234567890");
//
//                    for (String linea2 : lineas) {
//                        printerService.printLn(linea2);
//                    }
//                    printerService.printLn(linea);
//                    printerService.setTextAlignCenter();
//                    // esperamos 200 ms para que se envien la info
////                    sleep(DELAY_PRINTING_NORMAL);
//                    // write() simply appends the data to the buffer
//                    printer.write(modelQR);
//                    printer.write(sizeQR);
//                    printer.write(errorQR);
//                    printer.write(storeQR);
//                    printer.write(textoqr.getBytes());
//                    printer.write(printQR);
////                    printerService.printQRCode("Prueba desde Vendis App", context);
//                    printerService.setTextSizeNormal();
//                    printerService.setTextAlignRight();
//                    printerService.setTextFont(impresora.getTipoFuente());
//                    printerService.printLn(linea);
////                    printerService.setTextTypeBold();
//                    printerService.printLn((context.getString(R.string.derecha)));
//                    printerService.setTextAlignCenter();
//                    printerService.printLn((context.getString(R.string.centro)));
//                    printerService.setTextAlignLeft();
//                    printerService.printLn((context.getString(R.string.izquierda)));
//                    printerService.setTextAlignCenter();
//                    printerService.printLn((context.getString(R.string.end)));
//                    for (int h=0; h<impresora.getLinesBelow(); h++){
//                        printerService.printLn(" ");
//                    }
//                    // esperamos 200 ms para que se envien la info
////                    sleep(DELAY_PRINTING_NORMAL);
//                    break;
//            }
//
//            printerService.lineBreak();
//
//            if (impresora.getAbrirCaja() != null && impresora.getAbrirCaja().equals(1)) {
//                printerService.openCashDrawerPin2();
//                printerService.openCashDrawerPin5();
//            }
//
//            printerService.cutPart();
//
//            if (impresora.getBeepPrinter())
//                printerService.beepCorto();
//
//            // esperamos 500 ms para que se envien la info
//            sleep(DELAY_PRINTING_END);
//            printerService.close();
//
//        } catch (Exception ioex){
//            try {
//                Intent intent = new Intent();
//                intent.setAction("com.vendis.fail_printer");
//                context.sendBroadcast(intent);
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//            ioex.printStackTrace();
//        }
//
//        LogUtils.i(TAG, "End printer call");
//
//        if (imprimirAvisoListener != null){
////            if (impresora.getTipoImpresora() != PrefUtils.PRINTER_BLUETOOTH){
////                try {
////                    sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            } else {
//                try {
////                    if (venta.getSellLines().size() >100){
////                        if (venta.getSellLines().size() >200){
////                            sleep(3000);
////                        } else if (venta.getSellLines().size() >300){
////                            sleep(4000);
////                        } else {
////                            sleep(2000);
////                        }
////                    } else {
//                        sleep(1000);
////                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////            }
//
//            imprimirAvisoListener.terminoDeImprimir();
//        }
//    }
//
//    private List<String> formatearLinea(int tamanio, String texto){
//        List<String> lineas = new ArrayList<>();
//        //int tamanioLinea = texto.length() / tamanio;
//        String[] textos = texto.split(" ");
//
//        StringBuilder auxLine = new StringBuilder();
//
//        for (String textoAux: textos){
//
//            if ((auxLine.length() + textoAux.trim().length() + 1) <= tamanio){
//                auxLine.append(" ").append(textoAux.trim());
//            } else {
//                lineas.add(auxLine.toString().trim());
//                auxLine = new StringBuilder(textoAux.trim());
//            }
//
//        }
//
//        if (auxLine.length() > 0){
//            lineas.add(auxLine.toString().trim());
//        }
//
//        return lineas;
//    }
//
//    private List<String> formatearLineaG1(int tamanio, String texto, Integer tipoFuente){
//
//        int tamanio2 = tamanio;
//        if (tamanio == PrefUtils.PRINTER_55_MM_DEFAULT_CHARS){
//            tamanio2 = 16;
//        }else if (tamanio == PrefUtils.PRINTER_80_MM_DEFAULT_CHARS){
//            tamanio2 = 22;
//        }
//
//        if (tipoFuente != null){
//            if (tipoFuente == PrefUtils.FONT_B){
//                if (tamanio == PrefUtils.PRINTER_55_MM_DEFAULT_CHARS){
//                    tamanio2 = 20;
//                }else if (tamanio == PrefUtils.PRINTER_80_MM_DEFAULT_CHARS){
//                    tamanio2 = 48;
//                }
//            }
//        }
//
//        List<String> lineas = new ArrayList<>();
//        //int tamanioLinea = texto.length() / tamanio;
//        String[] textos = texto.split(" ");
//
//        StringBuilder auxLine = new StringBuilder();
//
//        for (String textoAux: textos){
//
//            if ((auxLine.length() + textoAux.trim().length() + 1) <= tamanio2){
//                auxLine.append(" ").append(textoAux.trim());
//            } else {
//                lineas.add(auxLine.toString().trim());
//                auxLine = new StringBuilder(textoAux.trim());
//            }
//
//        }
//
//        if (auxLine.length() > 0){
//            lineas.add(auxLine.toString().trim());
//        }
//
//        return lineas;
//    }
//
//    private String formatearLineaItem(int tamanio, String texto0, String texto1, String texto2, String texto3){
//
//        String lineas = "";
//
//        int tamanioSize = tamanio / 4;
//
//        lineas = texto3;
//
//        if (lineas.length() < tamanioSize){
//            int cantidadDif = tamanioSize - lineas.length();
//            lineas = obtenerVacio(cantidadDif) + lineas;
//        }else{
//            lineas = " " + lineas;
//        }
//
//        lineas = texto2 + lineas;
//
//        if (lineas.length() < (tamanioSize*2)){
//            int cantidadDif = (tamanioSize*2) - lineas.length();
//            lineas = obtenerVacio(cantidadDif) + lineas;
//        }
//
//        lineas = texto1 + lineas;
//        if (lineas.length() < (tamanioSize*3)){
//            int cantidadDif = (tamanioSize*3) - lineas.length();
//            lineas = obtenerVacio(cantidadDif) + lineas;
//        }
//
//        if ((lineas.length() + texto0.length()) < (tamanioSize*4)){
//            int cantidadDif = tamanio - (lineas.length() + texto0.length());
//            lineas = obtenerVacio(cantidadDif) + lineas;
//        }
//
//        lineas = texto0 + lineas;
//
//        return lineas;
//    }
//
//    private String obtenerVacio(int cantidad){
//        String linea = "";
//        for (int i= 0 ; i< cantidad; i++){
//            linea += " ";
//        }
//        return linea;
//    }
//
//    private boolean procesarListaPagosCard(List<PaymentLine> lista, Sell venta, PrinterService printerService, String linea, String tipoPago){
//
//        boolean estado = false;
//
//        for (PaymentLine payment: lista){
//
//            if (payment.getMethod() != null && payment.getMethod().equals(tipoPago)) {
////                if (payment.getMethod() != null && payment.getMethod().equals("card")) {
//
//                AdicionalPayment extras= payment.getExtras();
//
//                if (extras != null && extras.getPagoTarjetaMpos() != null){
//                    if (! estado){
//                        estado = true;
//                        printerService.lineBreak();
//
//                        printerService.printLn(linea);
//                        //vectorResp.add(110);
//                        printerService.setTextAlignCenter();
//                        printerService.printLn("DETALLE TRANSACCION");
//                    }
//
//                    try {
//
//                        printerService.printLn(linea);
//
//                        printerService.printLn("ID: " + extras.getPagoTarjetaMpos().getId());
//
//                        printerService.printLn("Titular de la Tarjeta: " + extras.getPagoTarjetaMpos().getCardHolderName());
//
//                        printerService.printLn("Tarjeta: " + extras.getPagoTarjetaMpos().getPan());
//
//                        printerService.printLn("Código Autorización: " + extras.getPagoTarjetaMpos().getAuthorizationCode());
//
//                        printerService.printLn("Tipo de Pago: " + extras.getPagoTarjetaMpos().getPaymentType());
//
//                        printerService.printLn("Total: " + extras.getPagoTarjetaMpos().getTotal());
//
//                        printerService.printLn("Monto impuesto: " + extras.getPagoTarjetaMpos().getTaxAmount());
//
//                        printerService.printLn("Monto propina: " + extras.getPagoTarjetaMpos().getTipAmount());
//
//                        if (venta != null && venta.getInvoiceNo() != null) {
//                            String tipoDocumento = "Nro Factura: ";
//
//                            if (venta.getIsSuspend() == 1){
//                                tipoDocumento = "Nro Pedido: ";
//                            }else if (venta.getIsSellNote() == 1){
//                                tipoDocumento = "Nro Nota de entrega: ";
//                            }
//
//                            printerService.printLn(tipoDocumento + venta.getInvoiceNo());
//                        }
//
//                        printerService.printLn("Referencia Pago: " + payment.getPaymentRefNo());
//
//                        printerService.printLn(linea);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
//        }
//        return estado;
//    }
//
////    /**
////     * Encode and print QR code
////     *
////     * @param str
////     *          String to be encoded in QR.
////     * @param errCorrection
////     *          The degree of error correction. (48 <= n <= 51)
////     *          48 = level L / 7% recovery capacity.
////     *          49 = level M / 15% recovery capacity.
////     *          50 = level Q / 25% recovery capacity.
////     *          51 = level H / 30% recovery capacity.
////     *
////     *  @param moduleSize
////     *  		The size of the QR module (pixel) in dots.
////     *  		The QR code will not print if it is too big.
////     *  		Try setting this low and experiment in making it larger.
////     */
////    public void printQR(Printer printer, String str, int errCorrect, int moduleSize){
////        //save data function 80
////        printer.write([0x1D]);//init
////        printer.write("(k");//adjust height of barcode
////        printer.write(str.length()+3); //pl
////        printer.write(0); //ph
////        printer.write(49); //cn
////        printer.write(80); //fn
////        printer.write(48); //
////        printer.write(str);
////
////        //error correction function 69
////        printer.write(0x1D);
////        printer.write("(k");
////        printer.write(3); //pl
////        printer.write(0); //ph
////        printer.write(49); //cn
////        printer.write(69); //fn
////        printer.write(errCorrect); //48<= n <= 51
////
////        //size function 67
////        printer.write(0x1D);
////        printer.write("(k");
////        printer.write(3);
////        printer.write(0);
////        printer.write(49);
////        printer.write(67);
////        printer.write(moduleSize);//1<= n <= 16
////
////        //print function 81
////        printer.write(0x1D);
////        printer.write("(k");
////        printer.write(3); //pl
////        printer.write(0); //ph
////        printer.write(49); //cn
////        printer.write(81); //fn
////        printer.write(48); //m
////    }
//}
