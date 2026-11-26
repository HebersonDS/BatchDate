package com.example.controleValidade.controller;

import com.example.controleValidade.dto.LoteAlerta;
import com.example.controleValidade.model.Lote;
import com.example.controleValidade.service.LoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotes")
public class LoteController {
    private final LoteService loteService;

    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @PostMapping("/{idProduto}")
    public Lote cadastrarLote(@RequestBody Lote lote,
                              @PathVariable Long idProduto){
        return loteService.cadastrarLote(lote, idProduto);
    }

    @GetMapping
    public List<Lote> listarLote(){
        return loteService.listarLotes();
    }

    @GetMapping("/vencidos")
    public List<Lote> listarLotesVencidos(){
        return loteService.listarLotesVencidos();
    }

    @GetMapping("/proximosvenc")
    public List<Lote> listarLotesProximosDeVencer(){
        return loteService.listarLotesProximosDaValidade();
    }

    @GetMapping("/proximos-validade/{idProduto}")
    public List<Lote> listarLotesPorProdutoProximosDeVencer(@PathVariable Long idProduto){
        return loteService.listarLotesProximosDaValidadePorProduto(idProduto);
    }

    @GetMapping("/alertas-validade")
    public List<LoteAlerta> listarAlertas(){
        return loteService.listarAlertasDeValidade();
    }

    @PutMapping("/{idLote}/entrada")
    public Lote adcionarQuantidade(
            @PathVariable Long idLote,
            @RequestBody Integer quatidadeRecebida){
        return  loteService.adicionarQuantidade(idLote,quatidadeRecebida);
    }

    @DeleteMapping("/{id}")
    public void excluirLote(@PathVariable Long id){
        loteService.excluirLote(id);
    }
}
