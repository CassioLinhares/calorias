package br.com.fiap.calorias.controller;

import br.com.fiap.calorias.dto.AlimentoCadastroDTO;
import br.com.fiap.calorias.dto.AlimentoExibicaoDTO;
import br.com.fiap.calorias.service.AlimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @PostMapping("/alimentos")
    @ResponseStatus(HttpStatus.CREATED)
    public AlimentoExibicaoDTO salvar(@Valid @RequestBody AlimentoCadastroDTO alimento){
        return alimentoService.salvarAlimento(alimento);
    }

    @GetMapping("/alimentos")
    @ResponseStatus(HttpStatus.OK)             //size = define quantos alimentos voltaram | page = informa qual pagina esta.
    public Page<AlimentoExibicaoDTO> litarTodos(
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao)
    {
        return alimentoService.listarTodos(paginacao);
    }

    @GetMapping("/alimentos/{alimentoId}")
    public ResponseEntity<AlimentoExibicaoDTO> buscarPorId(@PathVariable Long alimentoId){
        try {
            return ResponseEntity.ok(alimentoService.buscarPorId(alimentoId));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    //procura por um alimento especifico
//    @GetMapping(value = "/alimentos", params = "nome")
//    public AlimentoExibicaoDTO buscarPorNome(@RequestParam String nome){
//        return alimentoService.buscaPorNome(nome);
//    }

    //Procura por um nome em comum na lista de alimentos retorna um
    @GetMapping(value = "/alimentos", params = "nome")
    public Page<AlimentoExibicaoDTO> buscarPorTodosNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.DESC) Pageable pageable
            // quantos elementos por pagina |  nome do campo da class java a ser ordenado | forma de ordenação
            // não é a forma mais segura pois assim a URL não esta "travada". forma mais correta é fazer no service ou na propia query jpql.
    ){
        return alimentoService.buscaPorTodosNome(nome, pageable);
    }

    @GetMapping(value = "/alimentos", params = {"min", "max"})
    public Page<AlimentoExibicaoDTO> buscaPorCalorias(@RequestParam Double min, @RequestParam Double max, Pageable pageable){
        return alimentoService.buscaPorCalorias(min, max, pageable);
    }

    @GetMapping(value = "/alimentos", params = "qtdeGorduraMax")
    public Page<AlimentoExibicaoDTO> buscaPorQtdeGorduraMax(@RequestParam Double qtdeGorduraMax, Pageable pageable){
        return alimentoService.buscaPorQtdeGorduraMax(qtdeGorduraMax, pageable);
    }

    @GetMapping(value = "/alimentos", params = "qtdeProteina")
    public Page<AlimentoExibicaoDTO> buscaPorQtdeProteinaMax(@RequestParam Double qtdeProteina, Pageable pageable){
        return alimentoService.buscaPorQtdeProteinaMax(qtdeProteina, pageable);
    }

    @DeleteMapping("/alimentos/{alimentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long alimentoId){
        alimentoService.excluir(alimentoId);
    }

    @PutMapping("/alimentos")
    public ResponseEntity<AlimentoExibicaoDTO> atualizar(@RequestBody AlimentoCadastroDTO alimentoDTO){
        try {
            AlimentoExibicaoDTO alimentoExibicaoDTO = alimentoService.atualizar(alimentoDTO);
            return ResponseEntity.ok(alimentoExibicaoDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}