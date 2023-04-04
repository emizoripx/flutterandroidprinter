//package imprime;
//
//import android.util.Log;
//
//import com.mobiwire.CSAndroidGoLib.CsPrinter;
//import com.nbbse.printapi.Printer;
//
//import excepcion.ErrorFormatoPaqueteException;
//import excepcion.ErrorPapelException;
//import excepcion.ImpresoraErrorException;
//import excepcion.ImpresoraOcupadaException;
//import excepcion.NoHayPapelException;
//import excepcion.SobrecalentamientoException;
//import excepcion.TamanioPaqueteDatosException;
//import excepcion.VoltageBajoException;
//
//public class VerificadorImpresora {
//
//    private Printer printer;
//
//    public VerificadorImpresora(){
//
//        printer = Printer.getInstance();
//    }
//
//    public void verificarImpresora() throws VoltageBajoException, ImpresoraErrorException, NoHayPapelException, ErrorPapelException {
//        //Printer printer = Printer.getInstance();
//        Log.i("IMPRESION ","vOLTAGE " + printer.voltageCheck() + " [INITING:" + Printer.PRINTER_STATUS_OK + ",PRINTING:" + Printer.PRINTER_STATUS_OVER_HEAT + ",READY:" + Printer.PRINTER_STATUS_GET_FAILED + ",ERROR:" + Printer.PRINTER_STATUS_NO_PAPER + "] pRINTER STATUS " + printer.getPrinterStatus() + " [NOPAPER:" + Printer.PRINTER_NO_PAPER + ",EXISTPAPER:" + Printer.PRINTER_EXIST_PAPER + ",PAPERERROR:" + Printer.PRINTER_PAPER_ERROR + "]pAPER STATUS " + printer.getPaperStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " [PRINTER_STATUS_OK:0 -  PRINTER_STATUS_NO_PAPER:1 - PRINTER_STATUS_NO_REACTION:2 - PRINTER_STATUS_GET_FAILE:3 - PRINTER_STATUS_LOW_POWER:4 " + printer.getCurrentPrinterStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " - nvram " + printer.readPrintDistanceFromNvram());
//        if (printer.voltageCheck()){
//            switch (printer.getPrinterStatus()){
//                case Printer.PRINTER_STATUS_OVER_HEAT:
//                    Log.e("Error printer","Printer status over head");
//                    throw new ImpresoraErrorException();
//                case Printer.PRINTER_STATUS_GET_FAILED:
//                    Log.e("Error printer","printer status get failed");
//                    throw new ErrorPapelException();
//                case Printer.PRINTER_STATUS_NO_PAPER:
//                    Log.e("Error printer"," printer status no paper");
//                    throw new NoHayPapelException();
//
//            }
//        }else{
//            throw new VoltageBajoException();
//        }
//
//    }
//
//    public void verificarImpresora(int estado) throws VoltageBajoException, ImpresoraErrorException, NoHayPapelException, ErrorPapelException, ImpresoraOcupadaException, ErrorFormatoPaqueteException, SobrecalentamientoException, TamanioPaqueteDatosException {
//        //Printer printer = Printer.getInstance();
////        Log.i("IMPRESION ","vOLTAGE " + printer.voltageCheck() + " [INITING:" + Printer.PRINTER_STATUS_OK + ",PRINTING:" + Printer.PRINTER_STATUS_OVER_HEAT + ",READY:" + Printer.PRINTER_STATUS_GET_FAILED + ",ERROR:" + Printer.PRINTER_STATUS_NO_PAPER + "] pRINTER STATUS " + printer.getPrinterStatus() + " [NOPAPER:" + Printer.PRINTER_NO_PAPER + ",EXISTPAPER:" + Printer.PRINTER_EXIST_PAPER + ",PAPERERROR:" + Printer.PRINTER_PAPER_ERROR + "]pAPER STATUS " + printer.getPaperStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " [PRINTER_STATUS_OK:0 -  PRINTER_STATUS_NO_PAPER:1 - PRINTER_STATUS_NO_REACTION:2 - PRINTER_STATUS_GET_FAILE:3 - PRINTER_STATUS_LOW_POWER:4 " + printer.getCurrentPrinterStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " - nvram " + printer.readPrintDistanceFromNvram());
//
//        switch (estado){
//            case 1:
//                Log.e("Error printer","ocupada");
//                throw new ImpresoraOcupadaException();
//            case 2:
//                Log.e("Error printer","Sin papel");
//                throw new NoHayPapelException();
//            case 3:
//                Log.e("Error printer","printer status get failed");
//                throw new ErrorFormatoPaqueteException();
//            case 4:
//                Log.e("Error printer"," printer status no paper");
//                throw new ImpresoraErrorException();
//            case 8:
//                Log.e("Error printer"," printer status no paper");
//                throw new SobrecalentamientoException();
//            case 9:
//                Log.e("Error printer"," printer status no paper");
//                throw new VoltageBajoException();
//            case -2:
//                Log.e("Error printer"," printer status no paper");
//                throw new TamanioPaqueteDatosException();
//
//        }
//
//
//        /*
//         * 0- éxito
//         * 1- La impresora está ocupada
//         * 2- sin papel
//         * 3- El formato del error del paquete de datos de impresión.
//         * 4- Mal funcionamiento de la impresora.
//         * 8- Impresora sobre calor.
//         * 9- El voltaje de la impresora es demasiado bajo.
//         * -16- La impresión está sin terminar.
//         * -4- La impresora no ha instalado la biblioteca de fuentes.
//         * -2- El paquete de datos es demasiado largo.
//         * */
//
//    }
//
//    public void verificarImpresora(CsPrinter printer) throws VoltageBajoException, ImpresoraErrorException, NoHayPapelException, ErrorPapelException, ImpresoraOcupadaException, ErrorFormatoPaqueteException, SobrecalentamientoException, TamanioPaqueteDatosException {
//        //Printer printer = Printer.getInstance();
////        Log.i("IMPRESION ","vOLTAGE " + printer.voltageCheck() + " [INITING:" + Printer.PRINTER_STATUS_OK + ",PRINTING:" + Printer.PRINTER_STATUS_OVER_HEAT + ",READY:" + Printer.PRINTER_STATUS_GET_FAILED + ",ERROR:" + Printer.PRINTER_STATUS_NO_PAPER + "] pRINTER STATUS " + printer.getPrinterStatus() + " [NOPAPER:" + Printer.PRINTER_NO_PAPER + ",EXISTPAPER:" + Printer.PRINTER_EXIST_PAPER + ",PAPERERROR:" + Printer.PRINTER_PAPER_ERROR + "]pAPER STATUS " + printer.getPaperStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " [PRINTER_STATUS_OK:0 -  PRINTER_STATUS_NO_PAPER:1 - PRINTER_STATUS_NO_REACTION:2 - PRINTER_STATUS_GET_FAILE:3 - PRINTER_STATUS_LOW_POWER:4 " + printer.getCurrentPrinterStatus());
//        //Log.i("current status", "isPrinterOperation[TRUE,FALSE] " + printer.isPrinterOperation() + " - nvram " + printer.readPrintDistanceFromNvram());
//        Log.e("Error printer"," estado number " + CsPrinter.getPrinterStatus());
//        switch (CsPrinter.getPrinterStatus()){
//            case 1000:
//                Log.e("Error printer","ocupada");
//                throw new ImpresoraOcupadaException();
//            case 2000:
//                Log.e("Error printer","Sin papel");
//                throw new NoHayPapelException();
//            case 3000:
//                Log.e("Error printer","printer status get failed");
//                throw new ErrorFormatoPaqueteException();
//            case 4000:
//                Log.e("Error printer"," printer status no paper");
//                throw new ImpresoraErrorException();
//            case 8000:
//                Log.e("Error printer"," printer status no paper");
//                throw new SobrecalentamientoException();
//            case 9000:
//                Log.e("Error printer"," printer status no paper");
//                throw new VoltageBajoException();
//            case -2000:
//                Log.e("Error printer"," printer status no paper");
//                throw new TamanioPaqueteDatosException();
//
//        }
//
////        if (! CsPrinter.getPaperStatus()){
////            Log.e("Error printer","Sin papel");
////            throw new NoHayPapelException();
////        }
//
//        /*
//         * 0- éxito
//         * 1- La impresora está ocupada
//         * 2- sin papel
//         * 3- El formato del error del paquete de datos de impresión.
//         * 4- Mal funcionamiento de la impresora.
//         * 8- Impresora sobre calor.
//         * 9- El voltaje de la impresora es demasiado bajo.
//         * -16- La impresión está sin terminar.
//         * -4- La impresora no ha instalado la biblioteca de fuentes.
//         * -2- El paquete de datos es demasiado largo.
//         * */
//
//    }
//}
