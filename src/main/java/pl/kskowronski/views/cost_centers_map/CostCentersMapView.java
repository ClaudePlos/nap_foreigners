package pl.kskowronski.views.cost_centers_map;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import fr.dudie.nominatim.client.JsonNominatimClient;
import pl.kskowronski.data.entity.egeria.css.CostCenterDTO;
import pl.kskowronski.data.entity.egeria.css.CostCenterGeolocation;
import pl.kskowronski.data.service.egeria.css.CostCenterGeolocationRepo;
import pl.kskowronski.data.service.egeria.css.CostCentersService;
import pl.kskowronski.views.main.MainView;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.*;

import fr.dudie.nominatim.model.Address;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.conn.ssl.SSLSocketFactory;



@Route(value = "cost_centers_map", layout = MainView.class)
@PageTitle("cost centers map")
@RouteAlias(value = "cost_centers_map", layout = MainView.class)
public class CostCentersMapView extends VerticalLayout {

    private JsonNominatimClient nominatimClient;
    private static final Properties PROPS = new Properties();

    private LMap map;
    private LMarker markerZob;
    private LMarker markerRathaus;
    private LMarker marker;

    private CostCentersService costCentersService;

    private CostCenterGeolocationRepo costCenterGeolocationRepo;

    private Select<String> selectBusinessType = new Select<>("Country");

    public CostCentersMapView(CostCentersService costCentersService, CostCenterGeolocationRepo costCenterGeolocationRepo) throws IOException {
        this.costCentersService = costCentersService;
        this.costCenterGeolocationRepo = costCenterGeolocationRepo;
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);
        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);
        final String baseUrl = "https://nominatim.openstreetmap.org/";
        final String email = "claude-plos@o2.pl";
        nominatimClient = new JsonNominatimClient(baseUrl, httpClient, email);
        this.initMapComponents();
        this.addCostCenterForRekeep("Z");

        HorizontalLayout h01 = new HorizontalLayout();

        Button delete = new Button("Usuń", e ->{
            this.clearTheMap();
        });

        Button refresh = new Button("Odśwież", e ->{
            this.addCostCenterForRekeep(selectBusinessType.getValue());
        });
        refresh.setWidth("100px");

        Button buttGetData = new Button("Przeładuj dane (uaktualnienie SK)", e ->{
            costCentersService.deleteItemsFromNApSkGeolocationForArchiveSk();
            this.generateDataLocation("Z");
            this.generateDataLocation("C");
            this.generateDataLocation("K");
            this.generateDataLocation("T");
        });
        buttGetData.setWidth("250px");

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search SK");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            if (searchField.getValue().length() == 4) {
                this.clearTheMap();
                Optional<CostCenterGeolocation> costCentersGeo = costCenterGeolocationRepo.findById(searchField.getValue());
                if (costCentersGeo.isPresent()) {
                    marker = new LMarker(costCentersGeo.get().getLatitude().doubleValue(), costCentersGeo.get().getLongitude().doubleValue(), costCentersGeo.get().getCostCenterCode());
                    marker.setPopup("<p><center><b>" + costCentersGeo.get().getCostCenterCode() + "</b></center></p><p>" + costCentersGeo.get().getCostCenterDesc() + "</p>" + costCentersGeo.get().getDisplayName());
                    this.map.addLComponents(marker);
                }
            }
        });


        selectBusinessType.setItems("FM", "FOOD", "Catering", "Transport", "Kuchnie");
        selectBusinessType.setValue("FM");
        selectBusinessType.addValueChangeListener( v -> {
            this.addCostCenterForRekeep(selectBusinessType.getValue());
        });

        h01.add(buttGetData,delete, refresh, searchField, selectBusinessType);
        add(h01, this.map);
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

    private void addCostCenterForRekeep(String businessType)  {
        clearTheMap();

        if (businessType.equals("FM")) {
            businessType = "Z";
        } else if (businessType.equals("FOOD")) {
            businessType = "F";
        } else if (businessType.equals("Catering")) {
            businessType = "C";
        } else if (businessType.equals("Transport")) {
            businessType = "T";
        } else if (businessType.equals("Kuchnie")) {
            businessType = "K";
        }

        List<CostCenterGeolocation> costCentersGeo = new ArrayList<>();
        if (businessType.equals("Z") || businessType.equals("F")) {
            String contractType = "Publiczny";
            if (businessType.equals("F")){
                contractType = "Komercja";
            }
            costCentersGeo = costCenterGeolocationRepo.getAllForBusinessTypeWithContract("Z", contractType);
        } else {
            costCentersGeo = costCenterGeolocationRepo.getAllForBusinessType(businessType);
        }


        costCentersGeo.forEach( c -> {
            marker = new LMarker(c.getLatitude().doubleValue(), c.getLongitude().doubleValue(), c.getCostCenterCode());
            marker.setPopup("<p><center><b>" + c.getCostCenterCode() + "</b></center></p><p>" + c.getCostCenterDesc() + "</p>" + c.getDisplayName());
//            LIcon icon = new LIcon();
//            icon.setIconUrl("https://www.google.pl/search?sca_esv=577907868&sxsrf=AM9HkKncyQG4FXEC4qvNqPe9rJAUMq_l5Q:1698696773815&q=marker+png&tbm=isch&source=lnms&sa=X&ved=2ahUKEwie7KLOyp6CAxXHLRAIHYKGDfIQ0pQJegQIDRAB&biw=1920&bih=911#imgrc=Y8LUHPP8HPxZ3M");
//            marker.setIcon(icon);
            this.map.addLComponents(marker);
        });

    }

    public void generateDataLocation(String businessType) {

        List<CostCenterDTO> costCenters = costCentersService.getAllCostCentersForRekeep(businessType);

        costCenters.forEach( c -> {
            List<Address> addresses = new ArrayList<>();
            try {
                if (c.getCity() != null && c.getStreet() != null) {
                    addresses = nominatimClient.search(c.getStreet() + ", " + c.getCity() );
                    //addresses = nominatimClient.search(c.getCity() + ", Polska, " + c.getStreet() );
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (addresses.size() > 0 ) {
                CostCenterGeolocation gNew = new CostCenterGeolocation();
                gNew.setCostCenterCode(c.getSkKod());
                gNew.setLatitude(BigDecimal.valueOf(addresses.get(0).getLatitude()));
                gNew.setLongitude(BigDecimal.valueOf(addresses.get(0).getLongitude()));
                gNew.setDisplayName(addresses.get(0).getDisplayName());
                gNew.setCity(c.getCity());
                gNew.setStreet(c.getStreet());
                gNew.setCostCenterDesc(c.getSkDesc());
                gNew.setBusinessType(c.getBusinessType());
                gNew.setContractType(c.getContractType());
                gNew.setSkId(c.getSkId());
                costCenterGeolocationRepo.save(gNew);
            }
        });


    }

    private void clearTheMap() {
        List<LComponent> components = this.map.getComponents();
        if (components.size() == 0 ) {
            return;
        } else {
            for (int i = 0; i < components.size(); i++) {
                this.map.removeLComponents(components.get(i));
            }
            this.clearTheMap();
        }
    }
}
