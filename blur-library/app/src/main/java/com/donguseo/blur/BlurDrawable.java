package com.donguseo.blur;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BlurDrawable extends Drawable {
	
	private View bgView;
	private View view;

	private int size = 6;
	private int squal = size*size;
	private int[] pixels = new int[squal];
	Bitmap viewBitmap;

	@Override
	public void draw(Canvas canvas) {
		bgView.setDrawingCacheEnabled(true);
		Bitmap bm = bgView.getDrawingCache();
		if(bm == null){
			return;
		}
		
		if(viewBitmap == null){
			viewBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
		}
		
		Rect pRect = new Rect();
		Rect cRect = new Rect();
		
		bgView.getGlobalVisibleRect(pRect);
		view.getGlobalVisibleRect(cRect);
		
		int sumR = 0, sumG = 0, sumB = 0;
		int red = 0, green = 0, blue = 0;
		
		for(int i = cRect.left - pRect.left; i < cRect.right - pRect.left; i++){
			for(int j = cRect.top - pRect.top; j < cRect.bottom - pRect.top; j++){
				bm.getPixels(pixels , 0, size, i - size/2, j - size/2, size, size);
				for(int k = 0; k < squal; k++){
					sumR += (pixels[k] >> 16) & 0xFF ;
					sumG += (pixels[k] >> 8) & 0xFF ;
					sumB += pixels[k] & 0xFF ;
				}
				red = (sumR / squal);
				green = (sumG / squal);
				blue = (sumB / squal);
				int pixel = 0xFF000000 + (red << 16) + (green << 8) + blue;
				viewBitmap.setPixel(i - (cRect.left - pRect.left), j - (cRect.top - pRect.top), pixel );
				sumR = sumG = sumB = 0;
				red = green = blue = 0;
			}
		}
		
		canvas.drawBitmap(viewBitmap, null, getBounds(), null);
	}
	
	public View getView() { return view; }
	public void setView(View view) { this.view = view; }
	public View getParentView() { return bgView; }
	public void setBGView(View parentView) { this.bgView = parentView; }
	
	@Override public int getOpacity() { return 0; }
	@Override public void setAlpha(int alpha) { }
	@Override public void setColorFilter(ColorFilter cf) { }
}
