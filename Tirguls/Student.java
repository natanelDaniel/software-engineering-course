package Tirguls;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Student {
    private String _name;
    private int _id;
    private HashMap<String, Integer> _grades;

    public Student() {this("", 0);}

    public Student(String name, int id) {
        this._name = name;
        this._id = id;
        this._grades = new HashMap<String, Integer>();
    }

    public String getName() {return this._name;}

    public int getId() {return this._id;}

    private boolean isValidCourse(String course) {
        return this._grades.containsKey(course);
    }
    public int getGrade(String course) {
        if (isValidCourse(course)) {
            return this._grades.get(course);
        }
        return -1;
    }

    private static boolean isValidGrade(int grade) {
        return grade >= 0 && grade <= 100;
    }
    public void setGrade(String course, int grade) {
        if (!isValidGrade(grade)) {
            System.out.println("Invalid grade");
            return;
        }
        this._grades.put(course, grade);
    }

    public double getAverage() {
        if (this._grades.size() == 0) {
            return 0;
        }
        double sum = 0;
        for (Integer grade : this._grades.values()) {
            sum += grade;
        }
        return sum / this._grades.size();
    }

    public String highestGrade() {
        String course = "";
        int max = -1;
        int currentGrade;
        String currentCourse;

        Iterator<String> it = this._grades.keySet().iterator();
        while (it.hasNext()) {
            currentCourse = it.next();
            currentGrade = this._grades.get(currentCourse);
            if (currentGrade > max) {
                max = currentGrade;
                course = currentCourse;
            }
        }

//        option 2:
        for (Map.Entry<String, Integer> entry : this._grades.entrySet()) {
            currentCourse = entry.getKey();
            currentGrade = entry.getValue();
            if (currentGrade > max) {
                max = currentGrade;
                course = currentCourse;
            }
        }
        return course;
    }

    public String toString() {
        String str =  "Name: " + this._name + ", ID: " + this._id;
        if (this._grades.size() >= 0) {
            str += "\nGrades:";

            for (Map.Entry<String, Integer> entry : this._grades.entrySet()) {
                str += "\n\t" + entry.getKey() + ": " + entry.getValue();

            }
            str += "\nAverage: " + this.getAverage();
        }
        return str;
    }

    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student s = (Student) o;
            return this._id == s._id && this._name.equals(s._name);
        }
        return false;
    }
}