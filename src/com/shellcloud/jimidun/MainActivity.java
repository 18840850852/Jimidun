package com.shellcloud.jimidun;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView webIndex;

	private static final String URL_PREFIX = "http://192.168.54.40:8000";
	private static final String URL_HOST = "192.168.54.40:8000";
	private static final String URL_INDEX = URL_PREFIX + "/touch-html/index.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 显示界面上方的加载进度条
		setContentView(R.layout.activity_main);
		findViewById();
		initView();
	}

	private void initView() {
		initWebView();
		// webIndex.loadUrl("http://www.baidu.com");
		webIndex.loadUrl(URL_INDEX);
	}

	// 初始化webview，设置可以使用js
	private void initWebView() {
		WebSettings webSettings = webIndex.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		final Activity activity = this;
		webIndex.addJavascriptInterface(new JSShowHello(activity), "Android");
		
		// 在WebView中打开链接（默认行为是使用浏览器，设置此项后都用WebView打开）
		webIndex.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				/*if (Uri.parse(url).getHost().equals(URL_HOST)) {
					view.loadUrl(url);
					return true;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;*/
				view.loadUrl(url);
				return true;
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(activity, "Oh no! " + description,Toast.LENGTH_SHORT).show();
			}
		});
		// 处理webview的界面变化事件
		webIndex.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				activity.setProgress(progress * 1000);
			}
		});
	}

	private void findViewById() {
		webIndex = (WebView) findViewById(R.id.web_index);
	}

	@Override
	// 复写回退键事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webIndex.canGoBack()) {
			webIndex.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
