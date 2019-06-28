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
        //primaryStage.setScene(telaEntrada.getTelaEntrada());
        //primaryStage.show();

        TelaMips toper = new TelaMips(primaryStage, telaEntrada.getTelaEntrada(), "/home/conseg/Documentos/mipsSimulator/src/main/java/com/lippert/zanon/mips/TodasFuncionalidades.asm");
        Scene scene = toper.getTelaMips();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] agrs) {
        launch(agrs);
    }
}