package com.willbetter.practice_demo;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by ZJ on 2018/7/16
 * 带图片的TextView
 */

public class PictureTextView extends AppCompatTextView {


    private Paint paint;
    private Drawable drawable;
    private TextPaint textPaint;
    String text = "touhahahsdfasdfdfddasdfasdfasdfasdfasdfasdfasdf" +
            "asdfasdfasdasdfasdfasdfasdfasdfasdasdfasdgasdgasdgasdgasdasdgasdgasdgasdgasd" +
            "asdgasdgasdgasdgasdgasdgasdgasdgasdgasdgasdg" +
            "asdgasdgsdgasdgasdgsgsgsgsdgasdgsgddsagasdg" +
            "asdgsadgsagsgdasdgasdgsgsdgsdgasgsgsgsdgsgsdgdsgsdgsagsgdsgasdgsdgas" +
            "asdgasdgasdgasdgasdgasdgasdgasdgasdgasdgasdg" +
            "asdgasdgsdgasdgasdgsgsgsgsdgasdgsgddsagasdg" +
            "asdgsadgsagsgdasdgasdgsgsdgsdgasgsgsgsdgsgsdgdsgsdgsagsgdsgasdgsdgas" +
            "asdgasdgasdgasdgasdgasdgasdgasdgasdgasdgasdg" +
            "asdgasdgsdgasdgasdgsgsgsgsdgasdgsgddsagasdg" +
            "asdgsadgsagsgdasdgasdgsgsdgsdgasgsgsgsdgsgsdgdsgsdgsagsgdsgasdgsdgas" +
            "asdgasdgsadgasdgsagsdggasdgasdgsdgasdgsdaha尾";
    private StaticLayout staticLayout;
    private float textSize =30;

    public PictureTextView(Context context) {
        this(context,null);
    }

    public PictureTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PictureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(context,attrs);
        init();
    }

    private void initType(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PictureTextView);
        drawable = typedArray.getDrawable(R.styleable.PictureTextView_drawImage);


        typedArray.recycle();
    }

    private void init(){
//        setSingleLine(false);
//        paint = new Paint();
//        paint.setTextSize(30);

        textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        canvas.drawBitmap(bitmap,0,0,paint);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 得到字体高度
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int textHeight = (int) (Math.ceil(fontMetrics.descent - fontMetrics.ascent));
        textHeight = (int)(textHeight * getLayout().getSpacingMultiplier() + getLayout()
                .getSpacingAdd());

//        funOne(canvas, width, height, textHeight);
        funTwo(canvas);
    }

    /**
     * 第二种方法，遍历text，每次绘制完都获取一下文本的高度，跟图片高度进行比较，从而进行换行
     */
    private void funTwo(Canvas canvas) {
        int length = text.length();
        for (int i = 0; i < length; i++) {
            text.charAt(i);
//            canvas.drawText();
        }
    }

    /**
     * 第一种方法，就是精确算出在图片高度内总共能绘制多少字，然后截取，进行分段绘制
     */
    private void funOne(Canvas canvas, int width, int height, int textHeight) {
        // 算出图片同高的字有几行
        boolean v = height % textHeight == 0;
        int num = (int) (height / textHeight);
        if (!v){
             num+=1;
        }
        // 一定长度能画多少字
        int v1 = (int) ((getWidth() - width) / 15);
        // 总共绘制的字
        int sumText = num*v1;
        // 分段绘制,并且分段的内容 < 总字数
        if (sumText < text.length()){
            String substring = text.substring(0, sumText);
            staticLayout = new StaticLayout(substring,textPaint,getWidth()-width, Layout.Alignment.ALIGN_NORMAL,1,1,true);
            String substring1 = text.substring(sumText, text.length());
            StaticLayout staticLayout1 = new StaticLayout(substring1, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 1, true);
            canvas.save();
            canvas.translate(width,0);//从x，y开始画
            staticLayout.draw(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(0,height);
            staticLayout1.draw(canvas);
            canvas.restore();
        }else {
            staticLayout = new StaticLayout(text,textPaint,getWidth()-width, Layout.Alignment.ALIGN_NORMAL,1,1,true);
            canvas.save();
            canvas.translate(width,0);//从x，y开始画
            staticLayout.draw(canvas);
            canvas.restore();
        }
    }
}
