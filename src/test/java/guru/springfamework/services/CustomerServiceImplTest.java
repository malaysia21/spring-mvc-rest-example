package guru.springfamework.services;

import guru.springfamework.api.model.CustomerDTO;
import guru.springfamework.api.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
    @Mock
    CustomerRepository customerRepository;
    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }


    @Test
    public void getAllCustomers() throws Exception {
        List<Customer> customer = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customer);

        List<CustomerDTO> allCustomers = customerService.getAllCustomers();

        assertEquals(2, allCustomers.size());

    }

    @Test
    public void getCustomersById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("A");
        customer.setLastName("B");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customersById = customerService.getCustomersById(1L);

        assertEquals("A", customersById.getFirstName());
        assertEquals("B", customersById.getLastName());
        assertEquals("/shop/customers/1", customersById.getCustomerUrl());

    }

    @Test
    public void createCustomerTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("AGA");
        customerDTO.setLastName("PUZIA");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO newCustomer = customerService.createNewCustomer(customerDTO);

        assertEquals(customerDTO.getFirstName(), newCustomer.getFirstName());
        assertEquals("/shop/customers/1", newCustomer.getCustomerUrl());

    }

    @Test
    public void updateCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("AGA");
        customerDTO.setLastName("PUZIA");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);


        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.updateCustomerByDTO(1L, customerDTO);


        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/shop/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() {
        Long id = 1L;
        customerRepository.deleteById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

}