package de.secretcraft.scclient.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import de.secretcraft.scclient.FastSign;
import de.secretcraft.scclient.util.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class EventManager {
	private EventManager() {}

	public static void init() {
		map = new HashMap();
		triggerEvents = true;
		
		registerEvents(new EventSpecifier());
		registerEvents(new FastSign());
	}
	
	public static void sendPacketWithoutEvent(Packet packet) {
		boolean tmp = triggerEvents;
		triggerEvents = false;
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
		triggerEvents = tmp;
	}

	/**
	 * Call this method to fire an event. All of its listeners are called.
	 * 
	 * @param event
	 *            The event to be fired.
	 * @return {@link DisableEvent#isDisabled() event.isDisabeled()}
	 */
	public static boolean fireEvent(BaseEvent event) {
		if(!triggerEvents)
			return false;
		List<Pair<EventListener, Method>> l = map.get(event.getClass());
		if (l == null && event instanceof DisableEvent)
			return ((DisableEvent) event).isDisabled();
		if (l == null)
			return false;
		for (int i = 0; i < l.size(); ++i) {
			Pair<EventListener, Method> p = l.get(i);
			if(p.getFirst().isDestroyed()) {
				l.remove(i--);
				continue;
			}
			try {
				p.getSecond().invoke(p.getFirst(), event);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if (event instanceof DisableEvent)
			return ((DisableEvent) event).isDisabled();
		return false;
	}

	/**
	 * Registers all Methods of the listener with one parameter as an event
	 * listener method. The parameter's class must be a subclass of
	 * {@link BaseEvent}.<br>
	 * 
	 * Also registers events for Forge AND the FMLEventBus.
	 * 
	 * @param listener
	 */
	public static void registerEvents(EventListener listener) {
		MinecraftForge.EVENT_BUS.register(listener);
		FMLCommonHandler.instance().bus().register(listener);
		Method[] methods = listener.getClass().getMethods();
		for (Method m : methods) {
			registerMethod(listener, m);
		}
	}
	
	public static boolean registerMethod(EventListener listener, Method m) {
		Class<?>[] params = m.getParameterTypes();
		if (params.length != 1 || params[0].getName().startsWith("[")
				|| !isSubClassOf(params[0], BaseEvent.class)) {
			return false;
		}
	
		List l = map.get(params[0]);
		if (l == null) {
			l = new ArrayList<Pair<EventListener, Method>>();
			map.put(params[0], l);
		}
		l.add(new Pair(listener, m));
		return true;
	}

	private static boolean isSubClassOf(Class check, Class sup) {
		Class sup2 = check;
		while (!sup.equals(sup2)) {
			sup2 = sup2.getSuperclass();
			if (sup2 == null)
				return false;
		}
		return true;
	}

	private static String afterLastDot(String str) {
		return str.substring(str.lastIndexOf('.') + 1);
	}

	private static HashMap<Class, List<Pair<EventListener, Method>>> map = new HashMap();
	public static boolean triggerEvents = true;
}
