package za.co.pps.bank.mutual.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import za.co.pps.bank.mutual.communication.EmailSenderImpl;
import za.co.pps.bank.mutual.validation.logic.ValidateCreditCard;
import za.pps.bank.sftp.main.PPSSftpConfig;
import za.pps.bank.sftp.main.SftpConfig;


/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 16 Aug 2017
 * @version 1.0
 */

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"za.co.pps.bank.*", "za.pps.bank.*"})
@EnableScheduling
@EnableJpaRepositories("za.co.pps.bank.mutual.data.model")
@EntityScan("za.co.pps.bank.mutual.data.model")
//@IntegrationComponentScan
//@EnableIntegration
@Import({SftpConfig.class,PPSSftpConfig.class,EmailSenderImpl.class,ValidateCreditCard.class })
public class ApplicationConfig extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.sources(ApplicationConfig.class);
	}
	 
    public static void main( String[] args ) throws Exception
    {	
        SpringApplication.run(ApplicationConfig.class, args);
    }
    
    /*
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }
    */
}
