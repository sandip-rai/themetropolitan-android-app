package com.sandiprai.themetropolitan;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import java.util.HashMap;

public class processImage {
    // https://cypressnorth.com/mobile-application-development/setting-android-google-volley-imageloader-networkimageview/
    private static processImage mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public processImage(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    public processImage(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    private processImage() {
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final HashMap<String, Bitmap> mCache = new HashMap<>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static processImage getInstance(){
        if(mInstance == null){
            mInstance = new processImage();
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
            return this.mImageLoader;
    }
}
