package com.smok.web.algorithm.sort;

import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by liuaifen on 2016/3/16.
 */
public class SortTest {


    public static void main(String[] args) {
        testQuickSort();
    }


    private static void test(String algorithmName) {
        int arrLength = 100;
        int[] arr = generateArray(arrLength);


        System.out.println("before:");
        print(arr);

        System.out.println("after " + algorithmName + ":");
        if (algorithmName.equals("quickSort")) {
            QuickSort.sort(arr);
        } else if (algorithmName.equals("popSort")) {
            PopSort.sort(arr);
        }
        print(arr);
    }

    public static void testQuickSort() {
        test("quickSort");
    }

    public static void testPopSort() {
        test("popSort");
    }


    private static int[] generateArray(int arrayLength) {
        int[] arr = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            arr[i] = RandomUtils.nextInt(arrayLength * 10);
        }
        return arr;
    }

    private static void print(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
