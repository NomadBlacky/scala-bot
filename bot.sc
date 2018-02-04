import $ivy.`com.typesafe.akka::akka-actor:2.5.9`

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Success, Failure, Try}


class MyActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case "test" => log.info("received test!")
    case "ping" => sender ! "pong"
    case _      => log.info("received unknown message!")
  }
}

@main
def main(
  s: Int @doc("Timeout seconds") = 3
): Unit = {
  val system = ActorSystem("sample")
  val actor = system.actorOf(Props[MyActor])
  implicit val timeout = Timeout(3 seconds)

  val prompt = () => {
    print("\n> ")
    scala.io.StdIn.readLine.trim
  }

  Stream.continually(prompt())
    .filterNot(_.isEmpty)
    .takeWhile(_ != "exit")
    .foreach(input => {
      Try(Await.result(actor ? input, timeout.duration)) match {
        case Success(v) =>
          println(s"Success: $v")
        case Failure(t) if t.isInstanceOf[java.util.concurrent.TimeoutException] =>
          println("timeout")
        case Failure(t) =>
          println(t.printStackTrace)
      }
    })
}
