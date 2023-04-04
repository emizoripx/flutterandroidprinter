package imprime;

import android.graphics.Bitmap;

import java.io.IOException;

public interface PrinterVendis {

    int ALIGN_CENTER = 1;
    int ALIGN_RIGHT = 2;
    int ALIGN_LEFT = 3;

    int TEXT_SIZE_NORMAL = 10;
    int TEXT_SIZE_GRANDE = 11;
    int TEXT_SIZE_PEQUENIO = 12;

//    void setImpresora(Impresora impresora);
//    void setSucursal(Location sucursal);
//    void setBusiness(Business empresa);
//    void setContext(Context context);
//    void setSell(Sell sell);
//    void setPayment(Payment payment);
//    void setUsuario(User usuario);
//    void setMoneda(String moneda);
//    void setFecha(String fecha);
//    void setCashRegister(CashRegister cashRegister);
//    void setCashRegisterReport(CashRegisterReport cashRegisterReport);

    void iniciarPrinter();

    void printerImage(String pathImage) throws IOException;
    void printerText(String texto, int align, int size, Integer tipoFuente);
    void printerCodeQr(String contenidoQr);
    void printerCodeQr(Bitmap bitMapQr);
}
