package com.smok.web.algorithm.sort;

/**
 * Created by liuaifen on 2016/3/16.
 */
public class QuickSort {

    public static void sort(int[] arr) {

        sortRecusion(arr, 0, arr.length - 1);


    }

    private static void sortRecusion(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int low = start;
        int high = end;
        while (low < high) {
            if (arr[low] >= arr[low + 1]) {
                arr[low] = arr[low] ^ arr[low + 1];
                arr[low + 1] = arr[low] ^ arr[low + 1];
                arr[low] = arr[low] ^ arr[low + 1];

                low = low + 1;
            } else {
                if (low + 1 < high) {
                    arr[low + 1] = arr[low + 1] ^ arr[high];
                    arr[high] = arr[low + 1] ^ arr[high];
                    arr[low + 1] = arr[low + 1] ^ arr[high];
                }

                high = high - 1;
            }
        }

        sortRecusion(arr, start, low - 1);
        sortRecusion(arr, low + 1, end);
    }
}
