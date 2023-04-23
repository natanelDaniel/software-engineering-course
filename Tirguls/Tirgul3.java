package Tirguls;

import java.util.Arrays;
//plot a graph of the function y = x^2

public class Tirgul3 {
    public static void mulTenRef(int[] arr) {
        arr[0] *= 10;

    }
    public static void mulTenRef2(Integer num) {
        num *= 10;
    }
    public static void mulTenVal(int num) {
        num *= 10;
    }
    public static void mulTenVal2(Integer num) {
        num *= 10;
    }

//    function that gets a number and checks if it is prime
    public static boolean isPrime(int num) {
        int sqrt = (int) Math.sqrt(num);
        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

//    function that gets a number and return all the elements that are prime and makes the number
    public static String primeFactors(int num) {
        if (isPrime(num))
            return "1, " + num + "";
        String str = "1 ";
        for (int i = 2; i <= num/2 + 1; i++) {
            if (num % i == 0 && isPrime(i)) {
                str += ", " + i;
//                str = str.concat(", " + i);
            }
        }
//        remove the last ", "
//        str = str.substring(0, str.length() - 2);
        return str;
    }



    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        mulTenRef(arr);
        System.out.println(arr[0]);
        int num = 5;
        mulTenVal(num);
        System.out.println(num);
//        Wrapper classes is a class that wraps a primitive data type and allows it to be treated as an object.
//        int -> Integer
//        double -> Double
//        boolean -> Boolean
//        char -> Character
//        byte -> Byte
//        short -> Short
//        alweys sent by value
        Integer num2 = 5;
        mulTenVal2(num2);
        System.out.println(num2);
//        this function is not working because what is changing is the pointer to the object in the function, not the object itself
        // make Integer unmutable


        mulTenRef2(num2);

        System.out.println(num2);
//        arrays:
        int arr2[] = new int[5];
        int[] arr3 = new int[5];
        int[] arr4 = {1, 2, 3, 4, 5};

//        strings:
        String str = "Hello";
//        measure time of execution:
        long startTime = System.currentTimeMillis();

        int num3 = 101;
        String str2;
        str2 = primeFactors(num3);
        System.out.println(str2);
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");

        String[] str_arr = str2.split(", ");
//        move to int
        int[] int_arr = new int[str_arr.length];
        for (int i = 0; i < str_arr.length; i++) {
            int_arr[i] = Integer.parseInt(str_arr[i]);
        }
        System.out.println(Arrays.toString(int_arr));


    }
}
