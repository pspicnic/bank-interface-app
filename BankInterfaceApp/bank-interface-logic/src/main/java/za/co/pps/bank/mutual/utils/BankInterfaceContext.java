/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a global object for passing data in the sysytem
 * 
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 03 Apr 2018
 * @version 1.0
 */
public class BankInterfaceContext implements Serializable{

    private static final long serialVersionUID = 1L;
    private Map<String,Object> context = new HashMap<String,Object>();
    
	public BankInterfaceContext() {
		// TODO Auto-generated constructor stub
	}
	
	public void setAttribute(String key, Object value) {
		context.put(key, value);
    }

    public void removeAttribute(String key) {
    	context.remove(key);
    }

    public Object getAttribute(String key) {
        return context.get(key);
    }

    public boolean containsKey(String key) {
        return context.containsKey(key);
    }

    public Set<String> getKeys() {
        return context.keySet();
    }
	
}
