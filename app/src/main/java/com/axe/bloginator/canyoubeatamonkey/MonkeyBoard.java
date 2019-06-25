package com.axe.bloginator.canyoubeatamonkey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static android.graphics.Color.rgb;

/**
 * Created by alex on 12/26/14.
 */
public class MonkeyBoard extends View {
    private int mLevel;
    private int mLevelUp;
    private int mDifficulty;
    private boolean mLevelDown;
    private int mLevelDownC;
    private boolean mPlayMusic;
    private MediaPlayer mediaPlayer;

    private float mCellWidth;
    private float mCellHeight;
    private int mWidth;
    private int mHeight;

    private int mNumberLeft;
    private int mNumberTop;

    private int mCells[][];

    private Paint mBackgroundColor;
    private Paint mCellValuePaint;

    private int thenumbers = 1; // what number you're on

    private boolean counterOne = false;
    private boolean counterOneStarted = false;

    private boolean generatedBoard = false;

    private MyTimer mNoteTimer;
    private boolean mNoteTimerI;
    private boolean mNoteTimerR;
    //private int mNoteT;

    private enum mNoteT {
        tooBad, levelUp, levelDown
    }

    mNoteT noteType;

    //private boolean mLost;
    //private boolean mLostTimer = false;
    //private boolean mLevelUpT;
    //private boolean mLevelUpTimer = false;

    public MonkeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBackgroundColor = new Paint();
        mBackgroundColor.setColor(-16777216);

        mCellValuePaint = new Paint();
        mCellValuePaint.setColor(rgb(0,0,0));
        mCellValuePaint.setTextSize(18f);
        int mGestureThreshold = (int) (18.0f * getContext().getResources().getDisplayMetrics().density + 0.9f);
        mCellValuePaint.setTextSize(mGestureThreshold);

        mNumberLeft = (int) ((mCellWidth - mCellValuePaint.measureText("99")) / 2);
        mNumberTop = (int) ((mCellHeight - mCellValuePaint.getTextSize()) / 2);

        setFocusable(true);
        setFocusableInTouchMode(true);

        mCells = new int[6][14];

        mLevel = 3; // loaded from config
        mLevelDown = true; // loaded from config
        mPlayMusic = true; // loaded from config
        mDifficulty = 1; // loaded from config

        mLevelUp = 1;
        mLevelDownC = 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //int mCellWidth = 3;

        //canvas.drawRect(1, 1, 10, 10, mBackgroundColorSecondary);

        //canvas.drawRect(3 * mCellWidth, 0, 6 * mCellWidth, 3 * mCellWidth, mBackgroundColorSecondary);
        //canvas.drawRect(0, 3 * mCellWidth, 3 * mCellWidth, 6 * mCellWidth, mBackgroundColorSecondary);
        //canvas.drawRect(6 * mCellWidth, 3 * mCellWidth, 9 * mCellWidth, 6 * mCellWidth, mBackgroundColorSecondary);
        //canvas.drawRect(3 * mCellWidth, 6 * mCellWidth, 6 * mCellWidth, 9 * mCellWidth, mBackgroundColorSecondary);

        /*for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                //Cell cell = mCells.getCell(row, col);

                /*cellLeft = Math.round((col * mCellWidth) + paddingLeft);
                cellTop = Math.round((row * mCellHeight) + paddingTop);

                canvas.drawRect(
                        cellLeft, cellTop,
                        cellLeft + mCellWidth, cellTop + mCellHeight,
                        mBackgroundColorReadOnly);
            }
        }

        for (int row = 0; row < 14; row++) {
            for (int col = 0; col < 6; col++) {
                canvas.drawRect(
                        row * mCellHeight, col * mCellWidth,
                        row + mCellWidth, col + mCellHeight,
                        mBackgroundColorSecondary);
            }
        }*/

        int cellLeft;
        int cellTop;

        int paddingLeft = 0;
        int paddingTop = 0;

        float numberAscent = mCellValuePaint.ascent();

        //float cellTextSize = mCellHeight * 0.75f;
        //mCellValuePaint.setTextSize(cellTextSize);
        //mNumberLeft = (int) ((mCellWidth - mCellValuePaint.measureText("99")) / 2);
        //mNumberTop = (int) ((mCellHeight - mCellValuePaint.getTextSize()) / 2);

        if (thenumbers > 1 || counterOne){ // if you pressed a button or the timer ran out
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 14; col++) {

                    cellLeft = Math.round((col * mCellWidth) + paddingLeft);
                    cellTop = Math.round((row * mCellHeight) + paddingTop);

                    mBackgroundColor.setColor(rgb(col*10, row*10, col+row*10));

                    if ( mCells[row][col] != 0 && mCells[row][col] >= thenumbers) {
                        canvas.drawRect(
                                cellLeft, cellTop,
                                cellLeft + mCellWidth, cellTop + mCellHeight,
                                mBackgroundColor);
                    }
                }
            }
        } else {
                if (generatedBoard) { // draw the numbers, nothing has happened yet
                    for (int row = 0; row < 6; row++) {
                        for (int col = 0; col < 14; col++) {

                            cellLeft = Math.round((col * mCellWidth) + paddingLeft);
                            cellTop = Math.round((row * mCellHeight) + paddingTop);

                            mBackgroundColor.setColor(rgb(col * 10, row * 10, col + row * 10));

                            if (mCells[row][col] != 0) {
                                /*canvas.drawRect(
                                        cellLeft, cellTop,
                                        cellLeft + mCellWidth, cellTop + mCellHeight,
                                        mBackgroundColorSecondary);
                                        */

                                canvas.drawText(Integer.toString(mCells[row][col]),
                                        cellLeft + mNumberLeft + (mCellWidth / 2),
                                        cellTop + mNumberTop + (mCellHeight / 2) - numberAscent,
                                        mCellValuePaint);
                            }
                        }
                    }
                } else { // board hasn't been generated yet so generate it
                    generateBoard();
                    invalidate();
                }

        }

        /*switch (mDifficulty) {
            case 1:
                break;
            case 2:
                if (!counterOneStarted) startCounter();
            case 3:
                break;
        }*/

        if (mNoteTimerI){
            Bitmap note;

            switch (noteType) {
                case tooBad:
                    note = BitmapFactory.decodeResource(getResources(), R.drawable.monkeytoobad);
                    break;
                case levelUp:
                    note = BitmapFactory.decodeResource(getResources(), R.drawable.monkeylevelup);
                    break;
                case levelDown:
                    note = BitmapFactory.decodeResource(getResources(), R.drawable.monkeyleveldown);
                    break;
                default:
                    note = BitmapFactory.decodeResource(getResources(), R.drawable.monkeytoobad);
                    break;
            }
            canvas.drawBitmap(note, (mWidth/2) - (note.getScaledWidth(canvas) / 2), (mHeight/2), null);
            invalidate();

            if (!mNoteTimerR) {
                mNoteTimer = new MyTimer(1500, 500) {
                    @Override
                    public void onFinish() {
                        mNoteTimerI = false;
                        mNoteTimerR = false;
                        invalidate();
                    }
                };
                mNoteTimer.start();
                mNoteTimerR = true;
            }
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height = heightSize;

        mCellWidth = (width - getPaddingLeft() - getPaddingRight() ) / 14.0f;
        mCellHeight = (height - getPaddingTop() - getPaddingBottom() ) / 6.0f;

        mWidth = width;
        mHeight = height;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

            int x = (int) event.getX();
            int y = (int) event.getY();
            int where;

        switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    where = getCellAtPoint(x, y);
                    //Context context = getApplicationContext();
                    //Toast toast;
                    //toast = Toast.makeText(getContext(), Integer.toString(where), Toast.LENGTH_SHORT);
                    //toast.show();

                    if (thenumbers == where){ // the correct number was pressed
                        thenumbers = thenumbers + 1;

                        if(thenumbers > mLevel){ // player won
                            mLevelUp = mLevelUp + 1;

                            if (mLevelUp > 3){
                                mNoteTimerI = true;
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.monkeylevelup);
                                mediaPlayer.setLooping(false);
                                mediaPlayer.start();
                                noteType = mNoteT.levelUp;
                                mLevel = mLevel + 2;
                                mLevelUp = 1;
                                mLevelDownC = 1;
                            }
                            thenumbers = 1;
                            counterOne = false;
                            counterOneStarted = false;
                            generatedBoard = false;
                            //tim.cancel();
                        }

                    } else { // wrong number was pressed
                        if (where != 0) {
                            if (mLevelDown){
                                mLevelDownC = mLevelDownC + 1;
                            }

                            if (mLevelDownC > 3 && mLevelDown &&  mLevel > 3){
                                mNoteTimerI = true;
                                mediaPlayer = MediaPlayer.create(getContext(), R.raw.monkeyleveldown);
                                mediaPlayer.setLooping(false);
                                mediaPlayer.start();
                                noteType = mNoteT.levelDown;
                                mLevel = mLevel - 2;
                                mLevelUp = 1;
                                mLevelDownC = 1;
                            } else {
                                mNoteTimerI = true;
                                noteType = mNoteT.tooBad;
                                //mLevelUp = 1;
                                //mLevelDownC = 1;
                            }

                            thenumbers = 1;
                            counterOne = false;
                            counterOneStarted = false;
                            generatedBoard = false;
                            //tim.cancel();
                        }
                    }
                    invalidate();

                    /*
                    notify();
                    invalidate(); // selected cell has changed, update board as soon as you can
                    */
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
            //postInvalidate();

        return true;
    }

    private int getCellAtPoint(int x, int y) {
        // take into account padding
        int lx = x - getPaddingLeft();
        int ly = y - getPaddingTop();

        int row = (int) (ly / mCellHeight);
        int col = (int) (lx / mCellWidth);

        return mCells[row][col];
    }

    private int randInt(){
        Random rand = new Random();
        return rand.nextInt((100)) + 1;
    }

    public class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            counterOne = true;
            invalidate();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
        }

    }

    private void generateBoard(){
        int theCounter = 1; // for board generation - current number
        //thecounter = 1;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 14; col++) {
                mCells[row][col] = 0;
            }
        }

        while (theCounter <= mLevel) {
            for (int row = 0; row < 6; row++) {
                if (theCounter > mLevel) break;
                for (int col = 0; col < 14; col++) {
                    if (theCounter > mLevel) break;
                    if (randInt() == 50 && mCells[row][col] == 0) {
                        mCells[row][col] = theCounter;
                        theCounter = theCounter + 1;
                    }
                }
            }
        }
        generatedBoard = true;
    }


}
