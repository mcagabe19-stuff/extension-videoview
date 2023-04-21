# extension-videoview
Native Video Support For Android in Haxeflixel using Java VideoView

## Credits:
- <a href = "https://github.com/MAJigsaw77">Mihai Alexandru (M.A. Jigsaw)</a>  - doing the hole code for the extension

## If You want to use it credit him.

## A code Exemple to be able to use it

```haxe
#if android
import extension.videoview.VideoView;
#end
import flixel.FlxBasic;

class VideoPlayer extends FlxBasic {
	public var finishCallback:Void->Void = null;

	public function new(name:String) {
		super();

	        #if android
                VideoView.playVideo('file:///storage/emulated/0/Videos/video.mp4');// the video can be in any format webm mkv any
                VideoView.onCompletion = function(){
		        if (finishCallback != null){
			        finishCallback();
		        }
                }
		#end
	}
}
```

## just a little good thing you need to know this extension can run videos from the web browser too :)
