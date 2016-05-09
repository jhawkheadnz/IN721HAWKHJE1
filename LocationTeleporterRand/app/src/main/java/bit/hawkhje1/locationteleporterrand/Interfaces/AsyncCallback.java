package bit.hawkhje1.locationteleporterrand.Interfaces;

/**
 * Callback Interface for AsyncTask
 * @param <T> Type of Object for Callback
 */
public interface AsyncCallback<T> {
    void run(T result);
}
