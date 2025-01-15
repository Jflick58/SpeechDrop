package edu.vanderbilt.yunyulin.speechdrop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.Utils;

import java.util.Optional;
import java.util.UUID;

public class SpeechDropVerticle extends AbstractVerticle {
    public static String VERSION;
    public static String GIT_HASH;

    @Override
    public void start() throws Exception {
        VERSION = Utils.readFileToString(vertx, "VERSION").trim();
        GIT_HASH = Utils.readFileToString(vertx, "COMMITHASH").trim();

        /*
         * Instead of loading from a JSON file (-conf config.json),
         * build a config object from environment variables and defaults.
         * 
         * Original config.example.json was:
         * {
         *   "csrfSecret": "RANDOM_STRING_HERE",
         *   "mediaUrl": "https://media.speechdrop.net/uploads/",
         *   "debugMediaDownloads": true,
         *   "purgeIntervalInSeconds": 5184000,
         *   "host": "127.0.0.1",
         *   "port": "8080"
         * }
         */

        // Generate or retrieve CSRF secret
        String csrfSecret = Optional.ofNullable(System.getenv("CSRF_SECRET"))
            .orElse(UUID.randomUUID().toString());

        // Use MEDIA_URL env var or default to the original example
        String mediaUrl = Optional.ofNullable(System.getenv("MEDIA_URL"))
            .orElse("https://media.speechdrop.net/uploads/");

        // Use DEBUG_MEDIA_DOWNLOADS env var (boolean) or default to true
        boolean debugMediaDownloads = Optional.ofNullable(System.getenv("DEBUG_MEDIA_DOWNLOADS"))
            .map(Boolean::parseBoolean)
            .orElse(true);

        // Use PURGE_INTERVAL_IN_SECONDS env var (int) or default to 5184000
        int purgeIntervalInSeconds = Optional.ofNullable(System.getenv("PURGE_INTERVAL_IN_SECONDS"))
            .map(Integer::valueOf)
            .orElse(5184000);

        // Use HOST env var or default to "127.0.0.1"
        String host = Optional.ofNullable(System.getenv("HOST"))
            .orElse("0.0.0.0");

        // Use PORT env var or default to 6901
        int port = Optional.ofNullable(System.getenv("PORT"))
            .map(Integer::valueOf)
            .orElse(8080);

        // Build the config object
        JsonObject config = new JsonObject()
            .put("csrfSecret", csrfSecret)
            .put("mediaUrl", mediaUrl)
            .put("debugMediaDownloads", debugMediaDownloads)
            .put("purgeIntervalInSeconds", purgeIntervalInSeconds)
            .put("host", host)
            .put("port", port);

        // Read static templates (main.html, room.html, about.html) from resources
        String mainPage = Utils.readFileToString(vertx, "main.html");
        String roomTemplate = Utils.readFileToString(vertx, "room.html");
        String aboutPage = Utils.readFileToString(vertx, "about.html");

        // Create HTTP server and router
        HttpServerOptions serverOptions = new HttpServerOptions()
            .setCompressionSupported(true);
        HttpServer httpServer = vertx.createHttpServer(serverOptions);
        Router router = Router.router(vertx);

        // Pass our config to the existing SpeechDropApplication constructor
        new SpeechDropApplication(vertx, config, mainPage, roomTemplate, aboutPage)
            .mount(router);

        // Finally, start the server using our environment-based port/host
        httpServer
            .requestHandler(router::accept)
            .listen(port, host);

        // Optional: log the final config or environment for debugging
        // System.out.println("SpeechDrop config: " + config.encodePrettily());
    }
}
