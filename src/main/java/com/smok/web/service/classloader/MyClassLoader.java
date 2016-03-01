package com.smok.web.service.classloader;

/**
 * Created by liuaifen on 2016/2/24.
 */
public class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        Class c = findLoadedClass(name);
        if(c==null) {
            if(getParent()!=null) {
                c = getParent().loadClass(name);
            }else {
                c = findClass(name);
            }
        }
        return c;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
