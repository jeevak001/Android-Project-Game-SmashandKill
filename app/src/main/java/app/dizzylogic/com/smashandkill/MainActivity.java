package app.dizzylogic.com.smashandkill;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends Activity {

    private TitleScreen mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainScreen = new TitleScreen(this);
        setContentView(mainScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainScreen.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainScreen.pause();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Toast.makeText(this,"Back",Toast.LENGTH_SHORT).show();
            mainScreen.setActionForBackButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

