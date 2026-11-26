package com.example.controleValidade.controller;

import com.example.controleValidade.dto.ProdutoExportacao;
import com.example.controleValidade.model.Lote;
import com.example.controleValidade.model.Produto;
import com.example.controleValidade.service.ProdutoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto cadastrarProduto(@RequestBody Produto produto){
        return  produtoService.cadastrarProduto(produto);
    }

    @GetMapping
    public List<Produto> listarProdutos(){
        return produtoService.listarProdutos();
    }

    @GetMapping("/{idProduto}/lotes")
    public List<Lote> listaLotesdoProduto(@PathVariable Long idProduto){
        return produtoService.listarLotesDoProduto(idProduto);
    }

    @PutMapping("/{id}")
    public Produto editarProduto(@PathVariable Long id,
                                 @RequestBody Produto produto){
        return produtoService.editarProduto(id,produto);
    }

    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable Long id){
        produtoService.excluirProduto(id);
    }

    @GetMapping("/{id}/exportar")
    public ResponseEntity<ProdutoExportacao> exportarProduto(@PathVariable Long id){
        ProdutoExportacao dados = produtoService.produtoExportacao(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"produto-" + id + ".json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dados);
    }

}
