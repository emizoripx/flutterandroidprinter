package imprime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import java.io.File;

import com.zebra.sdk.comm.ConnectionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.github.escposjava.PrinterService;
import io.github.escposjava.print.Printer;
import io.github.escposjava.print.exceptions.BarcodeSizeError;
import io.github.escposjava.print.exceptions.QRCodeException;
import utils.PrefUtils;
import utils.LogUtils;

public class PrinterServiceZebraCpcl extends PrinterService{

    private static final String TAG = PrinterServiceZebraCpcl.class.getCanonicalName();
    private static final String CARRIAGE_RETURN = System.getProperty("line.separator");
    private String typeText, typeText2;
    protected boolean estadoinit;
    protected boolean bold;
    protected boolean underline;
    protected int align, size, marginLeft = 30;
    protected StringBuilder stringBuilder;
    private int posicionImp;
    private int cantidad = 0;

    public PrinterServiceZebraCpcl(Printer printer) throws IOException, ConnectionException {
        super(printer);
        LogUtils.i(TAG,"PrinterServiceZebraCpcl ");
        typeText = "7";
        typeText2 = "7";
        estadoinit = false;
        bold = false;
        underline = false;
        align = 0;
        size = 0;
        posicionImp = 0;
        stringBuilder = new StringBuilder();
//      stringBuilder.append("! UTILITIES\r\n").append("COUNTRY LATIN9\r\n").append("PRINT\r\n");
    }

    public void setCharsetText(String charsetText) {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl chasrset");
        this.charsetText = charsetText;
        if (charsetText.contains("(")) {
            this.charsetText = charsetText.substring(0, charsetText.indexOf("("));
        }

    }

    public void print(String text) {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext "+typeText +" "+ text);

        try {
            String commandText = text;
            stringBuilder.append(commandText);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void printLn(String text) {
        print(text + "\r\n");
    }

    public void lineBreak() {
        lineBreak(1);
    }

    public void lineBreak(int nbLine) {
        for (int i=0;i<nbLine;i++) {
            print("\r\n");
        }
    }

    public void printQRCode(String value, Context context) throws QRCodeException {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl QRCODE");
        printQRCode(value, 150, 300, context);
    }

    public void printQRCode(String value, int left, int size, Context context) throws QRCodeException {
        stringBuilder.append("! 0 200 200 280 1\r\n");
        stringBuilder.append("B QR " + left + " 0 M 2 U 6\r\n");
        stringBuilder.append("MA," + value + "\r\n");
        stringBuilder.append("ENDQR\r\n");
        stringBuilder.append("PRINT\r\n");
        stringBuilder.append("! U1 JOURNAL\r\n");
        stringBuilder.append("! U1 LMARGIN " + marginLeft + "\r\n");
    }



    public void setTextSizeNormal(){
        setTextSize(1,1);
        if (typeText.equals("7")){
            size = 0;
        }else {
            size = 3;
        }
        stringBuilder.append("! U1 SETLP " + typeText + " " + size + " 24\r\n");
    }

    public void setTextSize2H(){
//      setTextSize(1,2);
        if (typeText.equals("7")){
            size = 2;
        }else {
            size = 5;
        }
        stringBuilder.append("! U1 SETLP " + typeText + " " + size + " 46\r\n");
    }

    public void setTextSize2W(){
//      setTextSize(2,1);
        if (typeText.equals("7")){
            size = 2;
        }else {
            size = 5;
        }
        stringBuilder.append("! U1 SETLP " + typeText + " " + size + " 46\r\n");
    }

    public void setText4Square(){
        setTextSize(2,2);
        if (typeText.equals("7")){
            size = 2;
        }else {
            size = 5;
        }
        stringBuilder.append("! U1 SETLP " + typeText + " " + size + " 46\r\n");
    }

    public void setTextSecond(){
        if (typeText.equals("7")) {
            stringBuilder.append("! U1 SETLP " + typeText2 + " 0 24\r\n");
        }else{
            stringBuilder.append("! U1 SETLP " + typeText2 + " 3 24\r\n");
        }
    }

    public void setTextSecondGrande(){
        if (typeText.equals("7")){
            stringBuilder.append("! U1 SETLP " + typeText2 + " 2 46\r\n");
        }else {
            stringBuilder.append("! U1 SETLP " + typeText2 + " 5 46\r\n");
        }
    }
    private void setTextSize(int width, int height){
    }
    public void setTextStyle(int font, int size, int height){
        stringBuilder.append("! U1 SETLP " + font + " "+ size +" " + height + "\r\n");
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
        LogUtils.i(TAG,"PrinterServiceZebraCpcl typetext ");
        if (type.equalsIgnoreCase("B")){
            bold = true;
            stringBuilder.append("! U1 SETBOLD 2\r\n");
        }else if(type.equalsIgnoreCase("U")){
            underline = true;
//         stringBuilder.append("UNDERLINE ON\r\n");
        }else if(type.equalsIgnoreCase("NORMAL")){
            bold = false;
            stringBuilder.append("! U1 SETBOLD 0\r\n");
            underline = false;
//         stringBuilder.append("UNDERLINE OFF\r\n");
            if (typeText.equals("7")){
                size = 0;
            }else {
                size = 3;
            }
            stringBuilder.append("! U1 SETLP " + typeText + " " + size + " 24\r\n");
            align = 0;
        }
    }

    public void cutPart(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl cut");
        cut("PART");
    }

    public void cutFull(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl cut full");
        cut("FULL");
    }

    private void cut(String mode){
        if (mode.toUpperCase().equals("PART")){
            stringBuilder.append("! U1 PARTIAL-CUT\r\n");
        }else{
            stringBuilder.append("! U1 CUT\r\n");
        }
    }

    public void printBarcode(String code, String bc, int width, int height, String pos, String font) throws BarcodeSizeError {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl print barcode");
//      String commandBarcode = "BARCODE ";
        String commandBarcode = "! U1 B";
        //Type
        switch(bc.toUpperCase()){
            case "UPC-A":
                commandBarcode += " UPCA";
                break;
            case "UPC-E":
                commandBarcode += " UPCE";
                break;
            default: case "EAN13":
                commandBarcode += " EAN13";
                break;
            case "EAN8":
                commandBarcode += " EAN8";
                break;
            case "CODE39":
                commandBarcode += " 39";
                break;
            case "COD128":
                commandBarcode += " 128";
                break;
        }
        //Print Code
        if (! commandBarcode.equals("! U1 B")) {
            commandBarcode += " 1 2 100 0 0 " + code + " ST 187.10 T 2.60\r\n";
            stringBuilder.append(commandBarcode);
        }
    }

    public void setTextFontA(){
        setTextFont("A");
    }

    public void setTextFontB(){
        setTextFont("B");
    }

    private void setTextFont(String font){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl setfont "+ font);
        if (font.equalsIgnoreCase("B")){
            typeText = "0";
            typeText2 = "0";
            size = 3;
        }else{
            typeText = "7";
            typeText2 = "7";
            size = 0;
        }
    }

    public void setTextFont(Integer font){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl integer font");
        if (font == null){
            setTextFontA();
            return;
        }
        if (font  == PrefUtils.FONT_B){
            setTextFontB();
        }else{
            setTextFontA();
        }
    }

    public void setTextAlignCenter(){
        align = 1;
    }

    public void printTextAlign(String texto, int height, int font, int size, int x, int y, int align, boolean bold){
        stringBuilder.append("! 0 200 200 " + height + " 1\r\n");
        if (align == 1){
            stringBuilder.append("CENTER\r\n");
        }else if (align == 2){
            stringBuilder.append("RIGHT\r\n");
        }
        if (bold){
            stringBuilder.append("! U1 SETBOLD 2\r\n");
        }
        stringBuilder.append("TEXT " + font + " " + size + " " + (marginLeft + x) + " " + y + " " + texto + "\r\n");
        if (bold){
            stringBuilder.append("! U1 SETBOLD 0\r\n");
        }
        stringBuilder.append("PRINT\r\n");

    }

    public void setTextAlignRight(){
        align = 2;
    }

    public void setTextAlignLeft(){
        align = 0;
    }

    private void setTextAlign(String align){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl align " + align);
//      write((align + "\r\n").getBytes());
    }

    public void setTextDensity(int density){
    }

    public void setTextNormal(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl align text normal ");
        setTextProperties("LEFT", "A", "NORMAL", 1,1,9);
    }

    public void setTextProperties(String align, String font, String type, int width, int height, int density){
        setTextAlign(align);
//      setTextFont(font);
        setTextType(type);
        setTextSize(width, height);
        setTextDensity(density);
    }

    public void printImage(String filePath) throws IOException {
        printImage(BitmapFactory.decodeFile(filePath));
//      try{
//         Thread.sleep(500);
//      }catch (Exception ex){
//         ex.printStackTrace();
//      }
    }

    public void printImage2(String filePath) throws IOException {
//      printImage(decodeSampledBitmapFromUri(filePath, 90, 90));
    }

    public void printImage3(String fileName) throws IOException {
        File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + fileName);
//                                File imgFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/vendis/" + empresa.getLogo());

        if (imgFile.exists()) {

            printImage(imgFile.getAbsolutePath());

        }
    }


    public void printImage(Bitmap image) {
        ((ZebraCpclPrinter)printer).imprimiimagen(image);
    }

    public void init(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl init");


        stringBuilder.append("! U1 setvar \"device.languages\" \"line_print\"\r\n");
        stringBuilder.append("! U1 JOURNAL\r\n");
        stringBuilder.append("! U1 LMARGIN " + marginLeft + "\r\n");

    }

    public void initPrint() {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl initprint " + stringBuilder.toString());
        try {
//         write(stringBuilder.toString().getBytes());
//         write(UtilZebra.stringABytes(stringBuilder.toString()));
            write(stringBuilder.toString().getBytes(charsetText));
            LogUtils.i(TAG,"PrinterServiceZebraCpcl initprint final " + stringBuilder.toString());
            Thread.sleep(150);
            stringBuilder = new StringBuilder();
            stringBuilder.append("! U1 JOURNAL\r\n");
            stringBuilder.append("! U1 LMARGIN " + marginLeft + "\r\n");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void openCashDrawerPin2() {

    }

    public void openCashDrawerPin5() {

    }

    public void open() throws IOException, ConnectionException {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl open");
        printer.open();
    }

    public void close(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl close");
        printer.close();
    }

    public void beep(){
        stringBuilder.append("! U1 BEEP 24\r\n");
    }

    public void beepCorto(){
        stringBuilder.append("! U1 BEEP 8\r\n");
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
