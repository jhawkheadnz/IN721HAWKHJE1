package bit.hawkhje1.hardwaresensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Work on 5/16/2016.
 */
public class CustomCanvas extends SurfaceView implements Runnable {

    private static final float TXT_FONT_SIZE = 45f;
    private static final float TEXT_XPOS = 100f;
    private static final float TEXT_YPOS = 100f;
    private Thread thread;
    private boolean canDraw;
    private Canvas canvas;

    private Paint boxColor;
    private Paint backgroundColor;
    private RectF box;

    private float boxWidth;
    private float boxHeight;
    private float boxX;
    private float boxY;

    private String infoText;
    private Paint textPaint;
    private Paint linePaint;

    public void drawText(String text){
        this.infoText = text;
    }

    public float getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(float boxHeight) {
        this.boxHeight = boxHeight;
    }

    public float getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(float boxWidth) {
        this.boxWidth = boxWidth;
    }

    public float getBoxX() {
        return boxX;
    }

    public void setBoxX(float boxX) {
        this.boxX = boxX;
    }

    public float getBoxY() {
        return boxY;
    }

    public void setBoxY(float boxY) {
        this.boxY = boxY;
    }

    private SurfaceHolder surfaceHolder;

    public CustomCanvas(Context context) {
        super(context);

        // Initialize Drawing stuff
        initializeDrawingContent();
    }

    @Override
    public void run() {
        while(canDraw){

            if(!surfaceHolder.getSurface().isValid())
                continue;

            canvas = surfaceHolder.lockCanvas();

            canvas.drawRect(canvas.getClipBounds(), backgroundColor);
            canvas.drawText(infoText, TEXT_XPOS, TEXT_YPOS, textPaint);
            canvas.drawRect(boxX, boxY, (boxX + boxWidth), (boxY + boxHeight), boxColor);

            float centerBoxX = boxX + (boxWidth / 2);
            float centerBoxY = boxY + (boxWidth / 2);
            float width = canvas.getWidth();
            float height = canvas.getHeight();

            canvas.drawLine(0, 0, centerBoxX, centerBoxY, linePaint);
            canvas.drawLine(0, height, centerBoxX, centerBoxY, linePaint);
            canvas.drawLine(width, 0, centerBoxX, centerBoxY, linePaint);
            canvas.drawLine(width, height, centerBoxX, centerBoxY, linePaint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public float getCanvasWidth(){ return canvas.getWidth(); }
    public float getCanvasHeight(){ return canvas.getHeight(); }

    private void initializeDrawingContent(){

        // get the surface holder
        surfaceHolder = getHolder();

        //setup background color
        backgroundColor = new Paint();
        backgroundColor.setColor(Color.BLACK);

        boxColor = new Paint();
        boxColor.setColor(Color.parseColor("#CC9900"));

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);

        boxX = 0;
        boxY = 0;
        boxWidth = 100;
        boxHeight = 100;

        float width = boxX + boxWidth;
        float height = boxY + boxHeight;

        box = new RectF();
        box.set(boxX, boxY, width, height);

        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(TXT_FONT_SIZE);

        infoText = "";
    }

    public void pause(){
        canDraw = false;
    }

    public void resume(){
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }
}
