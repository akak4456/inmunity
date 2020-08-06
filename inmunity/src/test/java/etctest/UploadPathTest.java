package etctest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.inmunity.InmunityApplication;

import lombok.extern.java.Log;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
@SuppressWarnings("deprecation")
public class UploadPathTest {
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	@Test
	public void uploadPathTest() {
		log.info(path);
	}
}
