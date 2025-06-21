package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.Competicao
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class CompeticaoViewModel : ViewModel() {
    private val _competicoes = mutableStateOf<List<Competicao>>(emptyList())
    val competicoes: State<List<Competicao>> = _competicoes

    private val _competicaoDetalhe = mutableStateOf<Competicao?>(null)
    val competicaoDetalhe: State<Competicao?> = _competicaoDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    fun listarCompeticoes() {
        viewModelScope.launch {
            try {
                _competicoes.value = RetrofitClient.apiService.listarCompeticoes()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar competições: ${e.message}"
            }
        }
    }

    fun criarCompeticao(competicao: Competicao, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val novaCompeticao = RetrofitClient.apiService.criarCompeticao(competicao)
                _competicoes.value += novaCompeticao
                _mensagem.value = "Competição criada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar competição: ${e.message}"
            }
        }
    }

    // Adicione aqui os métodos para buscar, atualizar e deletar competições.
}