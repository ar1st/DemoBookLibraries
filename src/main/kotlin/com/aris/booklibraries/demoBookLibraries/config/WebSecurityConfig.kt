package com.aris.booklibraries.demoBookLibraries.config

import com.aris.booklibraries.demoBookLibraries.models.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var dataSource: DataSource

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select email as username,password,enabled "
                    + "from account "
                    + "where email = ? ")
            .authoritiesByUsernameQuery("select email as username, authority " +
                    "from account " +
                    "where email =? ")
    }

    @Throws(Exception::class)
    override  fun configure(http: HttpSecurity) {
        //thymeleaf
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/login*").anonymous()
            .antMatchers("/sign**").anonymous()
            .antMatchers("/signup/confirm**").anonymous()
            .antMatchers("/books/*/details").hasAnyAuthority("LIBRARIAN","USER")
            .antMatchers("/books/**").hasAuthority("LIBRARIAN")
            .antMatchers("/authors/**").hasAuthority("LIBRARIAN")
            .antMatchers("/loggedUser/**").hasAuthority("USER")
            .antMatchers("/libraries/**").hasAuthority("USER")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/main.html", true)
            .failureUrl("/login.html?error=true")
            .and()
            .logout()
            .logoutUrl("/perform_logout")
            .deleteCookies("JSESSIONID")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/icon/**")
    }
}