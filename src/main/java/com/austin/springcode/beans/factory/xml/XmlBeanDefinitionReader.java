package com.austin.springcode.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.austin.springcode.beans.factory.config.BeanDefinition;
import com.austin.springcode.beans.factory.support.BeanDefinitionRegistry;
import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.config.BeanReference;
import com.austin.springcode.beans.PropertyValue;
import com.austin.springcode.beans.factory.support.AbstractBeanDefinitionReader;
import com.austin.springcode.core.io.Resource;
import com.austin.springcode.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: XmlBeanDefinitionReader
 * @author: zqz
 * @date: 2023/7/30 14:43
 */

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            //判断元素
            if (!(childNodes.item(i) instanceof Element)) continue;
            //判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) continue;

            //解析标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            //增加对init-method、destroy-method的读取
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");

            //获取Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            //优先级id>name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                //lowerFirst就是去找第一位的
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            //定义bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            //额外设置到beanDefinition中
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            //读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                //判断元素
                if (!(childNodes.item(i) instanceof Element)) continue;
                //判断对象
                if (!"property".equals(childNodes.item(i).getNodeName())) continue;
                //解析标签property
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                //获取属性值 引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                //创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, attrValue);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            //最后注册bean
            getRegistry().registerBeanDefinition(beanName, beanDefinition);

        }
    }
}
