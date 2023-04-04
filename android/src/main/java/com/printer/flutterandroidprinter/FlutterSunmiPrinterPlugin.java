package com.printer.flutterandroidprinter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.AsyncTask;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;
import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import imprime.PrinterServiceZebraCpcl;
import imprime.ZebraCpclPrinter;
import io.github.escposjava.print.Printer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FlutterSunmiPrinterPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private static FlutterSunmiPrinterModule flutterSunmiPrinterModule;
  private static PrinterServiceZebraCpcl printerServiceZebraCpcl;

  private String RESET = "reset";
  private String START_PRINT = "startPrint";
  private String STOP_PRINT = "stopPrint";
  private String IS_PRINTING = "isPrinting";
  private String BOLD_ON = "boldOn";
  private String BOLD_OFF = "boldOff";
  private String UNDERLINE_ON = "underlineOn";
  private String UNDERLINE_OFF = "underlineOff";
  private String EMPTY_LINES = "emptyLines";
  private String PRINT_TEXT = "printText";
  private String PRINT_ROW = "printRow";
  private String PRINT_IMAGE = "printImage";
  private String PRINT_QR = "printQr";

  private String PRINT_ZEBRA = "printZebra";

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutterandroidprinter");
    channel.setMethodCallHandler(this);
    flutterSunmiPrinterModule = new FlutterSunmiPrinterModule();
    flutterSunmiPrinterModule.initAidl(flutterPluginBinding.getApplicationContext());

    Printer printer = new ZebraCpclPrinter(new BluetoothConnection(""));
    try {
      printerServiceZebraCpcl  = new PrinterServiceZebraCpcl(printer);
    } catch (Exception object) {
      System.out.println("Exception");
    }
  }

  // This static function is optional and equivalent to onAttachedToEngine. It
  // supports the old pre-Flutter-1.12 Android projects. You are encouraged to
  // continue supporting plugin registration via this function while apps migrate
  // to use the new Android APIs post-flutter-1.12 via
  // https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith
  // to keep them functionally equivalent. Only one of onAttachedToEngine or
  // registerWith will be called depending on the user's project.
  // onAttachedToEngine or registerWith must both be defined in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutterandroidprinter");
    channel.setMethodCallHandler(new FlutterSunmiPrinterPlugin());
    flutterSunmiPrinterModule = new FlutterSunmiPrinterModule();
    flutterSunmiPrinterModule.initAidl(registrar.context());

    Printer printer = new ZebraCpclPrinter(new BluetoothConnection(""));
    try {
      printerServiceZebraCpcl  = new PrinterServiceZebraCpcl(printer);
    } catch (Exception object) {
      System.out.println("Exception");
    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals(RESET)) {
      flutterSunmiPrinterModule.reset();
      result.success(null);
    } else if (call.method.equals(START_PRINT)) {
      flutterSunmiPrinterModule.startPrint();
      result.success(null);
    } else if (call.method.equals(STOP_PRINT)) {
      flutterSunmiPrinterModule.stopPrint();
      result.success(null);
    } else if (call.method.equals(IS_PRINTING)) {
      result.success(flutterSunmiPrinterModule.isPrinting());
    } else if (call.method.equals(BOLD_ON)) {
      flutterSunmiPrinterModule.boldOn();
      result.success(null);
    } else if (call.method.equals(BOLD_OFF)) {
      flutterSunmiPrinterModule.boldOff();
      result.success(null);
    } else if (call.method.equals(UNDERLINE_ON)) {
      flutterSunmiPrinterModule.underlineOn();
      result.success(null);
    } else if (call.method.equals(UNDERLINE_OFF)) {
      flutterSunmiPrinterModule.underlineOff();
      result.success(null);
    } else if (call.method.equals(PRINT_TEXT)) {
      String text = call.argument("text");
      int align = call.argument("align");
      boolean bold = call.argument("bold");
      boolean underline = call.argument("underline");
      int linesAfter = call.argument("linesAfter");
      int size = call.argument("size");
      flutterSunmiPrinterModule.text(text, align, bold, underline, size, linesAfter);
      result.success(null);
    } else if (call.method.equals(EMPTY_LINES)) {
      int n = call.argument("n");
      flutterSunmiPrinterModule.emptyLines(n);
      result.success(null);
    } else if (call.method.equals(PRINT_ROW)) {
      String cols = call.argument("cols");
      boolean bold = call.argument("bold");
      boolean underline = call.argument("underline");
      int textSize = call.argument("textSize");
      int linesAfter = call.argument("linesAfter");
      flutterSunmiPrinterModule.row(cols, bold, underline, textSize, linesAfter);
      result.success(null);
    } else if (call.method.equals(PRINT_IMAGE)) {
      String base64 = call.argument("base64");
      int align = call.argument("align");
      flutterSunmiPrinterModule.printImage(base64, align);
      result.success(null);
    }  else if (call.method.equals(PRINT_QR)) {
      String base64 = call.argument("text");
      int align = call.argument("align");
      int size = call.argument("size");
      String errorCorrectionLevel = call.argument("errorCorrectionLevel");

      flutterSunmiPrinterModule.printQr(base64, align, size, errorCorrectionLevel);
      result.success(null);
    }  else if (call.method.equals(PRINT_ZEBRA)) {
      AsyncTask.execute(new Runnable() {
        @Override
        public void run() {
          System.out.println("mac " + ((String) call.argument("mac")));
          String data = (String) call.argument("data");
//                        String data2 = "{\"texto\": \"prueba de texto\", \"valor\": 453, \"otrotexto\": \"otra prueba de texto\", \"lista\": [{\"1\": \"cadena1\"}, {\"1\": \"cadena2\"}, {\"1\": \"cadena3\"}]}";
//                        System.out.println("Son Iguales  " + data.equals(data2) + " " + data.length() + " " + data2.length());
//                        System.out.println("Son data  " + data);
//                        System.out.println("Son data  " + data2);
          //JSON Parser from Gson Library
          JsonParser parser = new JsonParser();
          //Creating JSONObject from String using parser
          JsonObject dataJSONObject = parser.parse(data).getAsJsonObject();
//                        String texto = dataJSONObject.get("texto").getAsString();
//                        System.out.println("texto  " + texto);
//                        int valor = dataJSONObject.get("valor").getAsInt();
//                        System.out.println("valor  " + texto);
          JsonArray array = dataJSONObject.getAsJsonArray("lines");

//                        for (int i = 0; i < array.size(); i++) {
//                            JsonElement element = array.get(i);
//
//                            JsonObject elemObject = element.getAsJsonObject();
//                            String elTexto = elemObject.get("1").getAsString();
//                            System.out.println("Element  " + elTexto);
//                        }

          try {

            Printer printer = new ZebraCpclPrinter(new BluetoothConnection((String) call.argument("mac")));
            System.out.println("printer" + printer == null);
            System.out.println("printer" + printer != null);
            printerServiceZebraCpcl  = new PrinterServiceZebraCpcl(printer);
            printerServiceZebraCpcl.setTextFont(1);
            printerServiceZebraCpcl.init();

            for (int i = 0; i < array.size(); i++) {
              JsonElement element = array.get(i);

              JsonObject elemObject = element.getAsJsonObject();

              String text = "";
              if (elemObject.has("line")) {
                text = elemObject.get("line").getAsString();
                printerServiceZebraCpcl.setTextSizeNormal();
                printerServiceZebraCpcl.setTextNormal();
                printerServiceZebraCpcl.printLn(text);
              } else if (elemObject.has("hr")) {
                text = elemObject.get("hr").getAsString();
                printerServiceZebraCpcl.printLn(text);
              } else if (elemObject.has("boldLine")) {

                text = elemObject.get("boldLine").getAsString();
                printerServiceZebraCpcl.setTextTypeBold();
                printerServiceZebraCpcl.printLn(text);
              } else if (elemObject.has("line2WH")) {
                text = elemObject.get("line2WH").getAsString();
                printerServiceZebraCpcl.setTextSize2H();
                printerServiceZebraCpcl.setTextSize2W();
                printerServiceZebraCpcl.printLn(text);
              } else if (elemObject.has("qrCode")) {
                text = elemObject.get("qrCode").getAsString();
                int margin = 150;
                if (elemObject.has("left")) {
                  String marginS = elemObject.get("left").getAsString();
                  margin = Integer.parseInt(marginS);
                }

                printerServiceZebraCpcl.printQRCode(text, margin, 0, context);
              } else if (elemObject.has("lineItems")) {
                text = elemObject.get("lineItems").getAsString();
                printerServiceZebraCpcl.printLn(text);
              } else if (elemObject.has("image")) {
                text = elemObject.get("image").getAsString();
                printerServiceZebraCpcl.printImage3(text);
              } else {
                System.out.println("elemente not found" + printer == null);
              }
              System.out.println("Element: " + text);
            }

//                            printerServiceZebraCpcl.printImage3("logo.bmp");

//                            printerServiceZebraCpcl.setTextAlignRight();
//                            printerServiceZebraCpcl.printLn("12345678901234567890123456789012345678901234567890");
//                            printerServiceZebraCpcl.setTextAlignCenter();
//                            printerServiceZebraCpcl.printLn(texto + " " + valor);
//                            printerServiceZebraCpcl.setTextAlignLeft();
//                            printerServiceZebraCpcl.printLn("1234567890123456789012345678901234567890123456789012345678901234567890");
//                            printerServiceZebraCpcl.printQRCode("https://www.emizor.com", context);
//                            printerServiceZebraCpcl.lineBreak();
//                            printerServiceZebraCpcl.cutPart();
            printerServiceZebraCpcl.initPrint();
            if (printerServiceZebraCpcl != null)
              printerServiceZebraCpcl.close();
            System.out.println("printerServiceZebraCpcl" + printer == null);
          } catch (Exception object) {
            System.out.println("Exceptionnn");
            object.printStackTrace();
          }
        }
      });
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
