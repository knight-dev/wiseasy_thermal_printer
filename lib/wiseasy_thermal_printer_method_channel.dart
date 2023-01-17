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
    final response = await methodChannel.invokeMethod<String>('initPrinter');
    return response;
  }

  @override
  Future<String?> startPrinting() async {
    final response = await methodChannel.invokeMethod<String>('startPrinting');
    return response;
  }

  @override
  Future<String?> stopPrint() async {
    final response = await methodChannel.invokeMethod<String>('stopPrint');
    return response;
  }
}
