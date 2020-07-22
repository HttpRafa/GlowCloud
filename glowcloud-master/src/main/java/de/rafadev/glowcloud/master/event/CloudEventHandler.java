package de.rafadev.glowcloud.master.event;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 10:44
// In the project GlowCloud
//
//------------------------------

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CloudEventHandler {}
