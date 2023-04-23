package Tirguls;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Tirgul4 {
    public static void main(String[] args) {
        // in java there is no template for a data type
        // there is collections that can hold any data type
        // all the collection can hendel only objects, not primitive data types - we can use wrapper classes
        // 2 types of collections:
        // 1. group of elements
        // 2. map - key value pairs(like a dictionary, hash table)
        // we can prepare the collection to hold the specific data type

        // collections : Map -> Hashmap, TreeMap
        //               List -> ArrayList, LinkedList
        //               Set -> HashSet, TreeSet
        //               Vector(like ArrayList but synchronized - thread safe, the data is synchronized
        //               between the threads)

        // Vectors: is alternative to ArrayList, it is synchronized, when we dont know how many elements we will have
        //          we can use Vector, it is slower than ArrayList
        //          we can append elements to the end of the vector, we can insert elements to the middle of the vector
        //          and to the start of the vector
        //          under java.util package
        // Vector<type> name = new Vector<type>();
        // v.firstElement() - returns the first element in the vector

        // Known collections:
        // ArrayList - is a list, we can add elements to the end of the list, we can insert elements to the middle of the list
        //             and to the start of the list

        // Set - is a collection that holds only unique elements, we can add elements to the set, we can remove elements from the set

        // Map - is a collection that holds key value pairs, we can add elements to the map, we can remove elements from the map
        // LinkedHashMap - is a map that holds the order of the elements that we added to the map
        // TreeMap - is a map that holds the elements in a sorted order - red black tree

        // iterator - is an object that allows us to iterate over a collection
        // example:
        // ArrayList<robot> arr = new ArrayList<robot>();
        // Iterator<robot> itr = arr.iterator();
        // while(itr.hasNext()) {
        //     robot r = itr.next();
        //     System.out.println(r.getName());
        // }
        // for(Robot r : arr) {
        //     System.out.println(r.getName());
        // }

        // HashSet:
        // HashSet<type> name = new HashSet<type>();

        // HashMap:
        // HashMap<key type, value type> name = new HashMap<key type, value type>();
        // Iterator<key type> itr = name.keySet().iterator();
        // while(itr.hasNext()) {
        //     key type key = itr.next();
        //     value type value = name.get(key);
        // }
        // for(key type key : name.keySet()) {
        //     value type value = name.get(key);
        // }
//        call garbage collector
//        System.gc();


        ArrayList<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");

        for (String color : colors) {
            System.out.println(color);
        }
        System.out.println("colors with tostring: " + colors);

        colors.add(0, "pink");

        String color = colors.get(0);
        System.out.println(" first color is " + color);

        // get last element
        color = colors.get(colors.size() - 1);
        System.out.println(" last color is " + color);

//        set function:
        colors.set(0, "black");
        System.out.println("colors: " + colors);

//        remove function:
        colors.remove(0);
        System.out.println("after removed first color: " + colors);

        if (colors.contains("blue")) {
            System.out.println("blue is in the list");
        }
        else {
            System.out.println("blue is not in the list");
        }
        System.out.println(colors);
        Collections.sort(colors);
        System.out.println(colors);


        // subList:
        List<String> subColors = colors.subList(0, 2);
        System.out.println("subColors: " + subColors);

        List<String> list = new ArrayList<>();


        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        System.out.println("sum of arr: " + sumArrayList(arr));





    }
    public static int sumArrayList(ArrayList<Integer> arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
//        return sum;
        //
        return arr.stream().mapToInt(Integer::intValue).sum();
        // stream() - returns a sequential stream considering collection as its source
        // mapToInt - returns an IntStream consisting of the results of applying the given function to the elements of this stream
        // sum() - returns the sum of elements in this stream
        // return arr.stream().mapToInt(i -> i).sum();
    }
    public static int func(ArrayList<Integer> arr) {
        if (arr.size() == 0) {
            return 0;
        }
        else {
            int last = arr.remove(arr.size() - 1);
            return last + func(arr);
        }
    }
}

