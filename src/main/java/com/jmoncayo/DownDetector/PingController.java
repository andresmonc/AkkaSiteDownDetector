package com.jmoncayo.DownDetector;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;

public class PingController extends AbstractBehavior<PingController.Command> {
    public interface Command extends Serializable {
    }

    public class StartCommand implements Command {
        public static final long serialVersionUID = 1L;
    }

    private PingController(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(PingController::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, msg ->{
                    return this;
                })
                .build();
    }
}
