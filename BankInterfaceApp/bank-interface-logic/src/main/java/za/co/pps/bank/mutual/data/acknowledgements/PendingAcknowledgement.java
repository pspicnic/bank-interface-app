/**
 * 
 */
package za.co.pps.bank.mutual.data.acknowledgements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
@XmlRootElement(name="PaymentsAcknowledgement")
public class PendingAcknowledgement extends AbstractAcknowledgement{
	
	public PendingAcknowledgement(){}
	
	public PendingAcknowledgement(Object...args){
		setPaymentId((Long)args[0]);
		setOriginalMessageId((Long)args[1]);
		setDatetime((String)args[2]);
		setCustomerId((String)args[3]);
		setCompanyName((String)args[4] );
		setUserMessage((String)args[5]);
		setDetailedMessage((String)args[6]);
		setOriginalFileName((String)args[7]);
		setOriginalReference((String)args[8]);
	}
	
	@XmlAttribute(name="type")
	private String type= Type.WARNING.getType();
	
	@XmlElement
	private Issues issues;

	public void setIssues(Issues issues){
		this.issues = issues;
	}
	
	
}