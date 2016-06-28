/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication8;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author FLAVIO-CX
 */
public class Motor extends Application{
    Stage stage;
    Scene scene;
    Group game;
    Canvas canvas;
    GraphicsContext graphic;
    ArrayList<String> input;
    int puntaje;
    
    double widthScreen;
    double heightScreen;
    
    public Motor(){
        puntaje = 0;
        input = new ArrayList<>();
    }
    
    public Parent getGame(){
        return game;
    }

    private void inicializaGame() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String tecla = event.getCode().toString();
                if(!input.contains(tecla)){
                    input.add(tecla);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String tecla = event.getCode().toString();
                if (input.contains(tecla)){
                    input.remove(tecla);
                }
            }
        });
        
        Sprite jugador1 = creaJugador();
        
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = now;

                graphic.clearRect(0, 0, widthScreen, heightScreen);
                
                if (input.contains("LEFT")){
                    jugador1.setAngulo(-5); //gira izquierda
                }
                if (input.contains("RIGHT")){
                    jugador1.setAngulo(5); //gira derecha
                }
                if (input.contains("UP")){
                    jugador1.update(elapsedTime, 100); //avanza
                }
                jugador1.setRotate(graphic);
                System.out.println(jugador1.toString());
                
                dibujaPuntaje(widthScreen - 110, 50); 
                
                graphic.setFill(Color.CORNFLOWERBLUE);
                graphic.fillOval(80, 80, 50, 50);
            }
        }.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        widthScreen = Screen.getPrimary().getVisualBounds().getWidth();
        heightScreen = Screen.getPrimary().getVisualBounds().getHeight();
        
        widthScreen = 400;
        heightScreen = 600;
        
        this.stage = primaryStage;
        canvas = new Canvas(widthScreen, heightScreen);
        canvas.getStyleClass().add(".fondoMenu");
        game = new Group(canvas);
        scene = new Scene(game);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setAlwaysOnTop(true);
        graphic = canvas.getGraphicsContext2D();
        
        inicializaGame();
        
        stage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }

    private void dibujaPuntaje(double x, int y) {
        Font theFont = Font.font("Roboto", FontWeight.BOLD, 16);
        graphic.setFont(theFont);
        graphic.setFill(Color.BLACK);
        graphic.fillText("Puntaje: " + puntaje, x, y);
    }

    private Sprite creaJugador() {
        Sprite jugador = new Sprite();
        Image jugador_img = new Image(getClass().getResourceAsStream("nave1.png"), 50, 50, true, true);
        jugador.setImage(jugador_img);
        jugador.setPosition(widthScreen / 2, heightScreen / 2);
        return jugador;
    }
    
    
}
