package io.github.escposjava.print;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;

import utils.LogUtils;


public class NetworkPrinter implements Printer {
   private OutputStream printer = null;
   private final String address;
   private final int port;

   public NetworkPrinter(String address, int port){
      this.address = address;
      this.port = port;
   }

   public void open()throws IOException {
      try {
         Socket socket = new Socket(this.address, this.port);
         socket.setSoTimeout(1000);
         printer = socket.getOutputStream();

         //socket.getInputStream()
      } catch (NoRouteToHostException ex){
         printer = null;
         ex.printStackTrace();
         throw ex;
      } catch (IOException e) {
         printer = null;
         e.printStackTrace();
         throw e;
      }
   }

   public void write(byte[] command){
      try {
         if (printer != null)
            printer.write(command);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   public void close(){
      try {
         printer.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
