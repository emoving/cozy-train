package song.sam.cozytrain.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.StepsRecord
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import song.sam.cozytrain.data.healthconnect.types.SleepSessionData
import song.sam.cozytrain.ui.healthconnect.UiState
import song.sam.cozytrain.ui.healthconnect.viewmodel.SleepSessionViewModel
import song.sam.cozytrain.ui.healthconnect.viewmodel.StepsViewModel

@Composable
fun DrawHealthConnectSubscreen(
    viewModelData1: ViewModelData<out Record>,
    viewModelData2: ViewModelData<out Record>,
    viewModelData3: ViewModelData<out Record>,
    onDisplayData: LazyListScope.() -> Unit,
    onError: (Throwable?) -> Unit = {}
) {

    var flag by remember { mutableStateOf(false) }
    val stepViewModel: StepsViewModel = hiltViewModel()
    val stepVMD: ViewModelData<StepsRecord> = stepViewModel.getViewModelData()

    val sleepsessionViewModel: SleepSessionViewModel = hiltViewModel()
    val sleepsessionVMD: ViewModelData<SleepSessionData> = sleepsessionViewModel.getViewModelData()

    Log.d("11", "${stepVMD}   ${stepVMD.data}")
    Log.d("22", "${sleepsessionVMD}   ${sleepsessionVMD.data}")

    if (viewModelData1.uiState == UiState.Success || viewModelData3.uiState == UiState.Success) {
         flag = false
         Log.d("성공", "인데 왜 화면이 안 뜨지")

        val webViewState =
            rememberWebViewState(
                url = "https://dev.cozytrain.com",
            )
        var webViewClient = AccompanistWebViewClient()
        var webChromeClient = AccompanistWebChromeClient()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = androidx.compose.ui.graphics.Color.Black)
        ){
            WebView(
                state = webViewState,
                client = webViewClient,
                chromeClient = webChromeClient,
                onCreated = { webView ->
                    with(webView) {
                        settings.run {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            javaScriptCanOpenWindowsAutomatically = false
                        }
                    }
                }
            )
        }
//        val context = LocalContext.current
//        val webview = remember {
//            WebView(context).apply {
//                    settings.javaScriptEnabled = true
//                    webViewClient = WebViewClient()
//                    loadUrl("https://dev.cozytrain.com")
//                }
//        }
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = { context ->
//                webview
//            }
//        )

//         AndroidView(
//             factory = { context ->
//                 WebView(context).apply {
//                     settings.javaScriptEnabled = true
//                     webViewClient = SslWebViewConnect()
//                     webChromeClient = WebChromeClient()
//                 }
//             },
//             update = { webView ->
//                 webView.loadUrl("https://dev.cozytrain.com")
//             },
//             modifier = Modifier.fillMaxSize()
//         )
    }
    else {
        flag = true
    }

    if(flag){
        Text("칙칙 포근포근 서비스를 이용하기 위해서는 삼성헬스 정보가 필요합니다. \n 권한을 허용해주세요.")
        TextButton(onClick = {
            Log.d("??","??")
            viewModelData1.onRequestPermissions(viewModelData1.permissions)
            viewModelData2.onRequestPermissions(viewModelData2.permissions)
//            viewModelData3.onRequestPermissions(viewModelData3.permissions)
        }) {
            Text("권한 허용")
        }
    }
}