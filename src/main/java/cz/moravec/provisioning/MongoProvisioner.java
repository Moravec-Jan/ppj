package cz.moravec.provisioning;

import cz.moravec.model.Measurement;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MongoProvisioner implements InitializingBean {

    private final MongoTemplate mongo;


    public MongoProvisioner(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void afterPropertiesSet() {
        if (isCollectionEmpty(Measurement.class)) {
            doProvision();
        }
    }

    private void doProvision() {
        Measurement measurement = new Measurement(10f, 15f, 30f);
        mongo.save(measurement, Measurement.COLLECTION_NAME);
    }

    private <T> boolean isCollectionEmpty(Class<T> clazz) {
        return !mongo.exists(new Query().limit(1), mongo.getCollectionName(clazz));
    }
}
