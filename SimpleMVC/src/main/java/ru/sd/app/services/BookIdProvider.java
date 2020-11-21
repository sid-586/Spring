package ru.sd.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.sd.web.dto.Book;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BookIdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = Logger.getLogger(BookIdProvider.class);

    public Integer provideId(Book book) {

        return Math.abs(book.hashCode() / 2);
    }

    public void initBookIdProvider() {
        logger.info("INITBookIdProvider");
    }

    public void destroyBookIdProvider() {
        logger.info("DESTROYBookIdProvider");
    }

    public void defaultInit() {
        logger.info("defaultINIT in BookIdProvider");
    }

    public void defaultDestroy() {
        logger.info("defaultDESTROY in BookIdProvider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("BookIdProvider - afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("BookIdProvider - DESTROY");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBEFOREInitialization invoked by bean " + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAFTERInitialization invoked by bean " + beanName);
        return null;
    }

    @PostConstruct
    public void postConstructBookIdProvider() {
        logger.info("PostConstruct");
    }

    @PreDestroy
    public void preDestroyBookIdProvider() {
        logger.info("PreDestroy");
    }

}
