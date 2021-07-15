package com.nbg.jeemart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.nbg.jeemart.databinding.ActivitySplashSreenBinding;


public class SplashSreen extends AppCompatActivity {
ActivitySplashSreenBinding binding;
Animation bottomAnim,sideAnim,fadeAnim;

  SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding=ActivitySplashSreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String text="Digitalizing Undgitalized";
        Spannable ss=new SpannableString(text);
        changeStatusBarColor();
        getSupportActionBar().hide();


        @SuppressLint("ResourceAsColor") ForegroundColorSpan fcsOrange=new ForegroundColorSpan(Color.rgb(255,89,0));
        @SuppressLint("ResourceAsColor") ForegroundColorSpan fcsGreen=new ForegroundColorSpan(Color.rgb(27,178,16));

        ss.setSpan(fcsGreen,0,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsOrange,13,25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        sideAnim= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        fadeAnim=AnimationUtils.loadAnimation(this,R.anim.fade_anim);


        binding.imageView.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences=getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);

                boolean isFirstTime=sharedPreferences.getBoolean("FirstTime",true);

                if(isFirstTime)
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("FirstTime",false);
                    editor.apply();
                    Intent intent=new Intent(SplashSreen.this, com.nbg.jeemart.OnBoarding.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                }
                else
                {
                    Intent intent=new Intent(SplashSreen.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    finishAfterTransition();
                }


            }
        },5000);


    }



    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));
        }
    }
}