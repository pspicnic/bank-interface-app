/**
 *
 */
package za.co.pps.bank.mutual.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @version 1.0
 * @date 23 Oct 2017
 */
@Configuration
public class BankInterfaceSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomSuccessHandler customSuccessHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				//  .antMatchers("/upload/**").hasAnyRole("ADMIN")
				//  .antMatchers("/upload/**").hasAnyRole("USER")
				.antMatchers("/viewFile/**").hasAnyRole("VIEWER")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.successHandler(customSuccessHandler)
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password("207248134").roles("USER")
				.and()
				.withUser("admin").password("password").roles("ADMIN")
				.and()
				.withUser("admin1").password("password1").roles("VIEWER");
	}


}