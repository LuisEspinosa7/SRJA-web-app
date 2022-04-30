/**
 * 
 */
package com.sevensoftware.domotica.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.sevensoftware.domotica.entities.Greeting;
import com.sevensoftware.domotica.entities.HelloMessage;


/**
 * @author LUIS
 *
 */
@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
