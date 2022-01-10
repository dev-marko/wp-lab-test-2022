package mk.ukim.finki.wp.lab2022.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is used to configure user login on path '/login' and logout on path '/logout'.
 * The only public page in the application should be '/'.
 * All other pages should be visible only for a user with role 'ROLE_ADMIN'.
 * Furthermore, in the "list.html" template, the 'Edit', 'Delete', 'Add' buttons should only be
 * visible for a user with role 'ROLE_ADMIN'.
 * The 'Like' button should only be visible for a user with role 'ROLE_USER'.
 * <p>
 * For login inMemory users should be used. Their credentials are given below:
 * [{
 * username: "user",
 * password: "user",
 * role: "ROLE_USER"
 * },
 * <p>
 * {
 * username: "admin",
 * password: "admin",
 * role: "ROLE_ADMIN"
 * }]
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO: If you are implementing the security requirements, remove this following line
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/news").hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/news", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String encodedPasswordAdmin = this.passwordEncoder.encode("admin");
        String encodedPasswordUser = this.passwordEncoder.encode("user");
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(encodedPasswordAdmin)
                .authorities("ROLE_ADMIN")
                .and()
                .withUser("user")
                .password(encodedPasswordUser)
                .authorities("ROLE_USER");
    }
}
