public class Address {
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;

    public Address(String streetNumber, String streetName, String city, String state, String country) {
        this.streetNumber = validateString(streetNumber, "Street Number");
        this.streetName = validateString(streetName, "Street Name");
        this.city = validateString(city, "City");
        this.state = validateString(state, "State");
        this.country = validateString(country, "Country");
    }
    private String validateString(String field,String fieldname){
        if(field == null || field.isEmpty() || !field.matches("[a-zA-z\\s]+")){
            throw new IllegalArgumentException(fieldname+"enter valid character");
        }
        return field;
    }
    @Override
    public String toString() {
        return streetNumber + " " + streetName + ", " + city + ", " + state + ", " + country;
    }
}