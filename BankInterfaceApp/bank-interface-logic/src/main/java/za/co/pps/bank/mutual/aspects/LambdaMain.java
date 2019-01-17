/**
 * 
 */
package za.co.pps.bank.mutual.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.hsqldb.lib.HashMap;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 18 May 2018
 * @version 1.0
 */
public class LambdaMain {
	
	
	private void testingMethod(int n, LambdaTest1 lambda){
		System.out.println( "Hello : " +  lambda.toString());
	}
	

	public static void main(String...args){
		
		/***
		String one = "Sonwabo", two = "Singatha";
		
		LambdaTest test = new LambdaTest() {
			@Override
			public void sayHello(String one, String two) {
					System.out.println( " This is my frist Anonymous class code ");
			}
		};
		
		int n1 = 3^3;
		System.out.println("====Power " + n1);
		
		
		test.sayHello(one,two);
		
		LambdaTest test1 = (one1,two1)-> System.out.println(String.format("This is my first Lambda code : {%s} {%s}",one1,two1));
		
		test1.sayHello(one,two);
		
		*/
		
		
		/**** Start of streams  ***/
		
		
		String[] arr = new String[]{"one","two","three"};
		Stream<String> stream = Arrays.stream(arr);
		
		
		
		@SuppressWarnings("serial")
		List<String> list = new ArrayList<String>(){{
			add("one"); 
			add("two");
			add("three");
			add("four");
		}};
		  
		Stream<String> stream2 = list.stream();	
		long count = list.stream().distinct().count();
		System.out.println(count);
		
		
		
		boolean isExists = list.stream().anyMatch(value -> {
			System.out.println("");
			if(value.contains("two")){
				//return true;
			}
			return false;}
		);
		
		System.out.println( isExists);
		
		  new LambdaMain().testingMethod(1, (one1,two1) -> { return  one1 + " " + two1;});		
		
		
		  
		  
		  
		  LambdaTest teste1 = () -> true;
		  
		  LambdaTest teste2 = () -> false;
		  
		  
		  boolean _v = teste.value() ?: teste2;
		  
		  
		  
		
		
		
		
		
		
		
	}
		
}
