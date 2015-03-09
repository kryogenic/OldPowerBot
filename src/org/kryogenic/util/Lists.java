package org.kryogenic.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for manipulating lists and arrays
 * @author kryo
 * @version 1.0
 */
public class Lists {

    public static boolean arrayContains(int[] array, int element) {
        for(int i : array) {
            if(i == element)
                return true;
        }
        return false;
    }

    /**
     * Converts a list of int primitives to an ArrayList of Integer objects
     *
     * @param array the array to convert
     * @return the converted list
     */
    // TODO: Finish the rest of the primitives
    public static List<Integer> toObjectList(int[] array) {
        List<Integer> list = new ArrayList<Integer>(array.length);
        for (int element : array) {
            list.add(element);
        }
        return list;
    }
    
    public static <T> T[] reverse(T[] array) {
        T[] reversedArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
        for(int i = array.length - 1, j = 0; i >= 0; i--, j++) {
            reversedArray[j] = array[i];
        }
        return reversedArray;
    }
    
    public static <T> List<T> reverse(List<T> list) {
        List<T> reversedList = new ArrayList<>();
        for(int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        return reversedList;
    }

}
