package com.facebook.presto.s2.geometry.functions;

import com.google.common.geometry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.toIntExact;
import static java.util.regex.Pattern.matches;

/**
 * Created by guycohen on 14/06/2017.
 */
public class S2Helper {

    public static S2Polygon parseWktPolygon(String polygon) {
        String coord = "[-]?[0-9]+[.]?[-]?[0-9]+";
        String spaces = "(\\s)+";
        String ospaces = "(\\s)*";
        String point = ospaces+coord+spaces+coord+ospaces;

        //Pattern pattern = Pattern.compile("POLYGON[(][(]("+number+","+number+"){3,}?[)][)]");
        String pattern = "POLYGON[(][(]"+point+"(,"+point+"){2,}?[)][)]";
        System.out.println(pattern);
        polygon = polygon.toUpperCase();
        Boolean match = Pattern.matches(pattern, polygon);
        System.out.println(match);
        if (match) {
            ArrayList<S2Point> points = new ArrayList<S2Point>();
            String[] items = Pattern.compile(ospaces+","+ospaces).split(polygon.replace("POLYGON((","").replace("))",""));
            for (String item: items) {
                String[] nums = item.trim().split(spaces);
                points.add(S2LatLng.fromDegrees(Double.parseDouble(nums[1]), Double.parseDouble(nums[0])).toPoint());
            }
            S2Loop loop = new S2Loop(points);
            S2PolygonBuilder polyBuilder = new S2PolygonBuilder();
            polyBuilder.addLoop(loop);
            return polyBuilder.assemblePolygon();
        }
        else {
            return new S2Polygon();
        }
    }

    public static S2CellUnion cover(S2Polygon polygon, int minLevel, int maxLevel) {
        if (polygon == null || polygon.numLoops()==0) return null;
        S2RegionCoverer coverer = new S2RegionCoverer();
        coverer.setMinLevel(minLevel);
        coverer.setMaxLevel(maxLevel);
        return coverer.getCovering(polygon);
    }

    public static S2CellUnion cover(S2Polygon polygon, int level) {
        return cover(polygon,level,level);
    }

    public static List<S2CellId> getChilds(S2CellId cellid) {
        List<S2CellId> childs = new ArrayList<S2CellId>();
        for (S2CellId c = cellid.childBegin(); !c.equals(cellid.childEnd()); c = c.next())
            childs.add(c);
        return childs;
    }
}
