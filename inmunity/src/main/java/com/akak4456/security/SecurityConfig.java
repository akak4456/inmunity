package com.akak4456.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.akak4456.domain.member.Role;
import com.akak4456.service.member.CustomOAuth2UserService;
import com.akak4456.service.member.MemberService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true, 
		securedEnabled = true,
		jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		//http.cors().and().csrf().disable();
		http
			.authorizeRequests()
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.successHandler(loginSuccessHandler)
			.permitAll()
		.and()
			.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
		.and()
			.rememberMe()
			.key("akak4456")
			.userDetailsService(memberService)
			.tokenRepository(getJDBCRepository())
			.tokenValiditySeconds(60*60*24)
		.and()
            .oauth2Login()
            .loginPage("/login")
            .userInfoEndpoint()
            .userService(customOAuth2UserService);
    }
	
	private PersistentTokenRepository getJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
}
