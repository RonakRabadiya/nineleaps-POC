package learn.rr.microservice.supplierms.it.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

@Configuration
public class CassandraTestConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String datacenter ;

    @Value("${spring.data.cassandra.port}")
    private int port ;

    @Override
    @Bean
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean bean = new CqlSessionFactoryBean();
        bean.setContactPoints(getContactPoints());
        bean.setKeyspaceName(getKeyspaceName());
        bean.setLocalDatacenter(getLocalDataCenter());
        bean.setPort(getPort());
        return bean;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return datacenter;
    }

    @Override
    protected String getKeyspaceName() {
        return new String(keySpace);
    }

}
