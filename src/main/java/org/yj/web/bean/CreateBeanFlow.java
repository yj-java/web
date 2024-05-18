package org.yj.web.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CreateBeanFlow implements ApplicationContextAware {

    private String msg;

    public CreateBeanFlow() {

    }

    public CreateBeanFlow(String msg) {
        this.msg = msg;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("调用了CreateBeanFlow的aware接口！");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("调用了postConstruct方法！");
    }
}
