package it.moondroid.gifwallpaper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import it.moondroid.gifwallpaper.R;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    private GifImageView gifImageView;
    private Button btnToggle;
    private Button btnBlur;
    private Button btnSetWallpaper;
    private String wallpaperFilePath = "";

    private boolean shouldBlur = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        btnToggle = (Button) findViewById(R.id.btnToggle);
        btnBlur = (Button) findViewById(R.id.btnBlur);
        btnSetWallpaper = (Button) findViewById(R.id.btnSetWallpaper);
        final Button btnClear = (Button) findViewById(R.id.btnClear);

//        blur = BlurMaskFilter.Blur.newInstance(this);
//        gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
//            @Override
//            public Bitmap onFrameAvailable(Bitmap bitmap) {
//                if (shouldBlur)
//                    return blur.blur(bitmap);
//                return bitmap;
//            }
//        });

        btnToggle.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBlur.setOnClickListener(this);
        btnSetWallpaper.setOnClickListener(this);

        try {
            InputStream inputStream = getResources().getAssets().open("bootanim-circle.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
            Log.d(TAG, "GIF width is " + gifImageView.getGifWidth());
            Log.d(TAG, "GIF height is " + gifImageView.getGifHeight());
        } catch (IOException e) {
            Log.w(TAG, "Could not load asset");
        }
//        new GifDataDownloader() {
//            @Override
//            protected void onPostExecute(final byte[] bytes) {
//                gifImageView.setBytes(bytes);
//                gifImageView.startAnimation();
//                Log.d(TAG, "GIF width is " + gifImageView.getGifWidth());
//                Log.d(TAG, "GIF height is " + gifImageView.getGifHeight());
//            }
//        }.execute("http://gifs.joelglovier.com/aha/aha.gif");

        handleSendImage();
    }


    @Override
    public void onClick(final View v) {

        switch (v.getId()){
            case R.id.btnBlur:
                shouldBlur = !shouldBlur;
                break;
            case R.id.btnToggle:
                if (gifImageView.isAnimating())
                    gifImageView.stopAnimation();
                else
                    gifImageView.startAnimation();
                break;
            case R.id.btnClear:
                gifImageView.clear();
                break;
            case R.id.btnSetWallpaper:
                setWallpaper();
                break;

        }
    }

    private void setWallpaper(){
        if(!wallpaperFilePath.isEmpty()){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPref.edit().putString(getResources()
                    .getString(R.string.preference_key_file_path), wallpaperFilePath)
                    .apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, WallpaperPreferenceActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void handleSendImage() {
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_VIEW.equals(action) && type != null) {

            String filePath = getIntent().getData().getPath();
            Log.d(TAG, "filePath: "+filePath);
            try {
                FileInputStream iStream =  new FileInputStream(filePath);
                byte[] bytes = IOUtils.toByteArray(iStream);
                gifImageView.setBytes(bytes);
                gifImageView.startAnimation();
                wallpaperFilePath = filePath;

            } catch (FileNotFoundException e) {
                Log.w(TAG, "Could not find file");
            } catch (IOException e) {
                Log.w(TAG, "Could not load file");
            }
        }
    }
}
