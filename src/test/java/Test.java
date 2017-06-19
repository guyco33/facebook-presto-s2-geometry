/**
 * Created by guycohen on 18/05/2017.
 */
import com.facebook.presto.s2.geometry.functions.S2GeometryFunctions;
import com.facebook.presto.s2.geometry.functions.S2Helper;
import com.google.common.geometry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.toIntExact;

public class Test {

    public static void main(String[] args) {

        //System.out.println(S2GeometryFunctions.s2ParseWkt("POLYGON((  35.2874851279194  33.0771839289384 , 35.2997589163715 33.0788380879624,35.301475530141 33.0697038650441,35.2871418051654 33.0690565219247,35.2874851279194 33.0771839289384))"));
        S2Region r = S2Helper.parseWktPolygon("POLYGON((  35.2874851279194  33.0771839289384 , 35.2997589163715 33.0788380879624,35.301475530141 33.0697038650441,35.2871418051654 33.0690565219247,35.2874851279194 33.0771839289384))");
        System.out.println(r.toString());
        S2CellUnion a = S2Helper.cover(new S2Polygon(),15);
        System.out.println(a.contains(S2CellId.fromToken("14e64b2c")));

//
//        System.out.println(S2GeometryFunctions.s2Cell(32.186451,34.862229,15));
//        ArrayList<S2Point> points = new ArrayList<S2Point>();
//        points.add(S2LatLng.fromDegrees(32.186451, 34.862229).toPoint());
//        points.add(S2LatLng.fromDegrees(32.185324, 34.862880).toPoint());
//        points.add(S2LatLng.fromDegrees(32.185863, 34.861341).toPoint());
//        S2Loop loop = new S2Loop(points);
//
//        S2PolygonBuilder polyBuilder = new S2PolygonBuilder();
//        polyBuilder.addLoop(loop);
//        S2Region region = polyBuilder.assemblePolygon();
//
//        S2RegionCoverer coverer = new S2RegionCoverer();
//        coverer.setMinLevel(10);
//        coverer.setMaxLevel(18);
//        //ArrayList<S2CellId> covering = new ArrayList<S2CellId>();
//        //coverer.getCovering(region);
//        //System.out.println(covering);
//
//        S2CellUnion cellUnion = coverer.getCovering(region);
//        //List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
//        //Iterator<S2CellId> iterator = cellUnion.iterator();
////        while(iterator.hasNext()) {
////            S2CellId cellid = iterator.next();
////            System.out.print(cellid.level()+" ");
////            System.out.println(cellid.toToken());
////        }
//        for (S2CellId cellid: cellUnion.cellIds()) {
//            System.out.print(cellid.level()+" ");
//            System.out.println(cellid.toToken());
//        }
    }}
