package io.github.escposjava.print;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;

import utils.LogUtils;

public class UsbPrinter implements Printer {
   private final String TAG = UsbPrinter.class.getName();

   private UsbDeviceConnection connection;
   private UsbInterface intrface;
   private UsbEndpoint mEndPoint;
   private Boolean forceClaim;

   public UsbPrinter(UsbDeviceConnection connection, UsbInterface intrface, UsbEndpoint mEndPoint, Boolean forceClaim){
      this.connection = connection;
      this.intrface = intrface;
      this.mEndPoint = mEndPoint;
      this.forceClaim = forceClaim;
   }

   public void open(){

   }

   public void write(byte[] command){
      try {
         connection.claimInterface(intrface, forceClaim);
         int resp = connection.bulkTransfer(mEndPoint, command, command.length, 0);
         LogUtils.i(TAG, " usb resp conect " + resp);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   public void close(){
   }

}
