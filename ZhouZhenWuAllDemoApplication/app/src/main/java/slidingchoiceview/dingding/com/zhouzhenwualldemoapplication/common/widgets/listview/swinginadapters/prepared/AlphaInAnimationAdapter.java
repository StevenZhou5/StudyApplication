package slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.widgets.listview.swinginadapters.prepared;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;

import slidingchoiceview.dingding.com.zhouzhenwualldemoapplication.common.widgets.listview.swinginadapters.AnimationAdapter;

public class AlphaInAnimationAdapter extends AnimationAdapter {

	public AlphaInAnimationAdapter(BaseAdapter baseAdapter) {
		super(baseAdapter);
	}

	@Override
	protected long getAnimationDelayMillis() {
		return DEFAULTANIMATIONDELAYMILLIS;
	}

	@Override
	protected long getAnimationDurationMillis() {
		return DEFAULTANIMATIONDURATIONMILLIS;
	}

	@Override
	public Animator[] getAnimators(ViewGroup parent, View view) {
		return new Animator[0];
	}
}
