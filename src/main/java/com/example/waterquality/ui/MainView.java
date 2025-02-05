package com.example.waterquality.ui;

import com.example.waterquality.model.WaterQuality;
import com.example.waterquality.service.EmailService;
import com.example.waterquality.service.WaterQualityService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsLine;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;

@Route("main")
@CssImport("./styles.css")
@PageTitle("Water Quality")
@RouteScope
@Component
public class MainView extends VerticalLayout {

    private final WaterQualityService waterQualityService;
    private final EmailService emailService;
    private List<WaterQuality> data;
    private String parameter;

    private Grid<WaterQuality> grid = new Grid<>(WaterQuality.class);
    private H3 chartTitle;
    private Chart pieChart = new Chart(ChartType.PIE);
    private Chart lineChart = new Chart(ChartType.LINE);
    private Configuration pieConfig = pieChart.getConfiguration();
    private Configuration lineConfig = lineChart.getConfiguration();

    @Autowired
    public MainView(WaterQualityService waterQualityService, EmailService emailService) {
        this.waterQualityService = waterQualityService;
        this.emailService = emailService;
        data = waterQualityService.getAllData();

        Span titleLabel = new Span("Water Quality Dashboard");
        titleLabel.addClassName("title-label");

        grid.setColumns("waterLevel", "pH", "dissolvedOxygen", "temperature", "turbidity", "tds", "status", "date", "time");
        grid.getColumnByKey("pH").setHeader("pH");
        grid.getColumns().forEach(column -> column.setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER));
        grid.addClassName("custom-grid");

        Button addDataButton = new Button("Add Simulated Data", e -> addSimulatedData());
        addDataButton.addClassName("custom-button");
        
        add(titleLabel, addDataButton, grid);

        setUpChart();
        updateGrid();
        updatePieChartData();
        updateLineChartData();
    }

    private void addSimulatedData() {
        WaterQuality simulatedData = waterQualityService.generateRandomData();

        List<String> analyse = waterQualityService.analyzeWaterQuality(simulatedData);
        String status = analyse.get(0);
        String msg = analyse.get(1);
        if (status.contains("Unsafe")) {
            emailService.sendEmail("yokiispartan521@gmail.com", "Water Quality Alert", status + "\n" + msg);
            Notification.show("Email sent successfully!");
        }

        updateGrid();
        updateChartTitle();
        updatePieChartData();
        updateLineChartData();
    }

    private void updateGrid() {
        data = waterQualityService.getAllData();
        Collections.reverse(data);
        grid.setItems(data);
    }

    private void setUpChart() {
        parameter = "pH";
        chartTitle = new H3("Water Level");
        chartTitle.addClassName("chart-title");

        pieConfig = pieChart.getConfiguration();
        lineConfig = lineChart.getConfiguration();

        PlotOptionsPie piePlot = new PlotOptionsPie();
        piePlot.setSize("100%");
        pieConfig.setPlotOptions(piePlot);
        pieChart.addClassName("custom-pie-chart");

        PlotOptionsLine linePlotOptions = new PlotOptionsLine();
        linePlotOptions.setEnableMouseTracking(true);
        lineConfig.setPlotOptions(linePlotOptions);
        lineChart.addClassName("custom-line-chart");

        ComboBox<String> parameterSelector = new ComboBox<>("Select Parameter");
        parameterSelector.setItems("pH", "Disolved Oxygen", "Temperature", "Turbidity", "TDS");
        parameterSelector.setValue("pH");
        parameterSelector.addValueChangeListener(event -> {
            parameter = event.getValue();
            System.out.println(parameter);
            updateChartTitle();
            updatePieChartData();
            updateLineChartData();
        });

        HorizontalLayout layout = new HorizontalLayout(new VerticalLayout(chartTitle, parameterSelector), new VerticalLayout(pieChart, lineChart));
        add(layout);

        updateChartTitle();
        updatePieChartData();
        updateLineChartData();
    }
    
    private void updateChartTitle() {
        chartTitle.setText(
            switch (parameter) {
                case "pH" -> "pH";
                case "dissolvedOxygen" -> "Disolved Oxygen";
                case "temperature" -> "Temperature";
                case "turbidity" -> "Turbidity";
                case "tds" -> "TDS";
                default -> "pH";
            }
        );
    }
    
    private void updatePieChartData() {
        DataSeries series = new DataSeries();
        int total = data.size();
        int safe = 0;

        for (int i = 0; i < total; i++) {
            switch (parameter) {
                case "pH" -> {
                    if (data.get(i).isPH()) {
                        safe++;
                    }
                }
                case "dissolvedOxygen" -> {
                    if (data.get(i).isDissolvedOxygen()) {
                        safe++;
                    }
                }
                case "temperature" -> {
                    if (data.get(i).isTemperature()) {
                        safe++;
                    }
                }
                case "turbidity" -> {
                    if (data.get(i).isTurbidity()) {
                        safe++;
                    }
                }
                case "tds" -> {
                    if (data.get(i).isTds()) {
                        safe++;
                    }
                }
            }
        }

        series.add(new DataSeriesItem("Safe", safe));
        series.add(new DataSeriesItem("Unsafe", total - safe));
        pieConfig.setSeries(series);
        pieChart.drawChart();
    }

    private void updateLineChartData() {
        DataSeries series = new DataSeries();

        for (int i = 0; i < data.size(); i++) {
            switch (parameter) {
                case "pH" -> series.add(new DataSeriesItem(data.get(i).getDate(), data.get(i).getpH()));
                case "dissolvedOxygen" -> series.add(new DataSeriesItem(data.get(i).getDate(), data.get(i).getDissolvedOxygen()));
                case "temperature" -> series.add(new DataSeriesItem(data.get(i).getDate(), data.get(i).getTemperature()));
                case "turbidity" -> series.add(new DataSeriesItem(data.get(i).getDate(), data.get(i).getTurbidity()));
                case "tds" -> series.add(new DataSeriesItem(data.get(i).getDate(), data.get(i).getTds()));
            }
        }

        lineConfig.setSeries(series);
        lineChart.drawChart();
    }
}