package sean.dataexchange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TreeSet;

import sean.dataexchange.common.activities.SampleActivityBase;
import sean.dataexchange.common.logger.Log;

public class MapActivity extends SampleActivityBase {

    private boolean mLogShown;
    private RelativeLayout rl_Main;
    SurveyPointHandler test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test = new SurveyPointHandler("TestFile.txt", this.getApplicationContext());
        test.loadSurveyPoints();
        setContentView(R.layout.map_activity);
        rl_Main = (RelativeLayout) findViewById(R.id.rl_main);
        ImageView iv = (ImageView) findViewById(R.id.kafadar);

        rl_Main.addView(new PointView(this, this.test.getSurveyPoints(), iv));

        String varString = getIntent().getStringExtra("Test");
        Log.d("HelloWorld - Second Activity", varString);

//        TouchImageView img = new TouchImageView(this);
//        img.setImageResource(R.drawable.kafadar);
//        img.setMaxZoom(4f);
//        View v = new MyView(getApplicationContext());
//        Bitmap bitmap = Bitmap.createBitmap(500/*width*/, 500/*height*/, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        v.draw(canvas);
//        setContentView(img);


        //Top Left:     39.752682, -105.223834
        //Bottom Right: 39.749394, -105.219008


    }

    class PointView extends View{

        Paint paint = new Paint();
        PointF point = new PointF();
        TreeSet<SurveyPoint> tsp = null;
        ImageView iv;
        GeoPoint topLeft = new GeoPoint(39.752682, -105.223834);
        GeoPoint bottomRight = new GeoPoint(39.749394, -105.219008);

        public class GeoPoint {
            private double latitude;
            private double longitude;

            private float x;
            private float y;

            public GeoPoint(double latitude, double longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.x = 0;
                this.y = 0;
            }

            public void setXY(float x, float y) {
                this.x = x;
                this.y = y;
            }

            public void setImageBounds(GeoPoint topLeft, GeoPoint bottomRight) {
                this.x = (float)((Math.abs(topLeft.getLatitude() - this.latitude)/Math.abs(topLeft.getLatitude()-bottomRight.getLatitude()))*((double)bottomRight.getX() - (double)topLeft.getX()));
                this.y = (float)((Math.abs(topLeft.getLongitude() - this.longitude)/Math.abs(topLeft.getLongitude()-bottomRight.getLongitude()))*((double)bottomRight.getY() - (double)topLeft.getY()));
            }

            public double getLatitude() {
                return this.latitude;
            }

            public double getLongitude() {
                return this.longitude;
            }

            public void setX(float x) {
                this.x = x;
            }

            public void setY(float y) {
                this.y = y;
            }

            public float getX() {
                return this.x;
            }

            public float getY() {
                return this.y;
            }
        }
        //Top Left:     39.752682, -105.223834
        //Bottom Right: 39.749394, -105.219008

        public PointView(Context context, TreeSet<SurveyPoint> sp, final ImageView iv) {
            super(context);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(15);
            paint.setStyle(Paint.Style.STROKE);
            this.iv = iv;
            this.tsp = sp;
        }

//        android.util.Log.d("Position", Float.toString(this.topLeft.getX()));
//        android.util.Log.d("Position", Float.toString(this.topLeft.getY()));
//        android.util.Log.d("Position", Float.toString(this.bottomRight.getX()));
//        android.util.Log.d("Position", Float.toString(this.bottomRight.getY()));
//
//        android.util.Log.d("Position", Float.toString(iv.getLeft()));
//        android.util.Log.d("Position", Float.toString(iv.getTop()));
//        android.util.Log.d("Position", Float.toString(iv.getBottom()));
//        android.util.Log.d("Position", Float.toString(iv.getRight()));

        @Override
        protected void onDraw(Canvas canvas) {
            // Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.kafadar);

            // canvas.drawBitmap(b, 0, 0, paint);
            canvas.drawCircle(point.x, point.y, 100, paint);
            this.topLeft.setX(iv.getLeft());
            this.topLeft.setY(iv.getTop());
            this.bottomRight.setX(iv.getRight());
            this.bottomRight.setY(iv.getBottom());
            for(SurveyPoint sp : this.tsp) {
                GeoPoint p = new GeoPoint(sp.getLatitude(), sp.getLongitude());
                p.setImageBounds(this.topLeft, this.bottomRight);
                canvas.drawCircle(p.getX(), p.getY(), 20, paint);
            }

        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    point.x = event.getX();
//                    point.y = event.getY();
//
//            }
//            invalidate();
//            return true;
//
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setTitle(mLogShown ? R.string.map : R.string.survey);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


