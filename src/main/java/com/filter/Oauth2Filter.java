package com.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.Data;
import reactor.core.publisher.Mono;

@Component
public class Oauth2Filter extends AbstractGatewayFilterFactory<Oauth2Filter.Config> {
    private static final Logger logger = LogManager.getLogger(Oauth2Filter.class);
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
            logger.info("Oauth2Filter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPreLogger()) {
                logger.info("Oauth2Filter Start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    logger.info("Oauth2Filter End>>>>>>" + exchange.getResponse());
                }
            }));
        });
    }

}
