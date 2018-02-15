package br.com.effect4.drools.problem.config;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {
	
	private Logger log = LoggerFactory.getLogger(DroolsConfiguration.class);

	@Autowired
    private DroolsProperties droolsProperties;
	
	@Bean
	public KieServices getKieServices() {
		log.debug("getting KieServices...");
		KieServices kieServices = KieServices.Factory.get();
		log.debug("KieServices");
		return kieServices;
	}
	
	@Bean
	public KieContainer getKieContainer() {
        log.debug("getting ReleaseId {}-{}:{}...", droolsProperties.getGroupId(), droolsProperties.getArtifactId(), droolsProperties.getVersion());
        ReleaseId releaseId = getKieServices().newReleaseId(droolsProperties.getGroupId(), droolsProperties.getArtifactId(), droolsProperties.getVersion());
        log.debug("ReleaseId got!");

        log.debug("getting KieContainer from releaseId...");
        KieContainer kieContainer = getKieServices().newKieContainer(releaseId);
        log.debug("KieContainer got!");

		return kieContainer;
	}

}
