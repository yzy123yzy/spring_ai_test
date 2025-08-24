package org.example.spring_ai_test.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @description:
 * @author: yzy
 * @date: 2025/7/2 20:19
 */
@RestController
public class FirstController {


    private final OllamaChatModel chatModel;

    @Autowired
    public FirstController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping(value = "/ai/generate", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.chatModel.stream(message);
    }

    @GetMapping(value="stream/print")
    public void printStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        printStream();
    }
    static class Publisher{
        Subscriber subscriber;
        Publisher(Subscriber subscriber){
            this.subscriber = subscriber;
        }
        void print(){
            int i = 0;
            while(i++ < 30){
                String s = String.valueOf(i);
                subscriber.onNext(k -> {
                    System.out.println("hello " + k);
                }, s);
            }
        }
    }
    static class Subscriber{
        void onNext(Consumer<String> consumer, String s){
            consumer.accept(s);
        }
    }
}