package de.secretcraft.scclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import de.secretcraft.scclient.event.EventManager;
import de.secretcraft.scclient.event.LoadConfigEvent;

public class Config {
	protected String name; 

	protected Map<String, String> data = new TreeMap<String, String>();
	
	protected boolean isModified = false;
	
	public Config(String fileName) {
		load(fileName);
	}
	
	/**
	 * Loads the file, triggers the {@link LoadConfigEvent} and then saves the file.
	 */
	public void reload() {
		load();
		EventManager.fireEvent(new LoadConfigEvent(this));
		if(isModified)
			save();
	}
	
	public void load() {
		load(name);
	}
	
	protected void load(String fileName) {
		name = fileName;
		File oFile = new File(fileName);
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(oFile));
			String line = "";
			while ((line = file.readLine()) != null) {
				String[] str = line.split(":", 2);
				if(str.length <= 1) continue;
				data.put(str[0].trim(), str[1].trim());
	        }
			file.close();
			isModified = false;
		} catch (FileNotFoundException fnfe) {
			// will be filled with default values and then be saved anyways
			// so this is expected for the first run
			// --> no print of stack trace
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				file.close();
			} catch(Exception e) {}
		}
	}
	
	public void save() {
		save(name);
	}
	
	protected void save(String fileName) {
		name = fileName;
		File oFile = new File(fileName);
		BufferedWriter file = null;
		try {
			file = new BufferedWriter(new FileWriter(oFile));
			for(Entry<String, String> entry : data.entrySet()) {
				file.write(entry.getKey() + ":" + entry.getValue() + "\n");
			}
			file.close();
			isModified = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch(Exception e) {}
		}
	}
	
	public void set(String key, Object value) {
		data.put(key, String.valueOf(value));
		isModified = true;
	}
	
	public void setDefault(String key, Object value) {
		if(!data.containsKey(key)) {
			data.put(key, String.valueOf(value));
			isModified = true;
		}
	}
	
	public int getInteger(String key) {
		try {
			return Integer.valueOf(data.get(key));
		} catch(Exception e) {
			return 0;
		}
	}
	
	public float getFloat(String key) {
		try {
			return Float.valueOf(data.get(key));
		} catch(Exception e) {
			return 0;
		}
	}
	
	public double getDouble(String key) {
		try {
			return Double.valueOf(data.get(key));
		} catch(Exception e) {
			return 0;
		}
	}
	
	public boolean getBoolean(String key) {
		try {
			return Boolean.valueOf(data.get(key));
		} catch(Exception e) {
			return false;
		}
	}
	
	public String getString(String key) {
		String val = data.get(key);
		return (val == null) ? "" : val;
	}
}
