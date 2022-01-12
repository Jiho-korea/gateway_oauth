package com.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpLogging;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import reactor.core.publisher.Mono;

@Component
public class Oauth2Filter extends AbstractGatewayFilterFactory<Oauth2Filter.Config> {
    private static final Logger log = LogManager.getLogger(Oauth2Filter.class);
    public Oauth2Filter() {
        super(Config.class);
    }
    
    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		return ((exchange, chain) -> {            
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
            	log.info("Oauth2Filter request header >>>>>>" + request.getHeaders());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                	log.info("Oauth2Filter response status code >>>>>>" + response.getRawStatusCode());
                	log.info("Oauth2Filter response header >>>>>>" + response.getHeaders());
                }
            }));
        });
    }

}
