package com.wiseasyflutter.wiseasy_thermal_printer;

import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** WiseasyThermalPrinterPlugin */
public class WiseasyThermalPrinterPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private ActivityPluginBinding activityBinding;
  private WiseasyPrint wiseasyPrint;
  private Context mContext; // Instance variable for context

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    onAttach(flutterPluginBinding.getApplicationContext(),flutterPluginBinding.getBinaryMessenger());

  }

  private void onAttach(Context applicationContext, BinaryMessenger messenger) {
    this.mContext = applicationContext;
    channel = new MethodChannel(messenger, "wiseasy_thermal_printer");
    channel.setMethodCallHandler(this);

    // setup
    wiseasyPrint = new WiseasyPrint(applicationContext);
    wiseasyPrint.startPrintThread();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }

    // initialize printer
    if (call.method.equals("initPrinter")) {
      int response = wiseasyPrint.initPrint();
      if(response != -1){
        result.success("Printer initialized successfully!");
      }else{
        result.error("FAILED", "Failed to initialize printer.", null);
      }
      return;
    } else {
      result.notImplemented();
    }

    // start print
    if (call.method.equals("startPrinting")) {
      int response = wiseasyPrint.startPrint();
      if(response != -1){
        result.success("Printing started successfully!");
      }else{
        result.error("FAILED", "Failed to start printing.", null);
      }
      return;
    } else {
      result.notImplemented();
    }

    // finish print
    // initialize printer
    if (call.method.equals("stopPrint")) {
      int response = wiseasyPrint.finishPrint();
      if(response != -1){
        result.success("Print finish successful!");
      }else{
        result.error("FAILED", "Failed to stop printing.", null);
      }
      return;
    } else {
      result.notImplemented();
    }
  }

//  @Override
//  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
//    activityBinding = binding;
//    wiseasyPrint = new WiseasyPrint();
//    wiseasyPrint.startPrintThread();
//  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
