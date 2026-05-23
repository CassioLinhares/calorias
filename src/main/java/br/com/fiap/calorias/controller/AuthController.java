package br.com.fiap.calorias.controller;

import br.com.fiap.calorias.config.security.TokenService;
import br.com.fiap.calorias.dto.LoginDTO;
import br.com.fiap.calorias.dto.TokenDTO;
import br.com.fiap.calorias.dto.UsuarioCadastroDTO;
import br.com.fiap.calorias.dto.UsuarioExibicaoDTO;
import br.com.fiap.calorias.model.Usuario;
import br.com.fiap.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@RequiredArgsConstructor //injeção de dependencias do Lombok envés do @Autowired | melhor pra tests
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO usuarioLoginDTO){
        // verificando se o email e a senha estão ok
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email(), usuarioLoginDTO.senha());
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // gerando o token
        String token = tokenService.tokenCreated( (Usuario) auth.getPrincipal() );//casting -> transf p/ obj do tipo Usuario
        return ResponseEntity.ok(new TokenDTO(token));//return em json
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO){
        UsuarioExibicaoDTO usuarioSalvo = usuarioService.Save(usuarioCadastroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }
}
