package org.lsi.usthb.datasecurity.usthb.annotation;




import org.lsi.usthb.datasecurity.usthb.domain.model.FrequencyType;
import org.lsi.usthb.datasecurity.usthb.domain.model.SecurityLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataSecurityLevel {

    SecurityLevel level() default SecurityLevel.NONE_SENSITIVE;
    FrequencyType frequency() default FrequencyType.NONE;


}
