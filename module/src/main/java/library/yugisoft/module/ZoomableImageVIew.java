package library.yugisoft.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ZoomableImageVIew extends ImageView implements View.OnTouchListener
{


    public Matrix matrix = new Matrix();
    public Matrix savedMatrix = new Matrix();


    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;


    private PointF mStartPoint = new PointF();
    private PointF mMiddlePoint = new PointF();
    private Point mBitmapMiddlePoint = new Point();


    private float matrixValues[] = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private float scale;
    private float oldEventX = 0;
    private float oldEventY = 0;
    private float oldStartPointX = 0;
    private float oldStartPointY = 0;
    private int mViewWidth = -1;
    private int mViewHeight = -1;
    private int mBitmapWidth = -1;
    private int mBitmapHeight = -1;
    private boolean mDraggable = false;
    private Dialog dialog;


    public ZoomableImageVIew(Context context) {
        this(context, null, 0);
    }

    public ZoomableImageVIew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomableImageVIew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnTouchListener(this);
        this.setScaleType(ScaleType.MATRIX);
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    public void setBitmap(Bitmap bitmap){
        if(bitmap != null){
            setImageBitmap(bitmap);

            mBitmapWidth = bitmap.getWidth();
            mBitmapHeight = bitmap.getHeight();
            mBitmapMiddlePoint.x = (mViewWidth / 2) - (mBitmapWidth /  2);
            mBitmapMiddlePoint.y = (mViewHeight / 2) - (mBitmapHeight / 2);

            matrix.postTranslate(mBitmapMiddlePoint.x, mBitmapMiddlePoint.y);
            this.setImageMatrix(matrix);
        }
    }


    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
    }






    @Override
    public boolean onTouch(View v, MotionEvent event){
        ImageView view = (ImageView) v;
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // ...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }



    public void drag(MotionEvent event){
        matrix.getValues(matrixValues);

        float left = matrixValues[2];
        float top = matrixValues[5];
        float bottom = (top + (matrixValues[0] * mBitmapHeight)) - mViewHeight;
        float right = (left + (matrixValues[0] * mBitmapWidth)) -mViewWidth;

        float eventX = event.getX();
        float eventY = event.getY();
        float spacingX = eventX - mStartPoint.x;
        float spacingY = eventY - mStartPoint.y;
        float newPositionLeft = (left  < 0 ? spacingX : spacingX * -1) + left;
        float newPositionRight = (spacingX) + right;
        float newPositionTop = (top  < 0 ? spacingY : spacingY * -1) + top;
        float newPositionBottom = (spacingY) + bottom;
        boolean x = true;
        boolean y = true;

        if(newPositionRight < 0.0f || newPositionLeft > 0.0f){
            if(newPositionRight < 0.0f && newPositionLeft > 0.0f){
                x = false;
            } else{
                eventX = oldEventX;
                mStartPoint.x = oldStartPointX;
            }
        }
        if(newPositionBottom < 0.0f || newPositionTop > 0.0f){
            if(newPositionBottom < 0.0f && newPositionTop > 0.0f){
                y = false;
            } else{
                eventY = oldEventY;
                mStartPoint.y = oldStartPointY;
            }
        }

        if(mDraggable){
            matrix.set(savedMatrix);
            matrix.postTranslate(x? eventX - mStartPoint.x : 0, y? eventY - mStartPoint.y : 0);
            this.setImageMatrix(matrix);
            if(x)oldEventX = eventX;
            if(y)oldEventY = eventY;
            if(x)oldStartPointX = mStartPoint.x;
            if(y)oldStartPointY = mStartPoint.y;
        }

    }

    public void zoom(MotionEvent event){
        matrix.getValues(matrixValues);

        float newDist = spacing(event);
        float bitmapWidth = matrixValues[0] * mBitmapWidth;
        float bimtapHeight = matrixValues[0] * mBitmapHeight;
        boolean in = newDist > oldDist;

        if(!in && matrixValues[0] < 1){
            return;
        }
        if(bitmapWidth > mViewWidth || bimtapHeight > mViewHeight){
            mDraggable = true;
        } else{
            mDraggable = false;
        }

        float midX = (mViewWidth / 2);
        float midY = (mViewHeight / 2);

        matrix.set(savedMatrix);
        scale = newDist / oldDist;
        matrix.postScale(scale, scale, bitmapWidth > mViewWidth ? mMiddlePoint.x : midX, bimtapHeight > mViewHeight ? mMiddlePoint.y : midY);

        this.setImageMatrix(matrix);


    }





    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float)Math.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }



    public void show() {
        getDialog().show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                RectF drawableRect = new RectF(0, 0, getDrawable().getBounds().width(), getDrawable().getBounds().height());
                RectF viewRect = new RectF(0, 0, getWidth(), getHeight());
                Matrix matrix = getImageMatrix();
                matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
                setImageMatrix(matrix);
               ZoomableImageVIew.this.matrix = matrix;
            }
        },100);
    }
    public void show(Drawable drawable) {
         this.setImageDrawable(drawable);
         show();
    }
    public void show(ImageView imageView) {
         show(imageView.getDrawable());
    }

    public void show(String view)
    {
        getDialog().show();
        yugi.imageLoader.displayImage(view, this, yugi.options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                RectF drawableRect = new RectF(0, 0, getDrawable().getBounds().width(), getDrawable().getBounds().height());
                RectF viewRect = new RectF(0, 0, getWidth(), getHeight());
                Matrix matrix = getImageMatrix();
                matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
                setImageMatrix(matrix);
                ZoomableImageVIew.this.matrix = matrix;

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    public Dialog getDialog() {
        if (dialog == null)
        {
            dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
            dialog.setContentView(this);
            dialog.getWindow().setLayout((int) (getScreenWidth() * .9), (int)(getScreenHeight() * .9) );
        }
        return dialog;
    }
    public static int getScreenWidth() {
        Point size = new Point();
        yugi.activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }
    public static int getScreenHeight() {
        Point size = new Point();
        yugi.activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

}