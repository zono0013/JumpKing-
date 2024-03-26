package jp.ac.ritsumei.ise.phy.exp2.is0665ri.jumpking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder sHolder ; // SurfaceHolder を格納
    private Thread thread ;/* Thread クラスのインスタンスを準備 */
    private Bitmap backgroundBitmap;
    private Bitmap character_rightBitmap;
    private Bitmap character_leftBitmap;
    private Bitmap character_right_charge_Bitmap;
    private Bitmap character_left_charge_Bitmap;

    private boolean left = false;
    private boolean left_now = false;
    private boolean left_jump = false;
    private long endleft;
    private boolean right = false;
    private boolean right_now = true;
    private boolean right_jump = false;
    private long endright;

    public boolean crouch = false;


    private long characterX; // キャラクターのX座標(表示するための)
    private long characterY; // キャラクターのY座標(表示するための)

    private long characterX0; // キャラクターのX座標(裏動作のための)
    private long characterY0; // キャラクターのY座標(裏動作のための)


    private float speed = (float) (9.8*2);// jumpの初速度


    private float jump_time; // jumpの物理公式のｔ
    private float touch_count; // 壁に当たった回数をcountする
    private float jump_y; //

    private float y; // ゲーム画面のキャラクターが移動できる一番下の座標
    private  boolean top_touch = false; // キャラがオブジェクトの上面に接触しているか？
    private boolean bottom_touch = false; // キャラがオブジェクトの下面に接触しているか？
    private boolean right_side_touch = false; // キャラがオブジェクトの右面に接触しているか？
    private boolean left_side_touch = false; // キャラがオブジェクトの左面に接触しているか？
    private boolean right_walk = false; // ⇒ボタンが押されたか？
    private boolean left_walk = false; // ⇐ボタンが押されたか？
    private long leftkabe; //左端の固定オブジェクト右壁の座標
    private long rightkabe; //右端の固定オブジェクト左壁の座標

    private List<ObjectWithCorners> objects; // オブジェクトのリスト

    private int h = 0; // オブジェクトの座標を更新する変数
//    private int h = 5000; // オブジェクトの座標を更新する変数

    private boolean move_b=true; // 動くことができるか？
    private int move_n = 0;

    private int count = 0;

    private int start_time = 0;

    private boolean text = false;

    private int result = 0;
    private int move_time = 0;
    private int back_time = 0;
    private int finish = 0;

    public void create_object(){
        objects = new ArrayList<>();

        ObjectWithCorners object1 = new ObjectWithCorners();
        object1.addCorner(new Rectangle(0, (int)(y)+100+h, 2000, 1200+h));

        ObjectWithCorners object2 = new ObjectWithCorners();
        object2.addCorner(new Rectangle(0, 0, 250, 1200));

        ObjectWithCorners object3 = new ObjectWithCorners();
        object3.addCorner(new Rectangle(1670, 0, 2400, 1200));
//        ↑↑枠組み↑↑


        ObjectWithCorners object1_1 = new ObjectWithCorners();
        object1_1.addCorner(new Rectangle(200, 600+h, 600, 1080+h));
        ObjectWithCorners object1_2 = new ObjectWithCorners();
        object1_2.addCorner(new Rectangle(1320, 600+h, 1920, 1080+h));
        ObjectWithCorners object1_3 = new ObjectWithCorners();
        object1_3.addCorner(new Rectangle(800, 250+h, 1100, 350+h));

        ObjectWithCorners object2_1 = new ObjectWithCorners();
        object2_1.addCorner(new Rectangle(1150, -200+h, 1400, -100+h));
        ObjectWithCorners object2_2 = new ObjectWithCorners();
        object2_2.addCorner(new Rectangle(1500, -500+h, 1920, -400+h));
        ObjectWithCorners object2_3 = new ObjectWithCorners();
        object2_3.addCorner(new Rectangle(1000, -500+h, 1200, -400+h));
        ObjectWithCorners object2_4 = new ObjectWithCorners();
        object2_4.addCorner(new Rectangle(650, -700+h, 850, -500+h));
        ObjectWithCorners object2_5 = new ObjectWithCorners();
        object2_5.addCorner(new Rectangle(100, -800+h, 500, -500+h));

        ObjectWithCorners object3_1 = new ObjectWithCorners();
        object3_1.addCorner(new Rectangle(800, -1200+h, 1000, -1150+h));
        ObjectWithCorners object3_2 = new ObjectWithCorners();
        object3_2.addCorner(new Rectangle(1200, -1200+h, 1350, -1150+h));
        ObjectWithCorners object3_3 = new ObjectWithCorners();
        object3_3.addCorner(new Rectangle(1500, -1450+h, 1800, -1400+h));
        ObjectWithCorners object3_4 = new ObjectWithCorners();
        object3_4.addCorner(new Rectangle(1100, -1650+h, 1300, -1400+h));
        ObjectWithCorners object3_5 = new ObjectWithCorners();
        object3_5.addCorner(new Rectangle(800, -1500+h, 1200, -1400+h));
        ObjectWithCorners object3_6 = new ObjectWithCorners();
        object3_6.addCorner(new Rectangle(600, -1850+h, 900, -1650+h));
        ObjectWithCorners object3_7 = new ObjectWithCorners();
        object3_7.addCorner(new Rectangle(100, -1950+h, 400, -1850+h));

        ObjectWithCorners object4_1 = new ObjectWithCorners();
        object4_1.addCorner(new Rectangle(550, -2150+h, 800, -2100+h));
        ObjectWithCorners object4_2 = new ObjectWithCorners();
        object4_2.addCorner(new Rectangle(0, -2500+h, 400, -2450+h));
        ObjectWithCorners object4_3 = new ObjectWithCorners();
        object4_3.addCorner(new Rectangle(550, -2500+h, 750, -2450+h));
        ObjectWithCorners object4_4 = new ObjectWithCorners();
        object4_4.addCorner(new Rectangle(1050, -2700+h, 1200, -2450+h));
        ObjectWithCorners object4_5 = new ObjectWithCorners();
        object4_5.addCorner(new Rectangle(550, -2800+h, 650, -2650+h));
        ObjectWithCorners object4_6 = new ObjectWithCorners();
        object4_6.addCorner(new Rectangle(1150, -2850+h, 1200, -2450+h));
        ObjectWithCorners object4_7 = new ObjectWithCorners();
        object4_7.addCorner(new Rectangle(1150, -2850+h, 1300, -2800+h));
        ObjectWithCorners object4_8 = new ObjectWithCorners();
        object4_8.addCorner(new Rectangle(550, -2950+h, 600, -2750+h));
        ObjectWithCorners object4_9 = new ObjectWithCorners();
        object4_9.addCorner(new Rectangle(1150, -3300+h, 1200, -3000+h));
        ObjectWithCorners object4_10 = new ObjectWithCorners();
        object4_10.addCorner(new Rectangle(550, -3300+h, 600, -3100+h));        // オブジェクトをリストに追加

        ObjectWithCorners object5_1 = new ObjectWithCorners();
        object5_1.addCorner(new Rectangle(500, -3350+h, 600, -3250+h));
        ObjectWithCorners object5_2 = new ObjectWithCorners();
        object5_2.addCorner(new Rectangle(1150, -3350+h, 1250, -3250+h));
        ObjectWithCorners object5_3 = new ObjectWithCorners();
        object5_3.addCorner(new Rectangle(200, -3600+h, 350, -3550+h));
        ObjectWithCorners object5_4 = new ObjectWithCorners();
        object5_4.addCorner(new Rectangle(1150, -3600+h, 1250, -3550+h));
        ObjectWithCorners object5_5 = new ObjectWithCorners();
        object5_5.addCorner(new Rectangle(1570, -3800+h, 1700, -3750+h));
        ObjectWithCorners object5_6 = new ObjectWithCorners();
        object5_6.addCorner(new Rectangle(1150, -3950+h, 1250, -3900+h));
        ObjectWithCorners object5_7 = new ObjectWithCorners();
        object5_7.addCorner(new Rectangle(950, -4000+h, 1050, -3950+h));
        ObjectWithCorners object5_8 = new ObjectWithCorners();
        object5_8.addCorner(new Rectangle(750, -4050+h, 850, -4000+h));
        ObjectWithCorners object5_9 = new ObjectWithCorners();
        object5_9.addCorner(new Rectangle(350, -3950+h, 450, -3900+h));
        ObjectWithCorners object5_10 = new ObjectWithCorners();
        object5_10.addCorner(new Rectangle(750, -4350+h, 1250, -4250+h));        // オブジェクトをリストに追加

        objects.add(object1);
        objects.add(object2);
        objects.add(object3);

        objects.add(object1_1);
        objects.add(object1_2);
        objects.add(object1_3);

        objects.add(object2_1);
        objects.add(object2_2);
        objects.add(object2_3);
        objects.add(object2_4);
        objects.add(object2_5);

        objects.add(object3_1);
        objects.add(object3_2);
        objects.add(object3_3);
        objects.add(object3_4);
        objects.add(object3_5);
        objects.add(object3_6);
        objects.add(object3_7);

        objects.add(object4_1);
        objects.add(object4_2);
        objects.add(object4_3);
        objects.add(object4_4);
        objects.add(object4_5);
        objects.add(object4_6);
        objects.add(object4_7);
        objects.add(object4_8);
        objects.add(object4_9);
        objects.add(object4_10);

        objects.add(object5_1);
        objects.add(object5_2);
        objects.add(object5_3);
        objects.add(object5_4);
        objects.add(object5_5);
        objects.add(object5_6);
        objects.add(object5_7);
        objects.add(object5_8);
        objects.add(object5_9);
        objects.add(object5_10);
    }

    private void hansya(){

        // キャラクターの座標とサイズ
        long charLeft = characterX;
        long charTop = characterY;
        long charRight = characterX + character_rightBitmap.getWidth();
        long charBottom = characterY + character_rightBitmap.getHeight();

        // オブジェクトリストの各オブジェクトの四つ角を描画
        for (ObjectWithCorners object : objects) {
            int left = 0;
            int right = 0;
            int top = 0;
            int bottom = 0;
            List<Rectangle> corners = object.getObjectCorners();
            for (Rectangle corner : corners) {

                left = corner.left;
                top = corner.top;
                right = corner.right;
                bottom = corner.bottom;
            }
            if(charRight > left && charLeft < right && charBottom < top+20 && charBottom > top){
                left_side_touch =false;
                right_side_touch = false;
                top_touch = true;
                left_jump = false;
                right_jump = false;
                bottom_touch = false;
                characterY = top-103;
                jump_time = 0;
                jump_y = 0;
                touch_count = 0;
                characterX0 = characterX;
                if(h==1080*5){
//                if(h==5000){
                    text = true;
                    move_b = false;
                }
                Log.d("ObjectCorners", "場所: " + "上に乗ったよ" );
            }else{
                top_touch =false;
            }

            if(charRight > 10+left && charLeft < -10+right && charTop > bottom-10 && charTop < bottom) {
                bottom_touch = true;
                touch_count += 1;
                Log.d("ObjectCorners", "場所: " + "下にあたったよ");
            }
            if (charTop < bottom && charBottom > top && charRight > left && charRight < left+10){
                left_side_touch =true;
                touch_count += 1;
                Log.d("ObjectCorners", "場所: " + "左にあたったよ");
            }
            if (charTop<bottom && charBottom>top && charLeft < right && charLeft > right -10){
                right_side_touch =true;
                touch_count += 1;
                Log.d("ObjectCorners", "場所: " + "右にあたったよ");
            }
            if (charTop < bottom && charBottom > top+10 && charRight > left-29 && charRight < left+20 && !right_jump){
                right_walk =true;
                rightkabe = left-100;
            }
            if (charTop<bottom && charBottom>top+10 && charLeft < right+29 && charLeft > right-20 && !left_jump){
                left_walk =true;
                leftkabe = right;
            }
        }
    }
    public MySurfaceView(Context context) {
        super(context);
        initialize() ;
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize() ;
    }
    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize() ;
    }
    private void initialize() {
        sHolder = getHolder() ; // SurfaceHolder を取得
        sHolder.addCallback(this) ; // 自身をコールバックとして登録

        // 画像を読み込む
        if (backgroundBitmap == null) {
            backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_1);
        }
        character_rightBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.king_right);
        character_leftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.king_left);
        character_right_charge_Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_2);
        character_left_charge_Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_3);

        characterY = 1080 - character_rightBitmap.getHeight();
        characterY0 = characterY;
        y=characterY;//最初のyの位置(絶対に変更しない)
        // 画像を縮小
        int newWidth = 100; // 新しい幅
        int newHeight_crouch = 66;
        int newHeight = 100; // 新しい高さ

        character_rightBitmap = Bitmap.createScaledBitmap(character_rightBitmap, newWidth, newHeight, true);
        character_leftBitmap = Bitmap.createScaledBitmap(character_leftBitmap, newWidth, newHeight, true);
        character_right_charge_Bitmap = Bitmap.createScaledBitmap(character_right_charge_Bitmap, newWidth, newHeight_crouch, true);
        character_left_charge_Bitmap = Bitmap.createScaledBitmap(character_left_charge_Bitmap, newWidth, newHeight_crouch, true);

        characterX = (backgroundBitmap.getWidth()/2)-(character_rightBitmap.getWidth()/2) ; // キャラクターの初期X座標
        characterX0 = characterX;

        endright = 1670;
        endleft = 100;

        create_object();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
        start_time = (int)(System.currentTimeMillis());
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    static final long FPS = 30;
    static final long FTIME = 100/ FPS;

    @Override
    public void run() {
        long loopC = 0 ; // ループ数のカウンタ
        long wTime = 0 ; // 次の描画までの待ち時間（ミリ秒）
        /* 必要に応じて描画に関する初期化をここに書く */
        long sTime = System.currentTimeMillis() ; // 開始時の現在時刻
        while (thread != null) {
            try {
                loopC++ ;
                if(move_b) {
                    uppdate_position();
                    drawCanvas(); /* drawCanvas メソッドで画面を描画 */
                }
                wTime = (loopC * FTIME) - (System.currentTimeMillis() - sTime) ;
                if (wTime > 0) {
                    Thread.sleep(wTime) ;
                }
            } catch (InterruptedException e) {
                /* 必要に応じて割り込み発生時の例外処理を追加 */
            }
        }
    }
    private void drawCanvas() {
        Canvas c = sHolder.lockCanvas();
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // 背景などのオブジェクトを描画するメソッド
        drawBackgroundAndObjects(c);

        if (text){
            drawGameOverText(c);
        }

        // キャラクターを描画するメソッド
        drawCharacter(c);

        drawCountText(c);

        sHolder.unlockCanvasAndPost(c);
    }

    // 背景などのオブジェクトを描画するメソッド
    private void drawBackgroundAndObjects(Canvas c) {

        Paint p = new Paint();
        p.setColor(Color.WHITE);

        // オブジェクトリストの各オブジェクトの四つ角を描画
        for (ObjectWithCorners object : objects) {
            List<Rectangle> corners = object.getObjectCorners();
            for (Rectangle corner : corners) {
                c.drawRect(corner.left, corner.top, corner.right, corner.bottom, p);
            }
        }
    }
    public boolean finish(){
        return text;
    }

    private void drawGameOverText(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setTextSize(100); // テキストのサイズを適宜調整

        // テキストを描画する座標を指定（例として x 座標を characterX、y 座標を characterY から適当に調整）
        float x = 275;
        float y = 200;// 上にオフセットして描画
        if(finish == 0){
            result = count;
            finish = 1;
        }

        c.drawText("Game Clear", x, y, p);
        c.drawText("Time:" + result/60 + "分" + result%60 + "秒", x, 2*y, p);
    }

    private void drawCountText(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(50); // テキストのサイズを適宜調整
        count = ((int)(System.currentTimeMillis()) - start_time - back_time)/1000;

        // テキストを描画する座標を指定（例として x 座標を characterX、y 座標を characterY から適当に調整）
        float x = 20;
        float y = 200;// 上にオフセットして描画

        c.drawText(count/60 + "分" + count%60 + "秒", x, y, p);


//
    }

    // キャラクターを描画するメソッド
    private void drawCharacter(Canvas c) {
        if (left_now && crouch) {
            characterY= characterY0 + 30;
            c.drawBitmap(character_left_charge_Bitmap, characterX, characterY, null);
        } else if (right_now && crouch) {
            characterY= characterY0 + 30;
            c.drawBitmap(character_right_charge_Bitmap, characterX, characterY, null);
        } else if (left_now) {
            c.drawBitmap(character_leftBitmap, characterX, characterY, null);
        } else if (right_now) {
            c.drawBitmap(character_rightBitmap, characterX, characterY, null);
        }
    }
    private void uppdate_position(){
        //
        float jump_x;
        //
        float jump_x0;
        //
        float jump_y0;
        // jump中の横移動速度
        float move = (float) (40);
        if(!right_jump && !left_jump) {
            if (left) {
                if(left_walk){
                    characterX0 = leftkabe;
                    right_now = false;
                    left_now = true;
                    left = false;
                    left_walk =false;
                    right_walk =false;
                } else if (characterX >= 30 + endleft) {
                    characterX0 -= 30; // キャラクターを左に移動
                    right_now = false;
                    left_now = true;
                    left = false;
                    right_walk =false;
                } else if (characterX >= endleft) {
                    characterX0 = endleft; // キャラクターを左に移動
                    right_now = false;
                    left_now = true;
                    left = false;
                    right_walk =false;
                }
                if (!top_touch && characterY < y){
                    left_jump = true;
                    speed = 0;
                }
            }
            if (right) {
                if(right_walk){
                    characterX0 = rightkabe;
                    right_now = true;
                    left_now = false;
                    right = false;
                    right_walk =false;
                    left_walk =false;
                } else if (characterX <= endright - 30) {
                    characterX0 += 30; // キャラクターを左に移動
                    right_now = true;
                    left_now = false;
                    right = false;
                    left_walk =false;
                } else if (characterX <= endright) {
                    characterX0 = endright; // キャラクターを左に移動
                    right_now = true;
                    left_now = false;
                    right = false;
                    left_walk =false;
                }
                if (!top_touch && characterY < y){
                    right_jump = true;
                    speed = 0;
                }
            }
            characterX = characterX0;
            characterY = characterY0;
        }
        else if(left_jump){
            left_walk = false;
            right_walk = false;
            if (y >= characterY) {
                jump_y0 = (float) ((speed * jump_time) - (4.9 * jump_time * jump_time));
                jump_x0 = (float) (move * jump_time);
                float n = 1;
                if(!bottom_touch) {

                    jump_time += 0.1F;
                } else {
                    jump_time += -0.1;
                }
                jump_y = (float) ((speed * jump_time) - (4.9 * jump_time * jump_time));
                jump_x = (float) (move * jump_time);
                if(!bottom_touch && !left_side_touch && !right_side_touch) {
                    characterY0 = (long) (characterY0 - (jump_y - jump_y0));
                    characterX0 = (long) (characterX0 - (jump_x - jump_x0));
                }else {
                    for (int i = 1; i==touch_count; i++) {
                        n = n * -(jump_x - jump_x0);
                    }
                    characterY0 = (long) (characterY0 - (jump_y - jump_y0));
                    characterX0 = (long) (characterX0 - n);
                }
                if (endleft < (characterX)) {
                    characterX = characterX0;
                } else {
                    characterX = (2*endleft)-characterX0;
                }
                characterY = characterY0;
                if (top_touch){
                    characterX0 = characterX;
                    top_touch = false;
                }
            } else {
                if (endleft < (characterX)) {
                    characterX = characterX0;
                } else {
                    characterX = (2*endleft)-(characterX0);
                    characterX0 = characterX;
                }
                characterY = characterY0 = (long) y;
                Log.d("drawCanvas: ", "characterX0;" + characterX0 + "characterX;" + characterX);
                jump_time = 0;
                jump_y = 0;
                left_jump = false;
                right_jump = false;
                bottom_touch =false;
                left_side_touch =false;
                right_side_touch = false;
                touch_count =0;
                left_walk =false;
                right_walk =false;
            }
            Log.d("drawCanvas1: ", "characterY;" + characterY + "characterX;" + characterX);
        }
        else {
            left_walk = false;
            right_walk = false;
            if (y >= characterY) {
                float n = 1;
                jump_y0 = (float) ((speed * jump_time) - (4.9 * jump_time * jump_time));
                jump_x0 = (float) (move * jump_time);
                if(!bottom_touch) {
                    jump_time += 0.1F;
                } else {
                    jump_time += -0.1F;
                }
                jump_y = (float) ((speed * jump_time) - (4.9 * jump_time * jump_time));
                jump_x = (float) (move * jump_time);
                if(!bottom_touch && !left_side_touch && !right_side_touch) {
                    characterY0 = (long) (characterY0 - (jump_y - jump_y0));
                    characterX0 = (long) (characterX0 + (jump_x - jump_x0));
                }else{
                    for (int i = 1; i==touch_count; i++) {
                        n = n * -(jump_x - jump_x0);
                    }
                    characterY0 = (long) (characterY0 - (jump_y - jump_y0));
                    characterX0 = (long) (characterX0 + n);
                }
                if (endright > (characterX)) {
                    characterX = characterX0;
                } else {
                    characterX = 2 * endright - (characterX0);
                }
                characterY = characterY0;
                if (top_touch){
                    characterX0 = characterX;
                    top_touch = false;
                }
            } else {
                if (endright > (characterX)) {
                    characterX = characterX0;
                } else {
                    characterX = 2 * endright - (characterX0);
                    characterX0 = characterX;
                }
                characterY = characterY0 = (long) y;
                jump_time = 0;
                jump_y = 0;
                right_jump = false;
                bottom_touch =false;
                left_side_touch =false;
                right_side_touch = false;
                touch_count = 0;
            }
        }
        hansya();
        if(characterY0 < 0){
            h += 1080;
            y += 1080;
            create_object();
            characterY = characterY0 = 1080;
        }
        if(characterY0 >1080 && characterY0 <= y) {
            h -= 1080;
            y -= 1080;
            create_object();
            characterY = characterY0 -= 1080;
        }
    }


    public void handleLeftButtonTouch() {
        if(!right_jump && !left_jump) {
            left = true;
        }
    }

    public void handleRightButtonTouch() {
        if(!right_jump && !left_jump) {
            right = true;
        }
    }

    public void handleJumpButtonTouch_short() {
        characterY= characterY0 - 30;
        crouch = false;
        speed = (float) (9.8*4);
        if(right_now){
            right_jump = true;
        }
        if(left_now){
            left_jump = true;
        }
    }
    public void handleJumpButtonTouch_middle() {
        characterY= characterY0 - 30;
        crouch = false;
        speed = (float) (9.8*6);
        if(right_now){
            right_jump = true;
        }
        if(left_now){
            left_jump = true;
        }
    }
    public void handleJumpButtonTouch_long() {
        characterY= characterY0 - 30;
        crouch = false;
        speed = (float) (9.8*10);
        if(right_now){
            right_jump = true;
        }
        if(left_now){
            left_jump = true;
        }
    }
    public void crouch_boolean(){
        crouch = true;
    }

    public void move_boolean(){
        move_n++;
        move_n = move_n%2;
        if (move_n ==1){
            move_time =(int)(System.currentTimeMillis());
            move_b = false;
        } else if (move_n ==0) {
            back_time = (int)(System.currentTimeMillis()-move_time) + back_time;
            move_b = true;
        }
    }
}