package learn.rr.microservice.productms.it;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public abstract  class AbstractIntegratinTest {
    @Autowired
    private  CassandraAdminOperations adminTemplate ;

    @Autowired
    ResourceLoader resourceLoader;

    @BeforeAll
    protected void setupEnvironment() throws IOException {
        List<String> queries = getQueries();
        for(String query : queries){
            adminTemplate.getCqlOperations().execute(query);
        }
    }

    private  List<String> getQueries() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:database/data.cql");
        String data = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        data= data.replaceAll("\\n","");
        System.out.println(data);
        return Arrays.asList(data.split(";"));
    }

    @AfterAll
    protected  void destroy(){
     adminTemplate.getCqlOperations().execute("DROP TABLE IF EXISTS product");
    }

    protected String convertObjectToJson(Object object){
        return new Gson().toJson(object);
    }


}
