package com.softwarejoint.contact;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Pankaj Soni on 27/09/17.
 * Software Joint Pvt. Ltd.
 * pankajsoni@softwarejoint.com
 */
public class PermissionManager {

    public static final int REQUEST_CODE_CAMERA = 101;
    public static final int REQUEST_CODE_GALLERY = 102;
    public static final int REQUEST_CODE_AUDIO = 103;
    public static final int REQUEST_CODE_VIDEO = 104;
    public static final int REQUEST_CODE_CALL = 105;
    public static final int REQUEST_CODE_READ_CONTACT = 106;
    public static final int REQUEST_CODE_EDIT_CONTACT = 107;

    private static final String TAG = "PackageManager";

    public static void readContactPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_READ_CONTACT);
    }

    public static void editContactPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_EDIT_CONTACT);
    }

    public static void cameraPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_CAMERA);
    }

    public static void galleryPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_GALLERY);
    }

    public static void audioPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_AUDIO);
    }

    public static void videoPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_VIDEO);
    }

    public static void callPermission(Activity activity, PermissionCallBack permissionCallBack) {
        requestPermission(activity, permissionCallBack, REQUEST_CODE_CALL);
    }

    public static boolean checkIfPermissionGranted(Context context, int requestCode) {
        String[] permissions = getPermissionsForFeature(requestCode);
        if (permissions == null || permissions.length == 0) return true;

        String[] permissionsNeeded = checkPermissionsNeeded(context, permissions);

        return (permissionsNeeded == null || permissionsNeeded.length == 0);
    }

//    private static boolean checkSelfPermission(Context context, String permission) {
//        try {
//            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
//        } catch (Exception ex) {
//            Logger.report(TAG, "checkSelfPermission", ex);
//        }
//
//        return false;
//    }

    private static String[] getPermissionsForFeature(int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACT:
                return new String[]{
                        Manifest.permission.READ_CONTACTS
                };
            case REQUEST_CODE_EDIT_CONTACT:
                return new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                };
            case REQUEST_CODE_GALLERY:
                return new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
            case REQUEST_CODE_CAMERA:
                return new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
            case REQUEST_CODE_AUDIO:
                return new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS
                };
            case REQUEST_CODE_VIDEO:
                return new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS
                };
            case REQUEST_CODE_CALL:
                return new String[]{
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE
                };
            default:
                break;
        }

        return null;
    }

    private static void requestPermission(Activity activity, PermissionCallBack permissionCallBack, int requestCode) {
        String[] permissionsNeeded = getPermissionsForFeature(requestCode);
        requestPermission(activity, permissionCallBack, permissionsNeeded, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermission(Activity activity, PermissionCallBack permissionCallBack,
                                          String[] permissions, int requestCode) {

        String[] permissionsNeeded = checkPermissionsNeeded(activity, permissions);

        if (permissionsNeeded == null || permissionsNeeded.length == 0) {
            if (permissionCallBack != null)
                permissionCallBack.onAccessPermission(true, requestCode);
            return;
        }

        if (permissionCallBack instanceof FragmentActivity) {
            ((FragmentActivity) permissionCallBack).requestPermissions(permissionsNeeded, requestCode);
        } else if (permissionCallBack instanceof Fragment) {
            ((Fragment) permissionCallBack).requestPermissions(permissionsNeeded, requestCode);
        }

        ActivityCompat.requestPermissions(activity, permissionsNeeded, requestCode);
    }

    private static String[] checkPermissionsNeeded(Context context, String[] permissions) {

        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) return null;

        ArrayList<String> permissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        return permissionsNeeded.toArray(new String[permissionsNeeded.size()]);
    }

    public static void onRequestPermissionResult(int requestCode, int[] grantResults,
                                                 PermissionCallBack permissionCallBack) {

        if (grantResults.length == 0) return;

        boolean granted = false;

        for (int grantResult: grantResults) {
            granted = (grantResult == PackageManager.PERMISSION_GRANTED);
            if (!granted) break;
        }

        if (permissionCallBack != null) {
            permissionCallBack.onAccessPermission(granted, requestCode);
        }
    }
}
