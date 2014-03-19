package de.secretcraft.scclient.event;

/**
 * Disable Events can be disabled, so that their initial minecraft function
 * won't be called.
 */
public abstract class DisableEvent extends BaseEvent {
	private boolean isDisabled = false;

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean disabled) {
		isDisabled = disabled;
	}
}
