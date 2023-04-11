package sk.ness.academy.springbootHibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDataSourceConfig;

@SpringBootTest
@ContextConfiguration(classes = { TestDataSourceConfig.class })
class AssignmentApplicationTests {

	@Test
	void contextLoads() {
	}

}
