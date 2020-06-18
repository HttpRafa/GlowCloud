package de.rafadev.glowcloud.master.module.loader;
//------------------------------
//
//Coded by RafaDev
//Date > 05.05.2020
//Projekt > Communication-Server
//
//------------------------------


import java.net.URL;
import java.net.URLClassLoader;

public class CloudModuleLoader extends URLClassLoader {

    public CloudModuleLoader(URL[] urls) {
        super(urls);
    }

    public Class<?> getClass(String classpath) throws ClassNotFoundException {
        Class<?> loadClass = super.loadClass(classpath);
        return loadClass;
    }

}
