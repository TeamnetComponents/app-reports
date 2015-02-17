package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.solutions.reportinator.convert.ConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BeanCollectionJasperDataSourceConverterTest {



    private List<Employee> employees;
    private List<String> fields;

    @Before
    public void setUp() throws Exception {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "Bogdan", "Iancu", 1000, "Solutii"));
        employees.add(new Employee(2, "Cristi", "Dumitru", 1000, "Solutii"));
        employees.add(new Employee(3, "Oana", "Popescu", 1000, "Solutii"));
        employees.add(new Employee(4, "Alex", "Cojocaru", 1000, "Solutii"));
        employees.add(new Employee(5, "Mihaela", "Scripcaru", 1000, "Solutii"));
        employees.add(new Employee(6, "Sad", "Panda", 1000000, "Boss"));

        fields = new ArrayList<>();
        fields.add("id");
        fields.add("firstName");
        fields.add("lastName");
        fields.add("department");
        fields.add("salary");


    }

    @Test
    public void testShouldReturnAValidJRDataSourceFromBeanCollection() throws Exception {
        BeanCollectionJasperDataSourceConverter<Employee> bcConvertor = new BeanCollectionJasperDataSourceConverter<>(fields);
        assertTrue("Field metadata mismatch between beanCollection and source",bcConvertor.getFieldMetadata().size() == fields.size());
        JRDataSource dataSource = bcConvertor.convert(employees);
        assertNotNull("Data source should not be null",dataSource);
    }

    @Test(expected = ConversionException.class)
    public void testShouldPassIfBeanCollectionIsNull() throws Exception {
        BeanCollectionJasperDataSourceConverter<Employee> bcConvertor = new BeanCollectionJasperDataSourceConverter<>(null);
        assertNull(bcConvertor);
    }

    @Test(expected = ConversionException.class)
    public void testShouldPassIfBeanCollectionIsEmpty() throws Exception {
        BeanCollectionJasperDataSourceConverter<Employee> bcConvertor = new BeanCollectionJasperDataSourceConverter<>(Collections.<String>emptyList());
        assertNull(bcConvertor);
    }

    private class Employee{
        private int id;
        private String firstName;
        private String lastName;
        private int salary;
        private String department;


        public Employee() {
        }

        public Employee(int id, String lastName, String firstName, int salary, String department) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.salary = salary;
            this.department = department;
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
}