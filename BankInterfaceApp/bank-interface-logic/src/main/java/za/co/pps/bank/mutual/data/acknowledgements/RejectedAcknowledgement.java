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
public class RejectedAcknowledgement extends AbstractAcknowledgement{
	
	public RejectedAcknowledgement(){}
	public RejectedAcknowledgement(Object...args){
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
	private String type=Type.REJECTED.getType();
	
	@XmlElement(name="Issues")
	private Issues issues;

	public void setIssues(Issues issues){
		this.issues = issues;
	}
	
	@Override
	public String toString() {
		return "RejectedAcknowledgement [type=" + type + ", issues=" + issues + ", getPaymentId()=" + getPaymentId()
				+ ", getOriginalMessageId()=" + getOriginalMessageId() + ", getDatetime()=" + getDatetime()
				+ ", getCustomerId()=" + getCustomerId() + ", getCompanyName()=" + getCompanyName()
				+ ", getUserMessage()=" + getUserMessage() + ", getDetailedMessage()=" + getDetailedMessage()
				+ ", getOriginalFileName()=" + getOriginalFileName() + ", getOriginalReference()="
				+ getOriginalReference() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
