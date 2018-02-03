import $ivy.`com.typesafe.akka::akka-actor:2.5.9`

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class MyActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("received test!")
    case _      => log.info("received unknown message!")
  }
}

@main
def main(): Unit = {
  val system = ActorSystem("sample")
  val actor = system.actorOf(Props[MyActor])

  Stream.continually {
    print("\n> ")
    val input = scala.io.StdIn.readLine.trim
    if (!input.isEmpty)
      actor ! input
    input
  }.takeWhile(_ != "exit").foreach(_ => ())
}
