package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.ListaPersonalizada
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class ListaPersonalizadaViewModel : ViewModel() {
    private val _listas = mutableStateOf<List<ListaPersonalizada>>(emptyList())
    val listas: State<List<ListaPersonalizada>> = _listas

    private val _listaDetalhe = mutableStateOf<ListaPersonalizada?>(null)
    val listaDetalhe: State<ListaPersonalizada?> = _listaDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    fun listarListas() {
        viewModelScope.launch {
            try {
                _listas.value = RetrofitClient.apiService.listarListasPersonalizadas()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar listas: ${e.message}"
            }
        }
    }

    fun criarLista(lista: ListaPersonalizada, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val novaLista = RetrofitClient.apiService.criarListaPersonalizada(lista)
                _listas.value += novaLista
                _mensagem.value = "Lista criada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar lista: ${e.message}"
            }
        }
    }
    // Adicione aqui os métodos para buscar, atualizar e deletar listas, se necessário.
}