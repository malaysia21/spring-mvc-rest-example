package guru.springfamework.controller;

import guru.springfamework.api.model.VendorDTO;
import guru.springfamework.api.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import static guru.springfamework.controller.AbstractRestControllerTest.asJasonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest {

    @MockBean
    VendorService vendorService;

   @Autowired
    MockMvc mockMvc;

   VendorDTO vendorDTO1;
   VendorDTO vendorDTO2;


    @Before
    public void setUp() throws Exception {
        vendorDTO1 = new VendorDTO("Agnieszka", VendorController.BASE_URL + "/1");
        vendorDTO2 = new VendorDTO("Anna", VendorController.BASE_URL + "/2");
    }

    @Test
    public void getAllVendorsTest() throws Exception {
        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO1, vendorDTO2));

        when(vendorService.getAllVendors()).thenReturn(vendorListDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

    }

    @Test
    public void addVendorTest() throws Exception {

        given(vendorService.createNewVendor(vendorDTO1)).willReturn(vendorDTO1);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorURL())));


    }

    @Test
    public void deleteVendorTest() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());

    }

    @Test
    public void getVendorByIdTest() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO1);

        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorURL())));


    }

    @Test
    public void patchVendorTest() throws Exception {


        when(vendorService.patchVendor(anyLong(), ArgumentMatchers.any(VendorDTO.class))).thenReturn(vendorDTO1);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJasonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorURL())));
    }

    @Test
    public void updateVendorTest() throws Exception {

        when(vendorService.updateVendorByDTO(anyLong(), ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(vendorDTO1);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJasonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorURL())));
    }


}
