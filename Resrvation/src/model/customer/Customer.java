package model.customer;

import java.util.regex.Pattern;

public class Customer {
    private  String firstName;
    private  String lastName;
    private String email;

    private static final String emailRegex = "^(.+)@(.+).(.+)$";


    private static final Pattern pattern = Pattern.compile(emailRegex);
    private static boolean isValidEmail(){
        String email = "idoladd@gmail.com";
        return pattern.matcher(email).matches();
    }


    public Customer (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static Customer createNewCustomer(String firstName, String lastName, String email)
            throws IllegalArgumentException {
        if(isValidEmail()) {
            return new Customer(firstName, lastName, email);
        } else {
        throw new IllegalArgumentException("Email format not recognized");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        return this.email.equals(((Customer)object).email)? true : false;
    }
    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
