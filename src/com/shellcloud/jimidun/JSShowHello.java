package com.shellcloud.jimidun;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSShowHello {
	
	private Context context;

	public JSShowHello(Context context) {
		super();
		this.context = context;
	}
	
	@JavascriptInterface
	public void showHello(){
		Toast.makeText(this.context, "hello!", Toast.LENGTH_LONG).show();
	}
	

}
