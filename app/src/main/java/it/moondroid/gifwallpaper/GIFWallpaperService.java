package it.moondroid.gifwallpaper;

import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Marco on 05/03/2015.
 */
public class GIFWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        try {
            // To use the animated GIF, you first have to convert it into a Movie object.
            // You can use the Movie class's decodeStream method to do so
            Movie movie = Movie.decodeStream(
                    getResources().getAssets().open("AndroidParticles.gif"));
            // Once the Movie object has been created,
            // pass it as a parameter to the constructor of the custom Engine
            return new GIFWallpaperEngine(movie);

        }catch(IOException e){
            Log.d("GIF", "Could not load asset");
            return null;
        }
    }

    private class GIFWallpaperEngine extends WallpaperService.Engine {

        // This integer represents the delay between re-draw operations.
        // A value of 20 gives you 50 frames per second.
        private final int frameDuration = 20;

        // This boolean lets the engine know if the live wallpaper is currently visible on the screen.
        // This is important, because we should not be drawing the wallpaper when it isn't visible.
        private boolean visible;

        private SurfaceHolder holder;
        private Movie movie;
        private Handler handler;

        public GIFWallpaperEngine(Movie movie) {
            this.movie = movie;
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
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
        }

        private Runnable drawGIF = new Runnable() {
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(3f, 3f);
                movie.draw(canvas, 0, 0);
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                if (movie.duration() > 0){
                    movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                }

                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }
        }
    }
}
