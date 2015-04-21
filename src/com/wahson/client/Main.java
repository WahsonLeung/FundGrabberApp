package com.wahson.client;

import com.wahson.service.IFundGrabberService;
import com.wahson.service.impl.FundGrabberServiceImpl;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        WebView webBrowser = createView();

        webBrowser.getEngine().load(String.valueOf(getClass().getClassLoader().getResource("resources/fundCal.html").toExternalForm()));
        Scene scene = new Scene(webBrowser, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public WebView createView() {
        WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                        if (newValue == Worker.State.SUCCEEDED) {
                            JSObject win = (JSObject) engine.executeScript("window");
                            IFundGrabberService service = new FundGrabberServiceImpl();
                            win.setMember("fundAppService", service);
                        }
                    }
                }
        );

        return webView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
