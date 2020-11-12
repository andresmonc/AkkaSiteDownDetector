package com.jmoncayo;

import akka.actor.typed.ActorSystem;
import com.jmoncayo.DownDetector.PingController;
import com.jmoncayo.ProbablyPrimeGenerator.FirstSimpleBehavior;
import com.jmoncayo.ProbablyPrimeGenerator.ManagerBehavior;
import com.jmoncayo.RacingSimulation.RaceControllerBehavior;
import com.jmoncayo.RacingSimulation.RacerBehavior;

public class Main {
    public static void main(String[] args) {
        // Start Ping System
        ActorSystem<PingController.Command> pingSystem = ActorSystem.create(PingController.create(), "ping-system");


    }
}
