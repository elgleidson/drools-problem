package br.com.effect4.drools.problem.api;

import br.com.effect4.drools.problem.pojos.Pojo;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DroolsResource {
	
	private Logger log = LoggerFactory.getLogger(DroolsResource.class);

	@Autowired
	private KieContainer kieContainer;

	@GetMapping(value = "/pojo")
	public Pojo pojo(@RequestParam(name = "a", required = true) int a, @RequestParam(name = "b", required = true) int b) {
        log.debug("creating Pojo with a={}, b={}", a, b);
        Pojo pojo = new Pojo();
        pojo.setNumberA(a);
        pojo.setNumberB(b);
        log.debug("pojo created!");

        //checkRulesLoaded();

	    log.debug("getting KieSession...");
        KieSession kieSession = this.kieContainer.newKieSession();
        log.debug("KieSession got!");

        log.debug("inserting global logger...");
        kieSession.setGlobal("log", log);
        log.debug("global logger inserted!");

        log.debug("Inserting pojo...");
        FactHandle factHandle = kieSession.insert(pojo);
        log.debug("pojo inserted!");

        log.debug("firing rules...");
        kieSession.fireAllRules();
        log.debug("rules fired!");

        log.debug("getting results...");
        Object result = kieSession.getObject(factHandle);
        log.debug("results got!");

        log.debug("returning results...");
        return (Pojo) result;
	}

}
