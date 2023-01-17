import 'wiseasy_thermal_printer_platform_interface.dart';

class WiseasyThermalPrinter {
  Future<String?> getPlatformVersion() {
    return WiseasyThermalPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> initPrinter() {
    return WiseasyThermalPrinterPlatform.instance.initializePrinter();
  }

  Future<String?> printSample() {
    return WiseasyThermalPrinterPlatform.instance.printSample();
  }

  Future<String?> paperFeed(int distance) {
    return WiseasyThermalPrinterPlatform.instance.paperFeed(distance);
  }

  Future<String?> printLine(
      String text, fontSize, String align, bool bold, bool italic) {
    return WiseasyThermalPrinterPlatform.instance
        .printLine(text, fontSize, align, bold, italic);
  }

  Future<String?> stopPrint() {
    return WiseasyThermalPrinterPlatform.instance.stopPrint();
  }
}
