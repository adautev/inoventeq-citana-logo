package citana.inoventeq.com.citanalogo;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AnimatedVectorDrawableCompat logoInitial;
    private AnimatedVectorDrawableCompat logoReverse;
    private ImageView logo_animated_infinite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ImageView logo_animated = findViewById(R.id.logo);
        logo_animated_infinite = findViewById(R.id.logo_animated_infinite);
        logo_animated.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_animated).mutate());
        Drawable drawable = logo_animated.getDrawable();
        if(drawable instanceof  AnimatedVectorDrawable) {
            ((AnimatedVectorDrawable)drawable).start();
        }
        AnimateInfiniteLogo();
    }

    private void AnimateInfiniteLogo() {
        if(logoInitial == null) {
            logoInitial = AnimatedVectorDrawableCompat.create(this, R.drawable.logo_animated);
        }
        if(logoReverse == null) {
            logoReverse = AnimatedVectorDrawableCompat.create(this, R.drawable.logo_animated_reverse);
        }
        logo_animated_infinite.setImageDrawable(logoInitial);
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        logoInitial.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(final Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        logoReverse.stop();
                        logo_animated_infinite.setImageDrawable(logoReverse);
                        logoReverse.start();
                    }
                });
            }
        });
        logoReverse.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(final Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        logoInitial.stop();
                        logo_animated_infinite.setImageDrawable(logoInitial);
                        logoInitial.start();
                    }
                });
            }
        });
        logoInitial.start();
    }

    public void animate(View view) {
        ImageView logo = findViewById(R.id.logo);
        logo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_animated).mutate());
        Drawable drawable = logo.getDrawable();
        if(drawable instanceof  AnimatedVectorDrawable) {
            final AnimatedVectorDrawable currentDrawable = (AnimatedVectorDrawable) drawable;
            currentDrawable.stop();
            currentDrawable.reset();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentDrawable.start();
                }
            }, 200);
        } else if(drawable instanceof  AnimatedVectorDrawableCompat){
            final AnimatedVectorDrawableCompat currentDrawable = (AnimatedVectorDrawableCompat) drawable;
            currentDrawable.stop();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentDrawable.start();
                }
            }, 200);

        }

    }
}
