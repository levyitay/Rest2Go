package com.Rest2Go;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class RestData{
	public long mId;
	public String mName;
	public String mAddress;
	public String mPhone;
	public String mPhone2;
	public String mImgUrl;
	public Drawable mIconDraw; 
	public Drawable mImageDraw;
	
	public RestData(long id, String name, String address, String phone, String phone2,String imgURL, BitmapDrawable iconDraw )
	{
		this.mId = id;
		this.mName=name;
		this.mAddress = address;
		if (phone!=null)
			this.mPhone = phone;
		if (phone2!=null)
			this.mPhone2 = phone2;
		if (iconDraw!=null){
			this.mIconDraw = iconDraw;
		}else{
			this.mIconDraw = null;
		}
		
	}
	
}