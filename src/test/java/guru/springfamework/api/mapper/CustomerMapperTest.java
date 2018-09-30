package guru.springfamework.api.mapper;

import guru.springfamework.api.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDto() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ana");
        customer.setLastName("Nowak");


        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        assertEquals("Ana", customerDTO.getFirstName());
        assertEquals("Nowak", customerDTO.getLastName());

    }

}