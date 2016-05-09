package bit.hawkhje1.locationteleportergps.Managers;

import android.content.Context;

import bit.hawkhje1.locationteleportergps.Classes.GeoPluginAsyncTask;
import bit.hawkhje1.locationteleportergps.Classes.GeoPluginInfo;
import bit.hawkhje1.locationteleportergps.Interfaces.AsyncCallback;
import bit.hawkhje1.locationteleportergps.Interfaces.TeleportationListener;

/**
 * Manages Teleportation
 */
public class TeleportationManager {

    // for log cat
    private static final String TELEPORTATION_MANAGER_INFO = "TELEPORTATION_MANAGER_INFO";

    // reference to context
    private Context context;

    // create teleportation listener
    private TeleportationListener teleportationListener;

    // constructor
    public TeleportationManager(Context context) {

        // set teleportation listener to null by default
        teleportationListener = null;

        // set the context
        this.context = context;
    }

    // set the teleportation listener
    public void setTeleportationListener(TeleportationListener listener){
        this.teleportationListener = listener;
    }

    // execute teleportation
    public void Teleport(){

        // create geoplugin async task
        GeoPluginAsyncTask geoPluginAsyncTask = new GeoPluginAsyncTask(context);

        // create callback handler
        GeoPluginCallbackHandler geoPluginCallbackHandler = new GeoPluginCallbackHandler();

        // attach the callback handler to geopluingAsyncTask
        geoPluginAsyncTask.setAsyncCallbackListener(geoPluginCallbackHandler);

        // execute async task
        geoPluginAsyncTask.execute();

    }

    // callback handler for geoplugin
    private class GeoPluginCallbackHandler implements AsyncCallback<GeoPluginInfo>{

        @Override
        public void run(GeoPluginInfo result) {
            if(teleportationListener != null)
                teleportationListener.onTeleport(result);
        }
    }

}

