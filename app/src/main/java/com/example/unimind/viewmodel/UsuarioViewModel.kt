package com.example.unimind.viewmodel

import Usuario
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unimind.data.UsuarioCadastro
import com.example.unimind.network.RetrofitClient
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios

    private val _usuarioDetalhe = mutableStateOf<Usuario?>(null)
    val usuarioDetalhe: State<Usuario?> = _usuarioDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    private val _loginStatus = mutableStateOf<LoginResult>(LoginResult.Nenhum)
    val loginStatus: State<LoginResult> = _loginStatus

    fun listarUsuarios() {
        viewModelScope.launch {
            try {
                _usuarios.value = RetrofitClient.apiService.listarUsuarios()
                _mensagem.value = "Usuários carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            }
        }
    }

    fun buscarUsuario(nome: String, senha: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = RetrofitClient.apiService.buscarUsuario(nome, senha)
                _usuarioDetalhe.value = usuario
                _mensagem.value = if (usuario != null) "Usuário encontrado." else "Usuário não encontrado."
                callback(usuario != null)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
                callback(false)
            }
        }
    }

    fun verificarLogin(nome: String, senha: String) {
        viewModelScope.launch {
            _loginStatus.value = try {
                val usuario = RetrofitClient.apiService.buscarUsuario(nome, senha)
                if (usuario != null) {
                    _usuarioDetalhe.value = usuario
                    LoginResult.Sucesso
                } else {
                    LoginResult.Erro("Usuário ou senha inválidos")
                }
            } catch (e: Exception) {
                LoginResult.Erro("Erro ao conectar: ${e.message}")
            }
        }
    }

    fun criarUsuario(nome: String, email: String, senha: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                val usuarioCadastro = UsuarioCadastro(nome = nome, email = email, senha = senha)
                val novoUsuario = RetrofitClient.apiService.criarUsuario(usuarioCadastro)
                _usuarios.value = _usuarios.value + novoUsuario
                _mensagem.value = "Usuário criado com ID ${novoUsuario.idUsuario}"
                onSucesso()
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar usuário: ${e.message}"
            }
        }
    }

    fun atualizarUsuario(nome: String, email: String, senha: String, idNivel: Int?) {
        val id = _usuarioDetalhe.value?.idUsuario
        if (id == null) {
            _mensagem.value = "Nenhum usuário selecionado para atualização."
            return
        }

        val usuarioAtualizado = _usuarioDetalhe.value?.copy(
            nome = nome,
            email = email,
            senha = senha,
            idNivel = idNivel
        )

        if (usuarioAtualizado == null) {
            _mensagem.value = "Não foi possível criar o objeto de usuário para atualização."
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.atualizarUsuario(id, usuarioAtualizado)
                if (response.isSuccessful) {
                    // Atualiza a lista local de usuários
                    _usuarios.value = _usuarios.value.map {
                        if (it.idUsuario == id) usuarioAtualizado else it
                    }
                    _usuarioDetalhe.value = usuarioAtualizado
                    _mensagem.value = "Usuário $id atualizado."
                } else {
                    _mensagem.value = "Erro ao atualizar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar usuário: ${e.message}"
                Log.d("VIEWMODEL", "Erro: ${e.stackTraceToString()}")
            }
        }
    }

    fun deletarUsuario(id: Int) {
        viewModelScope.launch {
            Log.d("DELETAR", "Chamando API para deletar $id")
            try {
                val response = RetrofitClient.apiService.deletarUsuario(id)
                if (response.isSuccessful) {
                    _mensagem.value = "Usuário deletado com sucesso"
                } else {
                    _mensagem.value = "Erro ao deletar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro: ${e.message}"
            }
        }
    }

    fun limparLoginStatus() {
        _loginStatus.value = LoginResult.Nenhum
    }

    fun setMensagem(msg: String) {
        _mensagem.value = msg
    }
}