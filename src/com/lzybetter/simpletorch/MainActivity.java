package com.lzybetter.simpletorch;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	private ToggleButton switchButton;
	private Camera camera;
	private RelativeLayout background;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		background = (RelativeLayout)findViewById(R.id.background);
		switchButton = (ToggleButton)findViewById(R.id.switch_button);
		SwitchButtonListener switchButtonListener = new SwitchButtonListener();
		switchButton.setOnClickListener(switchButtonListener);
		torchOpen();
	}
	
	class SwitchButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToggleButton button = (ToggleButton)v;
			if(!button.isChecked()){

				torchOpen();
			}else {
				torchClose();
			}
		}
		
	}
	
	private void torchOpen(){
		if(camera == null){
			camera = Camera.open();
		}
		Parameters p = camera.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(p);
		camera.startPreview();
		switchButton.setButtonDrawable(R.drawable.open);
		background.setBackgroundColor(Color.BLACK);
	}
	
	private void torchClose(){
		if(camera == null){
			camera = Camera.open();
		}
		Parameters p = camera.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(p);
		camera.stopPreview();
		background.setBackgroundColor(Color.WHITE);
		switchButton.setButtonDrawable(R.drawable.close);
		if(camera != null){
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null; 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
