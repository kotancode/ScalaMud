package com.kotancode.scalamud.core.cmd

import akka.actor._
import akka.routing._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.kotancode.scalamud.core.lang.EnrichedWord
import com.kotancode.scalamud.core.lang.Verb

class EnrichedCommand(val words:ListBuffer[EnrichedWord], val issuer:ActorRef) {
	
	def firstVerb = {
		words collectFirst {case w if w.pos == Verb => w} orElse words.headOption match {
			case Some(ew) => ew.value
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