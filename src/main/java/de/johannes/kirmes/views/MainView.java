package de.johannes.kirmes.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

import de.johannes.kirmes.views.home.DashboardView;
import de.johannes.kirmes.views.home.UserView;

/**
 * @author j.henkel
 */
@PageTitle("Home")
@Route("home")
@RouteAlias("")
@CssImport("./styles/views/main.css")
public class MainView extends AppLayout {

	private static final String CSS_CLASS_MAIN_VIEW__ICON = "icon";

	public MainView() {
		createHeader();
		createDrawer();
	}


	private void createHeader() {
		H1 logo = new H1("Kirmes CRM");
		logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

		var header = new HorizontalLayout(new DrawerToggle(), logo);

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.setWidthFull();
		header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

		addToNavbar(header);
	}


	private void createDrawer() {
		Tabs tabs = getTabs();
		addToDrawer(tabs);
	}

	private Tabs getTabs(){
		Tabs tabs = new Tabs();
		tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard", DashboardView.class));
		tabs.add(createTab(VaadinIcon.USER, "User", UserView.class));
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		return tabs;
	}

	private Tab createTab(VaadinIcon viewIcon, String tabName, Class<? extends Component> navigationClass) {

		RouterLink link = new RouterLink();
		link.setRoute(navigationClass);
		link.setTabIndex(-1);

		if (viewIcon != null) {
			Icon icon = viewIcon.create();
			icon.addClassNames(CSS_CLASS_MAIN_VIEW__ICON);
			link.add(icon, new Span(tabName));
		}

		Tab tab = new Tab(link);

		return new Tab(link);
	}
}
