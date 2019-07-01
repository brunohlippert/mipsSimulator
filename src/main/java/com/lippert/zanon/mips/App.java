package com.lippert.zanon.mips;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    private TelaEntrada telaEntrada;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MipsSimulator");

        telaEntrada = new TelaEntrada(primaryStage); 
        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    public static void main(String[] agrs) {
        launch(agrs);
    }
}