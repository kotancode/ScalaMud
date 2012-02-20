package com.kotancode.scalamud.core.cmd

import akka.actor._
import akka.routing._

import com.kotancode.scalamud.core.ServerStats
import com.kotancode.scalamud.core.TextMessage

class WizardCommandLib extends Actor {
	override def receive = handleWizardCommands
	
	def handleWizardCommands:Receive = {
		case AttachCommandLib => {
			attachToSender(sender)
		}
		case cmd:EnrichedCommand if cmd.firstVerb == "uptime" => {
			val now = System.nanoTime()
			val diffSeconds = (now - ServerStats.startTime) / 1000000000
			val diffMins = diffSeconds / 60
			val diffRealSecs = diffSeconds % 60
			cmd.issuer ! TextMessage("Server has been up for %d mins %s secs.".format(diffMins, diffRealSecs))
		}
	}
	
	def attachToSender(sender:ActorRef) = {
		sender ! AddCommand(Set("uptime"), self)
	}
}