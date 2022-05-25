package com.doubleclick.DashBoard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doubleclick.ViewModel.RecentOrdersForSellerViewModel;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",};

    public final static String[] days = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    private LineChartData lineData;
    private ColumnChartData columnData;
    private LineChartView chart_top_Line;
    private ColumnChartView chart_bottom_Column;
    private RecentOrdersForSellerViewModel recentOrdersForSellerViewModel;
    private static final ArrayList<ArrayList<Integer>> yValue = new ArrayList<>();

    public StatisticsFragment() {
        // Required empty public constructor
    }


    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        recentOrdersForSellerViewModel = new ViewModelProvider(this).get(RecentOrdersForSellerViewModel.class);
        chart_top_Line = view.findViewById(R.id.chart_top_Line);
        chart_bottom_Column = view.findViewById(R.id.chart_bottom_Column);
        recentOrdersForSellerViewModel.getYearLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<ArrayList<Integer>>>>() {
            @Override
            public void onChanged(ArrayList<ArrayList<ArrayList<Integer>>> arrayLists) {
                Log.e("arrayLists = ", arrayLists.toString());
                generateColumnData(arrayLists);
            }
        });

        generateInitialLineData();
        return view;
    }

    private void generateColumnData(ArrayList<ArrayList<ArrayList<Integer>>> arrayLists) {

        int numSubcolumns = 1;
        int numColumns = months.length; // = 12

        List<AxisValue> axisValues = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        // i for month
        for (int i = 0; i < arrayLists.size(); i++) {
            int counter = 0;
            values = new ArrayList<>();

            ArrayList<Integer> yValueInMonth = new ArrayList<>();
            yValueInMonth.add(0); // to keep frist value is empty
            // j for days
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                // x for unkown number for order
//                Log.e("arrayLists.size", "" + arrayLists.get(i).size());
                counter = counter + arrayLists.get(i).get(j).size();
                Log.e("arraysgetigetjZ", "" + arrayLists.get(i).get(j).size());
                yValueInMonth.add(arrayLists.get(i).get(j).size());
                if (j == 30) {
                    values.add(new SubcolumnValue((float) counter /* value of Month */, ChartUtils.pickColor()));
                    Log.e("counter", "" + counter);
                }

//                for (int x = 0; x < arrayLists.get(i).get(j).size(); x++) {
//                    counter++;
//                    if (counter == arrayLists.get(i).get(j).size()) {
//                        Log.e("Print", "" + arrayLists.get(i).get(j).size());
//                        values.add(new SubcolumnValue((float) arrayLists.get(i).get(j).size() /* value of Month */, ChartUtils.pickColor()));
//                    }
//                }
//                if (counter == 0) {
//                    yValue.add(counter);
//                } else {
//                    yValue.add(counter);
//                }


            }
            /* to put names by months Bottom of Axis */
            axisValues.add(new AxisValue(i).setLabel(months[i]));
            /* to put Column by Column in ArrayList {@columns} */
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
            yValue.add(yValueInMonth);
        }
        columnData = new ColumnChartData(columns);
        Log.e("AllValuesInMonth", yValue.toString());

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        chart_bottom_Column.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        chart_bottom_Column.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        chart_bottom_Column.setValueSelectionEnabled(true);

        chart_bottom_Column.setZoomType(ZoomType.HORIZONTAL);


    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData() { // for Initial data only ...
        int numValues = days.length;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel(days[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        chart_top_Line.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chart_top_Line.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 10, 31, 0);
        chart_top_Line.setMaximumViewport(v);
        chart_top_Line.setCurrentViewport(v);
        chart_top_Line.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, int columnIndex) {
        // Cancel last animation if not finished.
        chart_top_Line.cancelDataAnimation();
        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line. ,i can make two lines or more
        line.setColor(color);
        for (PointValue value : line.getValues()) {
            // Change target only for Y value.
            try {
                Log.e("XValue", "" + value.getX());
                Log.e("YYYYVVVVValue", "" + yValue.get(columnIndex));
                Log.e("AllValue", value.toString());
                Log.e("YValueInMonth", "" + value.getX() + " " + yValue.get(2));
                value.setTarget(value.getX(), (float) yValue.get(columnIndex).get((int) value.getX()) /* value bar day */);
            } catch (Exception e) {
                Log.e("Exception(XY)Value", "" + e.getMessage());
            }

        }

        // Start new data animation with 300ms duration;
        chart_top_Line.startDataAnimation(400);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            generateLineData(value.getColor(), columnIndex);

//            Toast.makeText(getContext(), "columnIndex  = " + columnIndex, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onValueDeselected() {

            generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }
}