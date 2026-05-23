package br.com.fiap.calorias.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {

    @GetMapping("/hello")
    public String getHelloWorld(){
        return "Hello world!";
    }

    @GetMapping("/ola")
    public String getOlaMundo(){
        return "Olá mundo!";
    }
}
