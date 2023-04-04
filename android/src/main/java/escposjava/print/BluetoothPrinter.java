package io.github.escposjava.print;

import java.io.IOException;
import java.io.OutputStream;

public class BluetoothPrinter implements Printer {
   private final String TAG = BluetoothPrinter.class.getName();

   private OutputStream printer = null;
//   private final String mDeviceAddress;
//   private UUID applicationUUID;

//   private BluetoothSocket mBluetoothSocket;
//   private BluetoothDevice mBluetoothDevice;
//   private BluetoothAdapter mBluetoothAdapter;

   public BluetoothPrinter(OutputStream printer){
      this.printer = printer;
   }

   public void open(){
      /*try {
         //
         Socket socket = new Socket(this.address, this.port);
         socket.setSoTimeout(1000);
         printer = socket.getOutputStream();
         //socket.getInputStream()
      } catch (IOException e) {
         e.printStackTrace();
      }*/
//      try {
//         printer = mBluetoothSocket.getOutputStream();
//      } catch (IOException eConnectException) {
//         LogUtils.d(TAG, "CouldNotConnectToSocket", eConnectException);
//         closeSocket(mBluetoothSocket);
//         return;
//      }
   }

//   private void closeSocket(BluetoothSocket nOpenSocket) {
//      try {
//         nOpenSocket.close();
//         Log.d(TAG, "SocketClosed");
//      } catch (IOException ex) {
//         Log.d(TAG, "CouldNotCloseSocket");
//      }
//   }

   public void write(byte[] command) {
      try {
         printer.write(command);
      } catch (IOException e) {
         // TODO Auto-generated catch block
//         e.printStackTrace();
      }
   }

   public void close(){
//      try {
//         printer.close();
//
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//
//      try {
//         mBluetoothSocket.close();
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
   }

}
