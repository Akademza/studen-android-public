package ai.pifagor.android.screens.webview;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.KeyEventDispatcher;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ai.pifagor.android.R;

public class PhotoLoader {
    private ComponentActivity mActivity;

    public PhotoLoader(ComponentActivity activity) {
        mActivity = activity;
        permissionRequest = mActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(mFileChooserValueCallback != null) {
                    if(result) {
                        openFileChooser2();
                    }
                    else {
                        mFileChooserValueCallback.onReceiveValue(null);
                    }
                }
            }
        });
    }

    private ValueCallback<Uri[]> mFileChooserValueCallback;
    private static final int REQUEST_CHOOSE_FILE = 101;
    private static final int REQUEST_TAKE_PHOTO = 102;
    private static String mCurrentPhotoPath;

    private final ActivityResultLauncher<String> permissionRequest;
    public void openFileChooser(ValueCallback<Uri[]> filePathCallback) {
        mFileChooserValueCallback = filePathCallback;
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openFileChooser2();
        }
        else {
            permissionRequest.launch(Manifest.permission.CAMERA);
        }
    }

    private void openFileChooser2() {
        String[] dialogItems = new String[] {mActivity.getString(R.string.action_add_file_dialog_file_chooser), mActivity.getString(R.string.action_add_file_dialog_take_photo)};
        new AlertDialog.Builder(mActivity)
                .setItems(dialogItems, (dialog, which) -> {
                    if(which == 0) {
                        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        contentSelectionIntent.setType("image/*");

                        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, mActivity.getString(R.string.action_add_file_file_chooser));
                        mActivity.startActivityForResult(chooserIntent, REQUEST_CHOOSE_FILE);
                    }
                    else if(which == 1) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                            try {
                                File photoFile = createPhotoImageFile();
                                Uri photoURI = FileProvider.getUriForFile(mActivity, "ai.pifagor.android.fileprovider", photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                mActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                            }
                            catch(IOException e) {
                                e.printStackTrace();
                                mFileChooserValueCallback.onReceiveValue(new Uri[]{});
                                mFileChooserValueCallback = null;
                            }
                        }
                        else {
                            mFileChooserValueCallback.onReceiveValue(new Uri[]{});
                            mFileChooserValueCallback = null;
                        }
                    }
                    else {
                        mFileChooserValueCallback.onReceiveValue(new Uri[]{});
                        mFileChooserValueCallback = null;
                    }
                    dialog.dismiss();
                })
                .setOnCancelListener(dialog -> {
                    mFileChooserValueCallback.onReceiveValue(new Uri[]{});
                    mFileChooserValueCallback = null;
                    dialog.dismiss();
                }).show();
    }

    private File createPhotoImageFile() throws IOException {
        File imageDirectory = new File(mActivity.getCacheDir(), "camera");
        if(!imageDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            imageDirectory.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileNamePrefix = "JPEG_" + timeStamp + "_";
        File imageFile = File.createTempFile(imageFileNamePrefix, ".jpg", imageDirectory);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_CHOOSE_FILE) {
            if(mFileChooserValueCallback == null) {
                return true;
            }
            Uri result;
            if(intent == null || resultCode != Activity.RESULT_OK) {
                result = null;
            }
            else {
                result = intent.getData();
            }

            if(result != null) {
                mFileChooserValueCallback.onReceiveValue(new Uri[]{result});
            }
            else {
                mFileChooserValueCallback.onReceiveValue(new Uri[]{});
            }
            mFileChooserValueCallback = null;
        }
        else if (requestCode == REQUEST_TAKE_PHOTO) {
            if(mFileChooserValueCallback == null) {
                return true;
            }
            if(mCurrentPhotoPath == null) {
                mFileChooserValueCallback.onReceiveValue(new Uri[]{});
                mFileChooserValueCallback = null;
                return true;
            }
            File file = new File(mCurrentPhotoPath);
            if (resultCode == Activity.RESULT_OK) {
                Uri localUri = Uri.fromFile(file);
                Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                mActivity.sendBroadcast(localIntent);

                Uri result = Uri.fromFile(file);
                mFileChooserValueCallback.onReceiveValue(new Uri[]{result});
            }
            else {
                if (file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
                mFileChooserValueCallback.onReceiveValue(new Uri[]{});
            }
            mFileChooserValueCallback = null;
        }
        return false;
    }
}
