package com.nbg.jeemart;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nbg.jeemart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101;
    ActivityMainBinding binding;
    private PermissionRequest myRequest;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        CusstomWebViewClient client=new CusstomWebViewClient(this);
        binding.webview.setWebViewClient(client);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setDomStorageEnabled(true);


        binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webview.setWebViewClient(new WebViewClient());

        binding.webview.getSettings().setSaveFormData(true);
        binding.webview.getSettings().setSupportZoom(false);
        binding.webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON);

        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                myRequest = request;

                for (String permission : request.getResources()) {
                    switch (permission) {
                        case "android.webkit.resource.AUDIO_CAPTURE": {
                            askForPermission(request.getOrigin().toString(), Manifest.permission.RECORD_AUDIO,MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                            break;
                        }
                    }
                }
            }
        });

        binding.webview.loadUrl("https://m.nearbygrocer.com/mystores/nbg45ayfpu63");

        changeStatusBarColor();




    }

    @Override
    public void onBackPressed() {
        if(binding.webview.canGoBack())
        {
            binding.webview.goBack();
        }
        else {
            super.onBackPressed();
        }
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
    {
        if(keyCode==keyEvent.KEYCODE_BACK && binding.webview.canGoBack()){
            binding.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,keyEvent);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                Log.d("WebView", "PERMISSION FOR AUDIO");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    myRequest.grant(myRequest.getResources());
                    binding.webview.loadUrl("https://m.nearbygrocer.com/mystores/nbg47laqpu65");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void askForPermission(String origin, String permission, int requestCode) {
        Log.d("WebView", "inside askForPermission for" + origin + "with" + permission);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission},
                        requestCode);
            }
        } else {
            myRequest.grant(myRequest.getResources());
        }
    }
}



class CusstomWebViewClient extends WebViewClient{
    private Activity activity;
    public CusstomWebViewClient(Activity activity)
    {
        this.activity=activity;
    }

    //API Level less than 24
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url)
    {
        return false;  //all the links would be opened in our app and not external website.
    }

    //Api level greater than 24
    @Override
    public boolean  shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
    {
        return  false;
    }

}
