package com.smok.web.algorithm.sort;

import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by liuaifen on 2016/3/16.
 */
public class PopSort {


    public static void sort(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        for (; end > start; end--) {
            for (int i = 0; i < end; i++) {
                if (arr[i] > arr[i + 1]) {
                    arr[i] = arr[i] ^ arr[i + 1];
                    arr[i + 1] = arr[i] ^ arr[i + 1];
                    arr[i] = arr[i] ^ arr[i + 1];
                }
            }
        }
    }


}
