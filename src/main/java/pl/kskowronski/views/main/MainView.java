package pl.kskowronski.views.main;

import java.util.Locale;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kskowronski.data.entity.inap.User;
import pl.kskowronski.data.service.inap.UserService;
import pl.kskowronski.views.cost_centers_map.CostCentersMapView;
import pl.kskowronski.views.work_gov_pl.WorkGovPlView;
import pl.kskowronski.views.workers_after_decision.WorkersAfterDecisionView;
import pl.kskowronski.views.workers_suspended.WorkersSuspendedView;
import pl.kskowronski.views.workers_to_acceptation.WorkersToAcceptationView;
import pl.kskowronski.views.about.AboutView;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/views/main/main-view.css")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;

    private transient UserService userService;

    private transient User worker;
    private Label labName = new Label();

    @Autowired
    public MainView( UserService userService ) {
        this.userService = userService;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> worker = userService.findByPassword(userDetails.getPassword());
        VaadinSession session = VaadinSession.getCurrent();
        if (worker.isPresent()) {
            session.setAttribute(User.class, worker.get());
        }
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);

        Anchor logout = new Anchor("/logout","Wyloguj");

        labName.setClassName("labName");
        layout.add(labName, logout);
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "nap_foreigners logo"));
        logoLayout.add(new H1(" Foreigners"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> workerOp = userService.findByUsername(userDetails.getUsername());
        worker = workerOp.get();

        labName.setText(worker.getUsername().toUpperCase(Locale.ROOT).substring(0,1));
        labName.setTitle(worker.getUsername());

        return new Tab[] {
            createTab("Do Akceptacji", WorkersToAcceptationView.class),
            createTab("Zawieszeni", WorkersSuspendedView.class),
            createTab("Po decyzji", WorkersAfterDecisionView.class),
            createTab("Praca.gov.pl", WorkGovPlView.class),
            createTab("Centra kosztów", CostCentersMapView.class),
            createTab("About", AboutView.class)
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
