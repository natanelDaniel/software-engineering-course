package Tirguls;

import java.util.ArrayList;
import java.util.HashMap;

public class Main_student {
    public static void main(String[] args) {
//        ArrayList<Student> students = new ArrayList<>();
//        students.add(new Student("Yossi", 123));
//        students.add(new Student("Moshe", 456));
//        students.add(new Student("David", 789));
//        students.add(new Student("Avi", 101112));
//        students.add(new Student("Yael", 131415));

        Student yosi = new Student("Yossi", 123);
        HashMap<String, Integer> yosiGrades = new HashMap<>();
        yosiGrades.put("Math", 100);
        yosiGrades.put("English", 90);
        yosiGrades.put("History", 80);

        for (String course : yosiGrades.keySet()) {
            yosi.setGrade(course, yosiGrades.get(course));
        }

        System.out.println(yosi);
    }
}
