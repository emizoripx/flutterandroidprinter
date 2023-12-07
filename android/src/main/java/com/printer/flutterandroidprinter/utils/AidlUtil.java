package com.printer.flutterandroidprinter.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.printer.flutterandroidprinter.entities.TableItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.api.LineApi;
import com.sunmi.printerx.api.PrintResult;
import com.sunmi.printerx.enums.Align;
import com.sunmi.printerx.enums.DividingLine;
import com.sunmi.printerx.enums.ErrorLevel;
import com.sunmi.printerx.enums.HumanReadable;
import com.sunmi.printerx.enums.ImageAlgorithm;
import com.sunmi.printerx.style.BarcodeStyle;
import com.sunmi.printerx.style.BaseStyle;
import com.sunmi.printerx.style.BitmapStyle;
import com.sunmi.printerx.style.QrStyle;
import com.sunmi.printerx.style.TextStyle;
import com.sunmi.printerx.PrinterSdk;


public class AidlUtil {

    private static AidlUtil mAidlUtil = new AidlUtil();
    private static final int LINE_BYTE_SIZE = 32;
    private Context context;
    private static LineApi lineApi;
    private int sizeFont;

    private AidlUtil() {
    }

    public static AidlUtil getInstance() {
        return mAidlUtil;
    }

    public List<String> getPrinterInfo(PrinterCallback printerCallback1, PrinterCallback printerCallback2) {
//        if (woyouService == null) {
//            return null;
//        }

        List<String> info = new ArrayList<>();
//        PackageManager packageManager = context.getPackageManager();
//        try {
//            woyouService.getPrintedLength(generateCB(printerCallback1));
//            woyouService.getPrinterFactory(generateCB(printerCallback2));
//            info.add(woyouService.getPrinterSerialNo());
//            info.add(woyouService.getPrinterModal());
//            info.add(woyouService.getPrinterVersion());
//            PackageInfo packageInfo = packageManager.getPackageInfo(SERVICE＿PACKAGE, 0);
//            if (packageInfo != null) {
//                info.add(packageInfo.versionName);
//                info.add(packageInfo.versionCode + "");
//            } else {
//                info.add("");
//                info.add("");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return info;
    }

    /**
     * Metodo para iniciar la impresion, siempre se debe ejecutar este metodo para inciar la impresion
     * @param lineApi Parametro necesario para la impresion
     */
    public void initPrinter(LineApi lineApi) {
        AidlUtil.lineApi = lineApi;
//        if (woyouService == null) {
//            return;
//        }
//
//        try {
//            woyouService.printerInit(null);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Metodo para finalizar la impresion, siempre se debe ejecutar este metodo para terminar la impresion.
     */
    public void endPrinter(){
        this.lineApi.autoOut();
    }

    public void setFontSize(int sizeFont) {
        this.sizeFont = sizeFont;
//        try {
//            woyouService.setFontSize(size, null);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }


    public void printQr(String data, int modulesize, int errorlevel) {
        if (lineApi == null) {
            return;
        }
        String levelError = "M";
        switch (errorlevel){
            case 0:
                levelError = "L";
                break;
            case 1:
                levelError = "M";
                break;
            case 2:
                levelError = "Q";
                break;
            case 3:
                levelError = "H";
                break;
        }

        try {
            lineApi.printQrCode(data, QrStyle.getStyle().setAlign(Align.CENTER).setWidth(modulesize).setHeight(modulesize).setErrorLevel(ErrorLevel.valueOf(levelError)));
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (lineApi == null) {
            return;
        }
        String position = "HIDE";
        switch (textposition){
            case 1:
                position = "POS_ONE";
                break;
            case 2:
                position = "POS_TWO";
                break;
            case 3:
                position = "POS_THREE";
                break;
        }
        try {
            BarcodeStyle barcodeStyle = BarcodeStyle.getStyle()
                    .setAlign(Align.CENTER)
                    .setHeight(height)
                    .setWidth(width)
                    .setDotWidth(2)
                    .setSymbology(Symbology.valueOf(symbology))
                    .setReadable(HumanReadable.valueOf(position));
            lineApi.printBarCode(data, barcodeStyle);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        if (lineApi == null) {
            return;
        }
        try {
            TextStyle textStyle = TextStyle.getStyle();
            if (isBold) {
                textStyle = textStyle.enableBold(true);
//                        woyouService.sendRAWData(ESCUtil.boldOn(), null);
            } else {
                textStyle = textStyle.enableBold(false);
            }

            if (isUnderLine) {
                textStyle = textStyle.enableUnderline(true);
//                woyouService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
            } else {
                textStyle = textStyle.enableUnderline(false);
//                woyouService.sendRAWData(ESCUtil.underlineOff(), null);
            }
            textStyle = textStyle.setTextSize(size);
            lineApi.printText(content, textStyle);
//            woyouService.printTextWithFont(content, null, size, null);
            // woyouService.lineWrap(3, null);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void printBitmap(Bitmap bitmap, int align) {
        if (lineApi == null) {
            return;
        }

        try {
            String position = "DEFAULT";
            switch (align){
                case 1:
                    position = "LEFT";
                    break;
                case 2:
                    position = "CENTER";
                    break;
                case 3:
                    position = "RIGHT";
                    break;
            }
            lineApi.printBitmap(bitmap, BitmapStyle.getStyle().setAlign(Align.valueOf(position)).setAlgorithm(ImageAlgorithm.DITHERING).setWidth(384).setHeight(150));
//            woyouService.setAlignment(align, null);
//            woyouService.printBitmap(bitmap, null);
//            woyouService.lineWrap(1, null);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void lineWrap(int line) {
        if (lineApi == null) {
            return;
        }
        try {
//            woyouService.lineWrap(line, null);
            lineApi.printDividingLine(DividingLine.EMPTY, line);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void printTable(LinkedList<TableItem> list) {
        if (woyouService == null) {
            return;
        }
        try {
            for (TableItem tableItem : list) {
//                woyouService.printColumnsString(tableItem.getText(), tableItem.getWidth(), tableItem.getAlign(), null);
                TextStyle[] styles = new TextStyle[tableItem.getAlign().length];
                for (int indice= 0; indice < tableItem.getAlign().length; indice ++){
                    int alignData = tableItem.getAlign()[indice];
                    String position = "DEFAULT";
                    switch (alignData){
                        case 1:
                            position = "LEFT";
                            break;
                        case 2:
                            position = "CENTER";
                            break;
                        case 3:
                            position = "RIGHT";
                            break;
                    }

                    styles[indice] = TextStyle.getStyle().setAlign(Align.valueOf(position));
                }

                lineApi.printTexts(tableItem.getText(), tableItem.getWidth(), styles);

            }
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void printTableItem(String[] text, int[] width, int[] align) {
        if (lineApi == null) {
            return;
        }
        try {
            TextStyle[] styles = new TextStyle[align.length];
            for (int indice= 0; indice < align.length; indice ++){
                int alignData = align[indice];
                String position = "DEFAULT";
                switch (alignData){
                    case 1:
                        position = "LEFT";
                        break;
                    case 2:
                        position = "CENTER";
                        break;
                    case 3:
                        position = "RIGHT";
                        break;
                }

                styles[indice] = TextStyle.getStyle().setAlign(Align.valueOf(position));
            }

            lineApi.printTexts(text, width, styles);
//            woyouService.printColumnsString(text, width, align, null);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void print3Line() {
        if (lineApi == null) {
            return;
        }

        try {
            lineApi.addText("\n", TextStyle.getStyle());
            lineApi.addText("\n", TextStyle.getStyle());
            lineApi.addText("\n", TextStyle.getStyle());
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

    public void sendRawData(byte[] data, PrinterSdk.Printer selectPrinter) {
        if (selectPrinter == null) {
            return;
        }

        try {
            selectPrinter.commandApi().sendEscCommand(data);
//            woyouService.sendRAWData(data, null);
        } catch (SdkException e) {
            e.printStackTrace();
        }
    }

//    public void sendRawDatabyBuffer(byte[] data, ICallback iCallback) {
//        if (woyouService == null) {
//            return;
//        }
//
//        try {
//            woyouService.enterPrinterBuffer(true);
//            woyouService.sendRAWData(data, iCallback);
//            woyouService.exitPrinterBufferWithCallback(true, iCallback);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
}
