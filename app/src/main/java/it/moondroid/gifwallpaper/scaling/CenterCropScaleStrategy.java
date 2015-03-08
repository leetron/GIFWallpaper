package it.moondroid.gifwallpaper.scaling;

import android.graphics.Movie;
import android.util.Log;

/**
 * Created by Marco on 07/03/2015.
 *
 * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width and height)
 * of the image will be equal to or less than the corresponding dimension of the view (minus padding).
 */
public class CenterCropScaleStrategy extends AbstractScaleStrategy {

    float scale;
    float xScale, yScale;

    public CenterCropScaleStrategy(Movie movie, int width, int height) {
        super(movie, width, height);

        xScale = ((float)width) / movie.width();
        yScale = ((float)height) / movie.height();
        setScale();
    }

    public CenterCropScaleStrategy(Movie movie) {
        super(movie);
        setScale();
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        xScale = ((float)width) / movie.width();
        setScale();
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        yScale = ((float)height) / movie.height();
        setScale();
    }

    private void setScale(){
        scale = Math.max(xScale, yScale);
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
