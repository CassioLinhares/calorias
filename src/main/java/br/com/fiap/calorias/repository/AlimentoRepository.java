package br.com.fiap.calorias.repository;

import br.com.fiap.calorias.dto.AlimentoExibicaoDTO;
import br.com.fiap.calorias.model.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

//    @Query("SELECT a FROM Alimento a WHERE a.nome= :nome")
//    public Optional<Alimento> searchByName(@Param("nome") String name); //nome da Query | procura por nome especifico

    @Query("SELECT a FROM Alimento a WHERE a.nome LIKE CONCAT(:nome, '%')")
    Page<Alimento> searchByAllName(@Param("nome") String name, Pageable pageable);

    @Query("SELECT a FROM Alimento a WHERE a.totalCalorias BETWEEN :minCalories AND :maxCalories ORDER BY a.totalCalorias DESC")
    Page<Alimento> searchByCalories(
            @Param("minCalories") Double minCalories,
            @Param("maxCalories") Double maxCalories,
            Pageable pageable
    );

    //consultas usando JPA
    @Query("SELECT a FROM Alimento a WHERE a.quantidadeGorduras < :amountFatMax ORDER BY a.quantidadeGorduras DESC")
    Page<Alimento> searchByAmountFatMax(Double amountFatMax, Pageable pageable);

    //  consulta usando DERIVED QUERY METHODS - consultas usando o spring (metodos padrao)
    Page<AlimentoExibicaoDTO> findByQuantidadeProteinaLessThan(Double quantidadeProteinaIsLessThan, Pageable pageable);
}
