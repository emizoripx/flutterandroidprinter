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
import android.net.Uri;
import android.os.Build;

import com.printer.flutterandroidprinter.utils.AidlUtil;
import com.printer.flutterandroidprinter.utils.Base64Utils;
import com.printer.flutterandroidprinter.utils.BitmapUtil;
import com.printer.flutterandroidprinter.utils.ESCUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlutterSunmiPrinterModule {

  private static boolean isPrinting = false;
  public static int DEFAULT_FONT_SIZE = 24;
  private Context context;

  public void initAidl(Context context) {
    AidlUtil.getInstance().connectPrinterService(context);
    AidlUtil.getInstance().initPrinter();
    this.context = context;
  }

  public void reset() {
    AidlUtil.getInstance().initPrinter();
  }

  public void startPrint() {
    isPrinting = true;
  }

  public void stopPrint() {
    isPrinting = false;
  }

  public boolean isPrinting() {
    return isPrinting;
  }

  public void boldOn() {
    AidlUtil.getInstance().sendRawData(ESCUtil.boldOn());
  }

  public void boldOff() {
    AidlUtil.getInstance().sendRawData(ESCUtil.boldOff());
  }

  public void underlineOn() {
    AidlUtil.getInstance().sendRawData(ESCUtil.underlineWithOneDotWidthOn());
  }

  public void underlineOff() {
    AidlUtil.getInstance().sendRawData(ESCUtil.underlineOff());
  }

  public void emptyLines(int n) {
    AidlUtil.getInstance().lineWrap(n);
  }

  public void setFontSize(int size) {
    AidlUtil.getInstance().setFontSize(size);
  }

  public void text(String text, int align, boolean bold, boolean underline, int size, int linesAfter) {
    // Set styles
    if (bold) {
      boldOn();
    }
    if (underline) {
      underlineOn();
    }

    // Print text
    setFontSize(size);
    AidlUtil.getInstance().printTableItem(new String[] { text }, new int[] { 32 }, new int[] { align });
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
      AidlUtil.getInstance().printTableItem(colsText, colsWidth, colsAlign);
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
    AidlUtil.getInstance().printBitmap(BitmapUtil.generateBitmap(text,9,size,size, errorCorrectionLevel), align);
  }
}
