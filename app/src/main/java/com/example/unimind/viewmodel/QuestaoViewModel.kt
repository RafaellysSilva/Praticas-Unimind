package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun listarQuestoes() {
        viewModelScope.launch {
            try {
                _questoes.value = RetrofitClient.apiService.listarQuestoes()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar questões: ${e.message}"
            }
        }
    }

    fun criarQuestao(questao: Questao, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val novaQuestao = RetrofitClient.apiService.criarQuestao(questao)
                _questoes.value += novaQuestao
                _mensagem.value = "Questão criada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar questão: ${e.message}"
            }
        }
    }

    // Adicione aqui os métodos para buscar, atualizar e deletar questões, se necessário.
}