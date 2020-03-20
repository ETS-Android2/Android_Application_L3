package fr.info.pl2020.plplg.authentication;

import fr.info.pl2020.plplg.security.StudentDetails;
import fr.info.pl2020.plplg.security.StudentDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc()
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentDetailsService studentDetailsService;

    @Test
    void loginOk() throws Exception {
        StudentDetails sd = new StudentDetails(1,"toto@gmail.com", "$2a$10$fex0RS2Nshik5jiIyWXWC.6MmI6eZb9QW9NLr7gBPNyZTQqWhJIPm");
        when(studentDetailsService.loadUserByUsername(any())).thenReturn(sd);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"toto@gmail.com\", \"password\": \"12345\"}")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void loginBadCredentials() throws Exception {
        StudentDetails sd = new StudentDetails(1,"toto@gmail.com", "$2a$10$fex0RS2Nshik5jiIyWXWC.6MmI6eZb9QW9NLr7gBPNyZTQqWhJIPm");
        when(studentDetailsService.loadUserByUsername(any())).thenReturn(sd);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"toto@gmail.com\", \"password\": \"1234\"}")
                .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Unauthorized")));
    }

    @Test
    void loginEmpty() throws Exception {
        StudentDetails sd = new StudentDetails(1,"toto@gmail.com", "$2a$10$fex0RS2Nshik5jiIyWXWC.6MmI6eZb9QW9NLr7gBPNyZTQqWhJIPm");
        when(studentDetailsService.loadUserByUsername(any())).thenReturn(sd);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{}")
                .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Unauthorized")));
    }
}
