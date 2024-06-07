package haw.bmaajp.groceriesapp.presentation.screen.signup

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

object SignUpDestination: NavigationDestination {
    override val route: String = "signUp"
    override val titleRes: Int = R.string.sign_up_title
}

@Composable
fun SignUpScreen(
    delivaryAppViewModel: DeliveryAppViewModel,
    viewModel: SignUpViewModel = viewModel(factory = DeliveryAppViewModel.Factory),
    navigateToLogin:()->Unit,
    navigateToHome:()->Unit,
    navigateBack:()->Unit,
){
    LaunchedEffect(Unit) {
        delivaryAppViewModel.setFooterState(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val accountUiState = viewModel.uiState.uiAccount
    val inforUserUiSate = viewModel.uiState.uiStateInforUser
    var uiStateTextField by remember {
        mutableStateOf("")
    }
    val passwordAgain = viewModel.passwordAgain

    val onChangePasswordValue: (String) -> Unit = { newPassword ->
        viewModel.updatePasswordAgain(newPassword)
    }
    val onChangeValue = viewModel::updateUiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp, end = 320.dp)
                .clickable {
                    navigateBack()
                }
        )
        Text(
            text = "Let’s Get Started",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "Create an new account",
            style = MaterialTheme.typography.headlineMedium
        )
        Column {
            TextFieldView(
                value = accountUiState.userName,
                onAccountChangeValue = {
                    onChangeValue(
                        accountUiState.copy(userName = it),
                        inforUserUiSate.copy(name = it),
                    )
                },
                textPlaceholder = "tên đăng nhập",
                sign = "account",
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(uiStateTextField.equals("emptyUserName")){
                ErrorText("tên đăng nhập không được để trống")
            }
            if(uiStateTextField.equals("lengthUserName")){
                ErrorText("phải có ít nhất 8 kí tự")
            }
            if(uiStateTextField.equals("checkUserName")){
                ErrorText(text = "tên đăng nhập đã tồn tại")
            }
        }

        Column {
            TextFieldView(
                value = accountUiState.email,
                onAccountChangeValue = {
                    onChangeValue(
                        accountUiState.copy(email = it),
                        inforUserUiSate.copy()
                    )
                },
                textPlaceholder = "email",
                sign = "account"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(uiStateTextField.equals("emptyEmail")){
                ErrorText("email không được để trống")
            }
            if(uiStateTextField.equals("errorSyntaxEmail")){
                ErrorText("sai cú pháp: example@gmail.com")
            }
            if(uiStateTextField.equals("checkEmail")){
                ErrorText(text = "email đã tồn tại")
            }
        }
        Column {
            TextFieldView(
                value = inforUserUiSate.numberPhone,
                onInforUserChangeValue = {
                    onChangeValue(
                        accountUiState.copy(),
                        inforUserUiSate.copy(numberPhone = it)
                    )
                },
                textPlaceholder = "số điện thoại",
                sign = "inforUser"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(uiStateTextField.equals("emptyPhoneNumber")){
                ErrorText("số điện thoại không được để trống")
            }
            if(uiStateTextField.equals("errorSyntaxPhoneNumber")){
                ErrorText("số điện thoại phải đủ 10 số")
            }
        }
        Column {
            TextFieldView(
                value = accountUiState.password,
                onAccountChangeValue = {
                    onChangeValue(
                        accountUiState.copy(password = it),
                        inforUserUiSate.copy()
                    )
                },
                textPlaceholder = "mật khẩu",
                sign = "password"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(uiStateTextField.equals("emptyPassword")){
                ErrorText("mật khẩu không được để trống")
            }
            if(uiStateTextField.equals("lengthPassword")){
                ErrorText("phải có ít nhất 8 kí tự")
            }
        }
        Column {
            TextFieldView(
                value = passwordAgain,
                onAccountChangeValue = onChangePasswordValue,
                textPlaceholder = "xác nhận lại mật khẩu",
                sign = "passwordAgain"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if(uiStateTextField.equals("emptyPasswordAgain")){
                ErrorText("vui lòng nhập lại mật khẩu")
            }
            if(uiStateTextField.equals("wrongPasswordAgain")){
                ErrorText("mật khẩu không trùng khớp")
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    if (checkEmpty(accountUiState.userName)) {
                        uiStateTextField = "emptyUserName"
                    } else if (accountUiState.userName.length < 8) {
                        uiStateTextField = "lengthUserName"
                    } else if (viewModel.checkUserName()) {
                        uiStateTextField = "checkUserName"
                    } else if (checkEmpty(accountUiState.email)) {
                        uiStateTextField = "emptyEmail"
                    } else if (!accountUiState.email.contains("@gmail.com")) {
                        uiStateTextField = "errorSyntaxEmail"
                    } else if (viewModel.checkEmail()) {
                        uiStateTextField = "checkEmail"
                    } else if (checkEmpty(inforUserUiSate.numberPhone)) {
                        uiStateTextField = "emptyPhoneNumber"
                    } else if (inforUserUiSate.numberPhone.length != 10) {
                        uiStateTextField = "errorSyntaxPhoneNumber"
                    } else if (checkEmpty(accountUiState.password)) {
                        uiStateTextField = "emptyPassword"
                    } else if (accountUiState.password.length < 8) {
                        uiStateTextField = "lengthPassword"
                    } else if (checkEmpty(passwordAgain)) {
                        uiStateTextField = "emptyPasswordAgain"
                    } else if (accountUiState.password != passwordAgain) {
                        uiStateTextField = "wrongPasswordAgain"
                    } else if (viewModel.checkEmail()) {
                        Log.i("checkmail", "ok")
                    } else {
                        uiStateTextField = ""
                        viewModel.addAccount()
                        delivaryAppViewModel.updateIdUser(viewModel.getLastInsertId())
                        navigateToHome()
                    }
                }
            },
            Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.primaryColor)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primaryColor)
            )
        ) {
            Text(
                text = "ĐĂNG KÝ",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White)
        }

        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Login here",
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.primaryColor),
                modifier = Modifier.clickable {
                    navigateToLogin()
                }
            )
        }
    }
}

@Composable
fun Spacer(modifier: Any) {

}

@Composable
fun ErrorText(
    text: String
){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Red,
    )
}

private fun checkEmpty(value: String):Boolean{
    return value.isBlank() || value.isEmpty()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldView(
    value: String,
    onAccountChangeValue:(String)->Unit = {},
    onInforUserChangeValue:(String)->Unit = {},
    textPlaceholder:String,
    sign: String,
){
    var showPassword by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            when (sign) {
                "account","password","passwordAgain" -> onAccountChangeValue(it)
                "inforUser" -> onInforUserChangeValue(it)
            }
        },
        placeholder = { Text(text = textPlaceholder) },
        visualTransformation =
        if(sign == "password" && showPassword) PasswordVisualTransformation()
        else if(sign == "passwordAgain" && showPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType =
            if (sign == "password" || sign == "passwordAgain") KeyboardType.Password
            else KeyboardType.Text,
            imeAction = if (sign == "passwordAgain")ImeAction.Done else{ ImeAction.Next })
        ,
        trailingIcon = {
            if(sign == "password" || sign == "passwordAgain"){
                IconButton(onClick = {
                    showPassword = !showPassword
                    Log.i("check", "$showPassword")
                }) {
                    Icon(
                        painter =
                        if (showPassword) painterResource(id = R.drawable.offeyeicon)
                        else painterResource(id = R.drawable.eyeicon),
                        contentDescription = if (showPassword) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        },
        singleLine = true,
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.primaryColor),
            unfocusedBorderColor = Color.Gray
        ),
        modifier = Modifier.background(color = Color.White)
    )
}