package com.kotancode.scalamud.core.cmd

import akka.actor._
import akka.routing._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.kotancode.scalamud.core.lang.EnrichedWord
import com.kotancode.scalamud.core.lang.Verb

class EnrichedCommand(val words:ListBuffer[EnrichedWord], val issuer:ActorRef) {
	
	def firstVerb = {
		words find(_.pos == Verb) orElse words.headOption map(_.value) match {
			case Some(ew) => ew
			case _ => ""
		}
	}
	
	override def toString = "[EnrichedCommand words = " + words + ", issuer=" + issuer + "]"
	
}

object EnrichedCommand {
	def apply(words:ListBuffer[EnrichedWord], issuer:ActorRef) = {
		val cmd = new EnrichedCommand(words, issuer)
		cmd
	}
}