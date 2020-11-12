package com.jmoncayo.DownDetector;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class PingController extends AbstractBehavior<PingController.Command> {
    public interface Command extends Serializable {
    }

    public static class StartCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final Map<URI, String> sites;

        public StartCommand(Map<URI, String> sites) {
            this.sites = sites;
        }

        public Map<URI, String> getSites() {
            return sites;
        }
    }

    public static class UpdateStatusCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final String status;
        private final URI site;

        public UpdateStatusCommand(URI site, String status) {
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

    private final Map<URI, String> sites = new HashMap<>();

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, msg -> {
                    System.out.println("start received");
                    msg.getSites().forEach((uri, s) -> {
                        ActorRef<PingBehavior.Command> pingActor = getContext().spawn(PingBehavior.create(), uri.toString().replace("/",""));
                        pingActor.tell(new PingBehavior.PingCommand(uri, getContext().getSelf()));
                        System.out.println("created ping actor: " + pingActor.path());
                    });
                    return this;
                })
                .onMessage(UpdateStatusCommand.class, msg -> {
                    sites.put(msg.getSite(), msg.getStatus());
                    return this;
                })
                .build();
    }
}
