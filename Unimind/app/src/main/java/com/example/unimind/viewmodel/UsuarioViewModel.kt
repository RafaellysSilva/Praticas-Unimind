package com.example.usuarioapp.viewmodel
//import com.example.usuarioapp.LoginResult // substitui com o nome certo do seu pacote
//package com.example.unimind.viewmodel
import com.example.unimind.viewmodel.LoginResult



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unimind.network.RetrofitUsuario
import com.example.unimind.data.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios

    private val _usuarioDetalhe = mutableStateOf<Usuario?>(null)
    val usuarioDetalhe: State<Usuario?> = _usuarioDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    fun listarUsuarios() {
        coroutineScope.launch {
            try {
                _usuarios.value = RetrofitUsuario.instance.listarUsuarios()
                _mensagem.value = "Usuários carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            }
        }
    }

    //tem q fzr pelo nome
    /*
    * fun buscarUsuario(nome: String, senha: String) {
        coroutineScope.launch {
            try {
                _usuarioDetalhe.value = RetrofitUsuario.instance.buscarUsuario(nome, senha)
                _mensagem.value = if (_usuarioDetalhe.value != null) "Usuário encontrado." else "Usuário não encontrado."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
            }
        }
    }
    */

    fun buscarUsuario(nome: String, senha: String, callback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val usuario = RetrofitUsuario.instance.buscarUsuario(nome, senha)
                _usuarioDetalhe.value = usuario
                _mensagem.value = if (usuario != null) "Usuário encontrado." else "Usuário não encontrado."
                callback(usuario != null)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
                callback(false)
            }
        }
    }

    private val _loginStatus = mutableStateOf<LoginResult>(LoginResult.Nenhum)
    val loginStatus: State<LoginResult> = _loginStatus

    fun verificarLogin(nome: String, senha: String) {
        coroutineScope.launch {
            try {
                val usuario = RetrofitUsuario.instance.buscarUsuario(nome, senha)
                _loginStatus.value = if (usuario != null) LoginResult.Sucesso
                else LoginResult.Erro("Usuário ou senha inválidos")
            } catch (e: Exception) {
                _loginStatus.value = LoginResult.Erro("Erro ao conectar: ${e.message}")
            }
        }
    }

    fun limparLoginStatus() {
        _loginStatus.value = LoginResult.Nenhum
    }

    fun criarUsuario(usuario: Usuario) {
        coroutineScope.launch {
            try {
                val novoUsuario = RetrofitUsuario.instance.criarUsuario(usuario)
                _usuarios.value = _usuarios.value + novoUsuario
                _mensagem.value = "Usuário criado com ID ${novoUsuario.idUsuario}"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar usuário: ${e.message}"
            }
        }
    }

    fun atualizarUsuario(id: Int, usuarioAtualizado: Usuario) {
        coroutineScope.launch {
            try {
                val response = RetrofitUsuario.instance.atualizarUsuario(id, usuarioAtualizado)
                if (response.isSuccessful) {
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
            }
        }
    }

    fun deletarUsuario(id: Int) {
        coroutineScope.launch {
            try {
                val response = RetrofitUsuario.instance.deletarUsuario(id)
                if (response.isSuccessful) {
                    _usuarios.value = _usuarios.value.filter { it.idUsuario != id }
                    _usuarioDetalhe.value = null
                    _mensagem.value = "Usuário $id deletado."
                } else {
                    _mensagem.value = "Erro ao deletar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar usuário: ${e.message}"
            }
        }
    }
}