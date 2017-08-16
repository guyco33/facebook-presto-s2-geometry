package com.facebook.presto.s2.geometry.functions;

import com.facebook.presto.spi.block.*;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.SqlNullable;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.*;
import org.apache.commons.math3.distribution.NormalDistribution;

import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
import io.airlift.slice.Slice;


public class StatsFunctions {


    private StatsFunctions() {}

    @ScalarFunction("normal_dist_invert")
    @Description("return the inverted normal distribution")
    @SqlType(StandardTypes.DOUBLE)
    @SqlNullable
    public static Double s2Cell(
            @SqlType(StandardTypes.DOUBLE ) double value)
    {
        NormalDistribution distribution = new NormalDistribution(0,1);
        return distribution.cumulativeProbability(value);

    }



}
