package com.android.doral.directions;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;


import com.android.doral.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WINDOWS-D20 on 20-3-2017.
 */

public class GoogleMapUtils {
    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Create bounds for Display Multiple marker in map
     * Zoom level set as per markers
     *
     * @param markers
     */
    public static void showAllMarkers(GoogleMap googleMap, Marker[] markers, int padding, boolean isAnimateCamera) {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            if (isAnimateCamera) {
                googleMap.animateCamera(cu);
            } else {
                googleMap.moveCamera(cu);
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Set bounds for Display Full Polyline on map
     * Zoom level set as per Polyline max area
     *
     * @param googleMap
     * @param polyline
     * @param padding
     */
    public static void showFullPolyline(GoogleMap googleMap, Polyline polyline, int padding, boolean isAnimateCamera) {
        if (googleMap != null && polyline != null) {
            List<LatLng> latLngs = polyline.getPoints();
            LatLng[] latLngsArray = new LatLng[latLngs.size()];
            latLngsArray = latLngs.toArray(latLngsArray);
            GoogleMapUtils.showAllMarkers(googleMap, latLngsArray, padding, isAnimateCamera); // Set zoom
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Set bounds for Display Full Polyline on map
     * Zoom level set as per Polyline and Markers max area
     *
     * @param googleMap
     * @param polyline
     * @param padding
     */
    public static void showFullPolyline(GoogleMap googleMap, Polyline polyline, Marker[] markers, int padding, boolean isAnimateCamera) {
        if (googleMap != null && polyline != null && markers != null) {
            List<LatLng> latLngs = polyline.getPoints();

            for (Marker marker : markers) {
                latLngs.add(marker.getPosition()); // Add all lat,lng of marker into polyline's lat,lng
            }

            LatLng[] latLngsArray = new LatLng[latLngs.size()];
            latLngsArray = latLngs.toArray(latLngsArray);

            GoogleMapUtils.showAllMarkers(googleMap, latLngsArray, padding, isAnimateCamera); // Set zoom
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Create bounds for Display Multiple marker in map
     * Zoom level set as per markers
     *
     * @param googleMap
     * @param latLngs
     * @param padding
     */
    public static void showAllMarkers(GoogleMap googleMap, LatLng[] latLngs, int padding, boolean isAnimateCamera) {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng marker : latLngs) {
                if (marker != null) {
                    builder.include(marker);
                }
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            if (isAnimateCamera) {
                googleMap.animateCamera(cu);
            } else {
                googleMap.moveCamera(cu);
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Move and rotate marker animation
     * Marker will rotate from Current angle to Moving angle
     *
     * @param marker
     * @param toLocation
     * @param CAMERA_ZOOM_TIME
     */
    public static void moveAndRotateMarkerWithAnimation(final Marker marker, final Location toLocation, int CAMERA_ZOOM_TIME) {
        // Get Rotation
        Location oldLocation = new Location("Old");
        oldLocation.setLatitude(marker.getPosition().latitude);
        oldLocation.setLongitude(marker.getPosition().longitude);
        final float toRotation = oldLocation.bearingTo(toLocation);

        moveAndRotateMarkerWithAnimation(marker, toRotation, toLocation, CAMERA_ZOOM_TIME);
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Move and rotate marker animation
     *
     * @param marker
     * @param toRotation
     * @param toLocation
     * @param CAMERA_ZOOM_TIME
     */
    public static void moveAndRotateMarkerWithAnimation(final Marker marker, final float toRotation, final Location toLocation, int CAMERA_ZOOM_TIME) {
        final long start = SystemClock.uptimeMillis();
        final float startRotation = marker.getRotation();
        final LatLng startLatLng = marker.getPosition();
        final long duration = CAMERA_ZOOM_TIME;
        float deltaRotation = Math.abs(toRotation - startRotation) % 360;
        final float rotation = (deltaRotation > 180 ? 360 - deltaRotation : deltaRotation) *
                ((toRotation - startRotation >= 0 && toRotation - startRotation <= 180) || (toRotation - startRotation <= -180 && toRotation - startRotation >= -360) ? 1 : -1);

        final LinearInterpolator interpolator = new LinearInterpolator();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                marker.setRotation((startRotation + t * rotation) % 360);

                double lng = t * toLocation.getLongitude() + (1 - t) * startLatLng.longitude;
                double lat = t * toLocation.getLatitude() + (1 - t) * startLatLng.latitude;

                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Fade in or Fade out marker animation
     *
     * @param marker
     * @param isFadeIn        true = fade_in, false = fade_out
     * @param durationInMilli Fade in/out duration in Milli second
     */
    public static void markerFadeInOutAnimation(final Marker marker, boolean isFadeIn, long durationInMilli) {
        ValueAnimator animator = isFadeIn ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
        animator.setDuration(durationInMilli);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                marker.setAlpha((float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Zoom in marker animation
     *
     * @param marker
     * @param durationInMilli Zoom in duration in Milli second
     */
    public static void markerZoomAnimation(final Marker marker, long durationInMilli) {

    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param mapFragment  container must be RelativeLayout
     * @param marginLeft
     * @param marginTop
     * @param marginRight
     * @param marginBottom
     */
    public static void changeMyLocationButtonPositionToBottom(SupportMapFragment mapFragment, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        View mapView = mapFragment.getView();
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Map scroll as per given X,Y pixel value
     * <p/>
     * NOTE : Map will scroll as per Screen orientation not as per Map Bearing(Rotation)
     *
     * @param googleMap
     * @param x         E.g. x=0 means center, x=5 means scroll to right, x=-5 means scroll to left
     * @param y         E.g. y=0 means center, y=5 means scroll to top, y=-5 means scroll to bottom
     */
    public static void mapScrollTo(GoogleMap googleMap, float x, float y) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.scrollBy(x, y);
        googleMap.animateCamera(cameraUpdate);
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param context
     * @param googleMap
     * @param latitude
     * @param longitude
     * @param radiusInMeters
     * @return
     */
    public static Circle drawCircleGeoFencingDataOnMap(Context context, GoogleMap googleMap, double latitude, double longitude, double radiusInMeters) {
        Circle circle = drawCircleGeoFencingDataOnMap(
                googleMap,
                latitude,
                longitude,
                radiusInMeters,
                ContextCompat.getColor(context, android.R.color.holo_red_light),
                ContextCompat.getColor(context, android.R.color.holo_blue_light),
                8
        );

        return circle;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param googleMap
     * @param latitude
     * @param longitude
     * @param radiusInMeters
     * @param strokeColor    ContextCompat.getColor(context, R.color.color_00BCF3)
     * @param shadeColor     ContextCompat.getColor(context, R.color.color_00BCF3)
     * @param strokeWidth
     * @return
     */
    public static Circle drawCircleGeoFencingDataOnMap(GoogleMap googleMap, double latitude, double longitude, double radiusInMeters, int strokeColor, int shadeColor, float strokeWidth) {
        LatLng position = new LatLng(latitude, longitude);

        Circle mCircle;
        // Marker mMarker;

        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(strokeWidth);
        mCircle = googleMap.addCircle(circleOptions);

        /*MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMarker = googleMap.addMarker(markerOptions);*/

        return mCircle;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param googleMap
     * @param marker    Marker which you want to scale size
     * @param bitmap    For E.g Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_image);
     */
    public static void scaleMarkerSizeAsPerZoom(GoogleMap googleMap, Marker marker, Bitmap bitmap) {
        float minimumZoomLevel = 9;
        scaleMarkerSizeAsPerZoom(googleMap, marker, bitmap, minimumZoomLevel);
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     *
     * @param googleMap
     * @param marker           Marker which you want to scale size
     * @param bitmap           For E.g Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_image);
     * @param minimumZoomLevel
     */
    public static void scaleMarkerSizeAsPerZoom(GoogleMap googleMap, Marker marker, Bitmap bitmap, float minimumZoomLevel) {
        float currentZoomLevel = googleMap.getCameraPosition().zoom;
        float finalZoom;

        Matrix scaledMatrix = new Matrix();

        if (currentZoomLevel < minimumZoomLevel) {
            if (currentZoomLevel < 7.0) {
                finalZoom = 0.30f;
            } else if (currentZoomLevel < 7.5) {
                finalZoom = 0.35f;
            } else if (currentZoomLevel < 8.0) {
                finalZoom = 0.40f;
            } else if (currentZoomLevel < 8.5) {
                finalZoom = 0.45f;
            } else {
                finalZoom = 0.50f;
            }
        } else if (currentZoomLevel >= 18) {
            finalZoom = 1.0f;
        } else {
            /*
            11-minimumZoomLevel = 3
            3*0.5 = 1.5
            1.5/10 = 0.15
            0.15+0.5 = 0.65
            */
            currentZoomLevel = currentZoomLevel > minimumZoomLevel ? currentZoomLevel - minimumZoomLevel : 1;
            finalZoom = currentZoomLevel * 0.5f;
            finalZoom = finalZoom / 10f;
            finalZoom = finalZoom + 0.5f;

            // finalZoom = currentZoomLevel * 0.1f;
            if (finalZoom < 0.4) {
                finalZoom = 0.4f;
            }
        }

        scaledMatrix.postScale(finalZoom, finalZoom);

        if (marker != null && bitmap != null) {
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), scaledMatrix, true);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
            marker.setIcon(bitmapDescriptor);
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * Open Google map application and start direction
     *
     * @param context
     * @param start   If start LatLng is null or 0.0 then Current Location will be consider
     * @param end
     */
    public static void openGoogleMapNavigation(Context context, LatLng start, LatLng end) {
        double startLatitude = 0.0;
        double startLongitude = 0.0;
        if (start != null && start.latitude != 0.0 && start.longitude != 0.0) {
            startLatitude = start.latitude;
            startLongitude = start.longitude;
        }
        double endLatitude = end.latitude;
        double endLongitude = end.longitude;

        String url = "";
        if (startLatitude == 0.0) {
            url = "google.navigation:q=" + endLatitude + "," + endLongitude;
        } else {
            url = "google.navigation:q=" + endLatitude + "," + endLongitude + "&daddr=" + startLatitude + "," + startLongitude;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        context.startActivity(intent);
    }


    /**
     * By Bhavesh<br/>
     * <p/>
     * Get Static Image path url
     *
     * @param context
     * @param height         Image height
     * @param width          Image width
     * @param directionPoint List of points
     * @return
     */
    public static String getGoogleMapStaticImageWithPathDrawUrl(Context context, int height, int width, ArrayList<LatLng> directionPoint) {
        /**
         * Example URL
         * https://maps.googleapis.com/maps/api/staticmap?size=600x600&path=color:0x7f6BA7FF|weight:5|23.195466,72.639777|23.195242,72.639691|23.194958,72.639645|23.194396,72.639597
         *
         *  %7C   is used for separator
         *  |     is used for multiple value
         */

        String url = "";

        if (!directionPoint.isEmpty()) {
            LatLng firstPos = new LatLng(directionPoint.get(0).latitude, directionPoint.get(0).longitude);
            LatLng lastPos = new LatLng(directionPoint.get(directionPoint.size() - 1).latitude, directionPoint.get(directionPoint.size() - 1).longitude);

            url = "https://maps.googleapis.com/maps/api/staticmap?" +
                    "size=" + height + "x" + width +

                    "&markers=color:blue%7Clabel:P%7C" + firstPos.latitude + "," + firstPos.longitude +
                    "&markers=color:black%7Clabel:D%7C" + lastPos.latitude + "," + lastPos.longitude +

                    "&key=" + context.getResources().getString(R.string.map_key) +
                    "&path=color:0x7f6BA7FF|weight:5";

            // "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794" +
            // "&markers=anchor:32,10%7Cicon:https://goo.gl/5y3S82%7CCanberra+ACT" +

            for (int i = 0; i < directionPoint.size(); i++) {
                url = url + "|" + directionPoint.get(i).latitude + "," + directionPoint.get(i).longitude;
            }
        }
        return url;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Create location points in between points<br/>
     * If two points distance is more then minimumDistanceInMeter.<br/>
     *
     * @param minimumDistanceInMeters If two points distance is more then minimumDistanceInMeter.
     * @param latLngs                 Add points in between polyLines points
     * @return
     */
    public static List<LatLng> generatePointsBetweenPolyline(final float minimumDistanceInMeters, List<LatLng> latLngs) {
        if (!latLngs.isEmpty()) {
            for (int l = 0; l < latLngs.size(); l++) {
                int firstPoint = l;
                int secondPoint = l + 1;

                if (secondPoint <= latLngs.size() - 1) {
                    Location firstLocation = new Location("First");
                    firstLocation.setLatitude(latLngs.get(firstPoint).latitude);
                    firstLocation.setLongitude(latLngs.get(firstPoint).longitude);
                    Location secondLocation = new Location("Second");
                    secondLocation.setLatitude(latLngs.get(secondPoint).latitude);
                    secondLocation.setLongitude(latLngs.get(secondPoint).longitude);

                    float distanceInMeters = firstLocation.distanceTo(secondLocation);
                    if (minimumDistanceInMeters < distanceInMeters) {
                        // Add new LatLng between firstLocation & secondLocation

                        float degrees = firstLocation.bearingTo(secondLocation);
                        LatLng newLatLngPoint = createNewLocationInSpecificDirection(new LatLng(firstLocation.getLatitude(), firstLocation.getLongitude()), degrees, minimumDistanceInMeters);
                        latLngs.add(secondPoint, newLatLngPoint);
                    }
                }
            }
        }

        return latLngs;
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Create a new location specified in meters and bearing from a previous location.
     *
     * @param fromLatLng      from where
     * @param bearing         which direction, in degree ( e.g. Location1.bearingTo(Location2) )
     * @param distanceInMeter meters from fromLatLng
     * @return If fromLatLng is not null and distanceInMeter is greater then ZERO than it will return a new location
     * otherwise return fromLatLng
     */
    public static LatLng createNewLocationInSpecificDirection(LatLng fromLatLng, float bearing, float distanceInMeter) {
        if (fromLatLng != null && distanceInMeter > 0) {
            /*
            // Below is manual code for create location

            Location newLocation = new Location("newLocation");

            // double radius = 6371.0; // earth's mean radius in km
            double radius = 6371000.0; // earth's mean radius in m
            double lat1 = Math.toRadians(fromLatLng.getLatitude());
            double lng1 = Math.toRadians(fromLatLng.getLongitude());
            double brng = Math.toRadians(bearing);
            double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distanceInMeter / radius) + Math.cos(lat1) * Math.sin(distanceInMeter / radius) * Math.cos(brng));
            double lng2 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(distanceInMeter / radius) * Math.cos(lat1), Math.cos(distanceInMeter / radius) - Math.sin(lat1) * Math.sin(lat2));
            lng2 = (lng2 + Math.PI) % (2 * Math.PI) - Math.PI;

            // normalize to -180...+180
            if (lat2 == 0 || lng2 == 0) {
                newLocation.setLatitude(0.0);
                newLocation.setLongitude(0.0);
            } else {
                newLocation.setLatitude(Math.toDegrees(lat2));
                newLocation.setLongitude(Math.toDegrees(lng2));
            }*/

            return SphericalUtil.computeOffset(fromLatLng, distanceInMeter, bearing);
        } else {
            return fromLatLng;
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * Return the direction degree of next point of given polyLine <br/>
     * For e.g.                                <br/>
     * polyline = A,B,C,D,E,F,G,H...           <br/>
     * onPathLocationPoint = D                 <br/>
     * Result = The direction degree of D to E <br/>
     *
     * @param onPathLocationPoint
     * @param polyline
     * @return The bearing in degree. If return 500 then no degree found
     */
    public static float getNextPointDirectionDegree(Location onPathLocationPoint, Polyline polyline) {
        if (onPathLocationPoint != null && polyline != null && !polyline.getPoints().isEmpty()) {
            LatLng findLatLng = new LatLng(onPathLocationPoint.getLatitude(), onPathLocationPoint.getLongitude());
            ArrayList<LatLng> pathPoints = (ArrayList<LatLng>) polyline.getPoints();
            int index = pathPoints.indexOf(findLatLng);
            if (index != -1) {

                Location locationA = new Location("A");
                Location locationB = new Location("B");

                int pathPointSize = pathPoints.size() - 1;

                if (pathPointSize == 1) {
                    // Only ONE Location available in the list
                    locationA.setLatitude(pathPoints.get(index).latitude);
                    locationA.setLongitude(pathPoints.get(index).longitude);
                    locationB.setLatitude(pathPoints.get(index).latitude);
                    locationB.setLongitude(pathPoints.get(index).longitude);

                    return locationA.bearingTo(locationB);
                }

                if (index >= 0 && index < pathPointSize) {
                    // Zero point to Second last point
                    locationA.setLatitude(pathPoints.get(index).latitude);
                    locationA.setLongitude(pathPoints.get(index).longitude);
                    locationB.setLatitude(pathPoints.get(index + 1).latitude);
                    locationB.setLongitude(pathPoints.get(index + 1).longitude);
                } else {
                    // Last point
                    locationA.setLatitude(pathPoints.get(index - 1).latitude);
                    locationA.setLongitude(pathPoints.get(index - 1).longitude);
                    locationB.setLatitude(pathPoints.get(index).latitude);
                    locationB.setLongitude(pathPoints.get(index).longitude);
                }
                return locationA.bearingTo(locationB);
            } else {
                // No location point found in polyline
                return 500;
            }
        } else {
            return 500;
        }
    }

    /**
     * By Bhavesh<br/>
     * <p/>
     * <p>
     * If provided location is near Polyline then it will return polyline point,
     * otherwise it will return as it is.
     *
     * @param location       A location which find on path or near path
     * @param polyline       Find location on this polyLine points
     * @param toleranceEarth Meters near by path will show on path
     * @return
     */
    public static Location getLatLngOnPathIfNearByLocation(Location location, Polyline polyline, double toleranceEarth, boolean isCheckAccuracyIsMoreOrNot) {
        LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Check nearest point on "SrcToDest path"
        if (polyline != null && !polyline.getPoints().isEmpty()) {

            if (isCheckAccuracyIsMoreOrNot) {
                // If Accuracy is more then "toleranceEarth" meters then show point on path according to accuracy

                double accuracy = location.getAccuracy();
                if (accuracy > toleranceEarth) {
                    toleranceEarth = accuracy;
                }
            }

            // newLatLng = PolyUtil.getNearestLocationOnEdgeOrPath(newLatLng, polyLineSrcToDest.getPoints(), true, true, toleranceEarth);
            newLatLng = PolyUtil.getNearestLocationOnEdgeOrPath(newLatLng, polyline.getPoints(), toleranceEarth);

            location.setLatitude(newLatLng.latitude);
            location.setLongitude(newLatLng.longitude);
        }

        return location;
    }
}