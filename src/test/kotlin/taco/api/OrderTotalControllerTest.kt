package taco.api

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.runtime.server.EmbeddedServer
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

import java.math.BigDecimal;

class OrderTotalControllerTest: StringSpec() {

    val embeddedServer = autoClose( 
        ApplicationContext.run(EmbeddedServer::class.java) 
    )

    val client = autoClose( 
        embeddedServer.applicationContext.createBean(RxHttpClient::class.java, embeddedServer.getURL())
    )

    init {
        "test calling invalid endpoint" {
            var req = HttpRequest.POST("/invalid-404", "")

            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }

            e.status.code shouldBe 404
        }

        "test not giving json" {
            var req = HttpRequest.POST("/order-total", "")

            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }

            e.status.code shouldBe 400
        }

        
        "test giving empty json structure" {
            var req = HttpRequest.POST("/order-total", "{}")

            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }

            e.status.code shouldBe 400
        }

        "test Not giving name" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "price": 3.30
            }
            """)
            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }
            e.status.code shouldBe 400
        }

        "test giving price with to much precision" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": 3.3099,
                "quantity": "1"
            }
            """)
            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }
            e.status.code shouldBe 400
        }

        "test giving price with to much precision and string" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": "3.3099",
                "quantity": "1"
            }
            """)
            val e = shouldThrow<HttpClientResponseException> { client.toBlocking().exchange(req, HttpResponse::class.java) }
            e.status.code shouldBe 400
        }

        "test Not giving price and quantity" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("0.0")) shouldBe 0
            rspBody.quantity shouldBe 0
        }

        "test to adding non required entries" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": 3.30,
                "quantity": "1",
                "note": "extra cheese"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("3.30")) shouldBe 0
            rspBody.quantity shouldBe 1
        }

        "test Not giving quantity" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": 3.30
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("0.0")) shouldBe 0
            rspBody.quantity shouldBe 0
        }

        "test Not giving price" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "quantity": 3
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("0.0")) shouldBe 0
            rspBody.quantity shouldBe 3
        }

        "test giving quantity as string" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": 3.30,
                "quantity": "1"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("3.30")) shouldBe 0
            rspBody.quantity shouldBe 1
        }

        "test giving price as int" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": 3,
                "quantity": "1"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("3.00")) shouldBe 0
            rspBody.quantity shouldBe 1
        }

        "test giving price as string" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": "3.30",
                "quantity": "1"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("3.30")) shouldBe 0
            rspBody.quantity shouldBe 1
        }

        "test multiple quantities of one taco" {
            var req = HttpRequest.POST("/order-total", 
            """
            {
                "name": "Veggie Taco",
                "price": "3.30",
                "quantity": "2"
            }
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("6.60")) shouldBe 0
            rspBody.quantity shouldBe 2
        }

        "test multiple types of tacos" {
            var req = HttpRequest.POST("/order-total", 
            """
            [
                {
                    "name": "Veggie Taco",
                    "price": 3.30,
                    "quantity": "1"
                },
                {
                    "name": "Chorizo Taco",
                    "price": 4.20,
                    "quantity": "1"
                }
            ]
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("7.50")) shouldBe 0
            rspBody.quantity shouldBe 2
        }

        "test 2 types of tacos with one of them having quantity 2" {
            var req = HttpRequest.POST("/order-total", 
            """
            [
                {
                    "name": "Veggie Taco",
                    "price": 3.30,
                    "quantity": "2"
                },
                {
                    "name": "Chorizo Taco",
                    "price": 3.30,
                    "quantity": "1"
                }
            ]
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("9.90")) shouldBe 0
            rspBody.quantity shouldBe 3
        }

        "test the 20% discount when purchasing 4 items of the same thing" {
            var req = HttpRequest.POST("/order-total", 
            """
            [
                {
                    "name": "Veggie Taco",
                    "price": 3.30,
                    "quantity": "4"
                }
            ]
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("10.56")) shouldBe 0
            rspBody.quantity shouldBe 4
        }

        "test the 20% discount when purchasing 100 of the same thing" {
            var req = HttpRequest.POST("/order-total", 
            """
            [
                {
                    "name": "Veggie Taco",
                    "price": 3.30,
                    "quantity": "100"
                }
            ]
            """)
            val rsp = client.toBlocking().exchange(req, OrderTotal::class.java) 

            rsp.status.code shouldBe 201
            rsp.body() shouldNotBe null

            val rspBody = rsp.body()!!

            rspBody.price.compareTo(BigDecimal("264.00")) shouldBe 0
            rspBody.quantity shouldBe 100
        }
    }
}