import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'wiseasy_thermal_printer_platform_interface.dart';

/// An implementation of [WiseasyThermalPrinterPlatform] that uses method channels.
class MethodChannelWiseasyThermalPrinter extends WiseasyThermalPrinterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('wiseasy_thermal_printer');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> initializePrinter() async {
    final response = await methodChannel.invokeMethod('initPrinter');
    return response;
  }

  @override
  Future<String?> printSample() async {
    final response = await methodChannel.invokeMethod('printSample');
    return response;
  }

  @override
  Future<String?> paperFeed(int distance) async {
    final response =
        await methodChannel.invokeMethod('paperFeed', {'distance': distance});
    return response;
  }

  @override
  Future<String?> printLine(
      String text, fontSize, String align, bool bold, bool italic) async {
    final response = await methodChannel.invokeMethod('printLine', {
      'text': text,
      'fontSize': fontSize,
      'align': align.toLowerCase(),
      'bold': bold,
      'italic': italic
    });
    return response;
  }
  @override
  Future<String?> printLeftRight(
      String text1,String text2, fontSize, String align, bool bold, bool italic) async {
    final response = await methodChannel.invokeMethod('printLeftRight', {
      'text1': text1,
      'text2': text2,
      'fontSize': fontSize,
      'align': align.toLowerCase(),
      'bold': bold,
      'italic': italic
    });
    return response;
  }

  @override
  Future<String?> stopPrint() async {
    final response = await methodChannel.invokeMethod('stopPrint');
    return response;
  }
}
