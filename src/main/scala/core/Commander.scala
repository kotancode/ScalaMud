package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._

import com.kotancode.scalamud.core.lang.EnrichedWord
import java.util.ArrayList
import edu.stanford.nlp.ling.Sentence
import edu.stanford.nlp.ling.TaggedWord
import edu.stanford.nlp.ling.HasWord
import edu.stanford.nlp.tagger.maxent.MaxentTagger
import scala.collection.JavaConverters._

class Commander extends Actor {
	def receive = {
		case s:String => {
			val words = s.split(" ");
			val wordList = new java.util.ArrayList[String]();
			for (elem <- words) wordList.add(elem)
		    val sentence = Sentence.toWordList(wordList);
		    val taggedSentence = Commander.tagger.tagSentence(sentence).asScala.toList
		
			var enrichedWords = new ArrayList[EnrichedWord]
		    for (tw : TaggedWord <- taggedSentence) {
		//		println(tw.value + "/" + tw.tag)
				val ew = EnrichedWord(tw)
				println(ew)
				enrichedWords.add(ew)
			}
	}
  }
}

object Commander {
	val tagger = new MaxentTagger("models/english-bidirectional-distsim.tagger")
}