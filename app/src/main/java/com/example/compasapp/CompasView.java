package com.example.compasapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CompasView extends View {

    public int azimuth;
    Bitmap bitmap;
    public CompasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.compass);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth()/2;
        int cy = getHeight()/2;

        int x = cx - bitmap.getWidth()/2;
        int y = cy - bitmap.getHeight()/2;

        if(azimuth == 180){
            azimuth = -azimuth;
        }

        canvas.rotate(-azimuth,cx,cy);
        canvas.drawBitmap(bitmap,x,y,null);
    }
}
