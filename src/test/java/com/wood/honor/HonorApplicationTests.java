package com.wood.honor;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.wood.honor.utils.AesUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@SpringBootTest
class HonorApplicationTests {

    private String language;

    @Value("#{systemProperties['user.language']}")
    private String locale;

    @Value("#{systemProperties['user.name']}")
    private String username;

    @Value("#{systemEnvironment}")
    private Map env;

    @Resource
    private Tika tika;

    @Test
    public String parseFile() throws IOException, TikaException {
        Path path = Paths.get("D:", "temp", "项目经理任命书-测试0003-20231101.xlsx");
        File file = path.toFile();
        System.out.println(file.getAbsolutePath());
        String data = tika.parseToString(file);
        System.out.println(data.length());
        return data;
    }

    @Test
    void contextLoads(@Value("#{systemProperties['java.io.tmpdir']}") String tmpdir) {
        System.out.println(this.locale);
        System.out.println(this.username);
        System.out.println(tmpdir);
        this.env.forEach((o, o2) -> {
            System.out.println(o + "=" +o2);
        });
    }


    @Test
    void myTest10() {
//        OGeXj/Ab6xixh4XOwqCJrw==
//        Rwg3A4+d5VAjUCG3fB9SFw==
        String factor = "xu.dm118dAADF!@$";
        String mobile = "OGeXj/Ab6xixh4XOwqCJrw==";
        String name = "Rwg3A4+d5VAjUCG3fB9SFw==";
        String decrypt = AesUtils.decrypt(mobile, factor);
        String decrypt2 = AesUtils.decrypt(name, factor);
        System.out.println(decrypt);
        System.out.println(decrypt2);


    }

}
