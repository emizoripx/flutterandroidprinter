package utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

//import bo.com.vendis.pos.BuildConfig;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2015/7/10.
 */
public class LogUtils {

    private static boolean mDebug = obtenerAcceso();

    private LogUtils()
    {
        /* cannot be instantiated */
        //throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static synchronized boolean obtenerAcceso() {
        boolean estadoResp = false;
        try {
            Properties properties = new Properties();
            String direccionProp = Environment.getExternalStorageDirectory() + "/Download/";
            File archivo = new File(direccionProp, "activarqe3Apzzt45__23.properties");

            if (archivo.exists()) {

                properties.load(new FileInputStream(archivo));
                if (properties.containsKey("logcat")) {
                    estadoResp = properties.getProperty("logcat").equals("true");

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            estadoResp = false;
        }

//        if (BuildConfig.DEBUG){
//            estadoResp = true;
//        }
        return estadoResp;
    }

    public static void setDebug(boolean debug){
        mDebug = debug;
    }

    public static void resetDebug(){
        setDebug(obtenerAcceso());
    }

    public static synchronized void v(final String tag, String msg){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            if (msg == null){
                msg = "";
            }
//            Log.v(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.v(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.v(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }

            }else{
                Log.v(tag, "- " + msg);
            }
//                    Log.v(tag, msg);

//                }
//            }).start();
        }
    }
    public static synchronized void d(final String tag, String msg){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.d(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.d(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.d(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }

            }else{
                Log.d(tag, "- " + msg);
            }
//            Log.d(tag, "CADENA FIN ??? ");
//                    Log.d(tag, msg);
//                }
//                }
//            }).start();
        }
    }
    public static synchronized void i(final String tag, String msg){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.i(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.i(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.i(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }

            }else{
                Log.i(tag, "- " + msg);
            }
//            Log.i(tag, "CADENA FIN ??? ");
//                    Log.i(tag, msg);
//                }
//                }
//            }).start();
        }
    }
    public static synchronized void w(final String tag, String msg){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.w(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.w(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.w(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }

            }else{
                Log.w(tag, "- " + msg);
            }
//            Log.w(tag, "CADENA FIN ??? ");
//                    Log.w(tag, msg);
//                }
//                }
//            }).start();
        }
    }
    public static synchronized void e(final String tag, String msg){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.e(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.e(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.e(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }

            }else{
                Log.e(tag, "- " + msg);
            }
//            Log.e(tag, "CADENA FIN ??? ");
//                    Log.e(tag, msg);
//                }
//                }
//            }).start();
        }
    }

    public static synchronized void v(final String tag, String msg, final Throwable trw){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.v(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.v(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.v(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }
                Log.v(tag, "- ",trw);

            }else{
                Log.v(tag, "- " + msg,trw);
            }
//            Log.v(tag, "CADENA FIN ??? ");
//                    Log.v(tag, msg, trw);
//                }
//                }
//            }).start();
        }
    }
    public static synchronized void d(final String tag, String msg, final Throwable trw){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                if(mDebug){
            if (msg == null){
                msg = "";
            }
//            Log.d(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.d(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.d(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }
                Log.d(tag, "- ",trw);

            }else{
                Log.d(tag, "- " + msg,trw);
            }
//            Log.d(tag, "CADENA FIN ??? ");
//                    Log.d(tag, msg, trw);
//                }
//                }
//            }).start();
        }
    }
    public static synchronized void i(final String tag, String msg, final Throwable trw){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            if (msg == null){
                msg = "";
            }
//            Log.i(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.i(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.i(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }
                Log.i(tag, "- ",trw);

            }else{
                Log.i(tag, "- " + msg,trw);
            }
//            Log.i(tag, "CADENA FIN ??? ");
//                    Log.i(tag, msg, trw);

//                }
//            }).start();
        }
    }
    public static synchronized void w(final String tag, String msg, final Throwable trw){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            if (msg == null){
                msg = "";
            }
//            Log.w(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.w(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.w(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }
                Log.w(tag, "- ",trw);

            }else{
                Log.w(tag, "- " + msg,trw);
            }
//            Log.w(tag, "CADENA FIN ??? ");
//                    Log.w(tag, msg, trw);

//                }
//            }).start();
        }
    }
    public static synchronized void e(final String tag, String msg, final Throwable trw){
        if(mDebug) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            if (msg == null){
                msg = "";
            }
//            Log.e(tag, "CADENA INI ??? ");
            if (msg.length() > 1500){
                int tamanioCorte = 1500;

                int tamanioFinal = msg.length();

                for (int i = 0; i< tamanioFinal; i+= tamanioCorte){

                    if ((i + tamanioCorte)> tamanioFinal){
                        Log.e(tag, "- " + msg.substring(i, tamanioFinal));
                    }else{
                        Log.e(tag, "- " + msg.substring(i, (i + tamanioCorte)));
                    }

                }
                Log.e(tag, "- ",trw);

            }else{
                Log.e(tag, "- " + msg,trw);
            }
//            Log.e(tag, "CADENA FIN ??? ");
//            Log.e(tag, msg, trw);

//                }
//            }).start();
        }
    }
//    private static synchronized void imprimirCadenaPorConsola(String cadena){
//        Log.i(TAG, "CADENA INI ??? ");
//        if (cadena.length() > 1500){
//            int tamanioCorte = 1500;
//
//            int tamanioFinal = cadena.length();
//
//            for (int i = 0; i< tamanioFinal; i+= tamanioCorte){
//
//                if ((i + tamanioCorte)> tamanioFinal){
//                    Log.i(TAG, "- " + cadena.substring(i, tamanioFinal));
//                }else{
//                    Log.i(TAG, "- " + cadena.substring(i, (i + tamanioCorte)));
//                }
//
//            }
//
//
//        }else{
//            Log.i(TAG, "- " + cadena);
//        }
//        Log.i(TAG, "CADENA FIN ??? ");
//    }

}
