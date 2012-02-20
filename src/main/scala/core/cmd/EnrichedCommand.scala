package com.kotancode.scalamud.core.cmd

import akka.actor._
import akka.routing._
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import com.kotancode.scalamud.core.lang.EnrichedWord
import com.kotancode.scalamud.core.lang.Verb

class EnrichedCommand(val words:HashSet[EnrichedWord], val issuer:ActorRef) {
	
	def firstVerb = {
		if (words.size == 1)
			words.head.value
		else {
			val outWords = words.filter( word => word.pos == Verb)
			if (outWords == Set.empty) 
				words.head.value
			else
				outWords.head.value	
		}
	}
	
	override def toString = "[EnrichedCommand words = " + words + ", issuer=" + issuer + "]"
	
}

object EnrichedCommand {
	def apply(words:HashSet[EnrichedWord], issuer:ActorRef) = {
		val cmd = new EnrichedCommand(words, issuer)
		cmd
	}
}