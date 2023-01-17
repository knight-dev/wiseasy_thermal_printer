import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wiseasy_thermal_printer/wiseasy_thermal_printer.dart';

void main() {
  DartPluginRegistrant.ensureInitialized();
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _wiseasyThermalPrinterPlugin = WiseasyThermalPrinter();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await _wiseasyThermalPrinterPlugin.getPlatformVersion() ??
              'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Wiseasy Test'),
        ),
        body: Column(
          children: [
            Center(
              child: Text('Running on: $_platformVersion\n'),
            ),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.initPrinter();
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("Initialize printer")),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.printSample();
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("Print Sample Receipt")),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.paperFeed(10);
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("PaperFeed - 10 Units")),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.paperFeed(100);
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("PaperFeed - 100 Units")),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.printLine(
                      "Hello GiftMe", 25, "left", false, false);
                  if (kDebugMode) {
                    print(res);
                  }
                  res = await _wiseasyThermalPrinterPlugin.printLine(
                      "Hello GiftMe", 35, "center", false, true);
                  if (kDebugMode) {
                    print(res);
                  }

                  res = await _wiseasyThermalPrinterPlugin.printLine(
                      "Hello GiftMe", 45, "right", true, false);
                  if (kDebugMode) {
                    print(res);
                  }

                  res = await _wiseasyThermalPrinterPlugin.paperFeed(100);
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("Print format test")),
            ElevatedButton(
                onPressed: () async {
                  var res = await _wiseasyThermalPrinterPlugin.stopPrint();
                  if (kDebugMode) {
                    print(res);
                  }
                },
                child: const Text("Stop printer")),
          ],
        ),
      ),
    );
  }
}
