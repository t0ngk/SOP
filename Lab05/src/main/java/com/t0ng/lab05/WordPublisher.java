package com.t0ng.lab05;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    protected Word words = new Word();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable("word") String word) {
        words.badWords.add(word);
        return words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable String word){
        words.badWords.remove(word);
        return words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable String word){
        words.goodWords.add(word);
        return words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable String word){
        words.goodWords.remove(word);
        return words.goodWords;
    }

    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence() {
        return (Sentence) rabbitTemplate.convertSendAndReceive("Direct", "GetQueue", "");
    }

    @RequestMapping(value = "/proof/{sentence}")
    public String proofSentence(@PathVariable String sentence){
        boolean goodWordFound = false;
        boolean badWordFound = false;

        for (String word: words.goodWords) {
            if (sentence.contains(word)) {
                goodWordFound = true;
                break;
            }
        }

        for (String word: words.badWords) {
            if (sentence.contains(word)) {
                badWordFound = true;
                break;
            }
        }

        if (goodWordFound && badWordFound) {
            rabbitTemplate.convertAndSend("Fanout","" , sentence);
            return "Found Bad & Good Word";
        } else if (goodWordFound) {
            rabbitTemplate.convertAndSend("Direct", "good", sentence);
            return "Found Good Word";
        } else if (badWordFound) {
            rabbitTemplate.convertAndSend("Direct", "bad", sentence);
            return "Found Bad Word";
        } else {
            return "Word Not Found";
        }
    }
}
