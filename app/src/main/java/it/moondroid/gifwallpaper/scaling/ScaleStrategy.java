package it.moondroid.gifwallpaper.scaling;

/**
 * Created by Marco on 07/03/2015.
 */
public interface ScaleStrategy {

    public float getXScale();
    public float getYScale();

    public float getXTranslation();
    public float getYTranslation();

    public void setWidth(int width);
    public void setHeight(int height);
}
