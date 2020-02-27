package hos.houns.pockmp.data

import hos.houns.pockmp.data.entity.TaskEntity
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.response.readText
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.URLProtocol
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */
class TasksApi(clientEngine: HttpClientEngine) {
    private  val BASE_ENDPOINT = "http://jsonplaceholder.typicode.com"
    private val client = HttpClient(clientEngine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }


    @ImplicitReflectionSerializer
    suspend fun getAllTasks():  List<TaskEntity>  {
        val response = client.get<HttpResponse> {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_ENDPOINT
                encodedPath = "/todos"
                //parameter("sort_by", "popularity.desc")
                //header(HEADER_AUTHORIZATION, API_KEY.asBearerToken())
            }
        }

        val jsonBody = response.readText()

        return Json.parse(jsonBody)
    }


}