import java.util.HashMap;
import java.util.Map;


/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer extends MapServer {
////    private int depth;
//    private double lonDpp;
////    private String[][] renderGgrid;
//    private double rasterUlLlon;
//    private double rasterLrLlon;
////    private int k;



    public Rasterer() {
        // YOUR CODE HERE




    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        double ullon = params.get("ullon");
        double lrlon = params.get("lrlon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double weidth = params.get("w");
        double lonDPP = (lrlon - ullon) / weidth;
        int depth = (int) (Math.log((ROOT_ULLON - ROOT_LRLON) * (-1) / (lonDPP * TILE_SIZE))
                / (Math.log(2.0))) + 1;
        if (depth > 7) {
            depth = 7;
        }
        int k = (int) Math.pow(2, depth) - 1;
        double[] arrayRasterUulLlon = getRasUllon(ullon, k);
        double rasterUlLlon = arrayRasterUulLlon[0];
        int xFirst = (int) arrayRasterUulLlon[1];
        double[] arrayRasterLrLon = getRasLrlon(lrlon, k);
        double rasterLrLon = arrayRasterLrLon[0];
        int xEnd = (int) arrayRasterLrLon[1];
        double[] arrayRasterUlLat = getRasUllat(ullat, k);
        double rasterUlLat = arrayRasterUlLat[0];
        int yFirst = (int) arrayRasterUlLat[1];
        double[] arrayRasterLrLat = getRasLrlat(lrlat, k);
        double rasterLrlat = arrayRasterLrLat[0];
        int yEnd = (int) arrayRasterLrLat[1];
        String[][] renderGrid = (getRenderGrid(xFirst, xEnd, yFirst, yEnd, depth));
        boolean querySuccess = true;
        if (ullon > lrlon || ullat < lrlat || ullon < ROOT_ULLON || ullat > ROOT_ULLAT) {
            querySuccess = false;
        }


//        System.out.println("raster_ul_lon: " + raster_ul_lon);
//        System.out.println("raster_lr_lon: " + raster_lr_lon);
//        System.out.println("raster_ul_lat: " +raster_ul_lat);
//        System.out.println("raster_lr_lat: " + raster_lr_lat);
//        System.out.println("render_grid: " + render_grid);
//
//        System.out.println("depth: " + depth);
//        System.out.println(x_first);
//        System.out.println(x_end);
//        System.out.println(y_first);
//        System.out.println(y_end);
//        System.out.println(LonDPP);
//        System.out.println(k);



        Map<String, Object> results = new HashMap<>();
        results.put("raster_ul_lon", rasterUlLlon);
        results.put("depth", depth);
        results.put("raster_lr_lon", rasterLrLon);
        results.put("raster_lr_lat", rasterLrlat);
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("query_success", querySuccess);

//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
//        System.out.println(results);
        return results;
    }
    /** getting the raster_ul_lon by passing the upper left longtitude and k:  */
    public double[] getRasUllon(double a, double kForUllon) {
        double ulLon = ROOT_ULLON;
        if (a <= ROOT_ULLON) {
            return new double[] {ROOT_ULLON, 0.0};
        }
        double constantUllon = (ROOT_LRLON - ROOT_ULLON) / (kForUllon + 1);
        for (int i = 0; i <= kForUllon; i++) {
            if (a >= ulLon && a < ulLon + constantUllon) {
                return new double[] {ulLon, i};
            }
            ulLon = ulLon + constantUllon;
        }
        return new double[] {ulLon};
    }

    /** getting the raster_lr_lon by passing the lower right longtitude */
    public double[] getRasLrlon(double a, double kForLrlon) {
        double lrLon = ROOT_ULLON;
        if (a >= ROOT_LRLON) {
            return new double[] {ROOT_LRLON, kForLrlon};
        }
        double constantUllon = (ROOT_LRLON - ROOT_ULLON) / (kForLrlon + 1);
        for (int i = 0; i <= kForLrlon; i++) {
            if (a >= lrLon && a < lrLon + constantUllon) {
                return new double[]{lrLon + constantUllon, i};
            }
            lrLon = lrLon + constantUllon;
        }
        return new double[]{lrLon + constantUllon};
    }
    /** getting the raster_ul_lat by passing the upper left latitude */
    public double[] getRasUllat(double a, double kForUllat) {
        double ulLat = ROOT_ULLAT;
        if (a >= ROOT_ULLAT) {
            return new double[] {ROOT_ULLAT, 0.0};
        }
        double constantUllat = (ROOT_ULLAT - ROOT_LRLAT) / (kForUllat + 1);
        for (double i = 0; i <= kForUllat; i++) {
            if (a <= ulLat && a > ulLat - constantUllat) {
                return new double[] {ulLat, i};
            }
            ulLat = ulLat - constantUllat;
        }
        return new double[] {ulLat};
    }
    /** getting the raster_lr_lat by passing the lower right latitude */
    public double[] getRasLrlat(double a, double kForLrlat) {
        double lrLat = ROOT_ULLAT;
        if (a < ROOT_LRLAT) {
            return new double[] {ROOT_LRLAT, kForLrlat};
        }
        double constantLrlat = (ROOT_ULLAT - ROOT_LRLAT) / (kForLrlat + 1);
        for (int i = 0; i <= kForLrlat; i++) {
            if (a <= lrLat && a > lrLat - constantLrlat) {
                return new double[] {lrLat - constantLrlat, i};
            }
            lrLat = lrLat - constantLrlat;
        }
        return new double[] {lrLat - constantLrlat};
    }

    /* getting the String of render grid which are the expected tiles*/
    public String[][] getRenderGrid(int x1, int x2, int y1, int y2, int D) {
        String[][] grid = new String[y2 - y1 + 1][x2 - x1 + 1];
        for (int i = 0; i <= y2 - y1; i++) {
            for (int j = 0; j <= x2 - x1; j++) {
                grid[i][j] = "d" + D + "_" + "x" + (j + x1)
                        + "_" + "y" + (i + y1) + ".png";
            }
        }
        return grid;
    }


}

