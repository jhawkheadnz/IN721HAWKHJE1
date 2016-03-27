package bit.hawkhje1.languagetrainer;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static App app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    /**
     * Get the context externally for LanguageManager (test)
     * @return Application Context
     */
    public static Context getContext() {
        return app.getApplicationContext();
    }

}