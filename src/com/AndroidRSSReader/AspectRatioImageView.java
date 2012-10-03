package com.AndroidRSSReader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioImageView extends ImageView {
	
	public static final int ALLIGN_BEST_FIT = 0;
	public static final int ALLIGN_BY_WIDTH = 1;
	public static final int ALLIGN_BY_HEIGHT = 2;
	boolean byWidth = true;
	private int allignType = 1;
	

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	int height = 0;
        int width = 0;
        
        if (allignType == 0) {
          Drawable drawable = getDrawable();
          if (drawable != null)
          {
              width =  MeasureSpec.getSize(widthMeasureSpec);
              int diw = drawable.getIntrinsicWidth();
              if (diw > 0)
              {
                  height = width * drawable.getIntrinsicHeight() / diw;
                  setMeasuredDimension(width, height);
              }
              else
                  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          }
          else
              super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        	
        }
        else if (allignType == 1) {
        	width = MeasureSpec.getSize(widthMeasureSpec);
          try{
        	  height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        	  setMeasuredDimension(width, height);
          } catch (Exception e) {
        	  setMeasuredDimension(0, 0);
          }
        } else if (allignType == 2) {
        	height = MeasureSpec.getSize(heightMeasureSpec);
            try{
                width = height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
                setMeasuredDimension(width, height);
            } catch (Exception e) {
            	 setMeasuredDimension(0, 0);
            }
        }
    	
    	
//    	int height = MeasureSpec.getSize(heightMeasureSpec);
//        int width = 0;
//        try{
//            width = height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
//            setMeasuredDimension(width, height);
//        } catch (Exception e) {
//        	 setMeasuredDimension(0, 0);
//        }
        
    	
//    	int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = 0;
//        try{
//            height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
//            setMeasuredDimension(width, height);
//        } catch (Exception e) {
//        	 setMeasuredDimension(0, 0);
//        }
    	
//    	int scrWidth = MeasureSpec.getSize(widthMeasureSpec);
//    	int scrHeight= MeasureSpec.getSize(heightMeasureSpec);
//    	int imgWidth = getDrawable().getIntrinsicWidth();
//    	int imgHeight= getDrawable().getIntrinsicHeight();
    }

	public int getAllignType() {
		return allignType;
	}

	public void setAllignType(int allignType) {
		this.allignType = allignType;
	}
    
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//        Drawable drawable = getDrawable();
//        if (drawable != null)
//        {
//            int width =  MeasureSpec.getSize(widthMeasureSpec);
//            int diw = drawable.getIntrinsicWidth();
//            if (diw > 0)
//            {
//                int height = width * drawable.getIntrinsicHeight() / diw;
//                setMeasuredDimension(width, height);
//            }
//            else
//                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//        else
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
    
}