package com.aris.booklibraries.demoBookLibraries.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder





@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var dataSource: DataSource

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select email,password,enabled "
                    + "from new_user "
                    + "where email = ?")
            .authoritiesByUsernameQuery("select email,authority "
                    + "from authority "
                    + "where email = ?")
    }


    @Throws(Exception::class)
    override  fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/h2-console/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin();

        http.csrf()
            .ignoringAntMatchers("/h2-console/**");
        http.headers()
            .frameOptions()
            .sameOrigin();
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }


}