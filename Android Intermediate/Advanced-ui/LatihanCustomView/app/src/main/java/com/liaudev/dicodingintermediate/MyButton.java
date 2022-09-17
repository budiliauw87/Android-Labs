package com.liaudev.dicodingintermediate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

/**
 * Created by Budiliauw87 on 2022-09-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MyButton extends AppCompatButton {
    private int txtColor = 0;
    private Drawable enabledBackground;
    private Drawable disabledBackground;

    public MyButton(@NonNull Context context) {
        super(context);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Mengubah background dan text dari Button
        if(isEnabled()){
            setBackground(enabledBackground);
            setText("Submit");
        }else{
            setText("Isi Dulu");
            setBackground(disabledBackground);
        }

        // Mengubah warna text pada button
        setTextColor(txtColor);

        // Mengubah ukuran text pada button
        setTextSize(12f);

        // Menjadikan object pada button menjadi center
        setGravity(Gravity.CENTER);
    }

    // pemanggilan Resource harus dilakukan saat kelas MyButton diinisialisasi, jadi harus dikeluarkan dari metode onDraw
    private void init(){
        txtColor = ContextCompat.getColor(getContext(),android.R.color.background_light);
        enabledBackground = ContextCompat.getDrawable(getContext(), R.drawable.bg_button);
        disabledBackground = ContextCompat.getDrawable(getContext(), R.drawable.bg_button_disable);
    }
}
