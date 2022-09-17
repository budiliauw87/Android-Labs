package com.liaudev.dicodingintermediate;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * Created by Budiliauw87 on 2022-09-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MyEditText extends AppCompatEditText implements View.OnTouchListener {
    private Drawable clearButtonImage ;
    public MyEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public MyEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Menambahkan hint pada editText
        setHint("Masukan nama anda");
        // Menambahkan text aligmnet pada editText
        setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (getCompoundDrawables()[2] != null) {
            Float clearButtonStart;
            Float clearButtonEnd;
            boolean isClearButtonClicked = false;
            if (getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = Float.valueOf((clearButtonImage.getIntrinsicWidth() + getPaddingStart()));
                if(motionEvent.getX() < clearButtonEnd){
                    isClearButtonClicked = true;
                }
            } else {
                clearButtonStart = Float.valueOf((getWidth() - getPaddingEnd() - clearButtonImage.getIntrinsicWidth()));
                if(motionEvent.getX() > clearButtonStart){
                    isClearButtonClicked = true;
                }
            }

            if (isClearButtonClicked) {
                switch (motionEvent.getAction()){
                    case ACTION_DOWN:
                        clearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
                        showClearButton();
                        return true;
                    case ACTION_UP:
                        clearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
                        if(getText()!=null){
                            setText(null);
                        }
                        hideClearButton();
                        return true;
                    default:
                        return false;
                }
            }

        }
        return false;
    }

    private void init(){
        // Menginisialisasi gambar clear button
        clearButtonImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
        // Menambahkan aksi kepada clear button
        setOnTouchListener(this);

        // Menambahkan aksi ketika ada perubahan text akan memunculkan clear button
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) showClearButton(); else hideClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    // Menampilkan clear button
    private void showClearButton() {
        setButtonDrawables(null,null,clearButtonImage,null);
    }

    // Menghilangkan clear button
    private void hideClearButton() {
        setButtonDrawables(null,null,null,null);
    }

    //Mengkonfigurasi button
    private void setButtonDrawables(Drawable startOfTheText, Drawable topOfTheText,Drawable endOfTheText, Drawable bottomOfTheText){
        // Sets the Drawables (if any) to appear to the left of,
        // above, to the right of, and below the text.
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText);
    }
}
