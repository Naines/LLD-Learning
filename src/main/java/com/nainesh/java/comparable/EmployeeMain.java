package com.nainesh.java.comparable;

import java.util.Arrays;
import java.util.Comparator;


/**
 *
 *
 *
 * public static <T, U> Comparator<T> comparing(
 *  @NotNull java.util.function.Function<? super T, ? extends U> keyExtractor,
 *   @NotNull Comparator<? super U> keyComparator
 * )
 * keyExtractor – the function used to extract the sort key
 * keyComparator – the Comparator used to compare the sort key
 *
 *
 */
public class EmployeeMain {

    public static void main(String[] args) {

        //
        int[][] arr = {
                {3, 2},
                {1, 5},
                {2, 2},
                {4, 1}
        };

        Arrays.sort(arr,
                Comparator.comparingInt((int[] a) -> a[1]).reversed()
                        .thenComparingInt(a -> a[0]));

        for (int[] a : arr) {
            System.out.println(Arrays.toString(a));
        }

        Employee employees[] = new Employee[]{
                new Employee("John", 25,3000.0, 9922001),
                new Employee("Ace", 22, 2000.0, 5924001),
                new Employee("Keith",35, 4000.0,3924401),
                new Employee("Zack",19, 4000.0,3924401)
            };

        Comparator<Employee> byName = Comparator.comparing(Employee::getName);
        Arrays.sort(employees, byName);
        print(employees);

        Comparator<Employee> byNameDesc = Comparator.comparing(
                Employee::getName, (s1, s2) -> {
                    return s2.compareTo(s1);
                }
        );
        Arrays.sort(employees, byNameDesc);
        print(employees);
        Arrays.sort(employees, byName.reversed());
        print(employees);


        //comparingInt
        System.out.println("\nPRINT BY AGE:");
        Comparator<Employee> byAge = Comparator.comparingInt(Employee::getAge);
        Arrays.sort(employees, byAge);
        print(employees);

        //natural order
        System.out.println("\n PRINT BY NATURAL/DEFAULT ORDER");
        Comparator<Employee> byNaturalOrder = Comparator.<Employee> naturalOrder();
        Arrays.sort(employees, byNaturalOrder);
        print(employees);

        System.out.println("\n PRINT BY NATURAL/DEFAULT REVERSED ORDER");
        Comparator<Employee> byNaturalReversedOrder = Comparator.<Employee> reverseOrder();
        Arrays.sort(employees, byNaturalReversedOrder);
        print(employees);

        //age and then name, uses thenComparing
        System.out.println("\nBY AGE AND THEN NAME");
        Comparator<Employee> byAgeAndName = Comparator.comparing(Employee::getAge).thenComparing(Employee::getName);
        Arrays.sort(employees, byAgeAndName);
        print(employees);

    }

    static void print(Employee arr[]){
        for(Employee e: arr){
            System.out.println(e);
        }
    }



    static class Employee implements Comparable<Employee>{

        String name;
        int age;
        double salary;
        long mobile;

        public Employee(String name, int age, double salary, long mobile) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", mobile=" + mobile +
                    '}';
        }

        @Override
        public int compareTo(Employee o) {
            return this.name.compareTo(o.name);
        }
    }

}
