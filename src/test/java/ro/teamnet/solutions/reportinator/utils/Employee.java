package ro.teamnet.solutions.reportinator.utils;

/**
 * Created by Bogdan.Iancu on 17-Feb-15.
 */
public class Employee{
    private int id;
    private String firstName;
    private String lastName;
    private int salary;
    private String department;
    private String address;
    private String position;
    private int hoursPerDay;
    private int yearsOfExperience;


    public Employee() {
    }

    public Employee(int id, String lastName, String firstName, int salary, String department) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.salary = salary;
        this.department = department;
    }

    public Employee(int id, String firstName, String lastName, int salary, String department, String address, String position, int hoursPerDay, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.department = department;
        this.address = address;
        this.position = position;
        this.hoursPerDay = hoursPerDay;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}' + "\n";
    }
}
