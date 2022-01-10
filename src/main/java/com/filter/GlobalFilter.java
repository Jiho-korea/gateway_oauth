package com.filter;

import lombok.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    private static final Logger log = LogManager.getLogger(GlobalFilter.class);
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
        	
        	ServerHttpRequest request = exchange.getRequest();
        	
//        	log.info("gateway request : " + request.getURI());        	
        	
//            logger.info("GlobalFilter baseMessage>>>>>>" + config.getBaseMessage());
//            if (config.isPreLogger()) {
//                logger.info("GlobalFilter Start>>>>>>" + exchange.getRequest());
//            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
            	
            	ServerHttpResponse response = exchange.getResponse();
            	
//					log.info("gateway response : " + response.getRawStatusCode());            	
//                if (config.isPostLogger()) {
//                    logger.info("GlobalFilter End>>>>>>" + exchange.getResponse());
//                }
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}