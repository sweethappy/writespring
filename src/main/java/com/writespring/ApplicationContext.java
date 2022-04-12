package com.writespring;

import com.youjh.Appconfig;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>(); //单例池
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); //beanDefinition

    public ApplicationContext(Class configClass){

        this.configClass=configClass;
        //解析配置类
        //ComponentScan注解->扫描路径->扫描->Beandefinition->BeanDefinitionMap
        scan(configClass);

        for(Map.Entry<String,BeanDefinition> entry : beanDefinitionMap.entrySet()){
            String beanname = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanname,beanDefinition);
                singletonObjects.put(beanname,bean);
            }
        }
    }

    public Object createBean(String beanName,BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            //依赖注入
           for(Field declaredField : clazz.getDeclaredFields()){
                if(declaredField.isAnnotationPresent(Autowired.class)){
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(instance,bean);
                }
           }

           //aware回调
           if(instance instanceof  BeanNameAware){
               ((BeanNameAware) instance).setBeanName(beanName);
           }

           //初始化
            if(instance instanceof InitializingBean){
                try {
                    ((InitializingBean) instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) {
        ComponentScan componentScan =(ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path= componentScan.value();
        path=path.replace(".","/");
        System.out.println(path);

        //扫描
        //类加载器 ： BootStrap，Ext，App => classpath
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                if (absolutePath.endsWith(".class")) {
                    String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                    String s = className.replace("\\", ".");
                    System.out.println(s);
                    try {
                        Class<?> aClass = classLoader.loadClass(s);
                        if(aClass.isAnnotationPresent(Component.class)){
                            //表示当前这个类是一个Bean
                            // 解析类=>BeanDefinition
                            Component componentAnnotation = aClass.getDeclaredAnnotation(Component.class);
                            String beanname = componentAnnotation.value();
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(aClass);
                            if(aClass.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = aClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            }
                            else {
                                beanDefinition.setScope("singleton");
                            }

                            beanDefinitionMap.put(beanname,beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public Object getBean(String beanName){
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                Object o = singletonObjects.get(beanName);
                return o;
            }
            else {
                Object bean = createBean(beanName,beanDefinition);
                return bean;
            }
        }
        else {
            throw new NullPointerException();
        }
    }
}
