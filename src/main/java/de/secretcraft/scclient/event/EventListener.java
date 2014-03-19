package de.secretcraft.scclient.event;

/**
 * Other event listeners must implement this interface because the
 * {@link EventManager} will only work with implementations of
 * {@link EventListener}.<br>
 * <br>
 * Also you have to register your listener with:<br>
 * {@link EventManager#registerEvents(EventListener)}
 */
public interface EventListener {
	public abstract boolean isDestroyed();
}
