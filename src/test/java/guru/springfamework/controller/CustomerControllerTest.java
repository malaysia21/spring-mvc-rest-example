package guru.springfamework.controller;

import guru.springfamework.api.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springfamework.controller.AbstractRestControllerTest.asJasonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstName("A");
        customerDTO1.setLastName("B");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName("A");
        customerDTO2.setLastName("B");

        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO1, customerDTO2);
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get(CustomerController.BASE_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstName("A");
        customerDTO1.setLastName("B");
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getCustomersById(anyLong())).thenReturn(customerDTO1);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("A")));

    }

    @Test
    public void createCustomer() throws Exception {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Aga");
        customerDto.setLastName("Nowak");


        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDto.getFirstName());
        returnedDto.setLastName(customerDto.getLastName());
        returnedDto.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customerDto))
                .thenReturn(returnedDto);

        mockMvc.perform(post(CustomerController.BASE_URL + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Aga")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));

    }


    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Aga");
        customerDto.setLastName("Nowak");


        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDto.getFirstName());
        returnedDto.setLastName(customerDto.getLastName());
        returnedDto.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.updateCustomerByDTO(anyLong(),ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(returnedDto);

        mockMvc.perform(put(CustomerController.BASE_URL + "/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Aga")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));

    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Aga");


        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customerDto.getFirstName());
        returnedDto.setLastName("Nowak");
        returnedDto.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(returnedDto);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Aga")))
                .andExpect(jsonPath("$.lastName", equalTo("Nowak")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}