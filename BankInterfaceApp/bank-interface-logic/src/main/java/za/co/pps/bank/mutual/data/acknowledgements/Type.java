/**
 * 
 */
package za.co.pps.bank.mutual.data.acknowledgements;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
public enum Type {

	INFO("info"),
	WARNING("warning"),
	REJECTED("rejected");
	
	Type(String type){
		this.type = type;
	}
	
	private String type;
	public String getType(){
		return this.type;
	}
}
