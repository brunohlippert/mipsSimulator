package com.lippert.zanon.mips;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TelaEntrada {
	private Stage mainStage; 
    private Scene cenaEntrada; 
    final FileChooser fileChooser = new FileChooser();
    final Button openButton = new Button("Abrir arquivo");
    private File file;

	private TextField tfContaCorrente;

	public TelaEntrada(Stage anStage) {
		mainStage = anStage;
	}

	public Scene getTelaEntrada() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		// grid.setGridLinesVisible(true);

		Text scenetitle = new Text("Selecione um arquivo .asm no formato aceito");
		scenetitle.setId("welcome-text");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("Arquivo:");
		grid.add(userName, 0, 1);

        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    file = fileChooser.showOpenDialog(mainStage);
                }
            });

		grid.add(openButton, 1, 1);

		Button btnIn = new Button("Simular");
		Button btnOut = new Button("Encerrar");
		HBox hbBtn = new HBox(30);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(btnIn);
		hbBtn.getChildren().add(btnOut);
		grid.add(hbBtn, 1, 4);

		// Botao encerrar
		btnOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});

		// Botao entrar
		btnIn.setOnAction(e -> {
			if(file == null){

            }else{
                try{
                    TelaMips toper = new TelaMips(mainStage, cenaEntrada, file.getAbsolutePath());
                    Scene scene = toper.getTelaMips();
                    mainStage.setScene(scene);
                }catch(Exception es){

                }
                
            }
		});

		cenaEntrada = new Scene(grid);
		return cenaEntrada;
    }
}
