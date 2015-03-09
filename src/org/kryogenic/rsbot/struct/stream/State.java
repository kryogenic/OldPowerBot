package org.kryogenic.rsbot.struct.stream;

import java.util.Collection;
import java.util.Map;

/**
 * @author: Kale
 * @date: 26/06/13
 *
 */
public abstract class State <T> {
    final Map<String, T> values;

    public State(final Map<String, T> values) {
        this.values = values;
    }

    /**
     * Gets a value
     * @param levels path to the value, ending in its name
     * @return the value, or null if the path was invalid
     */
    public Object get(Object... levels) {
        Map map = values;
        for(Object level : levels) {
            Object value = map.get(level);
            if(value instanceof Map) {
                map = (Map) map.get(level);
            } else {
                return value;
            }
        }
        return null;
    }

    /**
     * Puts a value
     * @param v the value to put
     * @param levels path to value, ending in its name (the key for the value)
     * @return true if the value was put, false otherwise
     */
    public boolean put(Object v, Object... levels) {
        Map map = values;
        for(int i = 0; i < levels.length; i++) {
            if(i == levels.length - 1) {
                map.put(levels[i], v);
                return true;
            } else {
                Object maybeMap = map.get(levels[i]);
                if(maybeMap instanceof Map) {
                    map = (Map) maybeMap;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
