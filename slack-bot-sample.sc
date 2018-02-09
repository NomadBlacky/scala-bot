import $ivy.`com.github.gilbertw1::slack-scala-client:0.2.2`

import akka.actor._
import akka.event.Logging
import slack.rtm._

implicit val system = ActorSystem("slack-bot-sample")

val token = scala.sys.env.lift("SLACK_BOT_TOKEN")
  .getOrElse(throw new IllegalStateException("Token not found"))
val client = SlackRtmClient(token)

val state = client.state
val channelName = "test"
val channelId = state.getChannelIdForName(channelName)
  .getOrElse(throw new IllegalStateException(s"Channnel '$channelName' not found"))

println(channelId)

client.sendMessage(channelId, "Hi")
