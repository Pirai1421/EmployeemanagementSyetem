
import java.util.*;
import java.sql.*;

public class EmployeeManagementSystem {
    private static ArrayList<Employee> employees = new ArrayList<>();

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/xepdb1";
    private static final String DB_USER = "employeeuser";
    private static final String DB_PASSWORD = "employeeuser";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadEmployeesFromDatabase();
        while (true) {
            System.out.println("\n1. Add Employee\n2. Update Employee\n3. Delete Employee\n4. View Employees\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        addEmployee(scanner);
                        break;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                case 2:
                    updateEmployee(scanner);
                    break;
                case 3:
                    deleteEmployee(scanner);
                    break;
                case 4:
                    viewEmployees(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose between 1 and 5.");
            }
        }
    }

    private static void loadEmployeesFromDatabase() {

            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String sql = "SELECT e.id, e.name, e.designation, e.phone_number, e.salary, e.email, " +
                        "a.street_number, a.street_name, a.city, a.state, a.country, " +
                        "w.company_name, w.designation AS work_designation " +
                        "FROM employees e " +
                        "JOIN addresses a ON e.id = a.employee_id " +
                        "JOIN work_experiences w ON e.id = w.employee_id";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String designation = resultSet.getString("designation");
                    String phoneNum = resultSet.getString("phone_number");
                    double salary = resultSet.getDouble("salary");
                    String emailId = resultSet.getString("email");

                    String streetNumber = resultSet.getString("street_number");
                    String streetName = resultSet.getString("street_name");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    String country = resultSet.getString("country");
                    Address address = new Address(streetNumber, streetName, city, state, country);

                    String companyName = resultSet.getString("company_name");
                    String workDesignation = resultSet.getString("work_designation");
                    WorkExperience workExperience = new WorkExperience(companyName, workDesignation);

                    Employee employee = new Employee(id, name, designation, salary, address, workExperience, phoneNum, emailId);
                    employees.add(employee);
                }

                connection.close();
            } catch (SQLException e) {
                System.out.println("Error loading employees from database: " + e.getMessage());
            }

    }

    private static void addEmployee(Scanner scanner) throws SQLException {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter mail id ");
        String emailId = scanner.nextLine();
        System.out.print("Enter phoneNum ");
        String phoneNum = scanner.nextLine();


        System.out.print("Enter Address of the employee: ");
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
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);


        String getNextIdSQL = "SELECT employee_seq.NEXTVAL FROM dual";
        PreparedStatement getNextIdStmt = connection.prepareStatement(getNextIdSQL);
        ResultSet rs = getNextIdStmt.executeQuery();
        rs.next();
        int employeeId = rs.getInt(1);

        Employee employee = new Employee(employeeId, name, designation, salary, address, workExperience, phoneNum, emailId);
        employees.add(employee);

        String insertEmployeeSQL = "INSERT INTO employees (id, name, designation, phone_number, salary, email) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeSQL);
        preparedStatement.setInt(1, employee.getId());

        preparedStatement.setString(2, name);
        preparedStatement.setString(3, designation);
        preparedStatement.setString(4, phoneNum);
        preparedStatement.setDouble(5, salary);
        preparedStatement.setString(6, emailId);
        preparedStatement.executeUpdate();

        String insertAddressSQL = "INSERT INTO addresses (id, employee_id, street_number, street_name, city, state, country) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addressStatement = connection.prepareStatement(insertAddressSQL);
        addressStatement.setInt(1, getNextAddressId(connection));
        addressStatement.setInt(2, employee.getId());
        addressStatement.setString(3, streetNumber);
        addressStatement.setString(4, streetName);
        addressStatement.setString(5, city);
        addressStatement.setString(6, state);
        addressStatement.setString(7, country);
        addressStatement.executeUpdate();

        String insertWorkExperienceSQL = "INSERT INTO work_experiences (id, employee_id, company_name, designation) VALUES (?, ?, ?, ?)";
        PreparedStatement workExperienceStatement = connection.prepareStatement(insertWorkExperienceSQL);
        workExperienceStatement.setInt(1, getNextWorkExperienceId(connection));
        workExperienceStatement.setInt(2, employee.getId());
        workExperienceStatement.setString(3, companyName);
        workExperienceStatement.setString(4, workDesignation);
        workExperienceStatement.executeUpdate();

        connection.close();
        System.out.println("Employee added successfully!");
    }

    private static int getNextEmployeeId(Connection connection) throws SQLException {
        String sql = "SELECT NVL(MAX(id), 100) + 1 FROM employees";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 101;
    }
    private static int updateEmp(Employee e) throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql="UPDATE EMPLOYEES SET NAME=?,DESIGNATION=?,PHONE_NUMBER=?,SALARY=?,EMAIL=? WHERE ID=?";
        PreparedStatement statement= connection.prepareStatement(sql);

        statement.setString(1,e.getName());
        statement.setString(2,e.getDesignation());
        statement.setString(3, e.getPhoneNum());
        statement.setDouble(4,e.getSalary());
        statement.setString(5,e.getEmailId());
        statement.setInt(6, e.getId());
        int rs=statement.executeUpdate();
        connection.close();
        return rs;
    }

    private static int getNextAddressId(Connection connection) throws SQLException {
        String sql = "SELECT NVL(MAX(id), 200) + 1 FROM addresses";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 201;
    }

    private static int getNextWorkExperienceId(Connection connection) throws SQLException {
        String sql = "SELECT NVL(MAX(id), 300) + 1 FROM work_experiences";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 301;
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

    private static void viewEmployees(Scanner s) {
        System.out.println("enter the employee id to view");
        int idview=s.nextInt();
        s.nextLine();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\nEmployee List:");
            for (Employee employee : employees) {
                if (employee.getId()==idview) {
                    System.out.println(employee);
                }
            }
        }
    }

    private static Employee getEmployeeById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql = "SELECT e.id, e.name, e.designation, e.phone_number, e.salary, e.email, " +
                "a.street_number, a.street_name, a.city, a.state, a.country, " +
                "w.company_name, w.designation AS work_designation " +
                "FROM employees e " +
                "JOIN addresses a ON e.id = a.employee_id " +
                "JOIN work_experiences w ON e.id = w.employee_id " +
                "WHERE e.id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String designation = resultSet.getString("designation");
            String phoneNum = resultSet.getString("phone_number");
            double salary = resultSet.getDouble("salary");
            String emailId = resultSet.getString("email");

            String streetNumber = resultSet.getString("street_number");
            String streetName = resultSet.getString("street_name");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String country = resultSet.getString("country");
            Address address = new Address(streetNumber, streetName, city, state, country);

            String companyName = resultSet.getString("company_name");
            String workDesignation = resultSet.getString("work_designation");
            WorkExperience workExperience = new WorkExperience(companyName, workDesignation);

            Employee employee = new Employee(id, name, designation, salary, address, workExperience, phoneNum, emailId);
            connection.close();
            return employee;
        } else {
            connection.close();
            return null;
        }
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee;
        try {
            employee = getEmployeeById(id);
        } catch (SQLException e) {
            System.out.println("Error fetching employee from database: " + e.getMessage());
            return;
        }

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

        System.out.print("Enter New Email (current: " + employee.getEmailId() + "): ");
        String emailId = scanner.nextLine();

        System.out.print("Enter New Phone Number (current: " + employee.getPhoneNum() + "): ");
        String phoneNum = scanner.nextLine();

        employee.setName(name);
        employee.setDesignation(designation);
        employee.setSalary(salary);

        employee.setPhoneNum(phoneNum);
        employee.setEmailId(emailId);

        try {
            int rowsUpdated = updateEmp(employee);
            if (rowsUpdated == 0) {
                System.out.println("Employee not updated in the database.");
            } else {
                System.out.println("Employee updated in the database.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Employee update successful");
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