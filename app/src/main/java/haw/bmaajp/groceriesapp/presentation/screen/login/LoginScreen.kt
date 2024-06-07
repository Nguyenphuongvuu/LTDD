package haw.bmaajp.groceriesapp.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import java.lang.reflect.Modifier

//object LoginDestination:NavigationDestination{
//    override val route: String = "login"
//    override val titleRes: Int = R.string.login_title
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToHomeScreen:()->Unit,
    navigateToSignUp:()->Unit,
    viewModel: LoginViewModel = viewModel(factory = DeliveryAppViewModel.Factory),
    delivaryAppViewModel: DeliveryAppViewModel,
    modifier: Modifier = Modifier
){
    LaunchedEffect(Unit) {
        delivaryAppViewModel.setFooterState(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var warningText by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.undraw_welcome_3gvl_1),
            contentDescription = "imageLoginView",
            modifier = Modifier.size(202.dp, 169.dp)
        )
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Log in to your existant account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            placeholder = { Text(text = "Username")},
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primaryColor),
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Password")},
            visualTransformation = if(passwordVisible) {
                PasswordVisualTransformation()
            }else{
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primaryColor),
                unfocusedBorderColor = Color.Gray
            ),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Image(
                        painter =
                        if (passwordVisible) painterResource(id = R.drawable.eyeicon)
                        else painterResource(id = R.drawable.offeyeicon),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(20.dp, 18.dp)
                    )
                }
            }
        )
        if(warningText == "wrong login"){
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "sai tên đăng nhập hoặc mật khẩu",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Red
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(5f))
            Text(
                text = "Forget Password?",
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.primaryColor),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .clickable { }
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.checkAccount(username, password)
                    if (viewModel.uiState.checkLogin) {
                        delivaryAppViewModel.updateIdUser(
                            viewModel.getIdUser(
                                username,
                                password
                            )
                        )
                        navigateToHomeScreen()
                    }else{
                        warningText = "wrong login"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primaryColor)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(166.dp, 52.dp),
        ) {
            Text(
                text = "LOG IN",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
        }

        Text(
            text = "Or connect using",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primaryColor)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(128.dp, 36.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icongoogle),
                    contentDescription = "googleButton",
                    modifier = Modifier
                        .size(17.5.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = "Google",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primaryColor)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(128.dp, 36.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconfacebook),
                    contentDescription = "facebookButton",
                    modifier = Modifier
                        .size(17.5.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = "Facebook",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don’t have an account?",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.primaryColor),
                modifier = Modifier
                    .clickable { navigateToSignUp() }
                    .padding(horizontal = 4.dp)
            )
        }

    }
}