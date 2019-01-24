
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class scalabilitygatling_1 extends Simulation {

	val httpProtocol = http
		.baseURL("http://www.marksandspencer.com")
		.inferHtmlResources()
		//.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""",""".*\.gif""",""".*\.jpeg""",""".*\.jpg""",""".*\.nc""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())

	//val uri18 = "http://www.marksandspencer.com"
	val Iterations = System.getProperty("Iterations").toInt
	val Users = System.getProperty("Users").toInt
	val ToHomePage = System.getProperty("HomePage")
	val ToPLP = System.getProperty("PLP")
	val ToPDP = System.getProperty("PDP")
	val ToAddToCart = System.getProperty("AddToCart")
	val ToViewBag = System.getProperty("ViewBag")
	
	//val Iterations = 5
	//val Users = 5
	//val ToHomePage = "true"
	//val ToPLP = "true"
	//val ToPDP = "true"
	//val ToAddToCart = "true"
	//val ToViewBag = "true"
	
	val scn = scenario("scalabilitygatling_1")
	.repeat(Iterations){
		exec(flushHttpCache)
		.doIf(ToHomePage == "true")
		{
		group("HomePage")
			{
			exec(
			http("HomePage_Req")
			.get("/")
			.extraInfoExtractor(extraInfo => List(extraInfo.response.statusCode.get))
			.check(status.is(200))
			)
			}
		.pause(2)
		}
		.doIf(ToPLP == "true")
		{
		group("PLP")
			{
			exec(
			http("PLP_Req")
			.get("/l/women/dresses")
			.extraInfoExtractor(extraInfo => List(extraInfo.response.statusCode.get))
			.check(status.is(200))
			//.check(css("a[class='prodAnchor']", "href").saveAs("pdpurl"))
			)
			}
		.pause(2)
		}
		.doIf(ToPDP == "true")
		{
		group("PDP")
			{
			exec(
			http("PDP_Req")
			//.get(uri18 + "${pdpurl}")
			.get("/asymmetric-cap-sleeve-midi-dress/p/p60126930")
			.extraInfoExtractor(extraInfo => List(extraInfo.response.statusCode.get))
			.check(status.is(200))
			)
			}
		.pause(2)
		}
		.doIf(ToAddToCart == "true")
		{
		group("AddToCart")
			{
			exec(
			http("AddToCart_Req")
			.get("/")
			.extraInfoExtractor(extraInfo => List(extraInfo.response.statusCode.get))
			.check(status.is(200))
			)
			}
		.pause(2)
		}
		.doIf(ToViewBag == "true")
		{
		group("ViewBag")
			{
			exec(
			http("ViewBag_Req")
			.get("/OrderCalculate")
			.extraInfoExtractor(extraInfo => List(extraInfo.response.statusCode.get))
			.check(status.is(200))
			)
			}
		.pause(2)
		}
	}
	setUp(scn.inject(
					//atOnceUsers(Users)
					rampUsers(Users) over(30 seconds)
					)).protocols(httpProtocol)		 
}