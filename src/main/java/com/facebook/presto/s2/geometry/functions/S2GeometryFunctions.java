package com.facebook.presto.s2.geometry.functions;

/**
 * Created by guycohen on 18/05/2017.
 */
import com.facebook.presto.spi.block.*;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.SqlNullable;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.*;
import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2Cap;
import com.google.common.geometry.S1Angle;
import com.google.common.geometry.S2RegionCoverer;

import io.airlift.slice.Slice;

import java.util.List;
import java.util.ArrayList;


import static io.airlift.slice.Slices.utf8Slice;
import static java.lang.Math.toIntExact;

public class S2GeometryFunctions {


    private S2GeometryFunctions() {}

    @ScalarFunction("s2_cell")
    @Description("Returns cell token for latitude,longitude degrees and level")
    @SqlType(StandardTypes.VARCHAR)
    @SqlNullable
    public static Slice s2Cell(
            @SqlType(StandardTypes.DOUBLE ) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon,
            @SqlType(StandardTypes.INTEGER) long level)
    {
        if (level<0 || level>30) return null;
        return (utf8Slice(S2CellId.fromLatLng(S2LatLng.fromDegrees(lat,lon)).parent(toIntExact(level)).toToken()));
    }

    @ScalarFunction("s2_cell")
    @Description("Returns cell token for latitude,longitude degrees in level 30")
    @SqlType(StandardTypes.VARCHAR)
    @SqlNullable
    public static Slice s2Cell(
            @SqlType(StandardTypes.DOUBLE ) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon)
    {
        return s2Cell(lat,lon,30);
    }

    @ScalarFunction("s2_level")
    @Description("Returns the level of cell token")
    @SqlType(StandardTypes.INTEGER)
    @SqlNullable
    public static Long s2Level(
            @SqlType(StandardTypes.VARCHAR) Slice celltoken)
    {
        return Long.valueOf(S2CellId.fromToken(celltoken.toStringUtf8()).level());
    }

    @ScalarFunction("s2_distance")
    @Description("Returns the distance in meter from cell token to point")
    @SqlType(StandardTypes.DOUBLE)
    @SqlNullable
    public static Double s2_distance(
            @SqlType(StandardTypes.VARCHAR) Slice celltoken,
            @SqlType(StandardTypes.DOUBLE ) double lat,
            @SqlType(StandardTypes.DOUBLE) double lon)
    {
        return S2CellId.fromToken(celltoken.toStringUtf8()).toLatLng().getEarthDistance(S2LatLng.fromDegrees(lat,lon));
    }

    @ScalarFunction("s2_cell_neighbours")
    @Description("Returns cell token neighbours in specific level")
    @SqlType("array(varchar)")
    @SqlNullable
    public static Block s2CellNeighbours(
            @SqlType(StandardTypes.VARCHAR) Slice celltoken,
            @SqlType(StandardTypes.INTEGER) long level)
    {
        if (level<0 || level>30) return null;

        List<S2CellId> output = new ArrayList<S2CellId>();
        S2CellId cellid = S2CellId.fromToken(celltoken.toStringUtf8());
        cellid.getAllNeighbors(toIntExact(level), output);
        return cellsArrayBlock(output);

    }

    @ScalarFunction("s2_cell_cover")
    @Description("Returns cell tokens in a meter radius for a specific level")
    @SqlType("array(varchar)")
    @SqlNullable
    public static Block s2CellCover(
            @SqlType(StandardTypes.VARCHAR) Slice celltoken,
            @SqlType(StandardTypes.DOUBLE) double radius,
            @SqlType(StandardTypes.INTEGER) long level)
    {
        if (level<0 || level>30) return null;

        S2CellId cellid = S2CellId.fromToken(celltoken.toStringUtf8());
        S2Cap circle = S2Cap.fromAxisAngle(cellid.toLatLng().toPoint(), S1Angle.degrees(360 * radius/1000 / (2 * Math.PI * 6371.01)));
        ArrayList<S2CellId> output = new ArrayList<S2CellId>();
        S2RegionCoverer.getSimpleCovering(circle, cellid.toLatLng().toPoint(), toIntExact(level) ,output);
        return cellsArrayBlock(output);
    }

    public static Block cellsArrayBlock(List<S2CellId> cells) {
        Slice[] slices = new Slice[cells.size()];
        for (int i = 0; i < cells.size(); i++)
            slices[i] = utf8Slice(cells.get(i).toToken());

        return (new SliceArrayBlock(slices.length, slices,true));
    }


}
