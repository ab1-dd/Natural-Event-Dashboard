package view;

import entity.NaturalEvent;
import interface_adapter.generateChart.ChartState;
import interface_adapter.generateChart.ChartViewModel;
import interface_adapter.generateChart.GenerateChartController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ChartView extends JDialog implements PropertyChangeListener {

    private final List<NaturalEvent> events;
    private final GenerateChartController controller;
    private final ChartViewModel viewModel;

    private final JSpinner unitSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 30, 1)); // 默认每 5 天为 1 单位
    private final ChartCanvasPanel canvasPanel = new ChartCanvasPanel();

    public ChartView(Window owner, List<NaturalEvent> events, GenerateChartController controller, ChartViewModel viewModel) {
        super(owner, "Event Frequency Histogram", ModalityType.MODELESS);
        this.events = events;
        this.controller = controller;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setSize(750, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        topPanel.add(new JLabel("Days per Unit (X-Axis):"));
        topPanel.add(unitSpinner);
        add(topPanel, BorderLayout.NORTH);

        add(canvasPanel, BorderLayout.CENTER);

        // call controller
        unitSpinner.addChangeListener(e -> triggerChartGeneration());


        triggerChartGeneration();
    }

    private void triggerChartGeneration() {
        int daysPerUnit = (Integer) unitSpinner.getValue();
        controller.generateChart(events, daysPerUnit);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ChartViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            // ViewModel 状态更新时重绘画布
            canvasPanel.repaint();
        }
    }

    private class ChartCanvasPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ChartState state = viewModel.getState();

            if (state.hasError()) {
                g.setColor(Color.RED);
                g.drawString("Error: " + state.getErrorMessage(), 20, 30);
                return;
            }

            int[] counts = state.getCounts();
            List<String> labels = state.getBinLabels();
            int maxCount = state.getMaxCount();

            if (counts == null || counts.length == 0) {
                g.drawString("No data available to display.", 20, 30);
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int numBins = counts.length;
            int padding = 50;
            int labelPadding = 25;
            int width = getWidth();
            int height = getHeight();

            int x0 = padding + labelPadding;
            int y0 = height - padding - labelPadding;
            int chartWidth = width - x0 - padding;
            int chartHeight = y0 - padding;

            // 绘制 X 轴与 Y 轴
            g2.setColor(Color.BLACK);
            g2.drawLine(x0, y0, x0 + chartWidth, y0);
            g2.drawLine(x0, y0, x0, y0 - chartHeight);

            // labels
            g2.drawString("Frequency (Count)", 10, y0 - chartHeight - 10);

            // making bar graph
            int barWidth = Math.max(1, chartWidth / numBins - 6);

            for (int i = 0; i < numBins; i++) {
                int barHeight = (int) (((double) counts[i] / maxCount) * chartHeight);
                int x = x0 + i * (chartWidth / numBins) + 3;
                int y = y0 - barHeight;

                // color filling
                g2.setColor(new Color(70, 130, 180));
                g2.fillRect(x, y, barWidth, barHeight);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x, y, barWidth, barHeight);

                // data above bars
                if (counts[i] > 0) {
                    g2.drawString(String.valueOf(counts[i]), x + barWidth / 2 - 4, y - 4);
                }

                // x axis label
                if (numBins < 12 || i % (numBins / 6 + 1) == 0) {
                    g2.drawString(labels.get(i), x, y0 + 15);
                }
            }
        }
    }
}