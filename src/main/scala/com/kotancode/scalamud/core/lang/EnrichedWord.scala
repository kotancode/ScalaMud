package com.kotancode.scalamud.core.lang

import edu.stanford.nlp.ling.TaggedWord

class PartOfSpeech
case object Noun extends PartOfSpeech
case object Verb extends PartOfSpeech
case object Adjective extends PartOfSpeech
case object DontCare extends PartOfSpeech

class EnrichedWord(value:String, tag:String, val pos:PartOfSpeech) extends TaggedWord(value, tag) {		
	
	override def toString = "[EnrichedWord: word=" + value +", tag=" + tag + ", pos=" + pos + "]"
}

object EnrichedWord {
	def apply(hw: TaggedWord) = {
		val ew = new EnrichedWord(hw.value, hw.tag, rootTypeOf(hw.tag))		
		ew
	}
	
	def rootTypeOf(s:String) = {
		s match {
			case "VB" | "VBD" | "VBG" | "VBN" | "VBP" | "VBZ" => Verb
			case  "NN" | "NNS" | "NNP" | "NNPS" => Noun
			case "JJ" | "JJR" | "JJS" => Adjective
			case _ => DontCare
		}
	}
}
