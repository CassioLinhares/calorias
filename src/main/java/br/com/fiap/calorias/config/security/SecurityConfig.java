package br.com.fiap.calorias.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // intercepta as requisições http e processa-las de acordos com as config definidas no filto
public class SecurityConfig {

    @Autowired
    private VerifyToken verifyToken;

    @Bean //pedindo para o spring criar e gerenciar este obj - instanciação
    public SecurityFilterChain filtrarCadeiaDeSecuranca(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/alimentos").hasRole("ADMIN")//ADMIN p/ acessar esta request
                        .requestMatchers(HttpMethod.DELETE, "/api/alimentos/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/alimentos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/alimentos/*").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/alimentos").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()// pra qualquer outra requisição em /alimentos basta estar autenticado
                )
                .addFilterBefore(
                        verifyToken,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean // é necessario para poder usar authenticationManager na classe AuthController
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

//SecurityFilterChain = responsavel por config a segurança das requisições http no spring |
//                      intercepta as requisições antes de chegar aos controllers da aplicação
//HttpSecurity = config as regras de segurança http como:
//              (autorização, autenticação, proteção contra ataques - CSRF E XSS, logout)

// foi desabilitado a proteção contra ataques do tipo CSRF - não é mais necessária.
//      pois é uma proteção contra cookies de terceiros (STATEFUL).
//habilitamos a autenticação STATELESS - token

// sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)),
//      diz ao Spring para não criar nem usar sessões HTTP, -> cada requisição deve ter sua própria autenticação.

//(tirou o padrão do spring que é autenticação STATEFUL)

// passwordEncoder - metodo responsável por criar a criptografia da senha (+ de qualquer coisa)