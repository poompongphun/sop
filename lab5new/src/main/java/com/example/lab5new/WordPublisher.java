package com.example.lab5new;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    protected Word words;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public WordPublisher() {
        this.words = new Word();
        this.words.goodWords.add("happy");
        this.words.goodWords.add("enjoy");
        this.words.goodWords.add("life");
        this.words.badWords.add("fuck");
        this.words.badWords.add("olo");
    }

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String s) {
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        this.words.badWords.remove(s);
        return this.words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s) {
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public String proofSentence(@PathVariable("sentence") String s) {

        boolean isInGood = false;
        boolean isInBad = false;

        for (int i = 0; i < this.words.goodWords.size(); i++) {
            if (s.contains(this.words.goodWords.get(i))) {
                isInGood = true;
                break;
            }
        }

        for (int i = 0; i < this.words.badWords.size(); i++) {
            if (s.contains(this.words.badWords.get(i))) {
                isInBad = true;
                break;
            }
        }

        if(isInGood && isInBad) {
            // fanout
            rabbitTemplate.convertAndSend("MyFanout", "", s);
            System.out.println("Found Bad & Good Word");
            return "Found Bad & Good Word";
        } else if (isInGood) {
            // direct to bad
            // direct to good
            rabbitTemplate.convertAndSend("MyDirect", "good", s);
            System.out.println("Found Good Word");
            return "Found Good Word";
        } else if (isInBad) {
            // direct to bad
            rabbitTemplate.convertAndSend("MyDirect", "bad", s);
            System.out.println("Found Bad Word");
            return "Found Bad Word";
        }
        return "Nothing is Happen";
    }

    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence() {
        System.out.println("hi");
        return (Sentence) rabbitTemplate.convertSendAndReceive("MyDirect", "", "");
    }
}
