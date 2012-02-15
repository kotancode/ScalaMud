package com.kotancode.scalamud.core.lang

import edu.stanford.nlp.ling.TaggedWord

case class PartOfSpeech
case object Noun extends PartOfSpeech
case object Verb extends PartOfSpeech
case object Adjective extends PartOfSpeech
case object DontCare extends PartOfSpeech

class EnrichedWord(value:String, tag:String) extends TaggedWord(value, tag) {	
	private var _pos: PartOfSpeech = _
	
	def pos = _pos
	def pos_= (value:PartOfSpeech):Unit = _pos = value 
	
	
	override def toString = "[EnrichedWord: word=" + value +", tag=" + tag + ", pos=" + pos + "]"
}

object EnrichedWord {
	def apply(hw: TaggedWord) = {
		val ew = new EnrichedWord(hw.value, hw.tag)		
		ew.pos = rootTypeOf(ew.tag)
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
