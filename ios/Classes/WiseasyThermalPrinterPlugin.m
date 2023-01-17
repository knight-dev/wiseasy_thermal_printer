#import "WiseasyThermalPrinterPlugin.h"
#if __has_include(<wiseasy_thermal_printer/wiseasy_thermal_printer-Swift.h>)
#import <wiseasy_thermal_printer/wiseasy_thermal_printer-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "wiseasy_thermal_printer-Swift.h"
#endif

@implementation WiseasyThermalPrinterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWiseasyThermalPrinterPlugin registerWithRegistrar:registrar];
}
@end
