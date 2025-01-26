import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static int empIdCounter = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Employee\n2. Update Employee\n3. Delete Employee\n4. View Employees\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    updateEmployee(scanner);
                    break;
                case 3:
                    deleteEmployee(scanner);
                    break;
                case 4:
                    viewEmployees();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose between 1 and 5.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter mail id ");
        String emailId=scanner.nextLine();
        System.out.print("Enter phoneNum ");
        String phoneNum=scanner.nextLine();


        System.out.print("Enter Address of the empolyee: ");
        System.out.print("Enter Street Number: ");
        String streetNumber = scanner.nextLine();

        System.out.print("Enter Street Name: ");
        String streetName = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        System.out.print("Enter Country: ");
        String country = scanner.nextLine();

        Address address = new Address(streetNumber, streetName, city, state, country);

        System.out.println("Enter the company details");
        System.out.print("Enter Company Name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter Work Designation: ");
        String workDesignation = scanner.nextLine();

        WorkExperience workExperience = new WorkExperience(companyName, workDesignation);


        Employee employee = new Employee(empIdCounter++, name, designation, salary, address, workExperience, phoneNum,emailId);
        employees.add(employee);

        System.out.println("Employee added successfully!");
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = findEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.print("Enter New Name (current: " + employee.getName() + "): ");
        String name = scanner.nextLine();

        System.out.print("Enter New Designation (current: " + employee.getDesignation() + "): ");
        String designation = scanner.nextLine();

        System.out.print("Enter New Salary (current: " + employee.getSalary() + "): ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter New email (current: " + employee.getEmailId() + "): ");
        String emailId = scanner.nextLine();
        System.out.print("Enter New phone number (current: " + employee.getEmailId() + "): ");
        String phoneNum = scanner.nextLine();


        employee.setName(name);
        employee.setDesignation(designation);
        employee.setSalary(salary);
        employee.setEmailId(emailId);
        employee.setPhoneNum(phoneNum);

        System.out.println("Employee update successful");
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();

        Employee employee = findEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee deleted successfully!");
    }

    private static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\nEmployee List:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static Employee findEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}
/// new commit