package pl.kskowronski.views.cost_centers_map;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fr.dudie.nominatim.client.JsonNominatimClient;
import pl.kskowronski.views.main.MainView;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCircle;
import software.xdev.vaadin.maps.leaflet.flow.data.LDivIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LIcon;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LPoint;
import software.xdev.vaadin.maps.leaflet.flow.data.LPolygon;

import fr.dudie.nominatim.model.Address;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.conn.ssl.SSLSocketFactory;



@Route(value = "cost_centers_map", layout = MainView.class)
@PageTitle("cost center map")
@RouteAlias(value = "cost_centers_map", layout = MainView.class)
public class CostCentersMapView extends VerticalLayout {

    private JsonNominatimClient nominatimClient;
    private static final Properties PROPS = new Properties();

    private LMap map;
    private LMarker markerZob;
    private LMarker markerRathaus;
    private LMarker marker;


    public CostCentersMapView() throws IOException {
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);
        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);
        final String baseUrl = "https://nominatim.openstreetmap.org/";
        final String email = "claude-plos@o2.pl";
        nominatimClient = new JsonNominatimClient(baseUrl, httpClient, email);
        this.initMapComponents();
        this.addCostCenterForRekeep();

        add(this.map);
        this.setSizeFull();
    }

    private void initMapComponents() throws IOException {

        this.markerZob = new LMarker(51.7696, 19.4696, "Pociąg");
        this.markerZob.setPopup("Łodź Fabryczna");

        final LMarker markerRekeep = new LMarker(51.79850, 19.39345);
        final LIcon rekeepLogo = new LIcon(
                // Important replace # with %23!
                " ");

        //rekeepLogo.setIconSize(100, 20);
        //rekeepLogo.setIconAnchor(50, 0);
        markerRekeep.setPopup("<a href='https://rekeep.pl' target='"  + "'>Rekeep Polska</a><p><center><b>Witam w Rekeep Polska!</b></center></p><p>Here is headquarter</p>");
        //markerRekeep.setIcon(rekeepLogo);

        final LMarker markerInfo = new LMarker(51.7732, 19.4805);
        final LDivIcon div = new LDivIcon(
                "Łódź");

        markerInfo.setDivIcon(div);

//        final LPolygon polygonNoc = new LPolygon(
//                Arrays.asList(
//                        new LPoint(51.7983546, 19.3931719),
//                        new LPoint(51.7976729, 19.3912079),
//                        new LPoint(51.7976729, 19.3912079),
//                        new LPoint(51.7976729, 19.3912079),
//                        new LPoint(51.7976729, 19.3912079)));
//        polygonNoc.setFill(true);
//        polygonNoc.setFillColor("#3366ff");
//        polygonNoc.setFillOpacity(0.8);
//        polygonNoc.setStroke(false);
//        polygonNoc.setPopup("Anfix Center");

        this.markerRathaus = new LMarker(51.78193, 19.45424, "L-22556");
        this.markerRathaus.setPopup("Old Town Hall");

        //this.circleRange = new LCircle(51.7803708, 19.4524011, 450);

        this.map = new LMap(52.005, 20.555, 7);
        //this.map.setTileLayer(LTileLayer.class.newInstance().);

        this.map.setSizeFull();
        // add some logic here for called Markers (token)
        //this.map.addMarkerClickListener(ev -> System.out.println(ev.getTag()));



        this.map.addLComponents(
                markerRekeep,
                markerInfo,
                this.markerZob,
                //polygonNoc,
                this.markerRathaus);
    }

    private void addCostCenterForRekeep() throws IOException {
        List<Address> addresses = nominatimClient.search(" Łódź, Polska, Piotrkowska 255");
        System.out.println(addresses);

        addresses.forEach( item -> {
            marker = new LMarker(item.getLatitude(), item.getLongitude(), item.getDisplayName());
            marker.setPopup(item.getDisplayName());
            this.map.addLComponents(marker);
        });
    }
}
