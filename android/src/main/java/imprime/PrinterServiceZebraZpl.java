package imprime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

public class PrinterServiceZebraZpl extends PrinterService{

    private static final String TAG = PrinterServiceZebraZpl.class.getCanonicalName();
    private static final String CARRIAGE_RETURN = System.getProperty("line.separator");
    private String typeText;
    protected boolean estadoinit;
    protected boolean bold;
    protected boolean underline;
    protected int align, size;
    protected StringBuilder stringBuilder;

    public PrinterServiceZebraZpl(Printer printer) throws IOException, ConnectionException {
        super(printer);
        LogUtils.i(TAG,"PrinterServiceZebraCpcl ");
        typeText = "4";
        estadoinit = false;
        bold = false;
        underline = false;
        align = 0;
        size = 0;
        stringBuilder = new StringBuilder();
    }

    public void setCharsetText(String charsetText) {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl chasrset");
        this.charsetText = charsetText;
        if (charsetText.contains("(")) {
            this.charsetText = charsetText.substring(0, charsetText.indexOf("("));
        }
    }

//   public void print(String text) {
//      LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext "+typeText +" "+ text);
//      print(text,0);
//   }

//   public void printCenter(String text) {
//      LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext "+typeText +" "+ text);
//      print(text,0);
//   }
//
//   public void printRigth(String text) {
//      LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext "+typeText +" "+ text);
//      print(text,2, 0);
//   }

    public void print(String text) {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext "+typeText +" "+ text);
//      if (! estadoinit)
//         initSetPrint();
        String alignText = "LEFT\r\n";
        if (align == 1){//center
            alignText = "CENTER\r\n";
        }else if (align == 2) {//rigth
            alignText = "RIGTH\r\n";
        }
        String boldText = "! U1 SETBOLD 0\r\n";
        if (bold){
            boldText = "! U1 SETBOLD 2\r\n";
        }

        String underlineText = "UNDERLINE OFF\r\n";
        if (underline){
            underlineText = "UNDERLINE ON\r\n";
        }
        //"! 0 200 200 50 1\r\n" +  +"PRINT\r\n" boldText + underlineText + alignText +
        String commandText = "TEXT " + typeText + " " + size + " 0 0 " + text + "\r\n";
        LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext " + commandText);
//      write(commandText.getBytes());
        stringBuilder.append(commandText);
//      endPrint();
//      initPrint();
//      write("text".getBytes());
    }

    public void printNotFormat(String text) {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl printtext normal -- " + text);
        print(text);
//      write((text + "\r\n").getBytes());
    }

    public void printLn(String text) {
        print(text);
    }

    public void lineBreak() {
//      lineBreak(1);
    }

    public void lineBreak(int nbLine) {
//      for (int i=0;i<nbLine;i++) {
//         print("");
//      }
    }

    public void printQRCode(String value, Context context) throws QRCodeException {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl QRCODE");
        printQRCode(value, 300, context);
    }

    public void printQRCode(String value, int size, Context context) throws QRCodeException {
//      QRCodeGenerator q = new QRCodeGenerator();
        //"! 0 200 200 500 1\r\n" +"CENTER\r\n" +
        String qrcodeCommand = "B QR 10 100 M 2 U 10\r\n" +
                "MA," + value + "\r\n" +
                "ENDQR\r\n";
//       + "FORM\r\n" + "PRINT\r\n"
        stringBuilder.append(qrcodeCommand);
//      write(qrcodeCommand.getBytes());
    }

    public void setTextSizeNormal(){
        setTextSize(1,1);
        size = 0;
    }

    public void setTextSize2H(){
//      setTextSize(1,2);
        size = 2;
    }

    public void setTextSize2W(){
//      setTextSize(2,1);
        size = 2;
    }

    public void setText4Square(){
        setTextSize(2,2);
        size = 2;
    }

    private void setTextSize(int width, int height){
//      if (height == 2 && width == 2) {
//         write(TXT_NORMAL);
//         write(TXT_4SQUARE);
//      } else {
//         setTextNormal();
//      }
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
        String boldText = "! U1 SETBOLD 0\r\n";
        if (bold){
            boldText = "! U1 SETBOLD 2\r\n";
        }

        String underlineText = "UNDERLINE OFF\r\n";
        if (underline){
            underlineText = "UNDERLINE ON\r\n";
        }
        if (type.equalsIgnoreCase("B")){
            bold = true;
            stringBuilder.append("! U1 SETBOLD 2\r\n");
        }else if(type.equalsIgnoreCase("U")){
            underline = true;
            stringBuilder.append("UNDERLINE ON\r\n");
        }else if(type.equalsIgnoreCase("NORMAL")){
            bold = false;
            stringBuilder.append("! U1 SETBOLD 0\r\n");
            underline = false;
            stringBuilder.append("UNDERLINE OFF\r\n");
            size = 0;
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
//         write("PARTIAL-CUT\r\n".getBytes());
            stringBuilder.append("PARTIAL-CUT\r\n");
        }else{
//         write("CUT\r\n".getBytes());
            stringBuilder.append("CUT\r\n");
        }
    }

    public void printBarcode(String code, String bc, int width, int height, String pos, String font) throws BarcodeSizeError {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl print barcode");
        String commandBarcode = "BARCODE ";
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
        if (! commandBarcode.equals("BARCODE ")) {
            //"! 0 200 200 210 1\r\n" +
//         commandBarcode = "CENTER\r\n" + commandBarcode;
            commandBarcode += " 1 1 50 150 10 " + code + "\r\n" + "TEXT 7 0 210 60 " + code + "\r\n";
//          + "FORM\r\n" + "PRINT\r\n"
//         write(commandBarcode.getBytes());
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
            typeText = "5";
        }else{
            typeText = "4";
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
//      setTextAlign("CENTER");
        align = 1;
        stringBuilder.append("CENTER\r\n");
    }

    public void setTextAlignRight(){
//      setTextAlign("RIGHT");
        align = 2;
        stringBuilder.append("RIGTH\r\n");
    }

    public void setTextAlignLeft(){
//      setTextAlign("LEFT");
        align = 0;
        stringBuilder.append("LEFT\r\n");
    }

    private void setTextAlign(String align){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl align " + align);
//      if (! estadoinit)
//      initSetPrint();
//      write((align + "\r\n").getBytes());
    }

    public void setTextDensity(int density){
//      switch (density){
//         case 0:
//            write(PD_N50);
//            break;
//         case 1:
//            write(PD_N37);
//            break;
//         case 2:
//            write(PD_N25);
//            break;
//         case 3:
//            write(PD_N12);
//            break;
//         case 4:
//            write(PD_0);
//            break;
//         case 5:
//            write(PD_P12);
//            break;
//         case 6:
//            write(PD_P25);
//            break;
//         case 7:
//            write(PD_P37);
//            break;
//         case 8:
//            write(PD_P50);
//            break;
//      }
    }

    public void setTextNormal(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl align text normal ");
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
//      printImage(BitmapFactory.decodeFile(filePath));
    }

    public void printImage2(String filePath) throws IOException {
//      printImage(decodeSampledBitmapFromUri(filePath, 90, 90));
    }


    public void printImage(Bitmap image) {
//      Image img = new Image();
//      int[][] pixels = img.getPixelsSlow(image);
//      for (int y = 0; y < pixels.length; y += 24) {
//         write(LINE_SPACE_24);
//         write(SELECT_BIT_IMAGE_MODE);
//         write(new byte[]{(byte)(0x00ff & pixels[y].length), (byte)((0xff00 & pixels[y].length) >> 8)});
//         for (int x = 0; x < pixels[y].length; x++) {
//            write(img.recollectSlice(y, x, pixels));
//         }
//         write(CTL_LF);
//      }
    }

    public void setCharCode(String code)  {
//      switch (code){
//         case "USA":
//            write(CHARCODE_PC437);
//            break;
//         case "JIS":
//            write(CHARCODE_JIS);
//            break;
//         case "MULTILINGUAL":
//            write(CHARCODE_PC850);
//            break;
//         case "PORTUGUESE":
//            write(CHARCODE_PC860);
//            break;
//         case "CA_FRENCH":
//            write(CHARCODE_PC863);
//            break;
//         default: case "NORDIC":
//            write(CHARCODE_PC865);
//            break;
//         case "WEST_EUROPE":
//            write(CHARCODE_WEU);
//            break;
//         case "GREEK":
//            write(CHARCODE_GREEK);
//            break;
//         case "HEBREW":
//            write(CHARCODE_HEBREW);
//            break;
//         case "WPC1252":
//            write(CHARCODE_PC1252);
//            break;
//         case "CIRILLIC2":
//            write(CHARCODE_PC866);
//            break;
//         case "LATIN2":
//            write(CHARCODE_PC852);
//            break;
//         case "EURO":
//            write(CHARCODE_PC858);
//            break;
//         case "THAI42":
//            write(CHARCODE_THAI42);
//            break;
//         case "THAI11":
//            write(CHARCODE_THAI11);
//            break;
//         case "THAI13":
//            write(CHARCODE_THAI13);
//            break;
//         case "THAI14":
//            write(CHARCODE_THAI14);
//            break;
//         case "THAI16":
//            write(CHARCODE_THAI16);
//            break;
//         case "THAI17":
//            write(CHARCODE_THAI17);
//            break;
//         case "THAI18":
//            write(CHARCODE_THAI18);
//            break;
//         case "LATIN3":
//            write(CHARCODE_ISO885915);
//            break;
//      }
    }

    public void init(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl init");
        stringBuilder.append("! 0 200 200 210 1\r\n");
//      write("! 0 200 200 210 1\r\n".getBytes());

    }

    public void initSetPrint(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl init");
//      write("! 0 200 200 210 1\r\n".getBytes());
    }

    public void endPrint() {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl initprint ");
        try {
//         write(("FORM\r\n" + "PRINT\r\n").getBytes());
            Thread.sleep(150);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void initPrint() {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl initprint " + stringBuilder.toString());
        try {
            stringBuilder.append("FORM\r\n" + "PRINT\r\n");
//         write(("FORM\r\n" + "PRINT\r\n").getBytes());
//         write(stringBuilder.toString().getBytes());
            Thread.sleep(1600);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        String cpclConfigLabel = "! 0 200 200 203 1\r\n"+ "BOX 20 20 190 190 8\r\n" + "T 0 4 100 117 TEST\r\nFORM\r\n" + "PRINT\r\n";
        write(cpclConfigLabel.getBytes());
    }

    public void openCashDrawerPin2() {
//      write(CD_KICK_2);
    }

    public void openCashDrawerPin5() {
//      write(CD_KICK_5);
    }

    public void open() throws IOException, ConnectionException {
        LogUtils.i(TAG,"PrinterServiceZebraCpcl open");
        printer.open();
    }

    public void close(){
        LogUtils.i(TAG,"PrinterServiceZebraCpcl close");
        initPrint();
        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printer.close();
    }

    public void beep(){
        initSetPrint();
//      write("BEEP 3\r\n".getBytes());
        stringBuilder.append("BEEP 3\r\n");
        endPrint();
//      initPrint();
    }

    public void beepCorto(){
//      initSetPrint();
//      write("BEEP 1\r\n".getBytes());
        stringBuilder.append("BEEP 1\r\n");
//      endPrint();
//      initPrint();
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
