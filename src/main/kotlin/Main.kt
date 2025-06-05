import kotlinx.coroutines.runBlocking
import Apod
import network.ApodApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

fun formatarData(data: String): String? {
    return try {
        val entrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        entrada.isLenient = false
        val dataObj = entrada.parse(data)
        val saida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        saida.format(dataObj!!)
    } catch (e: Exception) {
        println("Data inválida. Use o formato DD/MM/AAAA.")
        null
    }
}

fun main() = runBlocking {
    val API_KEY = "FT0cPlNNwRv3T2t7qEiggP0fhBBYcLEDl9RWhMrn"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/planetary/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApodApiService::class.java)
    val servico = Apod(api, API_KEY)

    while (true) {
        try {
            println("API APOD - NASA.\n")
            val parametros = pedirParametros()
            val resultado = servico.buscarDados(parametros)
            servico.mostrarResultados(resultado)

            println("\n-----")
            println("Deseja fazer nova consulta? (Y/N)")
            if (readLine()?.equals("Y", true) != true) {
                println("Obrigado por usar o programa!")
                break
            }
        } catch (e: Exception) {
            println("Erro ao buscar dados: ${e.message}")
        }
    }
}

fun pedirParametros(): Map<String, Any?> {
    val params = mutableMapOf<String, Any?>()

    println("Digite a data (DD/MM/AAAA) ou deixe em branco para outras opções:")
    val data = readLine()?.trim().orEmpty()

    if (data.isNotEmpty()) {
        formatarData(data)?.let { params["date"] = it }
        return params
    }

    println("Deseja buscar por período? (Y/N):")
    if (readLine()?.equals("Y", true) == true) {
        println("Data inicial:")
        val inicio = readLine()?.trim().orEmpty()
        println("Data final (opcional):")
        val fim = readLine()?.trim().orEmpty()
        formatarData(inicio)?.let { params["startDate"] = it }
        formatarData(fim)?.let { if (fim.isNotEmpty()) params["endDate"] = it }
    } else {
        println("Quantas imagens deseja? (opcional):")
        readLine()?.toIntOrNull()?.let { params["count"] = it }
    }

    return params
}