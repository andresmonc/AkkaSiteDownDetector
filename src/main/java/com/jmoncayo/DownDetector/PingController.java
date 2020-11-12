package com.jmoncayo.DownDetector;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

public class PingController extends AbstractBehavior<PingController.Command> {
    public interface Command extends Serializable {
    }

    public class StartCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final Map<URI, String> sites;

        public StartCommand(Map<URI, String> sites) {
            this.sites = sites;
        }

        public Map<URI, String> getSites() {
            return sites;
        }
    }

    public class UpdateStatusCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final String status;
        private final URI site;

        public UpdateStatusCommand(String status, URI site) {
            this.status = status;
            this.site = site;
        }

        public String getStatus() {
            return status;
        }

        public URI getSite() {
            return site;
        }
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
                .onMessage(StartCommand.class, msg -> {
                    msg.getSites().forEach((uri, s) -> {
                        System.out.println("create ping actors");
                    });
                    return this;
                })
                .build();
    }
}
