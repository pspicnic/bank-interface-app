/**
 * 
 */
package za.co.pps.bank.mutual.data.acknowledgements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
@XmlRootElement(name="Issues")
public class Issue{

	public Issue(){}
	
	public Issue(String string){
		this.issue = string;
	}
	@XmlAttribute(name="type")
	private Long type=12121L;
	
	@XmlValue
	private String issue;
	
}