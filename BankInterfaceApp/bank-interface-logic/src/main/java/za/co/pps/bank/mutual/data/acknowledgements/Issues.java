/**
 * 
 */
package za.co.pps.bank.mutual.data.acknowledgements;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
@XmlRootElement(name="Issues")
@XmlAccessorType (XmlAccessType.FIELD)
public class Issues {
	
	public Issues(){}
	
	@XmlElement(name="Issue")
	private List<Issue> issue;
	
	public  List<Issue> getIssuesList(){
		if(issue==null){
			issue = new ArrayList<Issue>();
		}
		return issue;
	}

}
