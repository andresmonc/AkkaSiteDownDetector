package com.jmoncayo.DownDetector;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

public class PingController extends AbstractBehavior<PingController.Command> {
    public interface Command extends Serializable {
    }

    public static class StartCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final Map<URI, Integer> sites;

        public StartCommand(Map<URI, Integer> sites) {
            this.sites = sites;
        }

        public Map<URI, Integer> getSites() {
            return sites;
        }
    }

    public static class UpdateStatusCommand implements Command {
        public static final long serialVersionUID = 1L;
        private final int status;
        private final URI site;

        public UpdateStatusCommand(URI site, int status) {
            this.status = status;
            this.site = site;
        }

        public int getStatus() {
            return status;
        }

        public URI getSite() {
            return site;
        }
    }

    private static class GetStatusesCommand implements Command {
    }

    private PingController(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(PingController::new);
    }

    private Map<URI, Integer> sites;
    private Object TIMER_KEY;

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, msg -> {
                    System.out.println("start received");
                    sites = msg.getSites();
                    return Behaviors.withTimers(timer -> {
                        timer.startTimerAtFixedRate(TIMER_KEY, new GetStatusesCommand(), Duration.ofSeconds(5));
                        return this;
                    });
                })
                .onMessage(GetStatusesCommand.class, msg -> {
                    System.out.println("Getting statuses...");
                    sites.forEach((uri, s) -> {
                        ActorRef<PingBehavior.Command> pingActor = getContext().spawn(PingBehavior.create(), uri.toString().replace("/", ""));
                        pingActor.tell(new PingBehavior.PingCommand(uri, getContext().getSelf()));
                        System.out.println("created ping actor: " + pingActor.path());
                    });
                    return this;
                })
                .onMessage(UpdateStatusCommand.class, msg -> {
                    sites.put(msg.getSite(), msg.getStatus());
                    sites.forEach((uri, integer) -> System.out.println(uri + ":" + integer));
                    return this;
                })
                .build();
    }
}
