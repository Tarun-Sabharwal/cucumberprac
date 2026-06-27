package org.example.order.common;

import jakarta.transaction.Transactional;
import java.lang.annotation.*;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Transactional
public @interface UseCase {}
