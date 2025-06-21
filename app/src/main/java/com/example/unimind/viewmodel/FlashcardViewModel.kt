package com.example.unimind.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.Flashcard
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class FlashcardViewModel : ViewModel() {
    private val _flashcards = mutableStateOf<List<Flashcard>>(emptyList())
    val flashcards: State<List<Flashcard>> = _flashcards

    private val _flashcardDetalhe = mutableStateOf<Flashcard?>(null)
    val flashcardDetalhe: State<Flashcard?> = _flashcardDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    fun listarFlashcards() {
        viewModelScope.launch {
            try {
                _flashcards.value = RetrofitClient.apiService.listarFlashcards()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar flashcards: ${e.message}"
            }
        }
    }

    fun criarFlashcard(flashcard: Flashcard, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val novoFlashcard = RetrofitClient.apiService.criarFlashcard(flashcard)
                _flashcards.value += novoFlashcard
                _mensagem.value = "Flashcard criado com sucesso."
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar flashcard: ${e.message}"
            }
        }
    }
    // Adicione aqui os métodos para buscar, atualizar e deletar flashcards.
}