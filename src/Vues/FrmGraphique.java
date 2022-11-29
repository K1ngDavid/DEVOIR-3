package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;
    DefaultCategoryDataset dataset;
    JFreeChart chart;
    ChartPanel graph;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        dataset = new DefaultCategoryDataset();
        for(int age : ctrlGraphique.getDataGraphique1().keySet()){
            dataset.setValue(ctrlGraphique.getDataGraphique1().get(age),"0",String.valueOf(age));
            System.out.println(String.valueOf(age));
        }

        chart = ChartFactory.createLineChart(
                "Moyenne des salaires par âge",
                "Age",
                "Salaire (€)",
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false
        );
        graph = new ChartPanel(chart);
        pnlGraph1.add(graph);
        pnlGraph1.validate();

        DefaultPieDataset pie_dataset = new DefaultPieDataset();
        for(String sexe : ctrlGraphique.getDataGraphique2().keySet()){
            pie_dataset.setValue(sexe,ctrlGraphique.getDataGraphique2().get(sexe));
        }

        chart = ChartFactory.createRingChart(
                "Pourcentage de femmes et d'hommes",
                pie_dataset,
                true,false,false
        );
        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        graph = new ChartPanel(chart);
        graph.setMouseWheelEnabled(true);

        pnlGraph3.add(graph);

        pnlGraph3.validate();



        DefaultKeyedValues2DDataset dataset1 = new DefaultKeyedValues2DDataset();


        for(int index : ctrlGraphique.getDataGraphique3().keySet()){
            dataset1.addValue(Double.parseDouble(ctrlGraphique.getDataGraphique3().get(index).get(0)),ctrlGraphique.getDataGraphique3().get(index).get(1),ctrlGraphique.getDataGraphique3().get(index).get(2));
            dataset1.addValue(Double.parseDouble(ctrlGraphique.getDataGraphique3().get(index).get(3)),ctrlGraphique.getDataGraphique3().get(index).get(4),ctrlGraphique.getDataGraphique3().get(index).get(5));
        }


        chart = ChartFactory.createStackedBarChart(
                "Pyramide des âges",
                "Age",
                "Hommes/Femmes",
                dataset1,
                PlotOrientation.HORIZONTAL,
                true,true,false
        );
        graph = new ChartPanel(chart);
        pnlGraph2.add(graph);
        pnlGraph2.validate();

        dataset = new DefaultCategoryDataset();
        for(int index : ctrlGraphique.getDataGraphique4().keySet()){
            dataset.setValue(Double.parseDouble(ctrlGraphique.getDataGraphique4().get(index)[0]),ctrlGraphique.getDataGraphique4().get(index)[2],ctrlGraphique.getDataGraphique4().get(index)[1]);
        }

        chart = ChartFactory.createBarChart(
                "Montant des ventes par magasin",
                "Magasins",
                "Montan des ventes",
                dataset,
                PlotOrientation.VERTICAL,
                true,false,false
        );
        graph = new ChartPanel(chart);
        pnlGraph4.add(graph);
        pnlGraph4.validate();
    }


}
