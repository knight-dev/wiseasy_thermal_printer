import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:wiseasy_thermal_printer/wiseasy_thermal_printer_method_channel.dart';

void main() {
  MethodChannelWiseasyThermalPrinter platform = MethodChannelWiseasyThermalPrinter();
  const MethodChannel channel = MethodChannel('wiseasy_thermal_printer');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
