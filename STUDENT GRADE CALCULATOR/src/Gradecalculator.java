import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gradecalculator {

    // A simple class to hold a student's data
    static class Student {
        String name;
        int totalMarks;
        double averagePercentage;
        String grade;

        public Student(String name, int totalMarks, double averagePercentage, String grade) {
            this.name = name;
            this.totalMarks = totalMarks;
            this.averagePercentage = averagePercentage;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return String.format("Student: %s\nTotal Marks: %d\nAverage Percentage: %.2f%%\nGrade: %s\n",
                    name, totalMarks, averagePercentage, grade);
        }
    }

    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;
        while (choice != 3) {
            System.out.println("\n--- Student Grade Management System ---");
            System.out.println("1. Add new student marks");
            System.out.println("2. View all student results");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                scan.nextLine(); // Consume the newline character
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine(); // Clear the invalid input from the scanner
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
        scan.close();
    }

    /**
     * Guides the user to input data for a new student and adds them to the list.
     */
    private static void addStudent() {
        System.out.print("Enter student's name: ");
        String name = scan.nextLine();

        System.out.print("Enter total number of subjects: ");
        int numSubjects = 0;
        try {
            numSubjects = scan.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number for subjects.");
            scan.nextLine(); // Clear invalid input
            return;
        }

        Student newStudent = calculateStudentResults(name, numSubjects);
        if (newStudent != null) {
            students.add(newStudent);
            System.out.println("Student added successfully!");
        }
    }

    /**
     * Prints the results for all students currently stored in the list.
     */
    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student data available. Please add a student first.");
            return;
        }
        System.out.println("\n--- All Student Results ---");
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }

    /**
     * Calculates the total marks, average percentage, and grade for a single student.
     * @param name The name of the student.
     * @param numSubjects The number of subjects.
     * @return A new Student object with calculated results, or null if input is invalid.
     */
    private static Student calculateStudentResults(String name, int numSubjects) {
        if (numSubjects <= 0) {
            System.out.println("Number of subjects must be greater than zero.");
            return null;
        }

        int totalMarks = 0;
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter marks for subject " + i + " (out of 100): ");
            int marks = -1;
            if (scan.hasNextInt()) {
                marks = scan.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number for marks.");
                scan.nextLine(); // Clear invalid input
                return null;
            }
            scan.nextLine(); // Consume newline

            // INPUT VALIDATION
            while (marks < 0 || marks > 100) {
                System.out.println("Invalid marks. Please enter marks between 0 and 100.");
                System.out.print("Enter marks for subject " + i + " (out of 100): ");
                if (scan.hasNextInt()) {
                    marks = scan.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter a number for marks.");
                    scan.nextLine(); // Clear invalid input
                    return null;
                }
                scan.nextLine(); // Consume newline
            }
            totalMarks += marks;
        }

        System.out.println("\n--- Student Result for " + name + " ---");
        System.out.println("Total marks obtained: " + totalMarks);

        double averagePercentage = (double) totalMarks / numSubjects;
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);

        String grade = calculateGrade(averagePercentage);
        System.out.println("Your Grade: " + grade);

        return new Student(name, totalMarks, averagePercentage, grade);
    }

    /**
     * Determines the grade based on the average percentage.
     * @param averagePercentage The calculated average percentage.
     * @return The corresponding grade as a String.
     */
    public static String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "B+";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C+";
        } else if (averagePercentage >= 50) {
            return "C";
        } else if (averagePercentage >= 40) {
            return "D+";
        } else if (averagePercentage >= 30) {
            return "D";
        } else {
            return "E";
        }
    }
}
