//package android.src.main.imprime;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.WriterException;
//import com.mobiwire.CSAndroidGoLib.CsPrinter;
//import com.nbbse.printapi.Printer;
//import com.pax.dal.IPrinter;
//import com.pax.dal.entity.EFontTypeAscii;
//import com.pax.dal.entity.EFontTypeExtCode;
//import com.pax.dal.exceptions.PrinterDevException;
//import com.pax.neptunelite.api.NeptuneLiteUser;
//import com.sunmi.utils.AidlUtil;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.InputStream;
//
//import java.util.Vector;
//
//import android.src.main.excepcion.ErrorFormatoPaqueteException;
//import android.src.main.excepcion.ErrorPapelException;
//import android.src.main.excepcion.ImpresoraErrorException;
//import android.src.main.excepcion.ImpresoraOcupadaException;
//import android.src.main.excepcion.NoHayPapelException;
//import android.src.main.excepcion.SobrecalentamientoException;
//import android.src.main.excepcion.TamanioPaqueteDatosException;
//import android.src.main.excepcion.VoltageBajoException;
//import bo.com.vendis.pos.R;
//import android.src.main.utils.LogUtils;
//
//public class ImprimirFactura {
//
//    private Context context;
//    private Printer printer;
//    private boolean estado;
//    //private int factnot;
//
//    private final int TAMANIO_NORMAL_3 = 22;
//    private final int TAMANIO_NORMAL_2 = 22;
//    private final int TAMANIO_NORMAL = 24;
//    private final int TAMANIO_GRANDE = 32;
//
//    protected final String linea = "--------------------------------";
//
//    private ImprimirAvisoListener avisoListener;
//
//    public ImprimirFactura(Context context) {
//        this.context = context;
//        printer = Printer.getInstance();
//        estado = false;
//        //factnot = 0;
//    }
//
//    public void setAvisoListener(ImprimirAvisoListener avisoListener) {
//        this.avisoListener = avisoListener;
//    }
//
//    // public void setFactnot(int factnot) {
//    //    this.factnot = factnot;
//    //}
//
//    public synchronized void  imprimirFactura(Vector<Object> imprimir) throws ImpresoraErrorException, NoHayPapelException, VoltageBajoException, ErrorPapelException, ImpresoraOcupadaException, SobrecalentamientoException, TamanioPaqueteDatosException, ErrorFormatoPaqueteException {
//        if (((Build.BRAND.toUpperCase().equals("PAX") && Build.MODEL.toUpperCase().equals("A920")))) {
//            imprimirFactura2(imprimir);
//            return;
//
//        }
//
//        if ((Build.BRAND.equals("MobiIoT") && Build.MODEL.equals("MP4"))) {
//            imprimirFactura3(imprimir);
//            return;
//        }
//
//        if (AidlUtil.getInstance().isConnect()){
//            imprimirFactura4(imprimir);
//            return;
//        }
//
//        if (!(Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1 && Build.BRAND.toUpperCase().equals("MOBIWIRE") && Build.MODEL.toUpperCase().equals("MOBIPRINT"))) {
//            return;
//        }
//
//        while (estado) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        estado = true;
//
//        new VerificadorImpresora().verificarImpresora();
//
//        Integer valor = 0;
//
//        int cont = 0;
//
//        for (Object objeto : imprimir) {
//            //Log.i("imprimir","imprimiendo ---iniciofila " + objeto.getClass().getName());
//
//            if (objeto instanceof String) {
//                //Log.i("imprimir","es string");
//                String linea = (String) objeto;
//                switch (valor) {
//                    case 0:
//                        //Log.i("imprimir","es caso 0");
//                        printer.printText(linea);
//                        break;
//                    case 1:
//                        //Log.i("imprimir","es caso 1");
//                        printer.printText(linea, true);
//                        valor = 0;
//                        break;
//                    case 2:
//                        //Log.i("imprimir","es caso 2");
//                        printer.printBitmap(linea);
//                        try {
//
//                            //Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(150);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir","Ferror al dormir");
//                        }
//                        valor = 0;
//                        break;
//                    case 3:
//                        //Log.i("imprimir","es caso 3");
//                        printer.printBitmap(context.getResources().openRawResource(R.raw.linea3));
//                        try {
//
//                            LogUtils.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(50);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            LogUtils.i("imprimir","error al dormir");
//                        }
//                        valor = 0;
//                        break;
//                    case 4:
//                        //Log.i("imprimir","es caso 4");
//                        printer.printText(linea, 2, true);
//                        valor = 0;
//                        break;
//                    case 5:
//                        //Log.i("imprimir","es caso 5");
//
//                        printer.printText(linea, 2);
//                        valor = 0;
//                        break;
//                    case 6:
//                        //Log.i("imprimir","es caso 6");
//                        printer.printBitmap(context.getResources().openRawResource(R.raw.linea3));
//                        try {
//
//                            LogUtils.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(60);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            LogUtils.i("imprimir","error al dormir");
//                        }
//                        valor = 0;
//                        break;
//                    case 7:
//                        //Log.i("imprimir","es caso 7");
//                        printer.printBitmap(context.getResources().openRawResource(R.raw.pie_de_pagina_vendis));
//                        valor = 0;
//                        try {
//
//                            // Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(150);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir","error al dormir");
//                        }
//                        break;
//                    case 8:
//                        //Log.i("imprimir","es caso 7");
//                        printer.printBitmap(context.getResources().openRawResource(R.raw.leyenda_generica));
//                        valor = 0;
//                        try {
//
//                            //Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(150);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir","error al dormir");
//                        }
//                        break;
//                    case 9:
//                        //Log.i("imprimir","es caso 5");
//                        printer.printText(linea, 3);
//                        valor = 0;
//                        break;
//
//                    case 10:
//                        int idImagen = Integer.parseInt(linea);
//                        printer.printBitmap(context.getResources().openRawResource(idImagen));
//                        valor = 0;
//                        try {
//
//                            //Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(250);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir","error al dormir");
//                        }
//                        break;
//                    case 11:
//                        //String rutaImagen = linea;
//                        printer.printBitmap(linea);
//                        valor = 0;
//                        try {
//
//                            //Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(250);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir","error al dormir");
//                        }
//                        break;
//                }
//                //Log.i("imprimir","fin string");
//            } else if (objeto instanceof Integer) {
//                //Log.i("imprimir","es entero");
//                valor = (Integer) objeto;
//            } else if (objeto instanceof InputStream) {
//                //Log.i("imprimir","es imagen");
//                printer.printBitmap((InputStream) objeto);
//                try {
//
//                    LogUtils.i("imprimir","durmiendo 30ms");
//                    Thread.sleep(50);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    LogUtils.i("imprimir","error al dormir");
//                }
//                valor = 0;
//            }
//
//            //Log.i("imprimir","fin ifs con++");
//            cont++;
//
//            if (cont == 40) {
//                cont = 0;
//                try {
//                    while (printer.isPrinterOperation()) {
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    try {
//                        Thread.sleep(1200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }
//
//        printer.printEndLine();
//
//        int contadorEspera = 0;
//        try {
//            while (printer.isPrinterOperation()) {
//                try {
//                    Thread.sleep(500);
//                    contadorEspera++;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if (contadorEspera == 10) {
//                    break;
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            try {
//                Thread.sleep(1000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        new VerificadorImpresora().verificarImpresora();
//
//        estado = false;
//
//        if (avisoListener != null){
//            avisoListener.terminoDeImprimir();
//        }
//        notify();
//
//    }
//
//    public synchronized void imprimirFactura2(Vector<Object> imprimir) throws ImpresoraErrorException, NoHayPapelException, VoltageBajoException, ErrorPapelException, ImpresoraOcupadaException, SobrecalentamientoException, TamanioPaqueteDatosException, ErrorFormatoPaqueteException {
//
//        while (estado) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        IPrinter iPrinter = null;
//
//        int status = -400;
//        try{
//            iPrinter = NeptuneLiteUser.getInstance().getDal(context).getPrinter();
//
//
//            iPrinter.init();
//            iPrinter.spaceSet((byte)0,(byte)0);
//            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//
//            status = iPrinter.getStatus();
//
//        }catch (PrinterDevException ex){
//            throw new ImpresoraErrorException();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        new VerificadorImpresora().verificarImpresora(status);
//        estado = true;
//
//        Integer valor = 0;
//
//        int cont = 0;
//        try {
//            iPrinter.setGray(500);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        for (Object objeto : imprimir) {
//            //Log.i("imprimir","imprimiendo ---iniciofila " + objeto.getClass().getName());
//            try {
//                iPrinter.setGray(500);
//                iPrinter.doubleHeight(false, false);
//                iPrinter.doubleWidth(false, false);
//                if (objeto instanceof String) {
//                    //Log.i("imprimir","es string");
//                    String linea = (String) objeto;
//                    switch (valor) {
//                        case -1:
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.leftIndent(130);
//                            iPrinter.fontSet(EFontTypeAscii.FONT_16_24,EFontTypeExtCode.FONT_24_24);
//                            iPrinter.printStr(linea + "\n", null);
//                            iPrinter.leftIndent(0);
//                            break;
//                        case 0:
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//                            break;
//                        case 1:
//                            //Log.i("imprimir","es caso 1");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);
//                            //iPrinter.invert(true);
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//
//                            valor = 0;
//                            //iPrinter.invert(false);
//                            break;
//                        case 2:
//                        case 3:
//                            //Log.i("imprimir","es caso 3");
//                            InputStream bm = context.getResources().openRawResource(R.raw.linea3);
//                            BufferedInputStream bufferedInputStream = new BufferedInputStream(bm);
//                            Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
//                            iPrinter.printBitmap(bmp);
//                            /*try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(1500);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//                            valor = 0;
//                            break;
//                        case 4:
//                            //Log.i("imprimir","es caso 4");
//                            iPrinter.invert(true);
//                            //https://aminoapps.com/c/the-loud-amino-espanol/page/blog/disfruta-los-episodios-de-tlh-actualizado/DDVw_3bhPuY2eBEbgDa63X2oYB7eEN12B
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            //iPrinter.leftIndent(90);
//
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//                            valor = 0;
//                            iPrinter.invert(false);
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);
//                            //iPrinter.leftIndent(0);
//                            break;
//                        case 5:
//                            //Log.i("imprimir","es caso 5");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//                            valor = 0;
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);
//                            break;
//                        case 6:
//                            //Log.i("imprimir","es caso 6");
//                           /* printer.printBitmap(context.getResources().openRawResource(R.raw.linea));
//                        try {
//
//                            Log.i("imprimir","durmiendo 30ms");
//                            Thread.sleep(50);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            Log.i("imprimir","error al dormir");
//                            //context.getResources().openRawResource(R.raw.pie_de_pagina)
//                            //context.getResources().openRawResource(R.raw.leyenda_generica)
//                        }*/
//                            InputStream bm2 = context.getResources().openRawResource(R.raw.linea3);
//                            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(bm2);
//                            Bitmap bmp2 = BitmapFactory.decodeStream(bufferedInputStream2);
//                            iPrinter.printBitmap(bmp2);
//                            /*try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(1500);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//                            valor = 0;
//                            break;
//                        case 7:
//                            //Log.i("imprimir","es caso 7");
//                            /*printer.printBitmap(context.getResources().openRawResource(R.raw.pie_de_pagina));
//                            valor = 0;
//                            try {
//
//                                // Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(150);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//                            InputStream bmpp = context.getResources().openRawResource(R.raw.pie_de_pagina_vendis);
//                            BufferedInputStream bufferedInputStreampp = new BufferedInputStream(bmpp);
//                            Bitmap bmppp = BitmapFactory.decodeStream(bufferedInputStreampp);
//                            iPrinter.printBitmap(bmppp);
//                            /*try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(1500);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//
//                            valor = 0;
//                            break;
//                        case 8:
//                            //Log.i("imprimir","es caso 7");
//                            /*printer.printBitmap(context.getResources().openRawResource(R.raw.leyenda_generica));
//                            valor = 0;
//                            try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(150);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//
//                            InputStream bmlg = context.getResources().openRawResource(R.raw.leyenda_generica);
//                            BufferedInputStream bufferedInputStreamlg = new BufferedInputStream(bmlg);
//                            Bitmap bmplg = BitmapFactory.decodeStream(bufferedInputStreamlg);
//                            iPrinter.printBitmap(bmplg);
//                            /*try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(1500);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//
//                            valor = 0;
//                            break;
//                        case 9:
//                            //Log.i("imprimir","es caso 5");
//                            /*printer.printText(linea, 3);
//                            valor = 0;*/
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            //iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//                            valor = 0;
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);
//                            break;
//
//                        case 10:
//                            int idImagen = Integer.parseInt(linea);
//                            /*printer.printBitmap(context.getResources().openRawResource(idImagen));
//                            valor = 0;
//                            try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(250);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//
//                            InputStream bmid = context.getResources().openRawResource(idImagen);
//                            BufferedInputStream bufferedInputStreamid = new BufferedInputStream(bmid);
//                            Bitmap bmpid = BitmapFactory.decodeStream(bufferedInputStreamid);
//                            iPrinter.printBitmap(bmpid);
//                            /*try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(1500);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//
//                            valor = 0;
//                            break;
//                        case 11:
//                            //String rutaImagen = linea;
//                            /*printer.printBitmap(linea);
//                            valor = 0;
//                            try {
//
//                                //Log.i("imprimir","durmiendo 30ms");
//                                Thread.sleep(250);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir","error al dormir");
//                            }*/
//                            File archivo = new File(linea);
//                            Uri uri = Uri.fromFile(archivo);
//                            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//
//                            BufferedInputStream bufferedInputStreamid2 = new BufferedInputStream(inputStream);
//                            Bitmap bmpid2 = BitmapFactory.decodeStream(bufferedInputStreamid2);
//                            iPrinter.printBitmap(bmpid2);
//
//                            valor = 0;
//                            /*iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.setGray(500);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            //iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.printStr(linea + "\n", "ISO-8859-1");
//                            valor = 0;
//                            iPrinter.doubleHeight(false, false);
//                            iPrinter.doubleWidth(false, false);*/
//                            break;
//                        case 100://letra pequenia con salto de linea
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.printStr(linea + "\n", null);
//                            valor = 0;
//                            break;
//                        case 101://letra pequenia sin salto de linea
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.printStr(linea, null);
//                            valor = 0;
//                            break;
//                        case 102://letra pequenia con doble with y height con salto de linea
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            iPrinter.printStr(linea + "\n", null);
//                            valor = 0;
//                            break;
//                        case 103://letra pequenia con doble with y height sin salto
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            iPrinter.printStr(linea, null);
//                            valor = 0;
//                            break;
//                        case 104://letra pequenia con salto de linea e invertido
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_8_16,EFontTypeExtCode.FONT_16_16);
//                            iPrinter.invert(true);
//                            iPrinter.printStr(linea + "\n", null);
//                            iPrinter.invert(false);
//                            valor = 0;
//                            break;
//                        case 110:// letra normal
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_16_24,EFontTypeExtCode.FONT_24_24);
//                            iPrinter.printStr(linea + "\n", null);
//                            valor = 0;
//                            break;
//                        case 111:// letra normal
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_16_24,EFontTypeExtCode.FONT_24_24);
//                            iPrinter.printStr(linea, null);
//                            valor = 0;
//                            break;
//                        case 112://letra normal con doble with y height con salto
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_16_24,EFontTypeExtCode.FONT_24_24);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            iPrinter.printStr(linea + "\n", null);
//                            valor = 0;
//                            break;
//                        case 113://letra normal con doble with y height sin salto
//                            //Log.i("imprimir","es caso 0");
//                            iPrinter.fontSet(EFontTypeAscii.FONT_16_24,EFontTypeExtCode.FONT_24_24);
//                            iPrinter.doubleHeight(true, true);
//                            iPrinter.doubleWidth(true, true);
//                            iPrinter.printStr(linea, null);
//                            valor = 0;
//                            break;
//
//                    }
//                    //Log.i("imprimir","fin string");
//                } else if (objeto instanceof Integer) {
//                    //Log.i("imprimir","es entero");
//                    valor = (Integer) objeto;
//                } else if (objeto instanceof InputStream) {
//                    //Log.i("imprimir","es imagen");
//                    //printer.printBitmap((InputStream) objeto);context.getFilesDir().getAbsolutePath() + "codigoQr.bmp
//                    InputStream bmob = (InputStream) objeto;
//                    BufferedInputStream bufferedInputStreamob = new BufferedInputStream(bmob);
//                    Bitmap bmpob = BitmapFactory.decodeStream(bufferedInputStreamob);
//                    iPrinter.printBitmap(bmpob);
//                    /*try {
//
//                        //Log.i("imprimir","durmiendo 30ms");
//                        Thread.sleep(1500);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        //Log.i("imprimir","error al dormir");
//                    }*/
//
//                    valor = 0;
//                /*try {
//
//                    LogUtils.i("imprimir","durmiendo 30ms");
//                    Thread.sleep(50);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    LogUtils.i("imprimir","error al dormir");
//                }
//                    valor = 0;*/
//                } else if (objeto instanceof Bitmap){
//
//                    Bitmap bmpido = (Bitmap) objeto;
//                    iPrinter.printBitmap(bmpido);
//                    /*try {
//
//                        //Log.i("imprimir","durmiendo 30ms");
//                        Thread.sleep(1500);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        //Log.i("imprimir","error al dormir");
//                    }*/
//
//                    valor = 0;
//                }
//
//                //Log.i("imprimir","fin ifs con++");
//                cont++;
//
//                if (cont == 400) {
//                    iPrinter.start();
//                    //Log.i("imprimir", "con = 50");
//                    /*try {
//
//                        //Log.i("imprimir", "durmiendo 30ms");
//                        Thread.sleep(1000);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        //Log.i("imprimir", "error al dormir");
//                    }*/
//                    cont = 0;
//                    try {
//                        while (iPrinter.getStatus() == 1) {
//                            try {
//
//                                //Log.i("imprimir", "durmiendo 30ms");
//                                Thread.sleep(10);
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                //Log.i("imprimir", "error al dormir");
//                            }
//                            //Log.i("imprimir", "esta imprimiendo");
//                        }
//                        //Log.i("imprimir", "ya no esta imprimiendo");
//                        //Log.i("imprimir", "termina de dormir");
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//
//                        try {
//
//                            //Log.i("imprimir", "durmiendo 30ms");
//                            Thread.sleep(1200);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            //Log.i("imprimir", "error al dormir");
//                        }
//
//                    }
//
//                    /*try {
//
//                        //Log.i("imprimir", "durmiendo 30ms");
//                        Thread.sleep(1200);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        //Log.i("imprimir", "error al dormir");
//                    }*/
//                    iPrinter = null;
//
//                    iPrinter = NeptuneLiteUser.getInstance().getDal(context).getPrinter();
//
//                    iPrinter.init();
//                    iPrinter.spaceSet((byte) 0, (byte) 0);
//                    iPrinter.setGray(500);
//
//                }
//                //Log.i("imprimir", "imprimiendo_finalfila");
//            }catch (Exception ex){
//
//            }
//
//        }
//
//        int contadorEspera = 0;
//        try {
//
//            iPrinter.printStr("\f", null);
//            iPrinter.start();
//            try {
//
//                Thread.sleep(1000);
//                status = iPrinter.getStatus();
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//
//            }
//
//            new VerificadorImpresora().verificarImpresora(status);
//
//            while (iPrinter.getStatus() != 0) {
//                try {
//
//                    Thread.sleep(500);
//                    contadorEspera++;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//
//                }
//
//                if (contadorEspera == 10) {
//                    break;
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            try {
//
//                Thread.sleep(1000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        estado = false;
//
//        if (avisoListener != null){
//            avisoListener.terminoDeImprimir();
//        }
//        notify();
//
//    }
//
//    public synchronized void imprimirFactura3(Vector<Object> imprimir) throws ImpresoraErrorException, NoHayPapelException, VoltageBajoException, ErrorPapelException, ImpresoraOcupadaException, SobrecalentamientoException, TamanioPaqueteDatosException, ErrorFormatoPaqueteException {
//
//        while (estado) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        LogUtils.i("IMPRIMIR", "size " + imprimir.size());
//
//        CsPrinter csPrinter=new CsPrinter();
//
//        new VerificadorImpresora().verificarImpresora(csPrinter);
//        estado = true;
//
//        Integer valor = 0;
//
//        int cont = 0;
//
//        for (Object objeto : imprimir) {
//
//            if (objeto instanceof String) {
//                //Log.i("imprimir","es string");
//                String linea = (String) objeto;
//                switch (valor) {
//                    case 0:
//                        //Log.i("imprimir","es caso 0");
////                        printer.printText(linea);
//                        CsPrinter.printText_FullParm(linea, 0,0, 1, 0, false, false);
////                        csPrinter.addTextToPrint(linea, null, 25, false, false, 0);
//                        break;
//                    case 1:
//                        //Log.i("imprimir","es caso 1");
////                        csPrinter.printText(linea, true);
//                        CsPrinter.printText_FullParm(linea, 0,0, 1, 0, false, false);
////                        csPrinter.addTextToPrint(linea, null, 25, false, false, 2);
//                        valor = 0;
//                        break;
//                    case 2:
//                        //Log.i("imprimir","es caso 2");
////                        csPrinter.printBitmap(linea);
//                        try {
//                            File archivo = new File(linea);
//                            Uri uri = Uri.fromFile(archivo);
//                            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//
//                            BufferedInputStream bufferedInputStreamid2 = new BufferedInputStream(inputStream);
//                            Bitmap bmpid2 = BitmapFactory.decodeStream(bufferedInputStreamid2);
//                            CsPrinter.printBitmap(context, bmpid2);
////                            csPrinter.addBitmapToPrint(bmpid2);
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }
////                        csPrinter.addBitmapToPrint(linea, null, 25, false, false, 2);
////                        try {
////
////                            //Log.i("imprimir","durmiendo 30ms");
////                            Thread.sleep(150);
////
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                            //Log.i("imprimir","error al dormir");
////                        }
//                        valor = 0;
//                        break;
//                    case 3:
//                    case 6:
//                        //Log.i("imprimir","es caso 3");
//                        CsPrinter.printBitmap(context,BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.linea3)));
//
////                        csPrinter.addBitmapToPrint(BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.linea3)));
////                        try {
////
////                            LogUtils.i("imprimir","durmiendo 30ms");
////                            Thread.sleep(50);
////
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                            LogUtils.i("imprimir","error al dormir");
////                        }
//                        valor = 0;
//                        break;
////                        case 6:
////                        //Log.i("imprimir","es caso 6");
//////                        csPrinter.printBitmap(context.getResources().openRawResource(R.raw.linea3));
//////                        csPrinter.addBitmapFromRawToPrint(context, R.raw.linea3);
////                        CsPrinter.printBitmap(context, BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.linea3)));
//////                        try {
//////
//////                            LogUtils.i("imprimir","durmiendo 30ms");
//////                            Thread.sleep(60);
//////
//////                        } catch (InterruptedException e) {
//////                            e.printStackTrace();
//////                            LogUtils.i("imprimir","error al dormir");
//////                        }
////                        valor = 0;
////                        break;
//                    case 4:
//                        //Log.i("imprimir","es caso 4");
////                        csPrinter.printText(linea, 2, true);
////                        csPrinter.addTextToPrint(linea, null, 35, false, false, 2);
//                        CsPrinter.printText_FullParm(linea, 1,0, 1, 2, false, false);
//                        valor = 0;
//                        break;
//                    case 5:
//                        //Log.i("imprimir","es caso 5");
//
////                        csPrinter.printText(linea, 2);
////                        csPrinter.addTextToPrint(linea, null, 35, false, false, 0);
//                        CsPrinter.printText_FullParm(linea, 1,0, 1, 0, false, false);
//                        valor = 0;
//                        break;
//
//                    case 7:
//                        //Log.i("imprimir","es caso 7");
////                        csPrinter.printBitmap(context.getResources().openRawResource(R.raw.pie_de_pagina_vendis));
////                        csPrinter.addBitmapFromRawToPrint(context, R.raw.pie_de_pagina_vendis);
//                        CsPrinter.printBitmap(context,BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.pie_de_pagina_vendis)));
//                        valor = 0;
//                        break;
//                    case 8:
//                        //Log.i("imprimir","es caso 7");
////                        csPrinter.printBitmap(context.getResources().openRawResource(R.raw.leyenda_generica));
////                        csPrinter.addBitmapFromRawToPrint(context, R.raw.leyenda_generica);
//                        CsPrinter.printBitmap(context,BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.leyenda_generica)));
//                        valor = 0;
////                        try {
////
////                            //Log.i("imprimir","durmiendo 30ms");
////                            Thread.sleep(150);
////
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                            //Log.i("imprimir","error al dormir");
////                        }
//                        break;
//                    case 9:
//                        //Log.i("imprimir","es caso 5");
////                        csPrinter.printText(linea, 3);
////                        csPrinter.addTextToPrint(linea, null, 45, false, false, 0);
//                        CsPrinter.printText_FullParm(linea, 2,0, 1, 0, false, false);
//                        valor = 0;
//                        break;
//                    case 10:
//                        int idImagen = Integer.parseInt(linea);
////                        csPrinter.printBitmap(context.getResources().openRawResource(idImagen));
////                        csPrinter.addBitmapFromRawToPrint(context, idImagen);
//                        CsPrinter.printBitmap(context, BitmapFactory.decodeResource(context.getResources(), idImagen));
//                        valor = 0;
//                        break;
//                    case 11:
//                        //String rutaImagen = linea;
////                        csPrinter.printBitmap(linea);
//                        try {
//                            File archivo2 = new File(linea);
//                            Uri uri2 = Uri.fromFile(archivo2);
//                            InputStream inputStream2 = context.getContentResolver().openInputStream(uri2);
//
//                            BufferedInputStream bufferedInputStreamid22 = new BufferedInputStream(inputStream2);
//                            Bitmap bmpid22 = BitmapFactory.decodeStream(bufferedInputStreamid22);
////                            csPrinter.addBitmapToPrint(bmpid22);
//                            CsPrinter.printBitmap(context, bmpid22);
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }
//                        break;
//                    case 1200://CREATE QR
//                        try {
//                            csPrinter.createBarQrCode(linea, BarcodeFormat.QR_CODE, 384, 280);
//                        } catch (WriterException e) {
//                            e.printStackTrace();
//                        }
//                        valor = 0;
//                        break;
////                    case 1201:
////                        //Log.i("imprimir","es caso 0");
//////                        printer.printText(linea);
////                        csPrinter.addTextToPrint(linea, null, 25, false, false, 0);
////                        break;
//                }
//                //Log.i("imprimir","fin string");
//            } else if (objeto instanceof Integer) {
//                //Log.i("imprimir","es entero");
//                valor = (Integer) objeto;
//            }
//
//            //Log.i("imprimir","fin ifs con++");
////            cont++;
//
//            cont++;
//
//            if (cont == 12) {
////                csPrinter.print(context);
////                csPrinter=new CsPrinter();
//                cont = 0;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        CsPrinter.printEndLine();
//        CsPrinter.printEndLine();
//
////        csPrinter.print(context);
//
//        try {
//
//            LogUtils.i("imprimir","durmiendo 30ms");
//            Thread.sleep(500);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            LogUtils.i("imprimir","error al dormir");
//        }
//
//        new VerificadorImpresora().verificarImpresora(csPrinter);
//
//        estado = false;
//
//        if (avisoListener != null){
//            avisoListener.terminoDeImprimir();
//        }
//
//        notify();
//
//    }
//
//    public synchronized void imprimirFactura4(Vector<Object> imprimir) throws ImpresoraErrorException, NoHayPapelException, VoltageBajoException, ErrorPapelException {
//        while (estado) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        estado = true;
//
//        Integer valor = 0;
//
//        int cont = 0;
//
//        //un bucle para setear en el buffer de impresion los datso a imprimir
//        for (Object objeto : imprimir) {
//
//            try {
//                if (objeto instanceof String) {
//
//                    String linea = (String) objeto;
//                    switch (valor) {
//                        case -5:
//                            valor = 0;
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false, 2);
//                            valor = 0;
//                            break;
//                        case -4:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL_3,false, false, 0);
//                            valor = 0;
//                            break;
//                        case -3:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL_3,false, false, 1);
//                            valor = 0;
//                            break;
//                        case -2:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false, 1);
//                            valor = 0;
//                            break;
//                        case -1:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL_2,false, false);
//                            valor = 0;
//                            break;
//                        case 0:
//                            //impresion por defecto
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false);
//                            break;
//                        case 1:
//                            AidlUtil.getInstance().printText(linea,TAMANIO_NORMAL,false, false);
//
//                            valor = 0;
//
//                            break;
//                        case 2:
//                        case 3:
//                            //impresion de una linea por una imagen
//                            InputStream bm = context.getResources().openRawResource(R.raw.linea3);
//                            BufferedInputStream bufferedInputStream = new BufferedInputStream(bm);
//                            Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
//                            AidlUtil.getInstance().printText(this.linea,TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            break;
//                        case 4:
//                            AidlUtil.getInstance().sendRawData(new byte[]{0x1D, 0x42, 0x00,0x1B, 0x21, 0x00});
//                            AidlUtil.getInstance().printText(linea,TAMANIO_NORMAL,false, false);
//                            AidlUtil.getInstance().sendRawData(new byte[]{0x1D, 0x42, 0x00,0x1B, 0x21, 0x00});
//                            valor = 0;
//                            break;
//                        case 5:
//                            AidlUtil.getInstance().printText(linea,TAMANIO_GRANDE,false, false);
//                            valor = 0;
//                            break;
//                        case 6:
//                            // impresion de una segunda linea
//                            InputStream bm2 = context.getResources().openRawResource(R.raw.linea3);
//                            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(bm2);
//                            Bitmap bmp2 = BitmapFactory.decodeStream(bufferedInputStream2);
//
//                            AidlUtil.getInstance().printBitmap(bmp2);
//                            valor = 0;
//                            break;
//                        case 7:
//                            //impresion de imagen de pie de pagina
//                            InputStream bmpp = context.getResources().openRawResource(R.raw.pie_de_pagina_vendis);
//                            BufferedInputStream bufferedInputStreampp = new BufferedInputStream(bmpp);
//                            Bitmap bmppp = BitmapFactory.decodeStream(bufferedInputStreampp);
//
//                            AidlUtil.getInstance().printBitmap(bmppp);
//                            valor = 0;
//                            break;
//                        case 8:
//                            // impresion de imagen de la leyenda generica
//                            InputStream bmlg = context.getResources().openRawResource(R.raw.leyenda_generica);
//                            BufferedInputStream bufferedInputStreamlg = new BufferedInputStream(bmlg);
//                            Bitmap bmplg = BitmapFactory.decodeStream(bufferedInputStreamlg);
//                            AidlUtil.getInstance().printBitmap(bmplg);
//                            valor = 0;
//                            break;
//                        case 9:
//                            valor = 0;
//                            AidlUtil.getInstance().printText(linea,TAMANIO_GRANDE,false, false);
//                            break;
//
//                        case 10:
//                            int idImagen = Integer.parseInt(linea);
//
//                            InputStream bmid = context.getResources().openRawResource(idImagen);
//                            BufferedInputStream bufferedInputStreamid = new BufferedInputStream(bmid);
//                            Bitmap bmpid = BitmapFactory.decodeStream(bufferedInputStreamid);
//                            AidlUtil.getInstance().printBitmap(bmpid);
//                            valor = 0;
//                            break;
//                        case 11:
//                            File archivo = new File(linea);
//                            Uri uri = Uri.fromFile(archivo);
//                            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//
//                            BufferedInputStream bufferedInputStreamid2 = new BufferedInputStream(inputStream);
//                            Bitmap bmpid2 = BitmapFactory.decodeStream(bufferedInputStreamid2);
//                            AidlUtil.getInstance().printBitmap(bmpid2);
//                            valor = 0;
//                            break;
//                        case 100:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            break;
//                        case 101:
//                            valor =0;
//                            AidlUtil.getInstance().printText(linea,TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            break;
//                        case 102:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_GRANDE,false, false);
//                            valor = 0;
//                            break;
//                        case -102:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_GRANDE,true, false, 2);
//                            valor = 0;
//                            break;
//                        case 103:
//                            AidlUtil.getInstance().printText(linea,TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            valor = 0;
//                            break;
//                        case 104:
//                            AidlUtil.getInstance().sendRawData(new byte[]{0x1D, 0x42, 0x00,0x1B, 0x21, 0x00});
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false);
//                            AidlUtil.getInstance().sendRawData(new byte[]{0x1D, 0x42, 0x00,0x1B, 0x21, 0x00});
//                            valor = 0;
//                            break;
//                        case 110:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            break;
//                        case 111:
//                            AidlUtil.getInstance().printText(linea,TAMANIO_NORMAL,false, false);
//                            valor = 0;
//                            break;
//                        case 112:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_GRANDE,false, false);
//                            valor = 0;
//                            break;
//                        case 113:
//                            AidlUtil.getInstance().printText(linea + "\n",TAMANIO_GRANDE,false, false);
//                            valor = 0;
//                            break;
//                        case 200:
//                            AidlUtil.getInstance().printQr(linea, 4, 3);
//                            break;
//
//                    }
//
//                } else if (objeto instanceof Integer) {
//
//                    if (valor == 210) {
////                       iPrinter.spaceSet((byte)0,((Integer) objeto).byteValue());
//                        AidlUtil.getInstance().printNLine(1);
//                        valor = 0;
//                    }else{
//                        valor = (Integer) objeto;
//                    }
//
//                } else if (objeto instanceof InputStream) {
//                    InputStream bmob = (InputStream) objeto;
//                    BufferedInputStream bufferedInputStreamob = new BufferedInputStream(bmob);
//                    Bitmap bmpob = BitmapFactory.decodeStream(bufferedInputStreamob);
//                    AidlUtil.getInstance().printBitmap(bmpob);
//                    valor = 0;
//                } else if (objeto instanceof Bitmap){
//
//                    Bitmap bmpido = (Bitmap) objeto;
//                    AidlUtil.getInstance().printBitmap(bmpido);
//                    valor = 0;
//                }
//
//                cont++;
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//
//        }
//
//        AidlUtil.getInstance().print3Line();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        estado = false;
//
//        if (avisoListener != null){
//            avisoListener.terminoDeImprimir();
//        }
//        notify();
//
//    }
//}
