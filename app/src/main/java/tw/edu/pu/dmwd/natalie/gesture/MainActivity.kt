package tw.edu.pu.dmwd.natalie.gesture

import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GestureDetector.OnGestureListener, OnTouchListener {
    lateinit var txv:TextView
    lateinit var gDetector:GestureDetector
    lateinit var img1: ImageView
    lateinit var img2: ImageView
    lateinit var img3: ImageView
    var count:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)

        img1.setOnTouchListener(this)
        img2.setOnTouchListener(this)
        img3.visibility=View.GONE

        txv = findViewById(R.id.txv)

        txv.setTextColor(Color.parseColor("#f8f249"))

        txv.setBackgroundColor(Color.BLUE)

        txv.setTypeface(

            Typeface.createFromAsset(assets,

                "font/HanyiSentyFingerPainting.ttf"))

        txv.getBackground().setAlpha(50) //0~255透明度值
        gDetector = GestureDetector(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (txv.text == "手勢辨別"){
            txv.text = "靜宜之美"
        }

        else{
            txv.text = "手勢辨別"
        }
        gDetector.onTouchEvent(event)
        return true
    }

    override fun onDown(p0: MotionEvent): Boolean {
        txv.text = "按下"
        return true
    }

    override fun onShowPress(p0: MotionEvent) {
        txv.text = "按下後無後續動作"
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        txv.text = "短按"
        return true
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        //txv.text = "拖曳"
        if (distanceY >= 0){
            txv.text = "向上拖曳"
        }

        else{
            txv.text = "向下拖曳"
        }
        return true
    }

    override fun onLongPress(p0: MotionEvent) {
        txv.text = "長按"

    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        //txv.text = "快滑"
        if (e1.x <= e2.x){
            txv.text = "往右快滑"
            count++
            if(count>5){count=0}
        }
        else{
            txv.text = "往左快滑"
            count--
            if(count<0){count=5}
        }

        var res:Int = getResources().getIdentifier("pu" + count.toString(),

            "drawable", getPackageName())

        findViewById<LinearLayout>(R.id.bg).setBackgroundResource(res)
        return true
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == img1) {
            txv.text = "精靈1"
        } else {
            txv.text = "精靈2"
        }

        if (event?.action == MotionEvent.ACTION_MOVE) {
            v?.x = event.rawX - v!!.width / 2
            v?.y = event.rawY - v!!.height / 2
        }
        var r1: Rect = Rect(img1.x.toInt(), img1.y.toInt(),

            img1.x.toInt() + 4*img1.width/11, img1.y.toInt() + img1.height)

        var r2: Rect = Rect(img2.x.toInt(), img2.y.toInt(),

            img2.x.toInt() + img2.width, img2.y.toInt() + img2.height)

        if(r1.intersect(r2)) {
            txv.text = "碰撞"
            img3.visibility=View.VISIBLE
        }
        else{
            img3.visibility=View.GONE
        }
        return true
    }

}