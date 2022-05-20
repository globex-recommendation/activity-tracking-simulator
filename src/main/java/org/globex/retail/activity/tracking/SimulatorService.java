package org.globex.retail.activity.tracking;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SimulatorService {

    private static final Logger log = LoggerFactory.getLogger(SimulatorService.class);

    @Inject
    ActivityTrackingService activityTrackingService;

    public Uni<Integer> simulate(int count) {
        log.info("Simulating " + count + " activities");
        Products products = new Products();
        String[][] group = products.group();
        List<Uni<Integer>> unis = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            unis.add(activityTrackingService.trackActivity(generateActivity(products.product(group))));
        }
        return Uni.combine().all().unis(unis).combinedWith(l -> {
            log.info("Finished simulating " + count + " activities");
            return count;
        });
    }

    private JsonObject generateActivity(String product) {
        return new JsonObject().put("idSite", "globex-retail")
                .put("activity", new JsonObject()
                        .put("userId", UUID.randomUUID().toString())
                        .put("url", "https://globex-retail.com/product/" + product + "/details")
                        .put("rand", UUID.randomUUID().toString())
                        .put("type", "like"))
                .put("userInfo", new JsonObject()
                        .put("localTime",transformEpochToDateTime(System.currentTimeMillis())))
                .put("actionInfo", new JsonObject()
                        .put("productId", product));
    }

    private String transformEpochToDateTime(long epoch) {

        ZonedDateTime ldt = Instant.ofEpochMilli(epoch).atZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        return ldt.format(formatter);
    }
}
