package it.moondroid.gifwallpaper.scaling;

import android.graphics.Movie;

/**
 * Created by Marco on 07/03/2015.
 */
public abstract class AbstractScaleStrategy implements ScaleStrategy {

    protected Movie movie;
    protected int width;
    protected int height;

    public AbstractScaleStrategy(Movie movie){
        this.movie = movie;
    }

    public AbstractScaleStrategy(Movie movie, int width, int height){
        this.movie = movie;
        this.width = width;
        this.height = height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}
