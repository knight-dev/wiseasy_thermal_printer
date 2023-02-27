import 'package:flutter_test/flutter_test.dart';
import 'package:wiseasy_thermal_printer/wiseasy_thermal_printer.dart';
import 'package:wiseasy_thermal_printer/wiseasy_thermal_printer_platform_interface.dart';
import 'package:wiseasy_thermal_printer/wiseasy_thermal_printer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockWiseasyThermalPrinterPlatform
    with MockPlatformInterfaceMixin
    implements WiseasyThermalPrinterPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<String?> initializePrinter() {
    // TODO: implement initializePrinter
    throw UnimplementedError();
  }

  @override
  Future<String?> paperFeed(int distance) {
    // TODO: implement paperFeed
    throw UnimplementedError();
  }

  @override
  Future<String?> printLine(String text, fontSize, String align, bool bold, bool italic) {
    // TODO: implement printLine
    throw UnimplementedError();
  }

  @override
  Future<String?> printSample() {
    // TODO: implement printSample
    throw UnimplementedError();
  }

  @override
  Future<String?> stopPrint() {
    // TODO: implement stopPrint
    throw UnimplementedError();
  }
}

void main() {
  final WiseasyThermalPrinterPlatform initialPlatform = WiseasyThermalPrinterPlatform.instance;

  test('$MethodChannelWiseasyThermalPrinter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelWiseasyThermalPrinter>());
  });

  test('getPlatformVersion', () async {
    WiseasyThermalPrinter wiseasyThermalPrinterPlugin = WiseasyThermalPrinter();
    MockWiseasyThermalPrinterPlatform fakePlatform = MockWiseasyThermalPrinterPlatform();
    WiseasyThermalPrinterPlatform.instance = fakePlatform;

    expect(await wiseasyThermalPrinterPlugin.getPlatformVersion(), '42');
  });
}
