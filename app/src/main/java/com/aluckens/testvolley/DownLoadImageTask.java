package com.aluckens.testvolley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ING_LUCKENS on 4/9/2018.
 */

public class DownLoadImageTask  extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;


    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

     @Override
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
