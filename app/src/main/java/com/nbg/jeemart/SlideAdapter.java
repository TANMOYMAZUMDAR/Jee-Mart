package com.nbg.jeemart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

public class SlideAdapter extends PagerAdapter {

 LayoutInflater layoutInflater;
    Context context;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    int images[]={
            R.raw.lf30_editor_zoxelsnw,
            R.raw.online_groceries,
            R.raw.handshake
    };


  int headings[]={
               R.string.Heading1,
          R.string.Heading2,
          R.string.Heading3
  };



    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {

       layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        LottieAnimationView animationView=view.findViewById(R.id.animationView);
        TextView textView=view.findViewById(R.id.slider_Heading);

        animationView.setAnimation(images[position]);
        textView.setText(headings[position]);

        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull  Object object) {

     container.removeView((ConstraintLayout)object);

    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull  Object object) {
        return view==(ConstraintLayout)object;
    }

}
