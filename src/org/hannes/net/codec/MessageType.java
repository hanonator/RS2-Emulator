package org.hannes.net.codec;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.hannes.net.MessageLength;

@Retention(RetentionPolicy.RUNTIME)
public @interface MessageType {

	MessageLength value() default MessageLength.FIXED;

}