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
        HashMap<URI, Integer> urlMap = new HashMap<>();
        try {
            urlMap.put(new URI("https://www.google.com"), 0);
            urlMap.put(new URI("https://www.github.com"), 0);
            urlMap.put(new URI("https://www.instagram.com"), 0);
            urlMap.put(new URI("https://www.facebook.com"), 0);
            urlMap.put(new URI("https://www.twitter.com"), 0);
            urlMap.put(new URI("https://www.gitlab.com"), 0);
            urlMap.put(new URI("https://www.microsoft.com"), 0);
            urlMap.put(new URI("https://asjdlfkjasdfkdjafklsadf.com"), 0);
            urlMap.put(new URI("https://jmoncayo.com"), 0);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        pingSystem.tell(new PingController.StartCommand(urlMap));
    }
}
