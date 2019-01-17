/**
 * 
 */
package za.co.pps.bank.mutual.data.acknoledgements;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import za.co.pps.AbstractTest;
import za.co.pps.bank.mutual.data.acknowledgements.AbstractAcknowledgement;
import za.co.pps.bank.mutual.data.acknowledgements.AcceptedAcknowledgement;
import za.co.pps.bank.mutual.data.acknowledgements.Issue;
import za.co.pps.bank.mutual.data.acknowledgements.Issues;
import za.co.pps.bank.mutual.data.acknowledgements.PendingAcknowledgement;
import za.co.pps.bank.mutual.data.acknowledgements.ProcessedAcknowledgement;
import za.co.pps.bank.mutual.data.acknowledgements.RejectedAcknowledgement;
import za.co.pps.bank.mutual.logic.NABResponseHandlerService;
import za.co.pps.bank.mutual.utils.CSVWriter;
import za.co.pps.bank.mutual.utils.PropertiesUtil;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 06 Sep 2017
 * @version 1.0
 */
@Profile(value = "LOCAL" )
@ActiveProfiles("LOCAL")
public class AcknowledgementsTest  extends AbstractTest{

	
	@Value("${file.output.exception.location}") 
	private String fileOutput;
	
	@Autowired
	CSVWriter csvWriter;
	
	@Autowired
	private PropertiesUtil propUtils; 
	
	@Autowired
	private NABResponseHandlerService nabResponseService;
	
	@Before
	public void setUp(){
	
	}
	
	private  void createXml(Class<?> clazz, Object instance, String location) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(clazz);
        Marshaller marshaller = jc.createMarshaller();
       // marshaller.marshal( instance, System.out);
        File file = new File(location);
        testFile(file);
        marshaller.marshal(instance,file);
	}
	

	private  AbstractAcknowledgement getObject(Class<? extends AbstractAcknowledgement> clazz, File xml){
		return JAXB.unmarshal(xml, clazz);
	}
	
	
	@Test
	//@Ignore
	@Rollback(value=true)
	public  void test_create_processed_acknowledgement() throws Exception{
		
		ProcessedAcknowledgement processed = new ProcessedAcknowledgement();
		processed.setPaymentId(50395420L);
		processed.setOriginalMessageId(50395418L);
		processed.setDatetime("2015/12/23");
		processed.setCustomerId("TEST02AU");
		processed.setCompanyName("Test Company Pty Ltd");
		processed.setUserMessage("Payment 50,395,420 has been successfully validated and will be forwaded to the processing system.");
		processed.setOriginalFileName("TEST.ABA.ENC");
		processed.setOriginalReference("Encrypted file");

		Issues issues = new Issues();
		issues.getIssuesList().add(new Issue("Uploaded Interchange 111274 for Customer 52504 and Payment Type DL_DIRECTDEBIT."));
		processed.setIssues( issues );
		String location = propUtils.getStatus_dd_folder()+"AcknoledgeMent_PROCESSED.ACK";
		createXml(ProcessedAcknowledgement.class, processed,location);
		System.out.println("==============ProcessedAcknowledgement=================");
	}
	
	
	@Test
	@Ignore
	public void test_create_accepted_acknowledgement()throws Exception{
		AcceptedAcknowledgement accepted = new AcceptedAcknowledgement();
		accepted.setPaymentId(50395420L);
		accepted.setOriginalMessageId(50395418L);
		accepted.setDatetime("2015/12/23");
		accepted.setCustomerId("TEST02AU");
		accepted.setCompanyName("Test Company Pty Ltd");
		accepted.setUserMessage("Payment 50,395,420 has been successfully validated and will be forwaded to the processing system.");
		accepted.setOriginalFileName("TEST.ABA.ENC");
		accepted.setOriginalReference("Encrypted file");
		
		String location = propUtils.getStatus_dd_folder()+"AcknoledgeMent_ACCEPTED.ACK";
		createXml(AcceptedAcknowledgement.class, accepted,location );
		System.out.println("===============AcceptedAcknowledgement========== ======" );
		System.out.println( propUtils.toString());
		
	}
	
	@Test
	@Ignore
	public  void test_create_pending_acknowledgement() throws Exception{
		
		PendingAcknowledgement pending = new PendingAcknowledgement();
		pending.setPaymentId(50395420L);
		pending.setOriginalMessageId(50395418L);
		pending.setDatetime("2015/12/23");
		pending.setCustomerId("TEST02AU");
		pending.setCompanyName("Test Company Pty Ltd");
		pending.setUserMessage("Payment 50,395,420 has been successfully validated and will be forwaded to the processing system.");
		pending.setOriginalFileName("TEST.ABA.ENC");
		pending.setOriginalReference("Encrypted file");

		Issues issues = new Issues();
		issues.getIssuesList().add(new Issue("Uploaded Interchange 111274 for Customer 52504 and Payment Type DL_DIRECTDEBIT."));
		pending.setIssues( issues );
	 
		String location = propUtils.getStatus_dd_folder()+"AcknoledgeMent_PENDING.ACK";
		createXml(PendingAcknowledgement.class, pending,location);
		System.out.println("==============PendingAcknowledgement=================");
	}
	
	@Test
	@Ignore
	public  void test_create_rejected_acknowledgement() throws Exception{
		
		RejectedAcknowledgement rejected = new RejectedAcknowledgement();
		rejected.setPaymentId(50395420L);
		rejected.setOriginalMessageId(50395418L);
		rejected.setDatetime("2015/12/23");
		rejected.setCustomerId("TEST02AU");
		rejected.setCompanyName("Test Company Pty Ltd");
		rejected.setUserMessage("Payment 50,395,420 has been successfully validated and will be forwaded to the processing system.");
		rejected.setOriginalFileName("TEST.ABA.ENC");
		rejected.setOriginalReference("Encrypted file");

		Issues issues = new Issues();
		issues.getIssuesList().add(new Issue("Uploaded Interchange 111274 for Customer 52504 and Payment Type DL_DIRECTDEBIT."));
		issues.getIssuesList().add(new Issue("Payment failed validation and requires repair"));
		issues.getIssuesList().add(new Issue("Repair is not allowed for this payment. Payment has been rejected"));

		rejected.setIssues( issues );
		String location = propUtils.getStatus_dd_folder()+"AcknoledgeMent_REJECTED.ACK";
		createXml(RejectedAcknowledgement.class, rejected,location);
		
		RejectedAcknowledgement marshalledObject = (RejectedAcknowledgement) getObject(RejectedAcknowledgement.class, new File(location));
		
		assertThat( marshalledObject.getCustomerId() )
			.isNotBlank()
			.isNotEmpty()
			.isEqualToIgnoringCase("TEST02AU");
		   
		System.out.println("==============PendingAcknowledgement=================");
		
	}
	
	@Test 
	@Ignore
	public void tes_process_acknowledgements() throws Exception{
		nabResponseService.processAcknowledgements();
	}
	
	/**
	 * @param file
	 */
	private void testFile(File file) {
		
		System.out.println("===================");
		System.out.println(file.getName());
		
	}
	
	
	public static void main(String...args){
		
System.out.println("===================");
		
		BigDecimal dec = new BigDecimal("10000.24");
		
		System.out.println(dec.toString().replace(".", ""));
	}
}
