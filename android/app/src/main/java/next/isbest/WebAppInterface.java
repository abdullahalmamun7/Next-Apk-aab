package next.isbest;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    private Context context;
    
    public WebAppInterface(Context c) {
        context = c;
    }
    
    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    
    @JavascriptInterface
    public String getAppVersion() {
        return "3.0.0";
    }
    
    @JavascriptInterface
    public String getPlatform() {
        return "Android";
    }
}