package imprime;

import android.graphics.Bitmap;

import java.io.IOException;

import io.github.escposjava.PrinterService;


public class PrinterEscPosGeneric implements PrinterVendis{

    private PrinterService printerService;

    public PrinterEscPosGeneric(){
        super();
    }

    protected void setPrinterService(PrinterService printerService) {
        this.printerService = printerService;
    }

    //    @Override
//    public void setImpresora(Impresora impresora) {
//
//    }
//
//    @Override
//    public void setSucursal(Location sucursal) {
//
//    }
//
//    @Override
//    public void setBusiness(Business empresa) {
//
//    }
//
//    @Override
//    public void setContext(Context context) {
//
//    }
//
//    @Override
//    public void setSell(Sell sell) {
//
//    }
//
//    @Override
//    public void setPayment(Payment payment) {
//
//    }
//
//    @Override
//    public void setUsuario(User usuario) {
//
//    }
//
//    @Override
//    public void setMoneda(String moneda) {
//
//    }
//
//    @Override
//    public void setFecha(String fecha) {
//
//    }
//
//    @Override
//    public void setCashRegister(CashRegister cashRegister) {
//
//    }
//
//    @Override
//    public void setCashRegisterReport(CashRegisterReport cashRegisterReport) {
//
//    }

    @Override
    public void iniciarPrinter() {
        printerService.init();
    }

    @Override
    public void printerImage(String pathImage) throws IOException {
        printerService.printImage(pathImage);

    }

    @Override
    public void printerText(String texto, int align, int size, Integer tipoFuente) {
        if (align == PrinterVendis.ALIGN_CENTER){
            printerService.setTextAlignCenter();
        } else if (align == PrinterVendis.ALIGN_RIGHT){
            printerService.setTextAlignRight();
        } else {
            printerService.setTextAlignLeft();
        }

        if (size == PrinterVendis.TEXT_SIZE_NORMAL){

        }


    }

    @Override
    public void printerCodeQr(String contenidoQr) {

//        printerService.printQRCode();
    }

    @Override
    public void printerCodeQr(Bitmap bitmapQr) {

    }
}
