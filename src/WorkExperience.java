public class WorkExperience {
    private String companyName;
    private String designation;

    public WorkExperience(String CompanyName, String Designation){
        this.companyName=ValidateString(CompanyName,"Companyname");
        this.designation=ValidateString(Designation,"Designation");
    }
    public String getDesignation(){
        return this.designation;
    }
    public String getCompanyName(){
        return this.companyName;
    }
    private String ValidateString(String field,String fieldname){
        if(field == null || field.isEmpty() || !field.matches("[a-zA-z\\s]+")){
            throw new IllegalArgumentException(fieldname+"enter valid character");
        }
        return field;
    }

    @Override
    public String toString() {
        return "Company: " + companyName + ", Designation: " + designation;
    }
}
