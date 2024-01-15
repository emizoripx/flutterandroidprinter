// import 'dart:typed_data';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutterandroidprinter/flutterandroidprinter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});


  @override
  State<StatefulWidget> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  void _print() async {
    // Test regular text
    SunmiPrinter.startPrint();
    SunmiPrinter.hr();
    SunmiPrinter.text(
      'Prueba impresión Sunmi',
      styles: const SunmiStyles(align: SunmiAlign.center),
    );
    SunmiPrinter.hr();

    // Test align
    SunmiPrinter.text(
      'izquierda',
      styles: const SunmiStyles(bold: true, underline: true),
    );
    SunmiPrinter.text(
      'centro',
      styles:
      const SunmiStyles(bold: true, underline: true, align: SunmiAlign.center),
    );
    SunmiPrinter.text(
      'derecha',
      styles: const SunmiStyles(bold: true, underline: true, align: SunmiAlign.right),
    );

    // Test text size
    SunmiPrinter.text('Texto extra pequeño',
        styles: const SunmiStyles(size: SunmiSize.xs));
    SunmiPrinter.text('Texto Medio', styles: const SunmiStyles(size: SunmiSize.md));
    SunmiPrinter.text('Texto Grande', styles: const SunmiStyles(size: SunmiSize.lg));
    SunmiPrinter.text('Texto Extra Grande',
        styles: const SunmiStyles(size: SunmiSize.xl));

    // Test row
    SunmiPrinter.row(
      cols: [
        SunmiCol(text: 'col1', width: 4),
        SunmiCol(text: 'col2', width: 4, align: SunmiAlign.center),
        SunmiCol(text: 'col3', width: 4, align: SunmiAlign.right),
      ],
    );

    // Test image
    ByteData bytes = await rootBundle.load('assets/rabbit_black.jpg');
    final buffer = bytes.buffer;
    final imgData = base64.encode(Uint8List.view(buffer));
    SunmiPrinter.image(imgData);
    SunmiPrinter.codeQr('https://pilotosiat.impuestos.gob.bo/consulta/QR?nit=311710026&cuf=1553FC196BCFC6B17CB1F53FAC68D9707EA86281079197E1DEB6BDC74&numero=232',size:250);

    SunmiPrinter.emptyLines(3);
    SunmiPrinter.stopPrint();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Prueba de impresión Sunmi'),
        ),
        body: Column(
          children: <Widget>[
            const SizedBox(height: 50),
            Center(
              child: TextButton(
                onPressed: _print,
                child: const Text('Prueba DEMO', style: TextStyle(fontSize: 20)),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
