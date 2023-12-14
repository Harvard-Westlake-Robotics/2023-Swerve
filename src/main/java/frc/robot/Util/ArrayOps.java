package frc.robot.Util;

/**
 * The ArrayOps class provides static utility methods for array operations.
 */
public class ArrayOps {
    
    /**
     * Adds a new element to the end of an array, returning a new array instance with the element appended.
     *
     * @param arr The original array to which the new element will be added.
     * @param val The element to add to the array.
     * @return A new array that contains all elements of the original array plus the new element.
     */
    @SuppressWarnings("unchecked")
    static <T> T[] add(T[] arr, T val) {
        // Create a new array with a size larger by one to accommodate the new value.
        final T[] newArr = (T[]) new Object[arr.length + 1];
        // Copy the contents of the original array to the new array.
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        // Set the last element of the new array to the new value.
        newArr[newArr.length - 1] = val;
        // Return the new array with the added element.
        return newArr;
    }
}
