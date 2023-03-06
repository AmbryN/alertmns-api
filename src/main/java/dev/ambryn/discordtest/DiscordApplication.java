package dev.ambryn.discordtest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Application;
import org.slf4j.LoggerFactory;

@ApplicationPath("/api")
public class DiscordApplication extends Application {
}