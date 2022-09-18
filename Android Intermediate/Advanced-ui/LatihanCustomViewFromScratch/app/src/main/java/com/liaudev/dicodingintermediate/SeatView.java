package com.liaudev.dicodingintermediate;

import static android.view.MotionEvent.ACTION_DOWN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

/**
 * Created by Budiliauw87 on 2022-09-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class SeatView extends View {
    private Paint backgroundPaint = new Paint();
    private Paint armrestPaint = new Paint();
    private Paint bottomSeatPaint = new Paint();
    private Rect mBounds = new Rect();
    private Paint numberSeatPaint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
    private Paint titlePaint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
    private ArrayList<Seat> seats = new ArrayList<>();
    private Seat selectedSeat;

    public SeatView(Context context) {
        this(context, null);
    }

    public SeatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("SeatView", "onMeasure ");
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        float halfOfHeight = height / 2;
        float halfOfWidth = width / 2;
        float value = -600F;

        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (i % 2 == 0) {
                seat.setX(halfOfWidth - 300F);
                seat.setY(halfOfHeight + value);
            } else {
                seat.setX(halfOfWidth + 100F);
                seat.setY(halfOfHeight + value);
                value += 300F;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("Seat", "ondraw");
        for (Seat seat : seats) {
            drawSeat(canvas, seat);
        }

        String text = "Silakan Pilih Kursi";
        titlePaint.setTextSize(50F);
        canvas.drawText(text, (getWidth() / 2F) - 197F, 100F, titlePaint);
    }

    private void drawSeat(Canvas canvas, Seat seat) {
        // Mengatur Warna ketika Sudah Dibooking
        if (seat.isBooked()) {
            backgroundPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.grey_200, null));
            armrestPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.grey_200, null));
            bottomSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.grey_200, null));
            numberSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));

        } else {
            backgroundPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.blue_500, null));
            armrestPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.blue_700, null));
            bottomSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.blue_200, null));
            numberSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.grey_200, null));

        }

        // Menyimpan State
        canvas.save();

        // Background
        canvas.translate(seat.getX(), seat.getY());
        Path backgroundPath = new Path();
        backgroundPath.addRect(0F, 0F, 200F, 200F, Path.Direction.CCW);
        backgroundPath.addCircle(100F, 50F, 75F, Path.Direction.CCW);
        canvas.drawPath(backgroundPath, backgroundPaint);

        // Sandaran Tangan
        Path armrestPath = new Path();
        armrestPath.addRect(0F, 0F, 50F, 200F, Path.Direction.CCW);
        canvas.drawPath(armrestPath, armrestPaint);
        canvas.translate(150F, 0F);
        armrestPath.addRect(0F, 0F, 50F, 200F, Path.Direction.CCW);
        canvas.drawPath(armrestPath, armrestPaint);

        // Bagian Bawah Kursi
        canvas.translate(-150F, 175F);
        Path bottomSeatPath = new Path();
        bottomSeatPath.addRect(0F, 0F, 200F, 25F, Path.Direction.CCW);
        canvas.drawPath(bottomSeatPath, bottomSeatPaint);

        // Menulis Nomor Kursi
        canvas.translate(0F, -175F);
        numberSeatPaint.setTextSize(50F);
        numberSeatPaint.getTextBounds(seat.getName(), 0, seat.getName().length(), mBounds);
        canvas.drawText(seat.name, 100F - mBounds.centerX(), 100F, numberSeatPaint);

        // Mengembalikan ke pengaturan sebelumnya
        canvas.restore();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float halfOfWidth = this.getWidth() / 2;
        float halfOfHeight = this.getHeight() / 2;
        Range<Float> widthColumnOne = Range.create((halfOfWidth - 300F), (halfOfWidth - 100F));
        Range<Float> widthColumnTwo = Range.create((halfOfWidth + 100F), (halfOfWidth + 300F));

        Range<Float> heightRowOne = Range.create((halfOfHeight - 600F), (halfOfHeight - 400F));
        Range<Float> heightRowTwo = Range.create((halfOfHeight - 300F), (halfOfHeight - 100F));
        Range<Float> heightRowTree = Range.create((halfOfHeight + 0F), (halfOfHeight + 200F));
        Range<Float> heightRowFour = Range.create((halfOfHeight + 300F), (halfOfHeight + 500F));

        if (event.getAction() == ACTION_DOWN) {
            float eventX = event.getX();
            float eventY = event.getY();

            if (widthColumnOne.contains(eventX) && heightRowOne.contains(eventY)) {
                booking(0);
            } else if (widthColumnTwo.contains(eventX) && heightRowOne.contains(eventY)) {
                booking(1);
            } else if (widthColumnOne.contains(eventX) && heightRowTwo.contains(eventY)) {
                booking(2);
            } else if (widthColumnTwo.contains(eventX) && heightRowTwo.contains(eventY)) {
                booking(3);
            } else if (widthColumnOne.contains(eventX) && heightRowTree.contains(eventY)) {
                booking(4);
            } else if (widthColumnTwo.contains(eventX) && heightRowTree.contains(eventY)) {
                booking(5);
            } else if (widthColumnOne.contains(eventX) && heightRowFour.contains(eventY)) {
                booking(6);
            } else {
                booking(7);
            }
        }
        return true;
    }

    private void init() {
        Log.e("SeatView", "init");
        seats.add(new Seat(1, "A1", false));
        seats.add(new Seat(2, "A2", false));
        seats.add(new Seat(3, "B1", false));
        seats.add(new Seat(4, "B2", false));
        seats.add(new Seat(5, "C1", false));
        seats.add(new Seat(6, "C2", false));
        seats.add(new Seat(7, "D1", false));
        seats.add(new Seat(8, "D2", false));
    }

    private void booking(int position) {
        for (Seat seat : seats) {
            seat.setBooked(false);
        }
        Seat seat = seats.get(position);
        seat.setBooked(true);
        this.selectedSeat = seat;
        invalidate();
    }

    public Seat getSelectedSeat() {
        return this.selectedSeat;
    }

    class Seat {
        private int id;
        private Float x = 0F;
        private Float y = 0F;
        private String name;
        private boolean booked = false;

        public Seat(int id, String name, boolean booked) {
            this.id = id;
            this.name = name;
            this.booked = booked;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Float getX() {
            return this.x;
        }

        public void setX(Float x) {
            this.x = x;
        }

        public Float getY() {
            return this.y;
        }

        public void setY(Float y) {
            this.y = y;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isBooked() {
            return this.booked;
        }

        public void setBooked(boolean booked) {
            this.booked = booked;
        }
    }

}
