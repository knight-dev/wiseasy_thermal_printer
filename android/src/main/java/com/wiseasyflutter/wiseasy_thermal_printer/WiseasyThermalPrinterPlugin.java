package com.wiseasyflutter.wiseasy_thermal_printer;

import android.content.Context;

import androidx.annotation.NonNull;
import android.os.Handler;
import android.os.Looper;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

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
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result rawResult) {
    Result result = new MethodResultWrapper(rawResult);
    final Map<String, Object> arguments = call.arguments();

    switch (call.method) {

      case "getPlatformVersion":
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;

      case "initPrinter":
        try {
          int response = wiseasyPrint.initPrint();
          if(response != -1){
            result.success("Printer initialized successfully!");
          }else{
            result.error("FAILED", "Failed to initialize printer.", null);
          }
        } catch (Exception ex) {
          result.error("Error", ex.getMessage(), exceptionToString(ex));
        }
        break;

      case "printSample":
        try {
          int response = wiseasyPrint.printSample();
          if(response != -1){
            result.success("Print sample started successfully!");
          }else{
            result.error("FAILED", "Failed to start printing.", null);
          }
        } catch (Exception ex) {
          result.error("Error", ex.getMessage(), exceptionToString(ex));
        }
        break;

      case "stopPrint":
        try {
          int response = wiseasyPrint.finishPrint();
          if(response != -1){
            result.success("Print finish successful!");
          }else{
            result.error("FAILED", "Failed to stop printing.", null);
          }
        } catch (Exception ex) {
          result.error("Error", ex.getMessage(), exceptionToString(ex));
        }
        break;

      case "paperFeed":
        if (arguments.containsKey("distance")) {
          // how far to print blank page
          int size = (int) arguments.get("distance");
          int response = wiseasyPrint.paperFeed(size);
          if(response != -1){
            result.success("Paper feed successful!");
          }else{
            result.error("FAILED", "Failed paper feed.", null);
          }
        } else {
          result.error("invalid", "argument 'distance' not found", null);
        }
        break;

       case "printLine":
          if (arguments.containsKey("text")) {

            // get arguments
            String text = (String) arguments.get("text");
            int fontSize = (int) arguments.get("fontSize");
            String align = (String) arguments.get("align");
            Boolean bold = (Boolean) arguments.get("bold");
            Boolean italic = (Boolean) arguments.get("italic");

            int response = wiseasyPrint.printLine(text, fontSize, align, bold, italic);

            if(response != -1){
              result.success("Print line successful!");
            }else{
              result.error("FAILED", "Failed to print line.", null);
            }
          } else {
            result.error("invalid", "argument 'text' not found", null);
          }
        break;
      case "printLeftRight":
        if (arguments.containsKey("text1")) {

          // get arguments
          String text1 = (String) arguments.get("text1");
          String text2 = (String) arguments.get("text2");
          int fontSize = (int) arguments.get("fontSize");
          String align = (String) arguments.get("align");
          Boolean bold = (Boolean) arguments.get("bold");
          Boolean italic = (Boolean) arguments.get("italic");

          int response = wiseasyPrint.printLeftRight(text1,text2, fontSize, align, bold, italic);

          if(response != -1){
            result.success("Print LeftRight successful!");
          }else{
            result.error("FAILED", "Failed to print LeftRight.", null);
          }
        } else {
          result.error("invalid", "argument 'text' not found", null);
        }
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  // MethodChannel.Result wrapper that responds on the platform thread.
  private static class MethodResultWrapper implements Result {
    private Result methodResult;
    private Handler handler;

    MethodResultWrapper(Result result) {
      methodResult = result;
      handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void success(final Object result) {
      handler.post(new Runnable() {
        @Override
        public void run() {
          methodResult.success(result);
        }
      });
    }

    @Override
    public void error(final String errorCode, final String errorMessage, final Object errorDetails) {
      handler.post(new Runnable() {
        @Override
        public void run() {
          methodResult.error(errorCode, errorMessage, errorDetails);
        }
      });
    }

    @Override
    public void notImplemented() {
      handler.post(new Runnable() {
        @Override
        public void run() {
          methodResult.notImplemented();
        }
      });
    }
  }

  private String exceptionToString(Exception ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    return sw.toString();
  }
}
