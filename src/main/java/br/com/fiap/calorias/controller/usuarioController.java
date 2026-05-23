package br.com.fiap.calorias.controller;

import br.com.fiap.calorias.dto.UsuarioCadastroDTO;
import br.com.fiap.calorias.dto.UsuarioExibicaoDTO;
import br.com.fiap.calorias.model.Usuario;
import br.com.fiap.calorias.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class usuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoDTO save(@Valid @RequestBody UsuarioCadastroDTO usuario){
        return usuarioService.Save(usuario);
    }

    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioExibicaoDTO> allSearch(){
        return usuarioService.AllSearch();
    }

    @GetMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioExibicaoDTO> searchById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.SearchById(id));
    }
//        try {
//            return ResponseEntity.ok(usuarioService.SearchById(id));
//        }catch (Exception e){
//            return ResponseEntity.notFound().build();
//        }
//ResponseEntity é uma classe <T> do spring que lança um erro 404 not found.
// caso não seja tratado pelo service.

    @GetMapping(value = "/usuarios", params = "email")
    public List<UsuarioExibicaoDTO> buscaPorEmail(@RequestParam String email){
        return usuarioService.buscaPorEmail(email);
    }

    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        usuarioService.Delete(id);
    }

    @PutMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public Usuario updateUser(@Valid @RequestBody Usuario usuario){
        return usuarioService.Update(usuario);
    }

}
