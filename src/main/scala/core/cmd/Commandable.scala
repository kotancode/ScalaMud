package com.kotancode.scalamud.core.cmd

import akka.actor._
import akka.routing._
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

sealed abstract class CommandMessage
case class AddCommand(verbs:Set[String], handlerTarget:ActorRef) extends CommandMessage
case class RemoveCommand(verb:String) extends CommandMessage
case class HandleCommand(command:EnrichedCommand) extends CommandMessage


trait Commandable { 

	private val verbHandlers: HashMap[Set[String], ActorRef] = new HashMap[Set[String], ActorRef]
	
	def handleCommandMessages:akka.actor.Actor.Receive = {
		case AddCommand(verbs, handlerTarget) => {
			verbHandlers.put(verbs, handlerTarget)
		}
		
		case HandleCommand(cmd) => {
			dispatch(cmd)
		}
		
		case RemoveCommand(verb) => {
			for ( (verbset, handler) <- verbHandlers) {
				if (verbset.contains(verb)) verbHandlers.remove(verbset)
			}
		}
	}
	
	def dispatch(cmd:EnrichedCommand) = {
		println("handled a command "+ cmd +".")
		println("command's first verb: " + cmd.firstVerb)
		val targetHandlers = verbHandlers.filterKeys(key => key.contains(cmd.firstVerb))
		targetHandlers foreach {case (key, value) => value ! cmd}
	}
}