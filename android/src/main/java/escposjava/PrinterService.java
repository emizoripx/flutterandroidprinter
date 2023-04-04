package io.github.escposjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zebra.sdk.comm.ConnectionException;

import io.github.escposjava.print.Printer;
import io.github.escposjava.print.exceptions.BarcodeSizeError;
import io.github.escposjava.print.exceptions.QRCodeException;
import io.github.escposjava.print.image.Image;
import utils.PrefUtils;
import utils.QRCodeHelper;

//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static io.github.escposjava.print.Commands.*;
public class PrinterService {

    private static final String CARRIAGE_RETURN = System.getProperty("line.separator");
    protected String charsetText;

    // interface printer que contiene la conexion con la impresora(wifi, usb o bluetooth)
    protected final Printer printer;

    public PrinterService(Printer printer) throws IOException, ConnectionException {
        this.printer = printer;
        charsetText = "ISO-8859-15";//por defecto LATIN 3 para la impresion
        open();
    }

    public void setCharsetText(String charsetText) {
        this.charsetText = charsetText;
        if (charsetText.contains("(")) {
            this.charsetText = charsetText.substring(0, charsetText.indexOf("("));
        }
    }

    public void print(String text) {
//      try {
//         write((new String(text.getBytes("ISO-8859-1"), "ISO-8859-15")).getBytes());
//      } catch (UnsupportedEncodingException e) {
//
//         e.printStackTrace();
//      }

        try {
            write(text.getBytes(charsetText));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void printLn(String text) {
        print(text + CARRIAGE_RETURN);
    }

    public void lineBreak() {
        lineBreak(1);
    }

    public void lineBreak(int nbLine) {
        for (int i=0;i<nbLine;i++) {
            write(CTL_LF);
        }
    }

    public void printQRCode(String value, Context context) throws QRCodeException {
        printQRCode(value, 300, context);
    }

    public void printQRCode(String value, int size, Context context) throws QRCodeException {
//      QRCodeGenerator q = new QRCodeGenerator();

        printImage(QRCodeHelper.newInstance(context).setContent(value).setErrorCorrectionLevel(ErrorCorrectionLevel.L).setTamanio(size,size).getQRCOde());
    }

    public void setTextSizeNormal(){
        setTextSize(1,1);
    }

    public void setTextSize2H(){
        setTextSize(1,2);
    }

    public void setTextSize2W(){
        setTextSize(2,1);
    }

    public void setText4Square(){
        setTextSize(2,2);
    }

    private void setTextSize(int width, int height){
        if (height == 2 && width == 2) {
            write(TXT_NORMAL);
            write(TXT_4SQUARE);
        }else if(height == 2) {
            write(TXT_NORMAL);
            write(TXT_2HEIGHT);
        }else if(width == 2){
            write(TXT_NORMAL);
            write(TXT_2WIDTH);
        }else{
            write(TXT_NORMAL);
        }
    }

    public void setTextTypeBold(){
        setTextType("B");
    }

    public void setTextTypeUnderline(){
        setTextType("U");
    }

    public void setTextType2Underline(){
        setTextType("U2");
    }

    public void setTextTypeBoldUnderline(){
        setTextType("BU");
    }

    public void setTextTypeBold2Underline(){
        setTextType("BU2");
    }

    public void setTextTypeNormal(){
        setTextType("NORMAL");
    }

    private void setTextType(String type){
        if (type.equalsIgnoreCase("B")){
            write(TXT_BOLD_ON);
            write(TXT_UNDERL_OFF);
        }else if(type.equalsIgnoreCase("U")){
            write(TXT_BOLD_OFF);
            write(TXT_UNDERL_ON);
        }else if(type.equalsIgnoreCase("U2")){
            write(TXT_BOLD_OFF);
            write(TXT_UNDERL2_ON);
        }else if(type.equalsIgnoreCase("BU")){
            write(TXT_BOLD_ON);
            write(TXT_UNDERL_ON);
        }else if(type.equalsIgnoreCase("BU2")){
            write(TXT_BOLD_ON);
            write(TXT_UNDERL2_ON);
        }else if(type.equalsIgnoreCase("NORMAL")){
            write(TXT_BOLD_OFF);
            write(TXT_UNDERL_OFF);
        }
    }

    public void cutPart(){
        cut("PART");
    }

    public void cutFull(){
        cut("FULL");
    }

    private void cut(String mode){
        for (int i = 0; i < 5; i++){
            write(CTL_LF);
        }
        if (mode.toUpperCase().equals("PART")){
            write(PAPER_PART_CUT);
        }else{
            write(PAPER_FULL_CUT);
        }
    }

    public void printBarcode(String code, String bc, int width, int height, String pos, String font) throws BarcodeSizeError {
        // Align Bar Code()
        write(TXT_ALIGN_CT);
        // Height
        if (height >=2 || height <=6) {
            write(BARCODE_HEIGHT);
        } else {
            throw new BarcodeSizeError("Incorrect Height");
        }
        //Width
        if (width >= 1 || width <=255) {
            write(BARCODE_WIDTH);
        } else {
            throw new BarcodeSizeError("Incorrect Width");
        }
        //Font
        if (font.equalsIgnoreCase("B")) {
            write(BARCODE_FONT_B);
        } else {
            write(BARCODE_FONT_A);
        }
        //Position
        if (pos.equalsIgnoreCase("OFF")) {
            write(BARCODE_TXT_OFF);
        } else if (pos.equalsIgnoreCase("BOTH")) {
            write(BARCODE_TXT_BTH);
        } else if (pos.equalsIgnoreCase("ABOVE")) {
            write(BARCODE_TXT_ABV);
        } else {
            write(BARCODE_TXT_BLW);
        }
        //Type
        switch(bc.toUpperCase()){
            case "UPC-A":
                write(BARCODE_UPC_A);
                break;
            case "UPC-E":
                write(BARCODE_UPC_E);
                break;
            default: case "EAN13":
                write(BARCODE_EAN13);
                break;
            case "EAN8":
                write(BARCODE_EAN8);
                break;
            case "CODE39":
                write(BARCODE_CODE39);
                break;
            case "ITF":
                write(BARCODE_ITF);
                break;
            case "NW7":
                write(BARCODE_NW7);
                break;
        }
        //Print Code
        if (!code.equals("")) {
            write(code.getBytes());
            write(CTL_LF);
        } else {
            throw new BarcodeSizeError("Incorrect Value");
        }
    }

    public void setTextFontA(){
        setTextFont("A");
    }

    public void setTextFontB(){
        setTextFont("B");
    }

    private void setTextFont(String font){
        if (font.equalsIgnoreCase("B")){
            write(TXT_FONT_B);
        }else{
            write(TXT_FONT_A);
        }
    }

    public void setTextFont(Integer font){
        if (font == null){
            write(TXT_FONT_A);
            return;
        }
        if (font  == PrefUtils.FONT_B){
            write(TXT_FONT_B);
        }else{
            write(TXT_FONT_A);
        }
    }

    public void setTextAlignCenter(){
        setTextAlign("CENTER");
    }

    public void setTextAlignRight(){
        setTextAlign("RIGHT");
    }

    public void setTextAlignLeft(){
        setTextAlign("LEFT");
    }

    private void setTextAlign(String align){
        if (align.equalsIgnoreCase("CENTER")){
            write(TXT_ALIGN_CT);
        }else if( align.equalsIgnoreCase("RIGHT")){
            write(TXT_ALIGN_RT);
        }else{
            write(TXT_ALIGN_LT);
        }
    }

    public void setTextDensity(int density){
        switch (density){
            case 0:
                write(PD_N50);
                break;
            case 1:
                write(PD_N37);
                break;
            case 2:
                write(PD_N25);
                break;
            case 3:
                write(PD_N12);
                break;
            case 4:
                write(PD_0);
                break;
            case 5:
                write(PD_P12);
                break;
            case 6:
                write(PD_P25);
                break;
            case 7:
                write(PD_P37);
                break;
            case 8:
                write(PD_P50);
                break;
        }
    }

    public void setTextNormal(){
        setTextProperties("LEFT", "A", "NORMAL", 1,1,9);
    }

    public void setTextProperties(String align, String font, String type, int width, int height, int density){
        setTextAlign(align);
        setTextFont(font);
        setTextType(type);
        setTextSize(width, height);
        setTextDensity(density);
    }

    public void printImage(String filePath) throws IOException {
//      File img = new File(filePath);
        printImage(BitmapFactory.decodeFile(filePath));
    }

    public void printImage2(String filePath) throws IOException {
        printImage(decodeSampledBitmapFromUri(filePath, 90, 90));
    }


    public void printImage(Bitmap image) {
        Image img = new Image();
        int[][] pixels = img.getPixelsSlow(image);
        for (int y = 0; y < pixels.length; y += 24) {
            write(LINE_SPACE_24);
            write(SELECT_BIT_IMAGE_MODE);
            write(new byte[]{(byte)(0x00ff & pixels[y].length), (byte)((0xff00 & pixels[y].length) >> 8)});
            for (int x = 0; x < pixels[y].length; x++) {
                write(img.recollectSlice(y, x, pixels));
            }
            write(CTL_LF);
        }
//        bus.write(CTL_LF);
//        bus.write(LINE_SPACE_30);
    }

    public void setCharCode(String code)  {
        switch (code){
            case "USA":
                write(CHARCODE_PC437);
                break;
            case "JIS":
                write(CHARCODE_JIS);
                break;
            case "MULTILINGUAL":
                write(CHARCODE_PC850);
                break;
            case "PORTUGUESE":
                write(CHARCODE_PC860);
                break;
            case "CA_FRENCH":
                write(CHARCODE_PC863);
                break;
            default: case "NORDIC":
                write(CHARCODE_PC865);
                break;
            case "WEST_EUROPE":
                write(CHARCODE_WEU);
                break;
            case "GREEK":
                write(CHARCODE_GREEK);
                break;
            case "HEBREW":
                write(CHARCODE_HEBREW);
                break;
            case "WPC1252":
                write(CHARCODE_PC1252);
                break;
            case "CIRILLIC2":
                write(CHARCODE_PC866);
                break;
            case "LATIN2":
                write(CHARCODE_PC852);
                break;
            case "EURO":
                write(CHARCODE_PC858);
                break;
            case "THAI42":
                write(CHARCODE_THAI42);
                break;
            case "THAI11":
                write(CHARCODE_THAI11);
                break;
            case "THAI13":
                write(CHARCODE_THAI13);
                break;
            case "THAI14":
                write(CHARCODE_THAI14);
                break;
            case "THAI16":
                write(CHARCODE_THAI16);
                break;
            case "THAI17":
                write(CHARCODE_THAI17);
                break;
            case "THAI18":
                write(CHARCODE_THAI18);
                break;
            case "LATIN3":
                write(CHARCODE_ISO885915);
                break;
        }
    }

    public void init(){
        write(HW_INIT);
    }

    public void openCashDrawerPin2() {
        write(CD_KICK_2);
    }

    public void openCashDrawerPin5() {
        write(CD_KICK_5);
    }

    public void open() throws IOException, ConnectionException {
        printer.open();
    }

    public void close(){
        printer.close();
    }

    public void beep(){
        write(BEEPER);
    }

    public void beepCorto(){
        write(BEEPERCORTO);
    }

    public void write(byte[] command){
        if (printer != null)
            printer.write(command);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromUri(String imageFile, int reqWidth, int reqHeight) throws IOException {
        // Get input stream of the image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream iStream = new FileInputStream(new File(imageFile));

        //String pathfile = getRealPathFromURI(imageUri);
        //pathfile = imageUri.getEncodedPath();

        /*int linea = 0;
        try {
            while ((linea = iStream.read()) > 0) {
                LogUtils.i(TAG, " linea " + linea);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.i(PosMainActivity.class.getName(), "iStream " + iStream);*/
        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(iStream, null, options);
        //BitmapFactory.decodeFile(pathfile, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        if (iStream != null) {
            iStream.close();
        }
        iStream = new FileInputStream(new File(imageFile));

        Bitmap bitmap = BitmapFactory.decodeStream(iStream, null, options);

        return bitmap;
    }
}
