package com.antonioleiva.materialeverywhere;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.koushikdutta.ion.Ion;

import java.util.concurrent.ExecutionException;

public class DetailView extends RelativeLayout {
    public ImageView image;

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        image = (ImageView) findViewById(R.id.image);
    }

    public static void launch(HomeActivity activity, final ViewGroup container, View iv, String transitionName, String url) {
        final DetailView v = (DetailView) activity.getLayoutInflater().inflate(R.layout.view_detail, container, false);
        iv.setTransitionName(transitionName);
        v.image.setTransitionName(transitionName);
        Transition shared = activity.inflateTransition(android.R.transition.move);
        shared.addTarget(transitionName);
        shared.setDuration(2000);
        Transition fade = activity.inflateTransition(android.R.transition.fade);
        fade.excludeTarget(transitionName, true);
        fade.setDuration(2000);
        final TransitionSet set = new TransitionSet();
        //set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        //set.addTransition(fade).addTransition(shared);
        set.addTransition(shared).addTransition(fade);
        Scene scene = new Scene(container, v);

        try {
            Bitmap bm = Ion.with(activity).load(url).asBitmap().get();
            v.image.setImageDrawable(new BitmapDrawable(activity.getResources(), bm));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        TransitionManager.go(scene, set);
    }

}
