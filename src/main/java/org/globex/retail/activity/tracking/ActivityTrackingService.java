package org.globex.retail.activity.tracking;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ActivityTrackingService {

    @ConfigProperty(name = "activity-tracking-service.host")
    String serviceHost;

    @Inject
    Vertx vertx;

    WebClient client;

    void onStart(@Observes StartupEvent e) {
        int port = serviceHost.contains(":") ? Integer.parseInt(serviceHost.substring(serviceHost.indexOf(":") + 1)) : 8080;
        String host = serviceHost.contains(":") ? serviceHost.substring(0, serviceHost.indexOf(":")) : serviceHost;
        WebClientOptions options = new WebClientOptions().setDefaultHost(host).setDefaultPort(port).setMaxPoolSize(100).setHttp2MaxPoolSize(100).setSsl(port == 443);
        client = WebClient.create(vertx, options);
    }

    public Uni<Integer> trackActivity(JsonObject activity) {
        return client.post("/track").sendJsonObject(activity).onItem().transform(HttpResponse::statusCode);
    }

}
