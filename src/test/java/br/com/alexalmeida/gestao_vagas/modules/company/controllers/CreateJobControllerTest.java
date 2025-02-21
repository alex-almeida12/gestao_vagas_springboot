package br.com.alexalmeida.gestao_vagas.modules.company.controllers;

import br.com.alexalmeida.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception{


        var createdJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createdJobDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    private static String objectToJson(Object obj){
        try{
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}