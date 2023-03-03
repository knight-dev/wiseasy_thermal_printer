package com.wiseasyflutter.wiseasy_thermal_printer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import io.flutter.embedding.android.FlutterActivity;

import wangpos.sdk4.libbasebinder.Printer;

public class WiseasyPrint extends Activity{
    private Printer mPrinter;
    private String TAG = "WiseasyThermalPrinter";
    private int command;
    private Context mContext;

    WiseasyPrint(Context cxt){
        mContext = cxt;
    }

    public void startPrintThread(){
        new Thread() {
            @Override
            public void run() {
                try {
                    mPrinter = new Printer(mContext);


                    String MODEL = Build.MODEL;
                    Log.d(TAG, "run: model = " + MODEL);
                    if (MODEL.equalsIgnoreCase("WPOS-MINI") || MODEL.equalsIgnoreCase("WPOS-QT")) {
                        mPrinter.setPrintType(1);
                    } else {
                        mPrinter.setPrintType(0);
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public int initPrint() {
        int result = -1;
        try {
            result = mPrinter.printInit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int finishPrint() {
        int result = -1;
        try {
            result = mPrinter.printFinish();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int paperFeed(int distance){
        int result = -1;
        try{
        mPrinter.printPaper(distance);
        result = 1;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int printLine(String text, int fontSize, String alignment, boolean bold, boolean italic){
        int result = -1;
        try{
            // set print alignment
            Printer.Align align = Printer.Align.LEFT;
            if(alignment.equals("center")){
                align = Printer.Align.CENTER;
            }
            if(alignment.equals("right")){
                align = Printer.Align.RIGHT;
            }
            mPrinter.printString(text, fontSize, align, bold, italic);


            result = 1;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }
    public int printLeftRight(String text1,String text2, int fontSize, String alignment, boolean bold, boolean italic){
        int result = -1;
        try{
            // set print alignment
            Printer.Align align = Printer.Align.LEFT;
            if(alignment.equals("center")){
                align = Printer.Align.CENTER;
            }
            if(alignment.equals("right")){
                align = Printer.Align.RIGHT;
            }
            mPrinter.print2StringInLine(text1,text2,2,Printer.Font.SERIF,fontSize, align, bold, italic,false);



            result = 1;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int printSample() {
        int[] status = new int[1];
        status[0] = -1;
        try {

//            mPrinter.printInit();
//            mPrinter.setGrayLevel(1);
            //InputStream inputStream = getAssets().open("ladybmp.png");
            //Bitmap bitmap =  BitmapFactory.decodeStream(inputStream);
            //mPrinter.printImageBase(bitmap,300, 300, Printer.Align.CENTER, 0);
            //bitmap.recycle();
            mPrinter.printPaper(10);

            mPrinter.printString("www.wiseasy.com", 25, Printer.Align.CENTER, true, false);
            mPrinter.printString("北京微智全景信息技术有限公司",25, Printer.Align.CENTER,false,false);
            mPrinter.printString("  ", 30, Printer.Align.CENTER, false,false);
            mPrinter.printString("--------------------------------------------", 30, Printer.Align.CENTER, false, false);
            mPrinter.printString("Meal Package:KFC $100 coupons", 25, Printer.Align.LEFT, false, false);
            mPrinter.printString("Selling Price:$90", 25, Printer.Align.LEFT, false, false);
            mPrinter.printString("Merchant Name:KFC（ZS Park）", 25, Printer.Align.LEFT, false, false);
            mPrinter.printString("Payment Time:17/3/29 9:27", 25, Printer.Align.LEFT, false, false);
            mPrinter.printString("--------------------------------------------", 30, Printer.Align.CENTER, false, false);
            mPrinter.printString("NO. of Coupons:5", 25, Printer.Align.LEFT, false, false);
            mPrinter.printString("Total Amount:$450", 25, Printer.Align.LEFT, false,false);
            mPrinter.printString("SN:1234 4567 4565", 25, Printer.Align.LEFT, false, false);
            //default content is too long to wrap
            mPrinter.printString("the content is too long to wrap the content is too long to wrap", 25, Printer.Align.LEFT, false, false);
            //font style print
            mPrinter.printStringExt("Default Font",0,0f,1.0f, Printer.Font.DEFAULT,20, Printer.Align.LEFT,false, false, false);
            mPrinter.printStringExt("Default Bold Font ", 0,0f,2.0f, Printer.Font.DEFAULT_BOLD, 20, Printer.Align.LEFT,false,false,false);
            mPrinter.printStringExt("Monospace Font ", 0,0f,1.0f, Printer.Font.MONOSPACE, 20, Printer.Align.LEFT,false,false,false);
            mPrinter.printStringExt("Sans Serif Font ", 0,0f,3.0f, Printer.Font.SANS_SERIF, 20, Printer.Align.LEFT,false,false,false);
            mPrinter.printStringExt("Serif Font ", 0,0f,5.0f, Printer.Font.SERIF, 20, Printer.Align.LEFT,false,false,false);
            //two content left and right in one line
            mPrinter.print2StringInLine("left","right",1.0f,Printer.Font.DEFAULT,25, Printer.Align.LEFT,false, false, false);

            int[] proportionArray = new int[]{3,1,1,1};
            String[] contentArray = new String[] {"Face mask", "1.5", "3.0", "PST"};
            mPrinter.printMultiseriateString(proportionArray,contentArray, 25, Printer.Align.LEFT, false, false);
            proportionArray = new int[]{3,1,1,1};
            contentArray = new String[] {"84 Disinfectant", "1.5", "3.0", "PST"};
            mPrinter.printMultiseriateString(proportionArray,contentArray, 25, Printer.Align.LEFT, false, false);
            proportionArray = new int[]{4,1,1};
            contentArray = new String[] {"subtotal", "3.0", "6.0"};
            mPrinter.printMultiseriateString(proportionArray,contentArray, 25, Printer.Align.LEFT, false, false);

            //default content print
            mPrinter.printStringExt("COMPLETED SALE TOTAL", 0, 0f, 2.0f, Printer.Font.DEFAULT_BOLD, 33, Printer.Align.CENTER, true, false, false);
//            mPrinter.printString("parag", 30, Printer.Align.CENTER, true, false);
//            mPrinter.printString("hank", 25, Printer.Align.CENTER, false, false);
//            mPrinter.printString("gene", 25, Printer.Align.CENTER, false, false);

            mPrinter.printPaper(100);
            //Detecting the in-place status of the card during printing
//             mPrinter.printPaper_trade(5,100);

//            mPrinter.printFinish();
            mPrinter.getPrinterStatus(status);
            Log.d(TAG, "testPrintString: status = " + status[0]);


        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return status[0];
    }


}

