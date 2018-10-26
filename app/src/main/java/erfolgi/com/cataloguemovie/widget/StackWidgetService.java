package erfolgi.com.cataloguemovie.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("->->", "SERVICE");
        return new StackRemotesViewFactory(this.getApplicationContext(), intent);
    }




}
