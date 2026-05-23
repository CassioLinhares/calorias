package br.com.fiap.calorias.service;

import br.com.fiap.calorias.dto.AlimentoCadastroDTO;
import br.com.fiap.calorias.dto.AlimentoExibicaoDTO;
import br.com.fiap.calorias.model.Alimento;
import br.com.fiap.calorias.repository.AlimentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    public AlimentoExibicaoDTO salvarAlimento(AlimentoCadastroDTO alimentoDTO){
        Alimento alimento = new Alimento();
        BeanUtils.copyProperties(alimentoDTO, alimento);
        alimento.setTotalCalorias(
                calcularCalorias(
                        alimento.getQuantidadeProteina(),
                        alimento.getQuantidadeCarboidrato(),
                        alimento.getQuantidadeGorduras()
                )
        );
        Alimento alimentoSalvo = alimentoRepository.save(alimento);
        return new AlimentoExibicaoDTO(alimentoSalvo);
    }

    public AlimentoExibicaoDTO buscarPorId(Long id){
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(id);
        if (alimentoOptional.isPresent()){
            return new AlimentoExibicaoDTO(alimentoOptional.get());
        } else {
            throw new RuntimeException("Alimento não existe!");
        }
    }
//retornar um objeto com uma lista com os dados mais informação sobre paginas
    public Page<AlimentoExibicaoDTO> listarTodos(Pageable paginacao){
        return alimentoRepository.findAll(paginacao).map(AlimentoExibicaoDTO::new);
    }

    public void excluir(Long id){
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(id);
        if (alimentoOptional.isPresent()){
            alimentoRepository.delete(alimentoOptional.get());
        } else {
            throw new RuntimeException("Alimento não encontrado!");
        }
    }

    public AlimentoExibicaoDTO atualizar(AlimentoCadastroDTO alimentoDTO){
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(alimentoDTO.alimentoId());
        if (alimentoOptional.isPresent()){
            Alimento alimento = new Alimento();
            BeanUtils.copyProperties(alimentoDTO, alimento);
            alimento.setTotalCalorias(
                    calcularCalorias(
                            alimento.getQuantidadeProteina(),
                            alimento.getQuantidadeCarboidrato(),
                            alimento.getQuantidadeGorduras()
                    )
            );
            return new AlimentoExibicaoDTO(alimentoRepository.save(alimento));
        } else {
            throw new RuntimeException("Alimento não encontrado!");
        }
    }
//Busca por nome especifico no banco | gera erro se houver +1 nome igual
//    public AlimentoExibicaoDTO buscaPorNome(String nome){
//        Optional<Alimento> alimentoOptional = alimentoRepository.searchByName(nome);
//        if (alimentoOptional.isPresent()){
//            return new AlimentoExibicaoDTO(alimentoOptional.get());
//        } else {
//            throw new AlimentoNaoEncontradoException("Alimento não existente na base de dados!");
//        }
//    }

    public Page<AlimentoExibicaoDTO> buscaPorTodosNome(String nome, Pageable pageable){
        return alimentoRepository.searchByAllName(nome, pageable)
                .map(AlimentoExibicaoDTO::new);
    }

    public Page<AlimentoExibicaoDTO> buscaPorCalorias(Double min, Double max, Pageable pageable){
        return alimentoRepository.searchByCalories(min,max, pageable)
                .map(AlimentoExibicaoDTO::new);
    }

    public Page<AlimentoExibicaoDTO> buscaPorQtdeGorduraMax(Double qtdeGorduraMax, Pageable pageable){
        return alimentoRepository.searchByAmountFatMax(qtdeGorduraMax, pageable)
                .map(AlimentoExibicaoDTO::new);
    }

    public Page<AlimentoExibicaoDTO> buscaPorQtdeProteinaMax(Double qtdeProteina, Pageable pageable){
        return alimentoRepository.findByQuantidadeProteinaLessThan(qtdeProteina, pageable);
    }

    private Double calcularCalorias(Double proteinas, Double carboidratos, Double gorduras){
        Double calorias = (proteinas * 4) + (carboidratos * 4) + (gorduras * 9);
        return calorias;
    }

}