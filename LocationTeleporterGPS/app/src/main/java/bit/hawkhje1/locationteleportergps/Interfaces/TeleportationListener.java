package bit.hawkhje1.locationteleportergps.Interfaces;

/**
 * Teleportation Listener for teleportation event
 * Raised when the Teleport is called in Teleportation Manager
 */
public interface TeleportationListener {
    void onTeleport(Object... data);
}
