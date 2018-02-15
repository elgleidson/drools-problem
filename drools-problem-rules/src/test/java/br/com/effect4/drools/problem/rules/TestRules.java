package br.com.effect4.drools.problem.rules;

import br.com.effect4.drools.problem.pojos.Pojo;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRules {

    static Logger log = LoggerFactory.getLogger(TestRules.class);
    static KieContainer kieContainer;
    KieSession kieSession;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        KieServices kieServices = KieServices.Factory.get();
        kieContainer = kieServices.newKieClasspathContainer();
    }

    @Before
    public void setUp() {
        kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("log", log);
    }

    @After
    public void tearDown() {
        kieSession.dispose();
    }

    @Test
    public void testRules() {
        Pojo p = new Pojo();
        p.setNumberA(4);
        p.setNumberB(2);

        FactHandle factHandle = kieSession.insert(p);
        kieSession.fireAllRules();
        Object object = kieSession.getObject(factHandle);
        Pojo result = Pojo.class.cast(object);

        assertThat(result.getAdditionResult()).isEqualTo(result.getNumberA() + result.getNumberB());
        assertThat(result.getSubtractionResult()).isEqualTo(result.getNumberA() - result.getNumberB());
        assertThat(result.getMultiplicationResult()).isEqualTo(result.getNumberA() * result.getNumberB());
        assertThat(result.getDivisionResult()).isEqualTo(result.getNumberA() / result.getNumberB());
    }
    
}
