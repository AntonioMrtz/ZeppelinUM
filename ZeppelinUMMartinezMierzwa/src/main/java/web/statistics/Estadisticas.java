package web.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import persistencia.jpa.dao.RestauranteDAO;
import web.usuario.UserSessionWeb;
import zeppelinum.ServicioGestionPedido;
import zeppelinum.ServicioGestionPlataforma;

@Named
@RequestScoped
public class Estadisticas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected BarChartModel barModel;
	protected BarChartModel barModel2;
	@Inject
	protected UserSessionWeb userSessionWeb;
	private int id;
	
	
	public BarChartModel getBarModel() {
		return barModel;
	}


	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	
	


	public BarChartModel getBarModel2() {
		return barModel2;
	}


	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}


	@PostConstruct
    public void init() {
		
		barModel = new BarChartModel();
		barModel2= new BarChartModel();
		createBarModel();
		createBarModel2();
		id=userSessionWeb.getUsuario().getId();
	}
	

    public void createBarModel() {
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("pedidos");

        List<Number> values = new ArrayList<>();
        values.add(ServicioGestionPedido.getServicioGestionPedido().findPedidoByUser(id).size());
        values.add(ServicioGestionPedido.getServicioGestionPedido().getAllPedidos().size());

        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Usuario");
        labels.add("Base de datos");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Número de pedidos del usuario frente al total de pedidos");
        options.setTitle(title);

        

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }

	
    public void createBarModel2() {
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("restaurantes");

        List<Number> values = new ArrayList<>();
        values.add(ServicioGestionPedido.getServicioGestionPedido().findPedidoByUserDifferentRestaurant(userSessionWeb.getUsuario().getId()));
        values.add(ServicioGestionPlataforma.getServicioGestionPlataforma().getNumAllRestaurantes());

        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("Usuario");
        labels.add("Total restaurantes");
        data.setLabels(labels);
        barModel2.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Número de restaurantes con un pedido del usuario vs restaurantes totales");
        options.setTitle(title);

        

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel2.setOptions(options);
    }
	
	
	
}