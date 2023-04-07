package de.johannes.kirmes.views.home;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import de.johannes.kirmes.data.service.UserService;
import de.johannes.kirmes.views.MainView;

/**
 * @author j.henkel
 */

@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard | Kirmes CRM")
@CssImport("./styles/views/home/dashboard.css")
public class DashboardView extends VerticalLayout {

	private static final String CSS_CLASS_DASHBOARD_VIEW = "dashboard-view";
	private static final String CSS_CLASS_DASHBOARD_VIEW_CHART = "dashboard-view__chart";

	private final UserService userService;

	@Autowired
	public DashboardView(UserService userService){
		this.userService = userService;
		addClassName(CSS_CLASS_DASHBOARD_VIEW);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		add(getUserStats(), getUserChart());
	}

	private Component getUserStats() {
		Span stats = new Span(userService.getUserCount() + " users");
		stats.addClassNames(
				LumoUtility.FontSize.XLARGE,
				LumoUtility.Margin.Top.MEDIUM);
		return stats;
	}

	private Chart getUserChart(){
		Chart chart = new Chart(ChartType.PIE);
		chart.addClassName(CSS_CLASS_DASHBOARD_VIEW_CHART);

		DataSeries dataSeries = new DataSeries();
		Map<String, Long> userCountByRole = userService.getUserCountByRole();
		userCountByRole.entrySet().stream().forEach(entry -> {
			DataSeriesItem item = new DataSeriesItem(entry.getKey(), entry.getValue());
			dataSeries.add(item);
		});

		chart.getConfiguration().setSeries(dataSeries);
		return chart;
	}
}
