package com.example.controleValidade.service;

import com.example.controleValidade.dto.ProdutoExportacao;
import com.example.controleValidade.model.Lote;
import com.example.controleValidade.model.Produto;
import com.example.controleValidade.repository.LoteRepository;
import com.example.controleValidade.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final LoteRepository loteRepository;


    public ProdutoService(ProdutoRepository produtoRepository, LoteRepository loteRepository) {
        this.produtoRepository = produtoRepository;
        this.loteRepository = loteRepository;
    }

    public Produto cadastrarProduto(Produto produto){
        if (produto.getNome() == null || produto.getNome().isBlank()){
            throw new IllegalArgumentException("O nome do produto é obrigatório");
        }

        if (produto.getCategoria() == null || produto.getCategoria().isBlank()){
            throw new IllegalArgumentException("A categoria do produto é obrigatória");
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProdutoExportacao produtoExportacao(Long id){
        Produto produto  = produtoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Produto não encontrado"));
        return new ProdutoExportacao(produto);
    }

    public List<Lote> listarLotesDoProduto(Long idProduto){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(()-> new IllegalArgumentException("Produto não encontrado"));
        return produto.getLotes();
    }

    public Produto editarProduto(Long id, Produto dadosproduto){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()->
                        new IllegalArgumentException("Produto não encontrado"));

        if(dadosproduto.getNome() == null||
            dadosproduto.getNome().isBlank()){
            throw new IllegalArgumentException("O nome do produto é obrigatório");
        }

        if(dadosproduto.getCategoria() == null ||
        dadosproduto.getCategoria().isBlank()){
            throw  new IllegalArgumentException("A categoria do produto é obrigatoria");
        }

        produto.setNome(dadosproduto.getNome());
        produto.setCategoria(dadosproduto.getCategoria());

        return produtoRepository.save(produto);
    }

    public void excluirProduto(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        if (loteRepository.existsByProdutoId(id)){
            throw new IllegalArgumentException("Não é possivel excluir produtos com lotes cadastrados");
        }
        produtoRepository.delete(produto);
    }



}
