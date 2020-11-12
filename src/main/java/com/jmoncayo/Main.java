package com.jmoncayo;

import akka.actor.typed.ActorSystem;
import com.jmoncayo.DownDetector.PingController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Start Ping System
        ActorSystem<PingController.Command> pingSystem = ActorSystem.create(PingController.create(), "ping-system");
        HashMap<URI,String> urlMap = new HashMap<>();
        try{
            urlMap.put(new URI("https://www.google.com"),"");
            urlMap.put(new URI("https://www.facebook.com"),"");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        pingSystem.tell(new PingController.StartCommand(urlMap));
    }
}
