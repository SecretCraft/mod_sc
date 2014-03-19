package de.secretcraft.scclient.event;

import de.secretcraft.scclient.Config;

public class LoadConfigEvent extends BaseEvent {
	public final Config config;
	
	public LoadConfigEvent(Config config) {
		this.config = config;
	}
}
