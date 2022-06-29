package com.example.myvocab;
import static org.assertj.core.api.Assertions.*;

import com.example.myvocab.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {

    @Test
    public void showPath(){
        FileService service=new FileService();

    }

}
