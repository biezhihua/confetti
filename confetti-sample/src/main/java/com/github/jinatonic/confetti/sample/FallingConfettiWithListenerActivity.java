package com.github.jinatonic.confetti.sample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.ConfettiGenerator;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.confetto.BitmapConfetti;
import com.github.jinatonic.confetti.confetto.Confetti;

import java.util.Random;

public class FallingConfettiWithListenerActivity extends AbstractActivity
        implements ConfettiGenerator, ConfettiManager.ConfettiAnimationListener {

    private TextView numConfettiTxt;
    private int numConfettiOnScreen;

    private int size;
    private int velocitySlow, velocityNormal;


    private Bitmap[] images = new Bitmap[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numConfettiTxt = (TextView) findViewById(R.id.num_confetti_txt);

        final Resources res = getResources();
        size = res.getDimensionPixelSize(R.dimen.big_confetti_size);
        velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);

        images[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_01), size, size, false);
        images[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_02), size, size, false);
        images[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_03), size, size, false);
        images[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_04), size, size, false);
        images[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_05), size, size, false);
        images[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_06), size, size, false);
        images[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.img_07), size, size, false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_confetti_with_listener;
    }

    @Override
    protected ConfettiManager generateOnce() {
        return getConfettiManager().setNumInitialCount(20)
                .setEmissionDuration(0)
                .setConfettiAnimationListener(this)
                .animate();
    }

    @Override
    protected ConfettiManager generateStream() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(20)
                .setConfettiAnimationListener(this)
                .animate();
    }

    @Override
    protected ConfettiManager generateInfinite() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(ConfettiManager.INFINITE_DURATION)
                .setEmissionRate(6)
                .setConfettiAnimationListener(this)
                .animate();
    }

    private ConfettiManager getConfettiManager() {
        final ConfettiSource source = new ConfettiSource(0, -size, container.getWidth(), -size);
        return new ConfettiManager(this, this, source, container)
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setRotationalVelocity(180, 90)
                .setTouchEnabled(false);
    }

    @Override
    public Confetti generateConfetti(Random random) {
        Log.d(TAG, "generateConfetti() called with: random = [" + random.nextInt(7) + "]");
        return new BitmapConfetti(images[random.nextInt(7)]);
    }

    @Override
    public void onAnimationStart(ConfettiManager confettiManager) {
        Toast.makeText(this, "Starting confetti animation", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(ConfettiManager confettiManager) {
        numConfettiOnScreen = 0;
        updateNumConfettiTxt();
        Toast.makeText(this, "Ending confetti animation", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfettiEnter(Confetti confetti) {
        numConfettiOnScreen++;
        updateNumConfettiTxt();
    }

    @Override
    public void onConfettiExit(Confetti confetti) {
        numConfettiOnScreen--;
        updateNumConfettiTxt();
    }

    private void updateNumConfettiTxt() {
        numConfettiTxt.setText(getString(R.string.num_confetti_desc, numConfettiOnScreen));
    }

    private static final String TAG = "FallingConfettiWithList";
}
