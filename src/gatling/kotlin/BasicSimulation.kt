import com.rodrigoschonardt.simpleapi.data.AddItemFormData
import io.gatling.core.body.StringBody
import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

class BasicSimulation : Simulation()
{
    val httpProtocol = http.baseUrl( "http://localhost:8080" )

    val create = exec( http( "create" ).post( "/items" )
        .asJson()
        .body( StringBody( """{"name":"test","desc":"test"}""" ) ).check( bodyString().saveAs( "uuid" ) ) )

    val get = exec( http( "getById" ).get( "/items/#{uuid}") )

    val getAll = exec( http( "getAll").get( "/items" ) )

    val scn = scenario( "test" ).exec( getAll ).exec( create ).exec( get )

    init
    {
        setUp(
            scn.injectOpen( atOnceUsers( 1000 ) )
        ).protocols( httpProtocol )
    }
}