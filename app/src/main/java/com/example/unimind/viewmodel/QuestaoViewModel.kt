package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.Alternativa
import com.example.unimind.data.Questao
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class QuestaoViewModel : ViewModel() {
    private val _questoes = mutableStateOf<List<Questao>>(emptyList())
    val questoes: State<List<Questao>> = _questoes

    private val _questaoDetalhe = mutableStateOf<Questao?>(null)
    val questaoDetalhe: State<Questao?> = _questaoDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    // --- ESTADO DO QUIZ ---
    private val _alternativaSelecionada = mutableStateOf<Alternativa?>(null)
    val alternativaSelecionada: State<Alternativa?> = _alternativaSelecionada

    private val _indiceQuestaoAtual = mutableStateOf(0)
    val indiceQuestaoAtual: State<Int> = _indiceQuestaoAtual

    private val _pontuacao = mutableStateOf(0)
    val pontuacao: State<Int> = _pontuacao

    private val _quizFinalizado = mutableStateOf(false)
    val quizFinalizado: State<Boolean> = _quizFinalizado

    // Limpa a mensagem de erro ou sucesso
    fun limparMensagem() {
        _mensagem.value = ""
    }

    // --- LÓGICA DO QUIZ ---

    fun selecionarAlternativa(alternativa: Alternativa) {
        _alternativaSelecionada.value = alternativa
    }

    fun verificarResposta() {
        if (_alternativaSelecionada.value?.correta == true) {
            _pontuacao.value++
        }
        // Lógica adicional pode ser adicionada aqui, como mostrar feedback de resposta
    }

    fun proximaQuestao() {
        if (_indiceQuestaoAtual.value < _questoes.value.size - 1) {
            _indiceQuestaoAtual.value++
            _alternativaSelecionada.value = null // Limpa a seleção para a próxima questão
        } else {
            _quizFinalizado.value = true
        }
    }

    fun reiniciarQuiz() {
        _indiceQuestaoAtual.value = 0
        _pontuacao.value = 0
        _alternativaSelecionada.value = null
        _quizFinalizado.value = false
        listarQuestoes() // Ou qualquer outra lógica para reiniciar as questões
    }


    // --- MÉTODOS DE BUSCA (GET) ---

    fun listarQuestoes() {
        viewModelScope.launch {
            try {
                _questoes.value = RetrofitClient.apiService.listarQuestoes()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar questões: ${e.message}"
            }
        }
    }

    fun buscarQuestao(id: Int) {
        viewModelScope.launch {
            try {
                _questaoDetalhe.value = RetrofitClient.apiService.buscarQuestao(id)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar questão: ${e.message}"
            }
        }
    }

    fun listarQuestoesPorAno(ano: Int) {
        viewModelScope.launch {
            try {
                _questoes.value = RetrofitClient.apiService.listarQuestoesPorAno(ano)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao filtrar por ano: ${e.message}"
            }
        }
    }

    fun listarQuestoesPorCategoria(idCategoria: Int) {
        viewModelScope.launch {
            try {
                _questoes.value = RetrofitClient.apiService.listarQuestoesPorCategoria(idCategoria)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao filtrar por categoria: ${e.message}"
            }
        }
    }

    fun listarQuestoesPorFonte(fonte: String) {
        viewModelScope.launch {
            try {
                _questoes.value = RetrofitClient.apiService.listarQuestoesPorFonte(fonte)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao filtrar por fonte: ${e.message}"
            }
        }
    }

    // --- MÉTODOS DE MODIFICAÇÃO (POST, PUT, DELETE) ---

    fun criarQuestao(questao: Questao, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.criarQuestao(questao)
                _mensagem.value = "Questão criada com sucesso."
                onSucesso() // Navega ou atualiza a UI
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar questão: ${e.message}"
            }
        }
    }

    fun atualizarQuestao(id: Int, questao: Questao, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.atualizarQuestao(id, questao)
                _mensagem.value = "Questão atualizada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar questão: ${e.message}"
            }
        }
    }

    fun deletarQuestao(id: Int, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deletarQuestao(id)
                _mensagem.value = "Questão deletada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar questão: ${e.message}"
            }
        }
    }
}