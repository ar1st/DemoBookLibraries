package com.aris.booklibraries.demoBookLibraries.config

import com.aris.booklibraries.demoBookLibraries.models.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
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

    //todo delete eraseCredentials
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.eraseCredentials(false).jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username,password,enabled "
                    + "from account "
                    + "where username = ?")
            .authoritiesByUsernameQuery("select u.username, r.authority " +
                    "from account u join authority r on " +
                    "r.account_id = u.account_id where u.username =?")
    }

    @Throws(Exception::class)
    override  fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole( Role.ADMIN.value )
            .antMatchers("/").permitAll()
            .antMatchers("/login*").permitAll()
            .antMatchers("/sign*").permitAll()
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
}