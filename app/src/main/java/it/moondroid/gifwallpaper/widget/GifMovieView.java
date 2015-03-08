package it.moondroid.gifwallpaper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by Marco on 08/03/2015.
 */
public class GifMovieView extends View {

    private Movie mMovie;
    private long mMoviestart;
    private InputStream mStream;

    public GifMovieView(Context context) {
        super(context);
    }

    public GifMovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GifMovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GifMovieView(Context context, InputStream stream) {
        super(context);
        mStream = stream;
        mMovie = Movie.decodeStream(mStream);
    }

    public void setMovieStream(InputStream stream){
        mStream = stream;
        mMovie = Movie.decodeStream(mStream);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        //super.onDraw(canvas);

        if (mMovie != null){
            final long now = SystemClock.uptimeMillis();
            if (mMoviestart == 0) {
                mMoviestart = now;
            }
            final int relTime = (int) ((now - mMoviestart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, 10, 10);
            this.invalidate();
        }

    }
}
