package org.jtest.app.util;

import org.jtest.app.runtime.RunTimeConfig;

public class MapUtil {
	public static String getMapValue(String key) {
		return RunTimeConfig.configmap.get(RunTimeConfig.getCurrentThreadName()).get(key);
	}

	public static void putMapValue(String key, String value) {
		RunTimeConfig.configmap.get(RunTimeConfig.getCurrentThreadName()).put(key, value);
	}
}
