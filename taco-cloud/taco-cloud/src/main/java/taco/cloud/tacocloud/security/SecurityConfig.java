package taco.cloud.tacocloud.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import taco.cloud.tacocloud.data.UserRepository;
import taco.cloud.tacocloud.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
    
    // //DEFINING USERS IN AN IN-MEMORY USER STORE
    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //         .username("buzz")
    //         .password("infinity")
    //         .roles("USER")
    //         .build();
    //     return new InMemoryUserDetailsManager(user);    
    // }

    // //AUTHENTICATING AGAINST A JDBC-BASED USER STORE
    // @Bean
    // public DataSource dataSource() {
    //     return new EmbeddedDatabaseBuilder()
    //         .setType(EmbeddedDatabaseType.H2)
    //         .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
    //         .build();
    // }

    // @Bean
    // public UserDetailsManager users(DataSource dataSource) {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //         .username("user")
    //         .password("password")
    //         .roles("USER")
    //         .build();
    //     JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    //     users.createUser(user);
    //     return users;    
    // }

    //LDAP-BACKED USER STORE
    // @Bean
    // public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
    //     EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean = 
    //         EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
    //     contextSourceFactoryBean.setPort(0);
    //     return contextSourceFactoryBean;
    // }

    // @Bean
    // AuthenticationManager ldapAuthenticationManager(
    //         BaseLdapPathContextSource contextSource) {
    //     LdapBindAuthenticationManagerFactory factory = 
    //         new LdapBindAuthenticationManagerFactory(contextSource);
    //     factory.setUserDnPatterns("uid={0},ou=people");
    //     factory.setUserDetailsContextMapper(new PersonContextMapper());
    //     return factory.createAuthenticationManager();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/design", "/orders").hasRole("USER")
                .requestMatchers("/", "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/ingredients").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/ingredients").hasRole("ADMIN")               
            ) 
            .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/design"))        
            .headers(headers -> headers.frameOptions().disable())
            .csrf().disable();
            // .csrf(csrf -> csrf
            //         .ignoringRequestMatchers(PathRequest.toH2Console()));
        return http.build();
        // http.cors()
        //     .and()
        //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //     .and().formLogin().loginPage("/login")
        //     .and().logout().logoutSuccessUrl("/")
        //     .and()
        //     .securityMatcher("/**")
        //     .authorizeHttpRequests(authorizeRequest -> authorizeRequest
        //         .requestMatchers(HttpMethod.GET, "/**").permitAll()
        //         .requestMatchers(HttpMethod.GET, "/design", "/orders").hasAnyAuthority("SCOPE_tmt:user")
        //         .requestMatchers(HttpMethod.POST, "/design", "/orders").hasAnyAuthority("SCOPE_tmt:user")
            
        //         );
        // http.csrf().ignoringRequestMatchers("/h2-console/**");
        // http.headers().frameOptions().sameOrigin();
        
        // return http.build();
    }
}
