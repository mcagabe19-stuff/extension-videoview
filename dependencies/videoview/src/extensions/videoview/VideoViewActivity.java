package extensions.videoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import org.haxe.lime.HaxeObject;

public class VideoViewActivity extends Activity {
	public static VideoView videoView;
	public static HaxeObject callback;
	public static String videoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		videoPath = extras.getString(VideoViewExtension.EXTRA_VIDEOPATH);
		callback = VideoViewExtension.callback;

		if(Build.VERSION.SDK_INT >= 19) 
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		else if(Build.VERSION.SDK_INT >= 16) 
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

		setContentView(R.layout.videoview_activity);

		videoView = (VideoView) findViewById(R.id.simpleVideoView);
	}

	@Override
	protected void onResume(){
		super.onResume();
		if (videoView != null) {
			videoView.resume();
		}
	}

	@Override
	protected void onPause(){
		super.onPause();
		if (videoView != null && videoView.isPlaying()) {
			videoView.pause();
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if (videoView != null) {
			videoView.stopPlayback();
		}
	}

	@Override
	protected void onStart(){
		super.onStart();

		videoView.setVideoURI(Uri.parse(videoPath));
		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) { 
				callback.call("onCompletion", new Object[] {});
				finish();
			}
		});
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) { 
				callback.call("onPrepared", new Object[] {});
			}
		});
		videoView.start();
	}

	@Override
	protected void onStop(){
		super.onStop();
		if (videoView != null) {
			videoView.stopPlayback();
		}
	}

	@Override
	public void onBackPressed(){
		callback.call("onCompletion", new Object[] {});
		finish();
	}

	@Override
	public void finish(){
		if (videoView != null) {
			videoView.stopPlayback();
		}
		super.finish();
	}
}
