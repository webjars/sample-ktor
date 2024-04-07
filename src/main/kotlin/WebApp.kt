import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import kotlinx.html.*
import org.webjars.WebJarVersionLocator

fun main() {
    val WEBJARS_PATH = "/webjars"
    fun WebJarVersionLocator.route(webJar: String, path: String) = run {
        path(webJar, path)?.let {
            "$WEBJARS_PATH/$it"
        }
    }

    val server = embeddedServer(CIO, port = 8080) {
        val webJarVersionLocator = WebJarVersionLocator()
        install(CallLogging)
        routing {
            staticResources(WEBJARS_PATH, "META-INF/resources/webjars")
            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    head {
                        title {
                            +"hello, world"
                        }
                        link(webJarVersionLocator.route("bootstrap", "css/bootstrap.min.css"), rel = "stylesheet")
                        script("text/javascript", webJarVersionLocator.route("bootstrap", "js/bootstrap.min.js")) { }
                    }
                    body {
                        nav("navbar navbar-expand-lg bg-body-tertiary") {
                            div("container-fluid") {
                                a {
                                    classes = setOf("navbar-brand")
                                    href = "#"
                                    +"Ktor WebJars Sample"
                                }
                            }
                        }

                        div("position-relative m-4") {
                            button {
                                classes = setOf("btn btn-primary position-absolute top-0 start-50 translate-middle-x")
                                attributes["data-bs-toggle"] = "modal"
                                attributes["data-bs-target"] = "#exampleModal"

                                +"Launch demo modal"
                            }
                        }

                        div("modal fade") {
                            id = "exampleModal"
                            tabIndex = "-1"
                            attributes["aria-labelledby"] = "exampleModalLabel"
                            attributes["aria-hidden"] = "true"
                            div("modal-dialog") {
                                div("modal-content") {
                                    div("modal-header") {
                                        h1("modal-title fs-5") {
                                            id = "exampleModalLabel"
                                            +"Modal title"
                                        }
                                        button {
                                            classes = setOf("btn-close")
                                            attributes["data-bs-dismiss"] = "modal"
                                            attributes["aria-label"] = "Close"
                                        }
                                    }
                                    div("modal-body") {
                                        +"hello, world"
                                    }
                                    div("modal-footer") {
                                        button {
                                            classes = setOf("btn btn-secondary")
                                            attributes["data-bs-dismiss"] = "modal"
                                            +"Close"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    server.start(wait = true)
}
