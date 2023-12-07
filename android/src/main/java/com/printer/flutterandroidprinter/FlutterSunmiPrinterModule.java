package com.printer.flutterandroidprinter;

import android.content.Context;
import android.util.Log;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.List;
import android.net.Uri;
import android.os.Build;
import java.lang.reflect.Method;
import android.annotation.SuppressLint;

import com.printer.flutterandroidprinter.utils.AidlUtil;
import com.printer.flutterandroidprinter.utils.Base64Utils;
import com.printer.flutterandroidprinter.utils.BitmapUtil;
import com.printer.flutterandroidprinter.utils.ESCUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sunmi.printerx.PrinterSdk;
import com.sunmi.printerx.SdkException;

public class FlutterSunmiPrinterModule {

  private static boolean isPrinting = false;
  public static int DEFAULT_FONT_SIZE = 24;
  private Context context;

  private PrinterSdk.Printer selectPrinter;

  public void initAidl(Context context) {
    this.context = context;
    initPrinter(context);
  }

  private void initPrinter(Context context) {
    try {
      PrinterSdk.getInstance().getPrinter(context, new PrinterSdk.PrinterListen() {
        @Override
        public void onDefPrinter(PrinterSdk.Printer printer) {
          selectPrinter = printer;
        }

        @Override
        public void onPrinters(List<PrinterSdk.Printer> printers) {

        }
      });
    } catch (SdkException e) {
      e.printStackTrace();
    }
  }

  public void reset() {
    initPrinter(context);
  }

  public void startPrint() {
    isPrinting = true;
    try {
      if (selectPrinter != null) {
        AidlUtil.getInstance().initPrinter(selectPrinter.lineApi());
      }
    } catch (SdkException e) {
      e.printStackTrace();
    }
  }

  public void stopPrint() {
    isPrinting = false;
    AidlUtil.getInstance().endPrinter();
  }

  public boolean isPrinting() {
    return isPrinting;
  }

  public void boldOn() {
    AidlUtil.getInstance().sendRawData(ESCUtil.boldOn(), selectPrinter);
  }

  public void boldOff() {
    AidlUtil.getInstance().sendRawData(ESCUtil.boldOff(), selectPrinter);
  }

  public void underlineOn() {
    AidlUtil.getInstance().sendRawData(ESCUtil.underlineWithOneDotWidthOn(), selectPrinter);
  }

  public void underlineOff() {
    AidlUtil.getInstance().sendRawData(ESCUtil.underlineOff(), selectPrinter);
  }

  public void emptyLines(int n) {
    AidlUtil.getInstance().lineWrap(n);
  }

  public void setFontSize(int size) {
    AidlUtil.getInstance().setFontSize(size);
  }

  public void text(String text, int align, boolean bold, boolean underline, int size, int linesAfter) {
//    // Set styles
//    if (bold) {
//      boldOn();
//    }
//    if (underline) {
//      underlineOn();
//    }

    // Print text
//    setFontSize(size);
//    AidlUtil.getInstance().printTableItem(new String[] { text }, new int[] { 32 }, new int[] { align });

    switch (align){
      case 0:
        AidlUtil.getInstance().sendRawData(ESCUtil.alignLeft(), selectPrinter);
        break;
      case 1:
        AidlUtil.getInstance().sendRawData(ESCUtil.alignCenter(), selectPrinter);
        break;
      case 2:
        AidlUtil.getInstance().sendRawData(ESCUtil.alignRight(), selectPrinter);
        break;
    };
    AidlUtil.getInstance().printText(text, size, bold, underline, align);
    if (linesAfter > 0) {
      emptyLines(linesAfter);
    }
    AidlUtil.getInstance().sendRawData(ESCUtil.alignLeft(), selectPrinter);
//    setFontSize(DEFAULT_FONT_SIZE);

    // Reset styles
//    if (bold) {
//      boldOff();
//    }
//    if (underline) {
//      underlineOff();
//    }
  }

  public void row(String colsStr, boolean bold, boolean underline, int textSize, int linesAfter) {
    try {
      // Set styles
      if (bold) {
        boldOn();
      }
      if (underline) {
        underlineOn();
      }

      // Prepare row data
      JSONArray cols = new JSONArray(colsStr);
      String[] colsText = new String[cols.length()];
      int[] colsWidth = new int[cols.length()];
      int[] colsAlign = new int[cols.length()];
      for (int i = 0; i < cols.length(); i++) {
        JSONObject col = cols.getJSONObject(i);
        String text = col.getString("text");
        int width = col.getInt("width");
        int align = col.getInt("align");
        colsText[i] = text;
        colsWidth[i] = width;
        colsAlign[i] = align;
      }

      // Print row
      setFontSize(textSize);
      AidlUtil.getInstance().printTableItem(colsText, colsWidth, colsAlign, bold, underline);
      if (linesAfter > 0) {
        emptyLines(linesAfter);
      }
      setFontSize(DEFAULT_FONT_SIZE);

      // Reset styles
      if (bold) {
        boldOff();
      }
      if (underline) {
        underlineOff();
      }
    } catch (Exception err) {
      Log.d("SunmiPrinter", err.getMessage());
    }
  }

  public void printImage(String base64, int align) {
    byte[] bytes = Base64Utils.decode(base64);
    for (int i = 0; i < bytes.length; ++i) {
      // ajust data
      if (bytes[i] < 0) {
        bytes[i] += 256;
      }
    }
    AidlUtil.getInstance().printBitmap(BitmapUtil.convertToThumb(bytes, 280), align);
    // AidlUtil.getInstance().lineWrap(1);
  }

  public void printQr(String text, int align, int size, String errorCorrectionLevel) {
//    AidlUtil.getInstance().printBitmap(BitmapUtil.generateBitmap(text,9,size,size, errorCorrectionLevel), align);
    AidlUtil.getInstance().printQr(text, size, errorCorrectionLevel, align);
  }

  @SuppressLint({"MissingPermission", "PrivateApi"})
  public static String getSN() {
    String serial = null;
    Class<?> c = null;
    try {
      c = Class.forName("android.os.SystemProperties");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    Method get = null;
    try {
      get = c.getMethod("get", String.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (Build.VERSION.SDK_INT >= 30) {
      try {
        serial = (String)get.invoke(c, "ro.sunmi.serial");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return serial;
    } else if (Build.VERSION.SDK_INT >= 26) {
      serial = Build.getSerial();
      return serial;
    } else {
      //安卓8以下使用Build.SERIAL相同方式
      //return Build.SERIAL;
      try {
        serial = (String) get.invoke(c, "ro.serialno");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return serial;
    }
  }
}
