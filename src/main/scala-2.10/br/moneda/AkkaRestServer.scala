import akka.actor.ActorSystem
object AkkaRestServer  extends App{
  implicit val system = ActorSystem("RadixNettyServer")

}

