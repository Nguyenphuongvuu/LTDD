package haw.bmaajp.groceriesapp.presentation.screen.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val accountRepository:AccountRepository): ViewModel(){
    var uiState by mutableStateOf(UiState())
        private set

    suspend fun getIdUser(usreName:String, passsword:String):Int {
        return withContext(Dispatchers.IO) {
            accountRepository.getIdUser(usreName, passsword)
        }
    }

    suspend fun checkAccount(userName:String, password:String):Boolean{
        return withContext(Dispatchers.IO){
            val result = accountRepository.checkAccount(userName,password) == 1
            uiState.checkLogin = result
            result
        }
    }
}

data class UiState(
    var typeAccount: Int = 1,
    var checkLogin: Boolean = false,
)