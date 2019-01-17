/**
 * 
 */
package za.co.pps.bank.mutual.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 23 Oct 2017
 * @version 1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	 	@ExceptionHandler(MultipartException.class)
	    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
	        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
	        return "redirect:/bank-interface/uploadStatus";
	    }

	 	/*
		@ExceptionHandler(MultipartException.class)
	    public String handleError2(MultipartException e) {
	        return "redirect:/errorPage";

	    }
	    */

}
