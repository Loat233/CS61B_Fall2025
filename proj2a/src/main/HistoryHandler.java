package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryHandler(NGramMap ngm){
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        for (String word : words) {
            TimeSeries ts = ngm.weightHistory(word, startYear, endYear);
            lts.add(ts);
            labels.add(word);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
