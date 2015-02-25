package ro.teamnet.solutions.reportinator.utils;

/**
 * @author Bogdan.Iancu
 * @version 1.0 Date: 23-Feb-15
 */

public class Person {
    private int age;
    private String nationality;

    public Person() {
    }

    public Person(int age, String nationality) {
        this.age = age;
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
