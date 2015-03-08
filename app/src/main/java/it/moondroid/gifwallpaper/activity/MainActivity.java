package it.moondroid.gifwallpaper.activity;

import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.felipecsl.gifimageview.library.GifImageView;

import java.io.IOException;
import java.io.InputStream;

import it.moondroid.gifwallpaper.R;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        try {
//            InputStream stream = getAssets().open("bootanim-circle.gif");
//            GifMovieView gifMovieView = (GifMovieView)findViewById(R.id.gifView);
//            gifMovieView.setMovieStream(stream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static final String TAG = "MainActivity";
    private GifImageView gifImageView;
    private Button btnToggle;
    private Button btnBlur;

    private boolean shouldBlur = false;
    BlurMaskFilter.Blur blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        btnToggle = (Button) findViewById(R.id.btnToggle);
        btnBlur = (Button) findViewById(R.id.btnBlur);
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


        try {
            InputStream inputStream = getResources().getAssets().open("bootanim-circle.gif");

        }catch (IOException e){

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
    }


    @Override
    public void onClick(final View v) {
        if (v.equals(btnToggle)) {
            if (gifImageView.isAnimating())
                gifImageView.stopAnimation();
            else
                gifImageView.startAnimation();
        } else if (v.equals(btnBlur)) {
            shouldBlur = !shouldBlur;
        } else {
            gifImageView.clear();
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


}
