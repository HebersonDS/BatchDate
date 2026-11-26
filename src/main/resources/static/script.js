const nomeProduto = document.getElementById("nomeProduto");
const categoriaProduto = document.getElementById("categoriaProduto");
const btnCadastrarProduto = document.getElementById("btnCadastrarProduto");
const listaLotes = document.getElementById("listaLotes");
let produtoSelecionado = null;

const codigoLote = document.getElementById("codigoLote");
const dataValidade = document.getElementById("dataValidade");
const quantidadeLote = document.getElementById("quantidadeLote");
const btnCadastrarLote = document.getElementById("btnCadastrarLote");
let loteSelecionado = null;

const quantidadeEntrada = document.getElementById("quantidadeEntrada");
const btnRegistrarEntrada = document.getElementById("btnRegistrarEntrada");

const btnAlertas = document.getElementById("btnAlertas");
const listaAlertas = document.getElementById("listaAlertas");
const btnExportar = document.getElementById("btnExportar");

const API = "http://localhost:8080";

const listaProdutos = document.getElementById("listaProdutos");

async function carregarProdutos() {

    const resposta = await fetch(`${API}/produtos`);

    const produtos = await resposta.json();

    listaProdutos.innerHTML = "";

    produtos.forEach(produto => {

        const item = document.createElement("p");

        item.textContent = `${produto.nome} - ${produto.categoria}`;

        item.style.cursor = "pointer";

        item.addEventListener("click", () => {
            produtoSelecionado = produto.id;
            carregarLotes(produto.id);
        });

        listaProdutos.appendChild(item);

    });
}

async function carregarLotes(idProduto) {
    loteSelecionado = null;

    const resposta = await fetch(`${API}/produtos/${idProduto}/lotes`);

    const lotes = await resposta.json();

    listaLotes.innerHTML = "";

    lotes.forEach(lote => {

        const item = document.createElement("p");

        item.style.cursor = "pointer";

        item.addEventListener("click", () => {
            loteSelecionado = lote.id;
        });

        item.textContent = `Lote: ${lote.codigoLote} | Validade: ${lote.dataValidade} | Quantidade: ${lote.quantidade}`;

        listaLotes.appendChild(item);

    });
}

async function cadastrarProduto() {

    const produto = {
        nome: nomeProduto.value,
        categoria: categoriaProduto.value
    };

    const resposta = await fetch(`${API}/produtos`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(produto)
    });

    if (resposta.ok) {
        alert("Produto cadastrado com sucesso!");

        nomeProduto.value = "";
        categoriaProduto.value = "";

        carregarProdutos();

    } else {
        const erro = await resposta.json();
        alert(erro.erro);
    }
}

async function cadastrarLote() {

    if (produtoSelecionado === null) {
        alert("Selecione um produto primeiro!");
        return;
    }

    const lote = {
        codigoLote: codigoLote.value,
        dataValidade: dataValidade.value,
        quantidade: Number(quantidadeLote.value)
    };

    const resposta = await fetch(`${API}/lotes/${produtoSelecionado}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(lote)
    });

    if (resposta.ok) {

        alert("Lote cadastrado com sucesso!");

        codigoLote.value = "";
        dataValidade.value = "";
        quantidadeLote.value = "";

        carregarLotes(produtoSelecionado);

    } else {

        const erro = await resposta.json();
        alert(erro.erro);

    }
}

async function registrarEntrada() {

    if (loteSelecionado === null) {
        alert("Selecione um lote primeiro!");
        return;
    }

    const quantidade = Number(quantidadeEntrada.value);

    const resposta = await fetch(`${API}/lotes/${loteSelecionado}/entrada`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(quantidade)
    });

    if (resposta.ok) {

        alert("Entrada registrada com sucesso!");

        quantidadeEntrada.value = "";

        carregarLotes(produtoSelecionado);

    } else {

        const erro = await resposta.json();
        alert(erro.erro);

    }
}

async function carregarAlertas() {

    const resposta = await fetch(`${API}/lotes/alertas-validade`);

    const alertas = await resposta.json();

    listaAlertas.innerHTML = "";

    if (alertas.length === 0) {
        listaAlertas.textContent = "Nenhum lote próximo do vencimento.";
        return;
    }

    alertas.forEach(alerta => {

        const item = document.createElement("p");

        item.textContent = `Produto: ${alerta.nomeProduto} | Categoria: ${alerta.categoria} | Lote: ${alerta.codigoLote} | Validade: ${alerta.dataValidade} | Quantidade: ${alerta.quantidade}`;

        listaAlertas.appendChild(item);

    });

}

carregarProdutos();

function exportarProduto() {

    if (produtoSelecionado === null) {
        alert("Selecione um produto primeiro!");
        return;
    }

    window.location.href = `${API}/produtos/${produtoSelecionado}/exportar`;

}

btnCadastrarProduto.addEventListener("click", cadastrarProduto);
btnCadastrarLote.addEventListener("click", cadastrarLote);
btnRegistrarEntrada.addEventListener("click", registrarEntrada);
btnAlertas.addEventListener("click", carregarAlertas);
btnExportar.addEventListener("click", exportarProduto);