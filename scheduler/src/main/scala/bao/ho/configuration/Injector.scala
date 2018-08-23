package bao.ho.configuration

import com.google.inject.Guice

trait Injector {
  val injector = Guice.createInjector(new Module)
}
