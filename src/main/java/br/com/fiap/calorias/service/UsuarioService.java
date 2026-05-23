package br.com.fiap.calorias.service;

import br.com.fiap.calorias.Exception.UsuarioNaoEcontradoException;
import br.com.fiap.calorias.dto.UsuarioCadastroDTO;
import br.com.fiap.calorias.dto.UsuarioExibicaoDTO;
import br.com.fiap.calorias.model.Usuario;
import br.com.fiap.calorias.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDTO Save(UsuarioCadastroDTO usuarioCadastroDTO){
        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioCadastroDTO.senha());
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDTO, usuario);
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioExibicaoDTO(usuarioSalvo);
    }

// Optional<tipoDeDadoRetorno - Generico> - pode ter algo presente ou não
    public UsuarioExibicaoDTO SearchById(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()){
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEcontradoException("Usuário não existe no banco de dados!");
        }
    }

    public List<UsuarioExibicaoDTO> AllSearch(){
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioExibicaoDTO::new)
                .toList();
        //strem - itera sobre a lista que vem de retorno
        //map - cria uma lista do tipo UsuarioExibicaoDTO
        //toList - armazena os dados do map
    }

    public void Delete(Long id){
        Optional<Usuario> usuarioRemove = usuarioRepository.findById(id);
        if (usuarioRemove.isPresent()){
            usuarioRepository.delete(usuarioRemove.get());
        } else {
            throw new UsuarioNaoEcontradoException("Usuário não existe no banco de dados!");
        }
    }

    public Usuario Update(Usuario usuario){
        Optional<Usuario> usuarioUpdate = usuarioRepository.findById(usuario.getUsuarioId());
        if (usuarioUpdate.isPresent()){
            return usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNaoEcontradoException("Usuário não existe no banco de dados!");
        }
    }

    public List<UsuarioExibicaoDTO> buscaPorEmail(String email){
        return usuarioRepository.searchBySpecificEmail(email)
                .stream()
                .map(UsuarioExibicaoDTO::new)
                .toList();
    }
}
