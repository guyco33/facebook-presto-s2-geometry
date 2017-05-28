/**
 * Created by guycohen on 18/05/2017.
 */
package com.facebook.presto.google.s2.functions;

import com.facebook.presto.spi.Plugin;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class GoogleS2FunctionsPlugin
        implements Plugin
{
    @Override
    public Set<Class<?>> getFunctions()
    {
        return ImmutableSet.<Class<?>>builder()
                .add(GoogleS2Functions.class)
                .build();
    }
}
