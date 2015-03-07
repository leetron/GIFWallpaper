package it.moondroid.gifwallpaper.scaling;

import android.graphics.Movie;

/**
 * Created by Marco on 07/03/2015.
 *
 * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width and height)
 * of the image will be equal to or less than the corresponding dimension of the view (minus padding).
 */
public class CenterInsideScaleStrategy extends AbstractScaleStrategy {

    float scale;

    public CenterInsideScaleStrategy(Movie movie, int width, int height) {
        super(movie, width, height);

        float xScale = ((float)width) / movie.width();
        float yScale = ((float)height) / movie.height();
        scale = Math.min(xScale, yScale);
    }

    @Override
    public float getXScale() {
        return scale;
    }

    @Override
    public float getYScale() {
        return scale;
    }

    @Override
    public float getXTranslation() {
        return width / (2 * scale) - movie.width() / 2;
    }

    @Override
    public float getYTranslation() {
        return height / (2 * scale) - movie.height() / 2;
    }
}
