package cz.moravec.config;

import cz.moravec.model.Measurement;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class MongoConfig implements InitializingBean {
    private static final String EXPIRATION_INDEX = "EXPIRATION_INDEX";

    private final WeatherProperties properties;

    private final MongoTemplate mongo;

    public MongoConfig(MongoTemplate mongo, WeatherProperties properties) {
        this.mongo = mongo;
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            mongo.indexOps(Measurement.COLLECTION_NAME).ensureIndex(new Index(Measurement.CREATION_TIME_NAME, Sort.Direction.ASC).expire(properties.getExpireAfterSeconds()).named(EXPIRATION_INDEX));
        } catch (UncategorizedMongoDbException e) {
            // using exception because there is no method for getting index expiration time
            // so we cannot find out if index has same expiration option as new
            // and we don't want drop index every time when app is started
            mongo.indexOps(Measurement.COLLECTION_NAME).dropIndex(EXPIRATION_INDEX);
            mongo.indexOps(Measurement.COLLECTION_NAME).ensureIndex(new Index().on(EXPIRATION_INDEX, Sort.Direction.ASC).expire(properties.getExpireAfterSeconds()));
        }

    }

}
