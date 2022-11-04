package kacper.litwinow.yournewsweek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.yournewsweek.databinding.ActivityHomeBinding
import kacper.litwinow.yournewsweek.helpers.gone
import kacper.litwinow.yournewsweek.helpers.show
import kacper.litwinow.yournewsweek.network.ConnectionState
import kacper.litwinow.yournewsweek.network.ConnectivityListener
import kacper.litwinow.yournewsweek.network.checkConnection

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ConnectivityListener {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnectionFunctionAndInterface()
    }

    private fun checkConnectionFunctionAndInterface() {
        checkConnection(this)
    }

    override fun onConnectionState(state: ConnectionState) {

        when (state) {
            ConnectionState.CONNECTED -> {
                binding.appbar.bannerOfflineMode.banner.gone()
            }
            else -> {
                binding.appbar.bannerOfflineMode.banner.show()
            }
        }
    }
}