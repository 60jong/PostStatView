package site.jongky.poststatview.service;

import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class VelogStatServiceTest {

    @Autowired
    VelogStatService velogStatService;

    @Test
    public void askAllPostIdsByUsername_소요시간_테스트() throws Exception {
        long start = System.currentTimeMillis();

        String username = "rudwhd515";
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZTIxMTY0ZWEtNGFmYy00M2JiLTk0ZDctN2UyMjFmMTA3ZDQyIiwidG9rZW5faWQiOiJmNWZjNzFiOS00NmY0LTQwYzItOWZlMy02NmIxMDI1YjNiNDUiLCJpYXQiOjE2Nzg1MTIwNzcsImV4cCI6MTY4MTEwNDA3NywiaXNzIjoidmVsb2cuaW8iLCJzdWIiOiJyZWZyZXNoX3Rva2VuIn0.v2smKKh-lqvgI9275lO4RO0GHlIaMtcK4zHfMieU9UM";
        velogStatService.askAllPostIdsByUsername(username);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    @Test
    public void getTotalVisitors_소요시간_테스트() throws Exception {
        String username = "rudwhd515";
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZTIxMTY0ZWEtNGFmYy00M2JiLTk0ZDctN2UyMjFmMTA3ZDQyIiwidG9rZW5faWQiOiJmNWZjNzFiOS00NmY0LTQwYzItOWZlMy02NmIxMDI1YjNiNDUiLCJpYXQiOjE2Nzg1MTIwNzcsImV4cCI6MTY4MTEwNDA3NywiaXNzIjoidmVsb2cuaW8iLCJzdWIiOiJyZWZyZXNoX3Rva2VuIn0.v2smKKh-lqvgI9275lO4RO0GHlIaMtcK4zHfMieU9UM";

        long start = System.currentTimeMillis();

        long totalReads = velogStatService.getTotalVisitors(username, refreshToken);
        System.out.println(totalReads);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    @Test
    public void getTas_소요시간_테스트() throws Exception {
        String username = "rudwhd515";


        long start = System.currentTimeMillis();

        List<String> tags = velogStatService.getTags(username);
        System.out.println(tags);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}