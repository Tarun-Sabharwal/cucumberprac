package org.example.inventory.common;

import java.lang.annotation.*;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface DomainService {}
