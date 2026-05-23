package br.com.fiap.calorias.repository;

import br.com.fiap.calorias.model.Alimento;
import br.com.fiap.calorias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

//HERDA UMA CLASSE COM VARIOS MÉTODOS PRONTOS - <classe em questão, tipo do dado do id>
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //SELECT * FROM TBL_USUARIO U WHERE U.email like '%@email%';
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE CONCAT('%', :email ,'%')")
    List<Usuario> searchBySpecificEmail(@Param("email") String email);

    UserDetails findByEmail(String email);
}
