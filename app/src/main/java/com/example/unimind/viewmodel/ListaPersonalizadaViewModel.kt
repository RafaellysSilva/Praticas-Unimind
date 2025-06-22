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

    fun limparMensagem() {
        _mensagem.value = ""
    }

    fun listarListas() {
        viewModelScope.launch {
            try {
                _listas.value = RetrofitClient.apiService.listarListasPersonalizadas()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar listas: ${e.message}"
            }
        }
    }

    fun buscarLista(id: Int) {
        viewModelScope.launch {
            try {
                _listaDetalhe.value = RetrofitClient.apiService.buscarListaPersonalizada(id)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar lista: ${e.message}"
            }
        }
    }

    fun criarLista(lista: ListaPersonalizada, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.criarListaPersonalizada(lista)
                _mensagem.value = "Lista criada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar lista: ${e.message}"
            }
        }
    }

    fun atualizarLista(id: Int, lista: ListaPersonalizada, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.atualizarListaPersonalizada(id, lista)
                _mensagem.value = "Lista atualizada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar lista: ${e.message}"
            }
        }
    }

    fun deletarLista(id: Int, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deletarListaPersonalizada(id)
                _mensagem.value = "Lista deletada com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar lista: ${e.message}"
            }
        }
    }
}