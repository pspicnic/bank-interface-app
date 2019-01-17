package za.co.pps;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.bank.mutual.config.ApplicationConfig;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationConfig.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class AbstractTest {
	protected Logger LOG = LoggerFactory.getLogger(this.getClass());
}
