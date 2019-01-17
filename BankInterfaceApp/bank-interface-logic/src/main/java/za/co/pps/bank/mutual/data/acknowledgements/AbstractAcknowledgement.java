/**
 * 
 */
package za.co.pps.bank.mutual.data.acknowledgements;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 06 Sep 2017
 * @version 1.0
 */
public class AbstractAcknowledgement {
	
	private Long paymentId;
	private Long originalMessageId;
	private String datetime;
	private String customerId;
	private String companyName;
	private String userMessage;
	private String detailedMessage;
	private String originalFileName;
	private String originalReference;
	
	@XmlElement(name="PaymentId")
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	@XmlElement(name="OriginalMessageId")
	public Long getOriginalMessageId() {
		return originalMessageId;
	}
	public void setOriginalMessageId(Long originalMessageId) {
		this.originalMessageId = originalMessageId;
	}
	@XmlElement(name="DateTime")
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	@XmlElement(name="CustomerId")
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@XmlElement(name="CompanyName")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@XmlElement(name="UserMessage")
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	@XmlElement(name="DetailedMessage")
	public String getDetailedMessage() {
		return detailedMessage;
	}
	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}
	@XmlElement(name="OriginalFilename")
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	@XmlElement(name="OriginalReference")
	public String getOriginalReference() {
		return originalReference;
	}
	public void setOriginalReference(String originalReference) {
		this.originalReference = originalReference;
	}
	
	private  void createXml(Class<?> clazz, Object instance , File file) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(clazz);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(instance, file);
	}
	
	private  AbstractAcknowledgement getObject(Class<? extends AbstractAcknowledgement> clazz, File xml){
		return JAXB.unmarshal(xml, clazz);
	}
	

}
