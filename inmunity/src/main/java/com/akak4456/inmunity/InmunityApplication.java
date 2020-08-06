package com.akak4456.inmunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EntityScan("com.akak4456.domain")
@EnableJpaRepositories("com.akak4456.persistent")
@SpringBootApplication(scanBasePackages = {"com.akak4456"})
public class InmunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmunityApplication.class, args);
	}
}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled",matchIfMissing=true)
class SchedulingConfiguration{
	
}