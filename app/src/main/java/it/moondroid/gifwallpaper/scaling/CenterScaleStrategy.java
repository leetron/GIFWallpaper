package it.moondroid.gifwallpaper.scaling;

import android.graphics.Movie;

/**
 * Created by Marco on 07/03/2015.
 *
 * Center the image in the view, but perform no scaling.
 */
public class CenterScaleStrategy extends AbstractScaleStrategy {

    public CenterScaleStrategy(Movie movie, int width, int height) {
        super(movie, width, height);
    }

    public CenterScaleStrategy(Movie movie) {
        super(movie);
    }

    @Override
    public float getXScale() {
        return 1.0f;
    }

    @Override
    public float getYScale() {
        return 1.0f;
    }

    @Override
    public float getXTranslation() {
        return width / 2 - movie.width() / 2;
    }

    @Override
    public float getYTranslation() {
        return height / 2 - movie.height() / 2;
    }
}
