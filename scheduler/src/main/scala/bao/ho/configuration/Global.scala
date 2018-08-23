package bao.ho.configuration

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.inject.Guice

trait Global {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}
