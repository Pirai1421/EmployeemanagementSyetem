public class Employee {
    private int id;
    private String name;
    private String designation;
    private double salary;
    private Address address;
    private WorkExperience workExperience;
    private String emailId;
    private String phoneNum;


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", address=" + address +
                ", workExperience=" + workExperience +
                ", emailId='" + emailId + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }

    public Employee(int id, String name, String designation, double salary, Address address, WorkExperience workExperience, String phoneNum, String emailId) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.address = address;
        this.workExperience = workExperience;
        this.phoneNum=phoneNum;
        this.emailId=emailId;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getEmailId(){
        return emailId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = validateString(name,"name");
    }

    public void setDesignation(String designation) {
        this.designation = validateString(designation,"designation ");
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        }
    }
    private String validateString(String field,String fieldname){
        if(field == null || field.isEmpty() || !field.matches("[a-zA-z\\s]+")){
            throw new IllegalArgumentException(fieldname+"enter valid character");
        }
        return field;
    }
    private String validatePhone(String field,String fieldname){
        if(field == null || field.isEmpty() || !field.matches("[0-9\\s]+") || field.length()!=10){
            throw new IllegalArgumentException(fieldname+"enter valid num");
        }
        return field;
    }
    private String validatemail(String field,String fieldname){
        if(field == null || field.isEmpty() || !field.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$") || field.length()!=10){
            throw new IllegalArgumentException(fieldname+"enter valid num");
        }
        return field;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum=validatePhone(phoneNum,"phone number");

    }
    public void setEmailId(String emailId){
        this.emailId=validateString(emailId,"email id");
    }
}
