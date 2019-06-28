package com.lippert.zanon.mips;

import java.io.File;
import java.io.FileInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaMips {
	private Stage mainStage;
	private Scene cenaEntrada;
    private Scene cenaMips;
    private Mips mips;
    private GridPane gridpaneImagemMips;

	public TelaMips(Stage mainStage, Scene telaEntrada, String path) { // conta
		this.mainStage = mainStage;
        this.cenaEntrada = telaEntrada;
        this.mips = new Mips();

        LeitorArquivoMips leitor = new LeitorArquivoMips(path);
        leitor.leArquivo();
        
        // boolean result = false;
        // do{
        //     result = mips.avancaClock();
            
        // }while(result == false);
	}

	public Scene getTelaMips() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Parte de cima da tela
        //Mips e registradores
        GridPane gridCIMA = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //imagem
        try {
            Image image1 = new Image(getClass().getClassLoader().getResource("mipsBase.png").toURI().toString());
            gridpaneImagemMips = new GridPane();
            ImageView im = new ImageView(image1);
            im.setFitHeight(400);
            im.setFitWidth(800);
            gridpaneImagemMips.getChildren().add(im);
            //gridpane.getChildren().remove(0);
            gridCIMA.add(gridpaneImagemMips, 0,0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridCIMA.add(getListaRegs(), 1,0);
        //Parte de baixo da tela
        //memoria e infos
        GridPane gridBAIXO = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        gridBAIXO.add(getListaMemoria(), 0, 0);
        grid.add(gridCIMA, 0,0);
        grid.add(gridBAIXO, 0,1);
		cenaMips = new Scene(grid);
		return cenaMips;
    }
    
    public ListView getListaRegs(){
        ListView<String> listView = new ListView<>();
        BlocoRegistradores bc = BlocoRegistradores.getInstance();
        for (int i = 0; i < 32; i++) {
            if(i < 10)
                listView.getItems().add("Reg "+i+":  "+bc.leToHex(i));
            else
            listView.getItems().add("Reg "+i+": "+bc.leToHex(i));
        }
        
        return listView;
    }

    public ListView getListaMemoria(){
        ListView<String> listView = new ListView<>();
        Memoria mem = Memoria.getInstance();
        String[] dados = mem.getDadosHex();
        listView.getItems().add("Base----------------------[+0]-----------[+4]-------------[+8]-------------[+c]-------------[+10]");
        //listView.getItems().add("0x00000000  0x00000000  0x00000000  0x00000000  0x00000000  0x00000000");
        for (int i = 0; i < dados.length; i+=5) {
            String res = Integer.toString(i+4194304, 2);
            int aux = 32 - res.length();
            for (int k = 0; k < aux;k++) {
                res = "0"+res;
            }
            if(dados.length - i >= 5){
                listView.getItems().add(mem.toHex(res)+"         "+dados[i]+"  "+dados[i+1]+"  "+dados[i+2]+"  "+dados[i+3]+"  "+dados[i+4]);
            }
        }
        listView.setPrefWidth(700);
        listView.setPrefHeight(200);
        return listView;
    }

}
