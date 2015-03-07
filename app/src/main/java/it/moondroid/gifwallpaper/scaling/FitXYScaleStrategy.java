package it.moondroid.gifwallpaper.scaling;

import android.graphics.Movie;

/**
 * Created by Marco on 07/03/2015.
 *
 * Scale the image filling the entire screen.
 */
public class FitXYScaleStrategy extends AbstractScaleStrategy {

    public FitXYScaleStrategy(Movie movie, int width, int height) {
        super(movie, width, height);
    }

    @Override
    public float getXScale() {
        return ((float)width) / movie.width();
    }

    @Override
    public float getYScale() {
        return ((float)height) / movie.height();
    }

    @Override
    public float getXTranslation() {
        return 0;
    }

    @Override
    public float getYTranslation() {
        return 0;
    }
}
