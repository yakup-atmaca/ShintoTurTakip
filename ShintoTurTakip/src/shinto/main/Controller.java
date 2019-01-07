package shinto.main;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import shinto.Launcher;

public class Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTabPane tabContainer;

     @FXML
    private Tab logoTab;
     
       @FXML
    private AnchorPane logoContainer;
    
    @FXML
    private Tab biletGirisiTab;

    @FXML
    private AnchorPane biletGirisiContainer;

    @FXML
    private Tab turlarTab;

    @FXML
    private AnchorPane turlarContainer;

    @FXML
    private Tab personelTab;

    @FXML
    private AnchorPane personelContainer;

    @FXML
    private Tab cariHesaplarTab;

    @FXML
    private AnchorPane cariHesaplarContainer;

    @FXML
    private Tab ucakBiletiTab;

    @FXML
    private AnchorPane ucakBiletiContainer;

    @FXML
    private Tab ayarlarTab;

    @FXML
    private AnchorPane ayarlarContainer;

    private double tabWidth = 100.0;
    public static int lastSelectedTabIndex = 1;

    @FXML
    public void initialize() {
        if (!Launcher.isSplashLoaded) {
            loadSplashScreen();
        }
        configureView();
    }

    private void loadSplashScreen() {
        try {
            Launcher.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("/shinto/splash/splash.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/shinto/main/main_view.fxml")));
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configureView() {
        tabContainer.setTabMinWidth(tabWidth);
        tabContainer.setTabMaxWidth(tabWidth);
        tabContainer.setTabMinHeight(200.0);
        tabContainer.setTabMaxHeight(200.0);
        tabContainer.setRotateGraphic(true);

        EventHandler<Event> replaceBackgroundColorHandler = event -> {
           // lastSelectedTabIndex = tabContainer.getSelectionModel().getSelectedIndex();

            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                currentTab.setStyle("-fx-background-color: -fx-focus-color;");
            } else {
                currentTab.setStyle("-fx-background-color: -fx-accent;");
            }
        };

        EventHandler<Event> logoutHandler = event -> {
            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                tabContainer.getSelectionModel().select(lastSelectedTabIndex);

                System.out.println("Logging out!");
            }
        };

         
        
        configureTab(biletGirisiTab, "Bilet\nSatış", "/shinto/res/ticket7.png", biletGirisiContainer, getClass().getResource("/shinto/biletgirisi/BiletGirisi.fxml"), replaceBackgroundColorHandler);
        configureTab(turlarTab, "Turlar", "/shinto/res/turlar1.png", turlarContainer, getClass().getResource("/shinto/turlar/Turlar.fxml"), replaceBackgroundColorHandler);
        configureTab(personelTab, "Personel", "/shinto/res/user-profile.png", ucakBiletiContainer, getClass().getResource("/shinto/ucakbileti/UcakBileti.fxml"), replaceBackgroundColorHandler);
        configureTab(ucakBiletiTab, "Uçak\nBileti", "/shinto/res/ticket7.png", personelContainer, getClass().getResource("/shinto/personel/Personel.fxml"), replaceBackgroundColorHandler);
        configureTab(cariHesaplarTab, "Cari\nHesaplar", "/shinto/res/cariHesap2.png", cariHesaplarContainer, getClass().getResource("/shinto/carihesaplar/CariHesaplar.fxml"), replaceBackgroundColorHandler);
        configureTab(ayarlarTab, "Ayarlar", "/shinto/res/settings.png", ayarlarContainer, getClass().getResource("/shinto/ayarlar/Ayarlar.fxml"), replaceBackgroundColorHandler);


        configureTab2(logoTab, "", "/shinto/res/back.jpg", logoContainer, getClass().getResource("/shinto/logo/Logo.fxml"), null);
        
        biletGirisiTab.setStyle("-fx-background-color: -fx-focus-color;");
    }

    private void configureTab(Tab tab, String title, String iconPath, AnchorPane containerPane, URL resourceURL, EventHandler<Event> onSelectionChangedEvent) {
        double imageWidth = 100.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);
        label.setMaxWidth(200);
        label.setPadding(new Insets(0, 0, 0, 0));
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14pt; -fx-font-weight: bold;");
        label.setTextAlignment(TextAlignment.CENTER);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(100.0);
        //tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText("");
        tab.setGraphic(tabPane);

        tab.setOnSelectionChanged(onSelectionChangedEvent);

        if (containerPane != null && resourceURL != null) {
            try {
                Parent contentView = FXMLLoader.load(resourceURL);
                containerPane.getChildren().add(contentView);
                AnchorPane.setTopAnchor(contentView, 0.0);
                AnchorPane.setBottomAnchor(contentView, 0.0);
                AnchorPane.setRightAnchor(contentView, 0.0);
                AnchorPane.setLeftAnchor(contentView, 0.0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 private void configureTab2(Tab tab, String title, String iconPath, AnchorPane containerPane, URL resourceURL, EventHandler<Event> onSelectionChangedEvent) {
        double imageWidth = 40.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        Label label = new Label(title);
        label.setMaxWidth(200);
        label.setPadding(new Insets(0, 0, 0, 0));
        label.setStyle("-fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: bold;");
        label.setTextAlignment(TextAlignment.LEFT);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(200.0);
        tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText("");
        tab.setGraphic(tabPane);

        tab.setOnSelectionChanged(onSelectionChangedEvent);

        if (containerPane != null && resourceURL != null) {
            try {
                Parent contentView = FXMLLoader.load(resourceURL);
                containerPane.getChildren().add(contentView);
                AnchorPane.setTopAnchor(contentView, 0.0);
                AnchorPane.setBottomAnchor(contentView, 0.0);
                AnchorPane.setRightAnchor(contentView, 0.0);
                AnchorPane.setLeftAnchor(contentView, 0.0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
