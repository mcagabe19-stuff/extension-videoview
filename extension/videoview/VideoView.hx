package extension.videoview;

#if !android
#error 'extension-videoview supports only in Android platform.'
#end

import lime.system.JNI;

/**
 * author: Mihai Alexandru (MAJigsaw77)
 */
	
class VideoView
{
	public static var onCompletion:Void -> Void = null;
	public static var onPrepared:Void -> Void = null;

	public static function playVideo(path:String = null):Void 
	{
		var _callbackFunc = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "setCallback", "(Lorg/haxe/lime/HaxeObject;)V");
		var _playVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "playVideo", "(Ljava/lang/String;)V");

		_callbackFunc(new CallBack());
		_playVideo(path);
	}
}

class CallBack {
	public function new() {}

	public function onCompletion() {
		if (VideoView.onCompletion != null)
			VideoView.onCompletion();
	}

	public function onPrepared() {
		if (VideoView.onPrepared != null)
			VideoView.onPrepared();
	}
}
