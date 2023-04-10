package service.forcustomer;

import model.customer.Customer;

import java.util.*;

public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<String, Customer>();
    private CustomerService(){ }

    public static CustomerService getSingleton(){
        return SINGLETON; }


    public void addCustomer(String firstName,String email, String lastName) throws IllegalArgumentException {
        Customer customer = Customer.createNewCustomer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String email){
        return customers.get(email);}
    public Collection<Customer>getAllCustomer(){
        return customers.values();}

}
