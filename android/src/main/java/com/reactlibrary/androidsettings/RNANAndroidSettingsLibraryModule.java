
package com.reactlibrary.androidsettings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.app.Activity;
import android.content.ComponentName;
import android.provider.Settings;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNANAndroidSettingsLibraryModule extends ReactContextBaseJavaModule {
  public static String getMiuiVersion() {
      String line;
      BufferedReader input = null;
      try {
          Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
          input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
          line = input.readLine();
          input.close();
      } catch (IOException ex) {
          return null;
      } finally {
          if (input != null) {
              try {
                  input.close();
              } catch (IOException e) {
              }
          }
      }
      return line;
  }


  private final ReactApplicationContext reactContext;

  public RNANAndroidSettingsLibraryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNANAndroidSettingsLibrary";
  }


  @ReactMethod
  public void open(String type_setting) {
    Intent intentCl = new Intent();
    switch (type_setting) {
        case "ACTION_SETTINGS":
            intentCl.setAction(Settings.ACTION_SETTINGS);
            break;
        case "ACTION_WIRELESS_SETTINGS":
            intentCl.setAction(Settings.ACTION_WIRELESS_SETTINGS);
            break;
        case "ACTION_AIRPLANE_MODE_SETTINGS":
            intentCl.setAction(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
            break;
        case "ACTION_WIFI_SETTINGS":
            intentCl.setAction(Settings.ACTION_WIFI_SETTINGS);
            break;
        case "ACTION_APN_SETTINGS":
            intentCl.setAction(Settings.ACTION_APN_SETTINGS);
            break;
        case "ACTION_BLUETOOTH_SETTINGS":
            intentCl.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
            break;
        case "ACTION_DATE_SETTINGS":
            intentCl.setAction(Settings.ACTION_DATE_SETTINGS);
            break;
        case "ACTION_LOCALE_SETTINGS":
            intentCl.setAction(Settings.ACTION_LOCALE_SETTINGS);
            break;
        case "ACTION_INPUT_METHOD_SETTINGS":
            intentCl.setAction(Settings.ACTION_INPUT_METHOD_SETTINGS);
            break;
        case "ACTION_DISPLAY_SETTINGS":
            intentCl.setAction(Settings.ACTION_DISPLAY_SETTINGS);
            break;
        case "ACTION_SECURITY_SETTINGS":
            intentCl.setAction(Settings.ACTION_SECURITY_SETTINGS);
            break;
        case "ACTION_LOCATION_SOURCE_SETTINGS":
            intentCl.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            break;
        case "ACTION_INTERNAL_STORAGE_SETTINGS":
            intentCl.setAction(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            break;
        case "ACTION_MEMORY_CARD_SETTINGS":
            intentCl.setAction(Settings.ACTION_MEMORY_CARD_SETTINGS);
            break;
        case "ACTION_APPLICATION_DETAILS_SETTINGS":
            String device = Build.MANUFACTURER;
            // System.out.println("Build.MANUFACTURER = " + device);
            if (device.equals("Xiaomi")) {
              String miuiVersion = getMiuiVersion();
              if ("V5".equals(miuiVersion)) {
                Uri packageURI = Uri.parse("package:" + reactContext.getPackageName());
                intentCl = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
              } else if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion)) {
                intentCl = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intentCl.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intentCl.putExtra("extra_pkgname", reactContext.getPackageName());
              } else {
                intentCl = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intentCl.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intentCl.putExtra("extra_pkgname", reactContext.getPackageName());
              }
            } else if (device.equals("Huawei")) {
              intentCl = new Intent();
              // intentCl.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
              ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
              intentCl.setComponent(comp);
            } else if (device.equals("Meizu")) {
              intentCl = new Intent("com.meizu.safe.security.SHOW_APPSEC");
              intentCl.addCategory(Intent.CATEGORY_DEFAULT);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
            } else if (device.equals("Sony")) {
              intentCl = new Intent();
              // intentCl.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
              ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
              intentCl.setComponent(comp);
            } else if (device.equals("OPPO")) {
              intentCl = new Intent();
              // intentCl.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
              ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
              intentCl.setComponent(comp);
            } else if (device.equals("LG")) {
              intentCl = new Intent("android.intent.action.MAIN");
              // intentCl.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
              ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
              intentCl.setComponent(comp);
            } else if (device.equals("Letv")) {
              intentCl = new Intent();
              // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intentCl.putExtra("packageName", BuildConfig.APPLICATION_ID);
              ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
              intentCl.setComponent(comp);
            } else {
              intentCl.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
              Uri uri = Uri.fromParts("package", reactContext.getPackageName(), null);
              intentCl.setData(uri);
            }
            break;
        default:
            intentCl.setAction(Settings.ACTION_SETTINGS);
            break;
    }


    intentCl.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    reactContext.startActivity(intentCl);
  }

  @ReactMethod
  public void main() {
      open("main");
  }
}
