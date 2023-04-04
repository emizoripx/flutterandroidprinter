package io.github.escposjava.print;

import com.zebra.sdk.comm.ConnectionException;

import java.io.IOException;
import java.net.NoRouteToHostException;

public interface Printer {
   void  open() throws NoRouteToHostException, IOException, ConnectionException;

   void write(byte[] command);

   void close();
}
