import 'wiseasy_thermal_printer_platform_interface.dart';

class WiseasyThermalPrinter {
  Future<String?> getPlatformVersion() {
    return WiseasyThermalPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> initPrinter() {
    return WiseasyThermalPrinterPlatform.instance.initializePrinter();
  }

  Future<String?> startPrint() {
    return WiseasyThermalPrinterPlatform.instance.startPrinting();
  }

  Future<String?> stopPrint() {
    return WiseasyThermalPrinterPlatform.instance.stopPrint();
  }
}
