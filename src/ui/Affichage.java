package ui;

import java.util.*;

import calcul.Analyse;
import file.FileReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import object.GraphCombinaison;

public class Affichage extends Application {

  private static Map<String,XYChart.Data<String,Integer>> chartDataNumeros = new HashMap<>(50);
  private static Map<String,XYChart.Data<String,Integer>> chartDataEtoiles = new HashMap<>(50);
  private Label label = new Label();
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public void start(Stage stage) {

    //Lancement des analyses
    Analyse.init();
    FileReader.main(new String[0]);
    
    //Initialisation du graphique des numéros
    XYChart.Series seriesNumeros = new XYChart.Series();
    Analyse.mapFrequenceNumero.keySet().forEach(k -> {
      XYChart.Data<String,Integer> data = new XYChart.Data<>(k.toString(), 0);
      chartDataNumeros.put(k.toString(), data);
      data.setNode(new HoveredThresholdNode(Analyse.mapFrequenceNumero.get(k)));
      seriesNumeros.getData().add(data);
    });
    //Initialisation du graphique des étoiles
    XYChart.Series seriesEtoiles= new XYChart.Series();
    Analyse.mapFrequenceEtoile.keySet().forEach(k -> {
    	XYChart.Data<String,Integer> data = new XYChart.Data<>(k.toString(), 0);
    	chartDataEtoiles.put(k.toString(), data);
    	data.setNode(new HoveredThresholdNode(Analyse.mapFrequenceEtoile.get(k)));
    	seriesEtoiles.getData().add(data);
    });
   
    //Préparation de la scène
    NumberAxis yAxis = new NumberAxis();
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Chiffre");
    yAxis.setLabel("Nombre d'apparitions");
    NumberAxis yAxisEtoiles = new NumberAxis();
    CategoryAxis xAxisEtoiles = new CategoryAxis();
    xAxisEtoiles.setLabel("Etoile");
    yAxisEtoiles.setLabel("Nombre d'apparitions");
    BarChart<String, Number> bcNumeros = new BarChart<String, Number>(xAxis, yAxis);
    BarChart<String, Number> bcEtoiles = new BarChart<String, Number>(xAxisEtoiles, yAxisEtoiles);
    VBox vb = new VBox();
    HBox tableBox = new HBox();
    HBox chartBox = new HBox();
    TableView<GraphCombinaison> table = new TableView<>();
    TableView<GraphCombinaison> tableGains = new TableView<>();
    
    bcNumeros.getData().add(seriesNumeros);
    bcNumeros.setAnimated(true);
    bcNumeros.setBarGap(0);
    bcNumeros.setPrefWidth(800);
    bcNumeros.setLegendVisible(false);
    bcEtoiles.getData().add(seriesEtoiles);
    bcEtoiles.setAnimated(true);
    bcEtoiles.setBarGap(0);
    bcEtoiles.setPrefWidth(300);
    bcEtoiles.setLegendVisible(false);
    
    table.setItems(FXCollections.observableArrayList(Analyse.mapCombinaison.values())
                                .sorted(Comparator.comparing(GraphCombinaison::getCoefficient)
                                                  .reversed()));
    tableGains.setItems(FXCollections.observableArrayList(Analyse.mapCombinaison.values())
                                .sorted(Comparator.comparing(GraphCombinaison::getNbGains)
                                                   .reversed()));
    table.getColumns().addAll(this.creerColonne("Combinaison", "combinaison", 150),
                              this.creerColonne("1", "un", 50),
                              this.creerColonne("2", "deux", 50),
                              this.creerColonne("3", "trois", 50),
                              this.creerColonne("4", "quatre", 50),
                              this.creerColonne("5", "cinq", 50),
                              this.creerColonne("E1", "eUn", 50),
                              this.creerColonne("E2", "eDeux", 50));
    tableGains.getColumns().addAll(this.creerColonne("Combinaison", "combinaison", 150),
                                   this.creerColonne("Gains", "gains", 300),
                                   this.creerColonne("Total", "nbGains", 50),
                                   this.creerColonne("%", "pourcentageGains", 50));
    
    table.setPrefWidth((double) table.getColumns().stream()
                                                  .map(c -> ((TableColumn)c).getPrefWidth())
                                                  .reduce((w1,w2) -> ((double)w1)+((double)w2))
                                                  .orElse(0.0)
                                                  + 10);
    tableGains.setPrefWidth((double) tableGains.getColumns().stream()
                                                            .map(c -> ((TableColumn)c).getPrefWidth())
                                                            .reduce((w1,w2) -> ((double)w1)+((double)w2))
                                                            .orElse(0.0)
                                                            + 10);
    
    vb.getChildren().addAll(label,chartBox, tableBox);
    chartBox.getChildren().addAll(bcNumeros, bcEtoiles);
    tableBox.getChildren().addAll(table, tableGains);
    tableBox.setSpacing(30);
    Scene scene = new Scene(vb, 1300, 900);
    stage.setTitle("Test");
    stage.setScene(scene);
    stage.show();
    
    //Tâche de mise à jour l'interface
    update();
//    Timeline updateUI = new Timeline(new KeyFrame(Duration.seconds(1),(ActionEvent event) -> update()));
//    updateUI.setCycleCount(Timeline.INDEFINITE);
//    updateUI.play();
  }

  private void update() {
    Analyse.mapFrequenceNumero.keySet().forEach(k -> chartDataNumeros.get(k.toString()).setYValue(Analyse.mapFrequenceNumero.get(k)));
    Analyse.mapFrequenceEtoile.keySet().forEach(k -> chartDataEtoiles.get(k.toString()).setYValue(Analyse.mapFrequenceEtoile.get(k)));
    label.setText("Nombre de combinaisons : " + Analyse.nbCombinaisons);
    label.setFont(Font.font(20));
  }
  
  private TableColumn<GraphCombinaison,String> creerColonne(String libelle, String propriete, double largeur){
    TableColumn<GraphCombinaison,String> c = new TableColumn<>(libelle);
    c.setCellValueFactory(new PropertyValueFactory<GraphCombinaison, String>(propriete));
    c.setPrefWidth(largeur);
    return c;
  }
  
  class HoveredThresholdNode extends StackPane {
    HoveredThresholdNode(int value) {
      final Label label = createDataThresholdLabel(value);
      setPrefSize(15,15);
      setOnMouseEntered((MouseEvent mouseEvent) -> {
          getChildren().setAll(label);
          toFront();
        }
      );
      setOnMouseExited((MouseEvent mouseEvent) -> getChildren().clear());
    }

    private Label createDataThresholdLabel(int value) {
      final Label label = new Label(value + "");
      label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
      label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
      label.setTextFill(Color.FIREBRICK);
      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      return label;
    }
  }
  
  public static void main(String[] args) {
    launch(args);
  }

}