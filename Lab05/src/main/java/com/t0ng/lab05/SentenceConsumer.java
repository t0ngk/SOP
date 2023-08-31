package com.t0ng.lab05;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    private Sentence sentence = new Sentence();

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentence.badSentences.add(s);
        System.out.println(s);
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentence.goodSentences.add(s);
        System.out.println(s);
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentence() {
        return sentence;
    }
}
