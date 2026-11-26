package com.example.controleValidade.service;

import com.example.controleValidade.dto.LoteAlerta;
import com.example.controleValidade.model.Produto;
import com.example.controleValidade.repository.LoteRepository;
import com.example.controleValidade.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import com.example.controleValidade.model.Lote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoteService {
    private final LoteRepository loteRepository;
    private final ProdutoRepository produtoRepository;

    public LoteService(LoteRepository loteRepository, ProdutoRepository produtoRepository) {
        this.loteRepository = loteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Lote cadastrarLote(Lote lote, Long idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não cadastrado"));
        if(lote.getQuantidade() == null || lote.getQuantidade() <= 0){
            throw new IllegalArgumentException("A quantidade de produtos precisa ser maior que zero");
        }
        if(lote.getCodigoLote() == null || lote.getCodigoLote().isBlank()){
            throw new IllegalArgumentException("O código de lote é obrigatório");
        }
        if(lote.getDataValidade() == null){
            throw new IllegalArgumentException("A data de validade é obrigatória");
        }

        lote.setProduto(produto);

        return loteRepository.save(lote);
    }

    public List<Lote> listarLotes(){
        return loteRepository.findAll();
    }

    public List<Lote> listarLotesVencidos(){
        LocalDate hoje = LocalDate.now();

        return loteRepository.findByDataValidadeBefore(hoje);
    }

    public List<Lote> listarLotesProximosDaValidade(){
        LocalDate hoje = LocalDate.now();
        LocalDate vencendo = hoje.plusDays(7);

        return loteRepository.findByDataValidadeBetween(hoje,vencendo);
    }

    public List<Lote> listarLotesProximosDaValidadePorProduto(Long idProduto){
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(7);


        return loteRepository.findByProduto_IdAndDataValidadeBetween(
                idProduto,hoje,limite
        );
    }

    public List<LoteAlerta> listarAlertasDeValidade(){
        List<LoteAlerta> loteAlertas = new ArrayList<>();
        for(Lote lote : listarLotesProximosDaValidade()){
            Produto p = lote.getProduto();
            LoteAlerta alerta = new LoteAlerta(p,lote);
            loteAlertas.add(alerta);
        }

        return loteAlertas;
    }

    public Lote adicionarQuantidade(Long idLote, Integer quantidadeRecebida) {

        Lote lote = loteRepository.findById(idLote)
                .orElseThrow(() ->
                        new IllegalArgumentException("Lote não encontrado"));

        if (quantidadeRecebida == null || quantidadeRecebida <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade recebida deve ser maior que zero"
            );
        }

        if(lote.getQuantidade() == null || lote.getQuantidade() < 0){
            throw new IllegalArgumentException(
                    "A quantidade atual do lote é invalida"
            );
        }

        lote.setQuantidade(lote.getQuantidade() + quantidadeRecebida);

        return loteRepository.save(lote);
    }

    public void excluirLote(Long id){
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lote não encontrado"));

        loteRepository.delete(lote);
    }
}
