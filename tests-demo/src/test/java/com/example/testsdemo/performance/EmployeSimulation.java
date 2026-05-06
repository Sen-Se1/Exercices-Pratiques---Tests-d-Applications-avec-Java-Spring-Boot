package com.example.testsdemo.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EmployeSimulation extends Simulation {

    HttpProtocolBuilder http = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scenario = CoreDsl.scenario("Consultation employés")
            .exec(HttpDsl.http("POST /api/employes")
                    .post("/api/employes")
                    .header("Content-Type", "application/json")
                    .body(StringBody("""
                            {
                              "nom": "Salah",
                              "departement": "Informatique",
                              "salaire": 3500
                            }
                            """))
                    .check(HttpDsl.status().is(201))
                    .check(jsonPath("$.id").saveAs("employeId")))
            .pause(1)
            .exec(HttpDsl.http("GET /api/employes")
                    .get("/api/employes")
                    .check(HttpDsl.status().is(200)))
            .pause(1)
            .exec(HttpDsl.http("GET /api/employes/{id}")
                    .get("/api/employes/#{employeId}")
                    .check(HttpDsl.status().is(200)));

    {
        setUp(
                scenario.injectOpen(
                        CoreDsl.rampUsers(30).during(10)
                )
        ).protocols(http)
                .assertions(
                        CoreDsl.global().responseTime().percentile(95).lt(500),
                        CoreDsl.global().successfulRequests().percent().gt(95.0)
                );
    }
}