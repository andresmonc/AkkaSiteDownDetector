package com.jmoncayo.DownDetector;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.net.URI;

public class PingBehavior extends AbstractBehavior<PingBehavior.Command> {

    public interface Command extends Serializable {
    }

    public static class PingCommand implements Command {
        private URI uri;
        private ActorRef<PingController.Command> sender;

        public PingCommand(URI uri, ActorRef<PingController.Command> sender) {
            this.uri = uri;
            this.sender = sender;
        }

        public URI getUri() {
            return uri;
        }

        public ActorRef<PingController.Command> getSender() {
            return sender;
        }
    }

    private PingBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(PingBehavior::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(PingCommand.class, msg -> {
                    System.out.println("I'm pinging");
                    SitePinger sitePinger = new SitePinger(msg.getUri());
                    msg.getSender().tell(new PingController.UpdateStatusCommand(msg.getUri(), sitePinger.pingSite()));
                    return Behaviors.stopped();
//
                })
                .build();
    }
}
