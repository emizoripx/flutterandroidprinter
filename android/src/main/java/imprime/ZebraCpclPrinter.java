package imprime;
import android.graphics.Bitmap;

import com.zebra.android.printer.internal.ZebraPrinterLegacyDelegator;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.internal.ZebraPrinterCpcl;

import io.github.escposjava.print.Printer;
import utils.LogUtils;

public class ZebraCpclPrinter implements Printer {

    private final String TAG = ZebraCpclPrinter.class.getName();

    Connection printerConnection = null;
    ZebraPrinter myPrinter;
//   private final String mDeviceAddress;
//   private UUID applicationUUID;

//   private BluetoothSocket mBluetoothSocket;
//   private BluetoothDevice mBluetoothDevice;
//   private BluetoothAdapter mBluetoothAdapter;

    public ZebraCpclPrinter(Connection printerConnection){
        this.printerConnection = printerConnection;
    }

    public ZebraPrinter getMyPrinter() {
        return myPrinter;
    }

    public void open() throws ConnectionException {
        LogUtils.i(TAG, "ZebraCpclPrinter open");
        try {
            printerConnection.open();
            LogUtils.i(TAG, "ZebraCpclPrinter openned");
            Thread.sleep(100);
//            printerConnection.write("! U1 BEGIN-PAGE\r\n".getBytes());
//            printerConnection.write("! U1 END-PAGE\r\n".getBytes());
//            Thread.sleep(100);
////            if(!printerConnection.isConnected())
//            {
//                LogUtils.i(TAG, "ZebraCpclPrinter isconnected");
//                try {
//                    myPrinter = ZebraPrinterFactory.getInstance(printerConnection);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i(TAG, "ZebraCpclPrinter open end");
    }

    public void imprimiimagen(Bitmap bitmap){
        LogUtils.i(TAG, "ZebraCpclPrinter openned");
        LogUtils.i(TAG, "ZebraCpclPrinter isconnected");
        try {
            LogUtils.i(TAG, "ZebraCpclPrinter send command");
            printerConnection.write("! UTILITIES\r\nIN-MILLIMETERS\r\nSETFF 0 0\r\nPRINT\r\n".getBytes());
            printerConnection.write("! U1 setvar \"device.languages\" \"zpl\"\r\n".getBytes());
            ZebraPrinter myPrinter2 = new ZebraPrinterCpcl(printerConnection, PrinterLanguage.CPCL);
            new ZebraPrinterLegacyDelegator(myPrinter2).getGraphicsUtil().printImage(bitmap, 80, 0, 0, -1,       false);
            printerConnection.write("! U1 setvar \"device.languages\" \"line_print\"\r\n".getBytes());
            printerConnection.write(("! 0 200 200 2 1\r\n" + "PRINT\r\n").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimiimagen(String archivo){
        LogUtils.i(TAG, "ZebraCpclPrinter openned");
        LogUtils.i(TAG, "ZebraCpclPrinter isconnected");
        try {
            LogUtils.i(TAG, "ZebraCpclPrinter send command");
            printerConnection.write("! UTILITIES\r\nIN-MILLIMETERS\r\nSETFF 0 0\r\nPRINT\r\n".getBytes());
            printerConnection.write("! U1 setvar \"device.languages\" \"zpl\"\r\n".getBytes());
            int x = 0;
            int y = 0;
            ZebraPrinter myPrinter2 = new ZebraPrinterCpcl(printerConnection, PrinterLanguage.CPCL);
            myPrinter.printImage(archivo, x, y);
            printerConnection.write("! UTILITIES\r\nIN-MILLIMETERS\r\nSETFF 0 0\r\nPRINT\r\n".getBytes());
            printerConnection.write("! U1 setvar \"device.languages\" \"line_print\"\r\n".getBytes());
            printerConnection.write("! 0 200 200 0 1\r\n".getBytes());
            printerConnection.write("PRINT\r\n".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] command){
        try {
            printerConnection.write(command);
        } catch (ConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            printerConnection.close();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }
}
