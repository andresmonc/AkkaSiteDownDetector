package com.jmoncayo;

import akka.actor.typed.ActorSystem;
import com.jmoncayo.DownDetector.PingController;

public class Main {
    public static void main(String[] args) {
        // Start Ping System
        ActorSystem<PingController.Command> pingSystem = ActorSystem.create(PingController.create(), "ping-system");


    }
}
