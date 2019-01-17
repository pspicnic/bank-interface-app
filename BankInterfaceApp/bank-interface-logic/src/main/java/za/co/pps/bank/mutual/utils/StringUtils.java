/**
 * 
 */
package za.co.pps.bank.mutual.utils;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 31 Aug 2017
 * @version 1.0
 */
public class StringUtils {
	
	public static String padRight(String string, int numberOfCharacters){
		return String.format("%1$-" + numberOfCharacters + "s", string);
	}
	
	public static String padLeft(String string, int numberOfCharacters){
		return String.format("%1$" + numberOfCharacters + "s", string);
	}
	
	public static String padWithSpecifiedCharacterRight(String string, int numberOfCharacters, char padCharacter){
		return String.format("%1$-" + numberOfCharacters + "s", string).replace(' ', padCharacter);
	}
	
	public static String padWithSpecifiedCharacterLeft(String string, int numberOfCharacters, char padCharacter){
		return String.format("%1$" + numberOfCharacters + "s", string).replace(' ', padCharacter);
	}

}
