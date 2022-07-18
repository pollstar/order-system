package academy.softserve.os.api;

import academy.softserve.os.mapper.AddressMapper;
import academy.softserve.os.model.Address;
import academy.softserve.os.model.UserDetailsImpl;
import academy.softserve.os.repository.UserRepository;
import academy.softserve.os.service.AddressService;
import academy.softserve.os.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AddressControllerGetAddressTest {
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;
    private Address address1, address2;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        address1 = Address.builder()
                .id(1L)
                .city("ХАРЬКОВ")
                .street("СУМСКАЯ")
                .house("10")
                .room("КУХНЯ")
                .build();

        address2 = Address.builder()
                .id(2L)
                .city("ЛЬВІВ")
                .street("АВСТРІЙСКА")
                .house("1")
                .room("РЕСТОРАН")
                .build();
    }

    @WithMockUser(value = "someuser", roles = "WORKER")
    @Test
    void givenApiGetAddress_getAddress_shouldReturnJsonListAddressAndReturnOkResponse() throws Exception {

        var addresses = Arrays.asList(address1, address2);

        when(service.findAddresses()).thenReturn(addresses);

        mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].city").value("ХАРЬКОВ"))
                .andExpect(jsonPath("$[0].street").value("СУМСКАЯ"))
                .andExpect(jsonPath("$[0].house").value("10"))
                .andExpect(jsonPath("$[0].room").value("КУХНЯ"));

    }

    @WithMockUser(value = "someuser", roles = "WORKER")
    @Test
    void givenApiGetAddressWithId_getAddressByIs_shouldReturnJsonAddressAndReturnOkResponse() throws Exception {

        var address1 = Address.builder()
                .id(1L)
                .city("ХАРЬКОВ")
                .street("СУМСКАЯ")
                .house("10")
                .room("КУХНЯ")
                .build();

        when(service.getAddressById(address1.getId())).thenReturn(Optional.of(address1));

        mockMvc.perform(get("/api/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("ХАРЬКОВ"))
                .andExpect(jsonPath("$.street").value("СУМСКАЯ"))
                .andExpect(jsonPath("$.house").value("10"))
                .andExpect(jsonPath("$.room").value("КУХНЯ"));

    }
}