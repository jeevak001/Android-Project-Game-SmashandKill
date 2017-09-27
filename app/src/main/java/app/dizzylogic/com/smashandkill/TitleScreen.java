package app.dizzylogic.com.smashandkill;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by Jeeva K on 5/2/2015.
 */
public class TitleScreen extends SurfaceView implements Runnable {

    Thread thread = null;
    Context myContext;
    SurfaceHolder holder;
    Paint paint;
    Canvas myCanvas;
    volatile boolean running = false;
    Bitmap gameBackground, main, play,playPressed,scores,scoresPressed,scoresBackground;
    boolean isPlayPressed = false, isScoresPressed = false,inGame = false,inScores = false;
    boolean gameFirstTime = true;
    Bitmap mole1,mole2,mole3,mole4,mole5,mole6,mole7,moleCover,boom;
    boolean moleRising = true;
    int gameScoreIncrement = 1;
    int moleNow,moleSpeed = 1;
    int moleRandom = 3;
    int moleMaXArray[] = {300,250,300,250,300,250,300};
    int moleXArray[] = {60,160,260,360,460,560,660};
    int gameScore = 0;
    boolean moleClicked = false;

    boolean inMain = true;

    public TitleScreen(Context context) {
        super(context);
        myContext = context;
        this.holder = getHolder();

        main = BitmapFactory.decodeResource(getResources(), R.drawable.main);
        main = Bitmap.createScaledBitmap(main, 800, 480, false);
        boom = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
        boom = Bitmap.createScaledBitmap(boom, 120, 94, false);
        play = BitmapFactory.decodeResource(getResources(), R.drawable.play);
        play = Bitmap.createScaledBitmap(play, 300, 75, false);
        playPressed = BitmapFactory.decodeResource(getResources(), R.drawable.play_pressed);
        playPressed = Bitmap.createScaledBitmap(playPressed, 300, 75, false);
        scores = BitmapFactory.decodeResource(getResources(), R.drawable.scores);
        scores = Bitmap.createScaledBitmap(scores, 300, 75, false);
        scoresPressed = BitmapFactory.decodeResource(getResources(), R.drawable.scores_pressed);
        scoresPressed = Bitmap.createScaledBitmap(scoresPressed, 300, 75, false);
        gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
        gameBackground = Bitmap.createScaledBitmap(gameBackground, 800, 480, false);
        scoresBackground = BitmapFactory.decodeResource(getResources(), R.drawable.scores_background);
        scoresBackground = Bitmap.createScaledBitmap(scoresBackground, 800, 480, false);
        moleCover = BitmapFactory.decodeResource(getResources(), R.drawable.mole_cover);
        moleCover = Bitmap.createScaledBitmap(moleCover, 100, 233, false);
        mole1 = BitmapFactory.decodeResource(getResources(), R.drawable.mole);
        mole1 = Bitmap.createScaledBitmap(mole1, 80, 200, false);
        mole2 = mole1;
        mole3 = mole1;
        mole4 = mole1;
        mole5 = mole1;
        mole6 = mole1;
        mole7 = mole1;
        moleNow = moleMaXArray[moleRandom];

    }

    public void setActionForBackButton(){

        if(inGame){
            inGame = false;
            inMain = true;
            isPlayPressed = false;
            gameFirstTime = true;
        }
        if(inScores){
            inScores = false;
            inMain = true;
            isScoresPressed = false;
        }
    }

    public void setRunning(boolean tmp) {
        running = tmp;
    }

    public void resume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                thread.join();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void run() {

        while (running) {
            if (!holder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = holder.lockCanvas();
            myCanvas = canvas;
            drawBitmap(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:

                if(inMain) {
                    if (X >= 450 && Y >= 250 && X <= 750 && Y <= 325) {
                        isPlayPressed = true;
                    }
                    if (X >= 450 && Y >= 350 && X <= 750 && Y <= 425) {
                        isScoresPressed = true;
                    }
                }
                if(inMain == false && inGame == true){
                    if( (X >= moleXArray[moleRandom]) && (X <= moleXArray[moleRandom]+80) &&
                            (moleNow <= (moleMaXArray[moleRandom]-20)) && (Y < moleMaXArray[moleRandom]) && (!moleClicked)){
                        myCanvas.drawBitmap(boom,400-(boom.getWidth()/2),50-(boom.getHeight()/2),null);

                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if(inMain) {
                    if (X >= 450 && Y >= 250 && X <= 750 && Y <= 325) {
                        inMain = false;
                        inScores = false;
                        inGame = true;
                    }
                    if (X >= 450 && Y >= 350 && X <= 750 && Y <= 425) {
                        inMain = false;
                        inScores = true;
                        inGame=false;
                    }
                }
                if(inMain == false && inGame == true){
                    if( (X >= moleXArray[moleRandom]) && (X <= moleXArray[moleRandom]+80) &&
                            (moleNow <= (moleMaXArray[moleRandom]-20)) && (Y < moleMaXArray[moleRandom]) && (!moleClicked)){
                            gameScore += gameScoreIncrement;
                            moleClicked = true;
                            if(gameScore == 50){
                                moleSpeed = 2;
                                gameScoreIncrement = 2;
                            }
                            if(gameScore == 100){
                                moleSpeed = 3;
                                gameScoreIncrement = 3;
                            }
                            if(gameScore == 200){
                                moleSpeed = 4;
                                gameScoreIncrement = 4;
                            }

                    }
                }
                break;
        }
        return true;
    }

    public void drawBitmap(Canvas c) {

        if (inMain) {
            c.drawBitmap(main, 0, 0, null);
            if (!isPlayPressed)
                c.drawBitmap(play, 450, 250, null);
            else
                c.drawBitmap(playPressed,  450, 250, null);

            if (!isScoresPressed)
                c.drawBitmap(scores, 450, 350, null);
            else
                c.drawBitmap(scoresPressed,  450, 350, null);
        }

        if(inMain == false && inGame == true){
            c.drawBitmap(gameBackground,  0, 0, null);
            setupMoles(c);
            setupMoleCovers(c);
            moveMoles(c);

        }

        if(inMain == false && inScores == true){
            c.drawBitmap(scoresBackground,  0, 0, null);
        }
    }

    private void setupMoles(Canvas c) {
        c.drawBitmap(mole1,  60, 300, null);
        c.drawBitmap(mole2,  160, 250, null);
        c.drawBitmap(mole3,  260, 300, null);
        c.drawBitmap(mole4,  360, 250, null);
        c.drawBitmap(mole5,  460, 300, null);
        c.drawBitmap(mole6,  560, 250, null);
        c.drawBitmap(mole7,  660, 300, null);
    }


    public void setupMoleCovers(Canvas c) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        c.drawText(String.valueOf(gameScore), 10, 25, paint);
        c.drawBitmap(moleCover, 50, 300, null);
        c.drawBitmap(moleCover,  150, 250, null);
        c.drawBitmap(moleCover,  250, 300, null);
        c.drawBitmap(moleCover,  350, 250, null);
        c.drawBitmap(moleCover,  450, 300, null);
        c.drawBitmap(moleCover,  550, 250, null);
        c.drawBitmap(moleCover,  650, 300, null);
    }

    public void moveMoles(Canvas c) {


       if((moleNow <= moleMaXArray[moleRandom]) && (moleNow >= (moleMaXArray[moleRandom]-90)) && (moleRising) && (!moleClicked)){
           c.drawBitmap(mole1,moleXArray[moleRandom],moleNow,null);
           setupMoleCovers(c);
           moleNow -= moleSpeed;
           Log.e("Jeeva",String.valueOf(moleNow));
           if(moleNow == (moleMaXArray[moleRandom]-90)){
               moleRising = false;
           }
       }
       if((moleNow >=(moleMaXArray[moleRandom]-90)) && (moleNow <= moleMaXArray[moleRandom]) && (!moleRising) && (!moleClicked)){
           c.drawBitmap(mole1,moleXArray[moleRandom],moleNow,null);
           setupMoleCovers(c);
           moleNow += moleSpeed;
           if(moleNow == moleMaXArray[moleRandom]){
               moleRising = true;
               moleRandom = (int)((Math.random())*7);
               Log.e("MOLE_NO",String.valueOf(moleRandom));
               moleNow = moleMaXArray[moleRandom];
               Log.e("MOLE_NOW",String.valueOf(moleNow));
           }
       }

       if(moleClicked){
           moleRising = true;
           moleRandom = (int)((Math.random())*7);
           Log.e("MOLE_NO",String.valueOf(moleRandom));
           moleNow = moleMaXArray[moleRandom];
           Log.e("MOLE_NOW",String.valueOf(moleNow));
           moleClicked = false;
       }


    }


}
