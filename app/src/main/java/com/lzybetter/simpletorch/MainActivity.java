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

import static android.hardware.Camera.*;
import static android.hardware.Camera.Parameters.*;

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
			camera = open();
		}
		Parameters p = camera.getParameters();
		p.setFlashMode(FLASH_MODE_TORCH);
		camera.setParameters(p);
		camera.startPreview();
		switchButton.setButtonDrawable(R.drawable.open);
		background.setBackgroundColor(Color.BLACK);
	}

	private void torchClose(){
		if(camera == null){
			camera = open();
		}
		Parameters p = camera.getParameters();
		p.setFlashMode(FLASH_MODE_OFF);
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
	protected void onPause() {
		super.onPause();
		torchClose();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		torchClose();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		torchOpen();
	}
}
