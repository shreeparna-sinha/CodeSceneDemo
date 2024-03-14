
package com.psl.assessment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.psl.assessment.exception.CustomSecurityException;
import com.psl.assessment.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

/**
* Configuration class for Spring Security settings.
* 
*/
public class SecurityConfig  {
    @Autowired
    private JwtAuthFilter authFilter;
    

    @Autowired
    CustomSecurityException customexception;
    

    /**
    * Public URLs that are accessible without authentication.
    */
    public static final String[] PUBLIC_URLS = {"/swagger-ui/**", "/v3/api-docs", "/api/v1/auth/**", "/v2/api-docs",
         "/swagger-resources/**", "/webjars/**", "/api/v1/app/user/auth", "/v3/api-docs/*"};

    /**
    * Configures the UserDetailsService for authentication.
    *
    * @return UserDetailsService bean
    */
    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
    	//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        
       return new UserInfoUserDetailsService();
    }
    
    /**
    * Configures the SecurityFilterChain for HTTP security.
    *
    * @param http HttpSecurity instance
    * @return SecurityFilterChain bean
    * @throws Exception Exception during configuration
    */
    @SuppressWarnings("removal")
	@Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth ->
                auth.requestMatchers("/quizmaster/authenticate").permitAll()
                .requestMatchers(PUBLIC_URLS).permitAll()

                        .requestMatchers("/quizmaster/**").authenticated()//hasAnyRole(Role.ADMIN.toString(),Role.TRAINER.toString(),Role.STUDENT.toString())
        )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customexception)
                							.accessDeniedHandler(customexception)
                		)
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();

    }
    
    

    /**
    * Configures the PasswordEncoder bean.
    *
    * @return PasswordEncoder bean
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    /**
    * Configures the AuthenticationProvider bean.
    *
    * @return AuthenticationProvider bean
    */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    /**
    * Configures the AuthenticationManager bean.
    *
    * @param config AuthenticationConfiguration instance
    * @return AuthenticationManager bean
    * @throws Exception Exception during configuration
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
