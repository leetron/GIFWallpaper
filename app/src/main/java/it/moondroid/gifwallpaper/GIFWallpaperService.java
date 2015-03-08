package it.moondroid.gifwallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import it.moondroid.gifwallpaper.scaling.CenterCropScaleStrategy;
import it.moondroid.gifwallpaper.scaling.CenterInsideScaleStrategy;
import it.moondroid.gifwallpaper.scaling.CenterScaleStrategy;
import it.moondroid.gifwallpaper.scaling.FitXYScaleStrategy;
import it.moondroid.gifwallpaper.scaling.ScaleStrategy;

/**
 * Created by Marco on 05/03/2015.
 */
public class GIFWallpaperService extends WallpaperService {

    private static final String TAG = GIFWallpaperService.class.getName();

    @Override
    public Engine onCreateEngine() {
        try {
            // To use the animated GIF, you first have to convert it into a Movie object.
            // You can use the Movie class's decodeStream method to do so
            Movie movie = Movie.decodeStream(
                    getResources().getAssets().open("bootanim-circle.gif"));
            // Once the Movie object has been created,
            // pass it as a parameter to the constructor of the custom Engine
            return new GIFWallpaperEngine(movie);

        } catch (IOException e) {
            Log.d(TAG, "Could not load asset");
            return null;
        }
    }

    private class GIFWallpaperEngine extends Engine
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        // This integer represents the delay between re-draw operations.
        // A value of 20 gives you 50 frames per second.
        private int frameDuration;

        // This boolean lets the engine know if the live wallpaper is currently visible on the screen.
        // This is important, because we should not be drawing the wallpaper when it isn't visible.
        private boolean visible;

        private SurfaceHolder holder;
        private Movie movie;
        private Handler handler;

        private ScaleStrategy scaleStrategy;
        private int width, height;
        private float xScale;
        private float yScale;
        private float xTranslation;
        private float yTranslation;

        private SharedPreferences sharedPref;

        public GIFWallpaperEngine(Movie movie) {
            this.movie = movie;
            handler = new Handler();

            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPref.registerOnSharedPreferenceChangeListener(this);

            onSharedPreferenceChanged(sharedPref, null);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

            this.width = width;
            this.height = height;

            setScalingParameters();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {

            onSharedPreferenceChanged(sharedPref, null);
            setScalingParameters();

            this.visible = visible;
            if (visible) {
                handler.post(drawGIF);
            } else {
                handler.removeCallbacks(drawGIF);
            }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawGIF);
            sharedPref.unregisterOnSharedPreferenceChangeListener(this);
        }

        private Runnable drawGIF = new Runnable() {
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                canvas.save();
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(xScale, yScale);
                movie.draw(canvas, xTranslation, yTranslation);

                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                if (movie.duration() > 0) {
                    movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                }

                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            int framesPerSecond = Integer.parseInt(sharedPreferences.getString(getResources()
                    .getString(R.string.preference_key_frames_per_second), "30"));
            frameDuration = (int) ((1.0f / framesPerSecond) * 1000);

            String scaleType = sharedPreferences.getString(getResources()
                .getString(R.string.preference_key_scale_type), "CENTER_CROP");
            scaleStrategy = getScaleStrategy(scaleType);
        }


        private ScaleStrategy getScaleStrategy(String scaleType){
            if(scaleType.equalsIgnoreCase("CENTER_CROP")){
                return new CenterCropScaleStrategy(movie);
            }
            if(scaleType.equalsIgnoreCase("CENTER_INSIDE")){
                return new CenterInsideScaleStrategy(movie);
            }
            if(scaleType.equalsIgnoreCase("FIT_XY")){
                return new FitXYScaleStrategy(movie);
            }
            if(scaleType.equalsIgnoreCase("CENTER")){
                return new CenterScaleStrategy(movie);
            }

            return new CenterCropScaleStrategy(movie);
        }

        private void setScalingParameters(){
            scaleStrategy.setWidth(width);
            scaleStrategy.setHeight(height);
            xScale = scaleStrategy.getXScale();
            yScale = scaleStrategy.getYScale();
            xTranslation = scaleStrategy.getXTranslation();
            yTranslation = scaleStrategy.getYTranslation();

            Log.d(TAG, "width " + width + " height "+ height);
            Log.d(TAG, "xScale " + xScale);
            Log.d(TAG, "yScale " + yScale);
            Log.d(TAG, "xTranslation " + xTranslation);
            Log.d(TAG, "yTranslation " + yTranslation);
        }
    }
}
