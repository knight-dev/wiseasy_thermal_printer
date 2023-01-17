import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'wiseasy_thermal_printer_method_channel.dart';

abstract class WiseasyThermalPrinterPlatform extends PlatformInterface {
  /// Constructs a WiseasyThermalPrinterPlatform.
  WiseasyThermalPrinterPlatform() : super(token: _token);

  static final Object _token = Object();

  static WiseasyThermalPrinterPlatform _instance =
      MethodChannelWiseasyThermalPrinter();

  /// The default instance of [WiseasyThermalPrinterPlatform] to use.
  ///
  /// Defaults to [MethodChannelWiseasyThermalPrinter].
  static WiseasyThermalPrinterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [WiseasyThermalPrinterPlatform] when
  /// they register themselves.
  static set instance(WiseasyThermalPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> initializePrinter() {
    throw UnimplementedError('initializePrinter() has not been implemented.');
  }

  Future<String?> startPrinting() {
    throw UnimplementedError('startPrinting() has not been implemented.');
  }

  Future<String?> stopPrint() {
    throw UnimplementedError('stopPrint() has not been implemented.');
  }
}
