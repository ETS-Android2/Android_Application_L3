package fr.info.pl2020.plplg.config;

import fr.info.pl2020.plplg.filter.AuthenticationFilter;
import fr.info.pl2020.plplg.filter.ExceptionHandlerFilter;
import fr.info.pl2020.plplg.security.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    private TokenStore tokenStore;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/swagger-ui.html", "/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationFailureEntryPoint())
                //.and().logout().permitAll().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));;
                /*.and().logout().permitAll().addLogoutHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    String auth = httpServletRequest.getHeader("Authorization");System.out.println("OKKKKKKKKKKKKKKKKKK");
                    if (auth != null) {
                        OAuth2AccessToken accessToken = tokenStore.readAccessToken(auth.replace("Bearer", "").trim());
                        tokenStore.removeAccessToken(accessToken);
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                        try {
                            httpServletResponse.getWriter().write("OK");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("OK1");
                    } else {
                        System.out.println("OK2");
                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }).logoutSuccessUrl("/logoutSuccess")*/;
        http.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/h2-console/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
