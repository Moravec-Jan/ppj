package cz.moravec.config;

import cz.moravec.provisioning.MongoProvisioner;
import cz.moravec.provisioning.MySqlProvisioner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ProvisioningConfig {

    @Profile({"test", "devel"})
    @Bean
    public MySqlProvisioner sqlProvisioner() {
        return new MySqlProvisioner();
    }

    @Profile("devel")
    @Bean
    public MongoProvisioner mongoProvisioner(MongoTemplate mongo) {
        return new MongoProvisioner(mongo);
    }

}
