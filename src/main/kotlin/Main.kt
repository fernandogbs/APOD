import kotlinx.coroutines.runBlocking
import Apod
import network.ApodApiService
import Earth
import network.EarthApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String? {
    return try {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        inputFormat.isLenient = false
        val parsedDate = inputFormat.parse(date)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        println("Data inválida. Use o formato DD/MM/AAAA.")
        null
    }
}

fun main() = runBlocking {
    val APIKEY = "FT0cPlNNwRv3T2t7qEiggP0fhBBYcLEDl9RWhMrn"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apodApi = retrofit.create(ApodApiService::class.java)
    val earthApi = retrofit.create(EarthApiService::class.java)

    val apodService = Apod(apodApi, APIKEY)
    val earthService = Earth(earthApi, APIKEY)

    var continueProgram = true
    while (continueProgram) {
        try {
//            println("Deseja consultar qual API?\n(1) APOD\n(2) Earth")
//            val choice = readLine()?.toIntOrNull()
//
//            if (choice == null || (choice != 1 && choice != 2)) {
//                println("Opção inválida. Por favor, escolha 1 ou 2.")
//                continue
//            }
//
//            when (choice) {
//                1 -> {

//                }
//                2 -> {
//                    println("Você escolheu a API Earth.\n")
//                    val params = collectEarthParams()
//                    if (params != null) {
//                        val result = earthService.fetchData(
//                            params["lat"] as Double,
//                            params["lon"] as Double,
//                            params["dim"] as Double?,
//                            params["date"] as String?
//                        )
//                        earthService.displayResults(result)
//                    }
//                }
//            }

            println("API APOD - NASA.\n")
            val params = collectApodParams()
            val result = apodService.fetchData(params)
            apodService.displayResults(result)

            println("\n-----")
            println("Deseja efetuar novas consultas? (Y/N)")
            continueProgram = readLine()?.equals("Y", ignoreCase = true) ?: false
            if (!continueProgram) println("Obrigado por usar o programa!")

        } catch (e: Exception) {
            println("Erro ao buscar dados: ${e.message}")
        }
    }
}

fun collectApodParams(): Map<String, Any?> {
    val params = mutableMapOf<String, Any?>()

    println("Forneça os dados para consulta: (OPCIONAL)")
    println("Digite a data ou deixe em branco para usar a data atual:")
    val inputDate = readLine().toString()

    if (inputDate.isBlank()) {
        println("Deseja utilizar um período de data (Y/N):")
        val usePeriod = readLine()?.equals("Y", ignoreCase = true) ?: false
        if (usePeriod) {
            println("Digite a data inicial:")
            val startDate = readLine()?.trim().orEmpty()
            println("Digite a data final (Opcional):")
            val endDate = readLine()?.trim().orEmpty()

            if (startDate.isNotEmpty()) {
                val formattedStart = formatDate(startDate)
                if (formattedStart != null) params["startDate"] = formattedStart
            }
            if (endDate.isNotEmpty()) {
                val formattedEnd = formatDate(endDate)
                if (formattedEnd != null) params["endDate"] = formattedEnd
            }
        } else {
            println("Quantas imagens deseja receber? (OPTIONAL):")
            val count = readLine()?.trim()?.toIntOrNull()
            if (count != null) params["count"] = count
        }
    } else {
        val date = formatDate(inputDate)
        if (date != null) params["date"] = date
    }

    return params
}

fun collectEarthParams(): Map<String, Any?>? {
    val params = mutableMapOf<String, Any?>()

    println("Digite a latitude:")
    val lat = readLine()?.trim()?.toDoubleOrNull()
    if (lat == null) {
        println("Erro: Latitude inválida.")
        return null
    }
    params["lat"] = lat

    println("Digite a longitude:")
    val lon = readLine()?.trim()?.toDoubleOrNull()
    if (lon == null) {
        println("Erro: Longitude inválida.")
        return null
    }
    params["lon"] = lon

    println("Digite a dimensão (opcional, em graus, ex: 0.15):")
    val dim = readLine()?.trim()?.toDoubleOrNull()
    params["dim"] = dim

    println("Digite a data (opcional, formato DD/MM/AAAA):")
    val inputDate = readLine()?.trim()
    if (!inputDate.isNullOrEmpty()) {
        val formattedDate = formatDate(inputDate)
        params["date"] = formattedDate
    }

    return params
}