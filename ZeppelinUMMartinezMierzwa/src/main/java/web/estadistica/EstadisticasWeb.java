package web.estadistica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import persistencia.dto.EstadisticaOpinionDTO;
import web.usuario.UserSessionWeb;
import zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class EstadisticasWeb implements Serializable {
    @Inject
    protected UserSessionWeb usuarioSesion;
    protected ServicioGestionPlataforma servicioPlataforma;
    private LineChartModel lineModel;

    public EstadisticasWeb() {      
        servicioPlataforma = ServicioGestionPlataforma.getServicioGestionPlataforma();  }

    @PostConstruct
    public void initLineChart() {
        createLineModel();
    }

    private void createLineModel() {
        List<EstadisticaOpinionDTO> estadisticas = servicioPlataforma.getEstadisticasOpinion(usuarioSesion.getUsuario().getId());
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            values.add(0);
        }
        for (EstadisticaOpinionDTO e : estadisticas) {
            values.set(e.getNota().intValue(), e.getTotal());
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Valoraciones a restaurantes");
        dataSet.setBorderColor("rgb(75, 192, 192)");

        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");
        data.setLabels(labels);

        // Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }
    public Integer getNumVisitas() {
        return servicioPlataforma.getNumVisitas(usuarioSesion.getUsuario().getId());
    }
    public LineChartModel getLineModel() {
        return lineModel;
    }
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}