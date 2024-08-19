package com.manas.custom_spring_initilizer.constants;

import java.util.ArrayList;
import java.util.TreeMap;

public class ApplicationPropertiesConstants {

    public static final TreeMap<String, ArrayList<String>> map;

    static {
        map = new TreeMap<>() {
            {
                put("data-redis", new ArrayList<>() {
                    {
                        add("spring.data.redis.host=<Host connection>");
                        add("spring.data.redis.port=<Set Port>");
                        add("spring.data.redis.password=<Set Connection Password>");
                    }
                });

                put("data-mongodb", new ArrayList<>() {
                    {
                        add("spring.data.mongodb.uri=<Connection URI>");
                        add("spring.data.mongodb.database=<Database Name>");
                    }
                });

                put("data-jpa", new ArrayList<>() {
                    {
                        add("server.port=<Port Number>");
                        add("spring.datasource.url=<Datasource uri>");
                        add("spring.datasource.username=<Username>");
                        add("spring.datasource.password=<Password>");
                        add("spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver");
                        add("spring.jpa.show-sql=true");
                        add("spring.jpa.hibernate.ddl-auto=update");
                    }
                });

                put("cloud-gateway", new ArrayList<>() {
                    {
                        add("eureka.client.service-url.defaultZone=<Eureka URL>");
                        add("spring.cloud.gateway.routes[0].id=<Service registered Name>");
                        add("spring.cloud.gateway.routes[0].uri=<Service uri>");
                        add("spring.cloud.gateway.routes[0].predicates[0]=<Service Endpoints>");
                    }
                });
            }
        };
    }
}
