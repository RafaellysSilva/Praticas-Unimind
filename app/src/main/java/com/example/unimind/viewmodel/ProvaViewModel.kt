package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.Prova
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class ProvaViewModel : ViewModel() {
    private val _provas = mutableStateOf<List<Prova>>(emptyList())
    val provas: State<List<Prova>> = _provas

    private val _provaDetalhe = mutableStateOf<Prova?>(null)
    val provaDetalhe: State<Prova?> = _provaDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    fun listarProvas() {
        viewModelScope.launch {
            try {
                _provas.value = RetrofitClient.apiService.listarProvas()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar provas: ${e.message}"
            }
        }
    }

    fun criarProva(prova: Prova, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val novaProva = RetrofitClient.apiService.criarProva(prova)
                _provas.value += novaProva
                _mensagem.value = "Prova criada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar prova: ${e.message}"
            }
        }
    }

    // Adicione aqui os métodos para buscar, atualizar e deletar provas, se necessário.
}