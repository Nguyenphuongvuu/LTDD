package haw.bmaajp.groceriesapp.presentation.screen.signup

class SignUpViewModel(
    private val accountRepository: AccountRepository,
    private val inforUserRepository: InfoUserRepository
):ViewModel(){
    var uiState by mutableStateOf(UiState())
        private set

    var passwordAgain by mutableStateOf("")

    fun updatePasswordAgain(paraPasswordAgain: String){
        passwordAgain = paraPasswordAgain
    }

    suspend fun checkUserName():Boolean{
        return withContext(Dispatchers.IO){
            val result = accountRepository.checkUserName(uiState.uiAccount.userName) == 1
            result
        }
    }

    suspend fun checkEmail():Boolean{
        return withContext(Dispatchers.IO){
            val result = accountRepository.checkEmail(uiState.uiAccount.email) == 1
            result
        }
    }

    suspend fun getLastInsertId():Int{
        return accountRepository.getLastInsertId()
    }

    private fun InputValueAccount(checkUiState:UiStateAccount = uiState.uiAccount):Boolean{
        return with(checkUiState){
            userName.isNotBlank() && password.isNotBlank() && email.isNotBlank()
        }
    }

    suspend fun addAccount(){
        if(InputValueAccount() && InputValueInforUser()){
            accountRepository.insertAccount(uiState.uiAccount.addAccount())
            val idAccountForeignKey = accountRepository.getLastInsertId()
            addInforUser(idAccountForeignKey)
        }
    }

    fun updateUiState(uiStateAccount: UiStateAccount, uiStateInforUser: UiStateInforUser){
        uiState =
            UiState(
                uiAccount = uiStateAccount,
                uiStateInforUser = uiStateInforUser,
                isEntryValid = InputValueAccount(uiStateAccount)
            )
    }

    private fun InputValueInforUser(
        checkUiState: UiStateInforUser = uiState.uiStateInforUser
    ):Boolean{
        return with(checkUiState){
            numberPhone.isNotBlank()
        }
    }

    private suspend fun addInforUser(id:Int){
        if(InputValueInforUser()){
            inforUserRepository.insertInfoUser(uiState.uiStateInforUser.addInforUser(id))
        }
    }
}