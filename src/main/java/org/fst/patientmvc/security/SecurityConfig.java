package org.fst.patientmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
   private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        JdbcUserDetailsManagerConfigurer jdbcUserDetailsManagerConfigurer = new JdbcUserDetailsManagerConfigurer();

        // Configurer les requêtes SQL
        jdbcUserDetailsManagerConfigurer
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username = ?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_role where username = ?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);

        return jdbcUserDetailsManagerConfigurer.getUserDetailsService();
    }
//    @Bean
//    public UserDetailsManager userDetailsService(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder passwordEncoder = passwordEncoder() ;
////        String encodedPWD  = passwordEncoder.encode("1234") ;
////        System.out.println(encodedPWD);
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("user1").password(encodedPWD).roles("USER").build()); // no password encoder pour ne pas coder le mot de passe
////        manager.createUser(User.withUsername("user2").password(passwordEncoder.encode("1111")).roles("USER").build());
////        manager.createUser(User.withUsername("admin").password(passwordEncoder.encode("2345")).roles("USER", "ADMIN").build());
////return manager;
//
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/").permitAll()).formLogin(withDefaults());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/user/**").hasRole("USER")).formLogin(withDefaults());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/admin/**" , "/user/**").hasRole("ADMIN")).formLogin(withDefaults());
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/**").authenticated()).formLogin(withDefaults());
        http.exceptionHandling((exceptionHandling)-> exceptionHandling.accessDeniedPage("/403") ) ;
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder() ;
    }
}
