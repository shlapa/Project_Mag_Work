package testproject;

import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@AutoConfigureDataJpa
public class ItemRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    ItemService itemService;

    @Test
    public void contextTest() throws Exception {

        assertThat(itemService).isNotNull();
    }

    @Test
    void testReturnedValue() throws Exception {
        mvc.perform(get("/api/v1/items/")
                        .accept("application/json"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType("application/json")));
    }

    @Test
    public void testGetItemByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/items/{itemId}", 1L)
                        .accept("application/json"))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().contentType("application/json")));
    }

}
