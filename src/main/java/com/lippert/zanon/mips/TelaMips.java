package com.lippert.zanon.mips;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.List;

import com.lippert.zanon.mips.Controle.estados;
import com.lippert.zanon.mips.Controle.instrucoes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TelaMips {
	private Stage mainStage;
	private Scene cenaEntrada;
    private Scene cenaMips;
    private Mips mips;
    private GridPane gridpaneImagemMips;
    private ImageView ImageViewMIPS;
    private ListView<String> listMemoria;
    private ListView<String> listRegs;
    private ListView<String> listRegsInvisiveis;
    private ListView<String> listSinaisControle;
    private Label InstrucaoAtual, EstadoAtual;

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

            ImageViewMIPS = new ImageView(image1);
            ImageViewMIPS.setFitHeight(400);
            ImageViewMIPS.setFitWidth(800);
            gridpaneImagemMips.getChildren().add(ImageViewMIPS);
            //gridpane.getChildren().remove(0);
            gridCIMA.add(gridpaneImagemMips, 0,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listRegs= new ListView<String>();
        listRegs.setItems(getListaRegs().getItems());
        gridCIMA.add(listRegs, 1,0);


        //Parte de baixo da tela
        //memoria e infos
        GridPane gridBAIXO = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        listMemoria = new ListView<String>();
        listMemoria.setPrefWidth(700);
        listMemoria.setPrefHeight(200);
        listMemoria.setItems(getListaMemoria().getItems());
        gridBAIXO.add(listMemoria, 0, 0);

        listRegsInvisiveis= new ListView<String>();
        listRegsInvisiveis.setPrefWidth(180);
        listRegsInvisiveis.setPrefHeight(200);
        listRegsInvisiveis.setItems(getRegsInvisiveis().getItems());
        gridBAIXO.add(listRegsInvisiveis, 1, 0);


        listSinaisControle= new ListView<String>();
        listSinaisControle.setPrefWidth(175);
        listSinaisControle.setPrefHeight(200);
        listSinaisControle.setItems(getSinaisControle().getItems());
        gridBAIXO.add(listSinaisControle, 2, 0);
        
        GridPane gridButton = new GridPane();
        gridButton.setAlignment(Pos.CENTER);
        gridButton.setPadding(new Insets(5, 5, 5, 5));

        Button btnAvancaClock = new Button("Avança clock");
        Button btnParaFim = new Button("Rodar tudo");

        btnAvancaClock.setOnAction(e -> {
            btnAvancaClock.setDisable(true);
           boolean fim = mips.avancaClock();
           if(fim){
            btnParaFim.setDisable(true);
            btnAvancaClock.setDisable(true);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fim do programa");
            alert.setHeaderText("Execução concluída");

            alert.showAndWait();

           }else {
            try {
                Image image1 = new Image(getClass().getClassLoader().getResource(Controle.getInstance().img).toURI().toString());
                gridpaneImagemMips = new GridPane();
                ImageViewMIPS = new ImageView(image1);
                ImageViewMIPS.setFitHeight(400);
                ImageViewMIPS.setFitWidth(800);
                gridpaneImagemMips.getChildren().add(ImageViewMIPS);
                //gridpane.getChildren().remove(0);
                gridCIMA.add(gridpaneImagemMips, 0,0);
            } catch (Exception es) {
                es.printStackTrace();
            }
            listMemoria.setItems(getListaMemoria().getItems());
            listRegs.setItems(getListaRegs().getItems());
            listRegsInvisiveis.setItems(getRegsInvisiveis().getItems());
            listSinaisControle.setItems(getSinaisControle().getItems());
            if(Controle.getInstance().getEstadoAtual() == estados.BUSCA)
                InstrucaoAtual.setText("Instrução Atual: ");
            else{
                if(Controle.getInstance().getInstrucaoAtual() == instrucoes.Tipo_R){
                    if(Controle.getInstance().getEstadoAtual() == estados.EXEC)
                        InstrucaoAtual.setText("Instrução Atual: "+ULA.getInstance().getTipor());
                    else if(Controle.getInstance().getEstadoAtual() == estados.DECODE)
                        InstrucaoAtual.setText("Instrução Atual: "+instrucoes.Tipo_R.name());
                }else
                    InstrucaoAtual.setText("Instrução Atual: "+Controle.getInstance().getInstrucaoAtual().name());
            }
                
            EstadoAtual.setText("Estado Atual: "+Controle.getInstance().getEstadoAtual().name());
           }
           btnAvancaClock.setDisable(false);
        });

        btnParaFim.setOnAction(e -> {
            boolean fim = false;
            do{
                fim = mips.avancaClock();
            }while(!fim);
            
            try {
                Image image1 = new Image(getClass().getClassLoader().getResource(Controle.getInstance().img).toURI().toString());
                gridpaneImagemMips = new GridPane();
                ImageViewMIPS = new ImageView(image1);
                ImageViewMIPS.setFitHeight(400);
                ImageViewMIPS.setFitWidth(800);
                gridpaneImagemMips.getChildren().add(ImageViewMIPS);
                //gridpane.getChildren().remove(0);
                gridCIMA.add(gridpaneImagemMips, 0,0);
            } catch (Exception es) {
                es.printStackTrace();
            }
            listMemoria.setItems(getListaMemoria().getItems());
            listRegs.setItems(getListaRegs().getItems());
            listRegsInvisiveis.setItems(getRegsInvisiveis().getItems());
            listSinaisControle.setItems(getSinaisControle().getItems());
            if(Controle.getInstance().getEstadoAtual() == estados.BUSCA)
                InstrucaoAtual.setText("Instrução Atual: ");
            else{
                if(Controle.getInstance().getInstrucaoAtual() == instrucoes.Tipo_R){
                    if(Controle.getInstance().getEstadoAtual() == estados.EXEC)
                        InstrucaoAtual.setText("Instrução Atual: "+ULA.getInstance().getTipor());
                    else if(Controle.getInstance().getEstadoAtual() == estados.DECODE)
                        InstrucaoAtual.setText("Instrução Atual: "+instrucoes.Tipo_R.name());
                }else
                    InstrucaoAtual.setText("Instrução Atual: "+Controle.getInstance().getInstrucaoAtual().name());
            }

            EstadoAtual.setText("Estado Atual: "+Controle.getInstance().getEstadoAtual().name());
            btnParaFim.setDisable(true);
            btnAvancaClock.setDisable(true);
        });
        
        gridButton.add(btnAvancaClock, 0, 0);
        gridButton.add(btnParaFim, 1, 0);
        InstrucaoAtual = new Label("Instrução Atual: ");
        EstadoAtual = new Label("Estado Atual: ");

        GridPane gridExec = new GridPane();
        gridExec.setAlignment(Pos.CENTER);
        gridExec.setPadding(new Insets(5, 5, 5, 5));

        gridExec.add(InstrucaoAtual, 0, 0);
        gridExec.add(EstadoAtual, 0, 1);

        gridButton.add(gridExec, 2, 0);

        grid.add(gridCIMA, 0,0);
        grid.add(gridBAIXO, 0,1);
        grid.add(gridButton, 0,2);
		cenaMips = new Scene(grid);
		return cenaMips;
    }
    
    public ListView<String> getListaRegs(){
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

    public ListView<String> getListaMemoria(){
        ListView<String> listView = new ListView<>();
        Memoria mem = Memoria.getInstance();
        String[] dados = mem.getDadosHex();
        listView.getItems().add("---------- Memória ----------");
        listView.getItems().add("Base----------------------[+0]-----------[+4]-------------[+8]-------------[+c]-------------[+10]");
        //listView.getItems().add("0x00000000  0x00000000  0x00000000  0x00000000  0x00000000  0x00000000");
        for (int i = 0; i < dados.length; i+=5) {
            String res = Integer.toString((i*4)+4194304, 2);
            int aux = 32 - res.length();
            for (int k = 0; k < aux;k++) {
                res = "0"+res;
            }
            if(dados.length - i >= 5){
                listView.getItems().add(mem.toHex(res)+"         "+dados[i]+"  "+dados[i+1]+"  "+dados[i+2]+"  "+dados[i+3]+"  "+dados[i+4]);
            }else {
                String linha = mem.toHex(res)+"         ";
                for(int k = i; k < dados.length; k++){
                    linha+= dados[k]+"  ";
                }
                listView.getItems().add(linha);
            }
        }
        listView.setPrefWidth(700);
        listView.setPrefHeight(200);
        return listView;
    }

    public ListView<String> getRegsInvisiveis(){
        ListView<String> listView = new ListView<>();
        Memoria mem = Memoria.getInstance();
        BlocoRegistradores bc = BlocoRegistradores.getInstance();
        String pcTxt = Integer.toString((mips.getPC()*4)+4194304, 2);
        int aux = 32 - pcTxt.length();
        for (int k = 0; k < aux;k++) {
            pcTxt = "0"+pcTxt;
        }
        listView.getItems().add("Registradores inivisiveis");
        listView.getItems().add("PC: "+mem.toHex(pcTxt));
        listView.getItems().add("IR: "+mem.toHex(mips.getRegInstrucao()));
        listView.getItems().add("RegA: "+mem.toHex(mips.getRegA()));
        listView.getItems().add("RegB: "+mem.toHex(mips.getRegB()));
        if(Controle.getInstance().getEstadoAtual() == estados.BUSCA){
            int end = new BigInteger(mips.getRegUlaSaida(), 2).intValue();
            String endAux = Integer.toString((end*4)+4194304, 2);
            int aux2 = 32 - endAux.length();
            for (int k = 0; k < aux2;k++) {
                endAux = "0"+endAux;
            }

            listView.getItems().add("UlaSaida: "+mem.toHex(endAux));
        }else{
            listView.getItems().add("UlaSaida: "+mem.toHex(mips.getRegUlaSaida()));
        }
       
        listView.getItems().add("RegMem: "+mem.toHex(mips.getRegDadosMemoria ()));
        
        return listView;
    }

    public ListView<String> getSinaisControle(){
        ListView<String> listView = new ListView<>();
        Controle ctr = Controle.getInstance();
        listView.getItems().add("Sinais controle");
        listView.getItems().add("PCEscCond: "+ctr.PCEscCond);
        listView.getItems().add("PCEsc: "+ctr.PCEsc);
        listView.getItems().add("louD: "+ctr.louD);
        listView.getItems().add("LerMem: "+ctr.LerMem);
        listView.getItems().add("EscMem: "+ctr.EscMem);
        listView.getItems().add("MemParaReg: "+ctr.MemParaReg);
        listView.getItems().add("IREsc: "+ctr.IREsc);
        listView.getItems().add("FontePC: "+ctr.FontePC);
        listView.getItems().add("ULAOp: "+ctr.ULAOp);
        listView.getItems().add("ULAFonteB: "+ctr.ULAFonteB);
        listView.getItems().add("ULAFonteA: "+ctr.ULAFonteA);
        listView.getItems().add("EscReg: "+ctr.EscReg);
        listView.getItems().add("RegDst: "+ctr.RegDst);
        
        return listView;
    }

}
