package spellchecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SpellChecker extends Application {
    
    static GUI gui = new GUI();
    
    public static ArrayList<String> dictionary      = new ArrayList();
    public static ArrayList<Word>   rawText         = new ArrayList();
    public static ArrayList<String> incorrectSpell  = new ArrayList();
    public static int 				numIncorrect 	= 0;
    public static int				numCorrect   	= 0;
    public static File 				f1 = new File("fulldictionary1.txt");
    public static File 				f2; 
    public static Scene 			scene;
    public static StackPane			root;
    
    @Override
    public void start(Stage primaryStage) {
    	root = new StackPane();
        root.getChildren().add(gui);
        root.setId("root");
        
        scene = new Scene(root, 700, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("StyleSheet.css").toExternalForm());
        
        primaryStage.setTitle("Spell Checker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        
        try {
            readDictionary();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpellChecker.hashDictionary();
        
        launch(args);
    }
    
    public static boolean isAlpha(String s){
        return s.matches("[a-zA-Z]+");
    }
    
    public static void readDictionary() throws FileNotFoundException{
        Scanner stdin = new Scanner(f1); 
        
        stdin.useDelimiter("\\s+");
        
        while(stdin.hasNext()){
            String s;
            s = stdin.next(); 
            
            dictionary.add(s);
        }
    }
    
    public static void readWords() throws FileNotFoundException{
    	rawText.clear();
        
        f2 = new File(GUI.fileName);
        Scanner stdin = new Scanner(f2);
        
        while (stdin.hasNext()){
            String s1 = stdin.next();
            String s2 = s1.replaceAll("[^a-zA-Z\\s]", " ").replaceAll("\\s+", " ");

            if (isAlpha(s2)){
                Word word = new Word(s1, s2);
                rawText.add(word);
            }    
        }
    }
    
    public static void readCustom(){
        rawText.clear();
        
        String s;
        s = GUI.ta.getText();
        
        Scanner stdin = new Scanner(s);
        
        while (stdin.hasNext()){
            String s1 = stdin.next();
            String s2 = s1.replaceAll("[^a-zA-Z\\s]", " ").replaceAll("\\s+", " ");

            if(isAlpha(s2)){
               Word word = new Word(s1, s2);
               rawText.add(word);
            }
        }
    }
    
    public static void hashDictionary(){
        HashTable dictionaryTable = new HashTable(dictionary.size() * 3);
        dictionaryTable.putDictionary(dictionary);
        int x = 0;
    }
    
    public static void checkSpelling(){
        GUI.flow.getChildren().clear();
        incorrectSpell.clear();
        numIncorrect = 0;
        
        for (Word w : rawText){
            if (GUI.start){
                if (isSpelledCorrectly(w)){
                    numCorrect++;
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.BLACK);
                    GUI.flow.getChildren().add(t1);
                } else {
                    incorrectSpell.add(w.formatted);
                    numIncorrect++;                
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.RED);
                    t1.setFont(Font.font(null, FontWeight.BOLD, 12));
                    GUI.flow.getChildren().add(t1);
                }
                
            } else if (GUI.browse) {
                if (isSpelledCorrectly(w)){
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.BLACK);
                    t1.setStyle("-fx-font-family: Arial; -fx-font-size: 12;");
                    GUI.flow.getChildren().add(t1);
                } else {                    
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.BLACK);
                    t1.setStyle("-fx-font-family: Arial; -fx-font-size: 12;");
                    GUI.flow.getChildren().add(t1);
                }
            } else if(GUI.change){
                if (isSpelledCorrectly(w)){                   
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.BLACK);
                    t1.setStyle("-fx-font-family: Arial; -fx-font-size: 12;");
                    GUI.flow.getChildren().add(t1);
                } else {                 
                    Text t1 = new Text(w.unformatted + " ");            
                    t1.setFill(Color.RED);
                    t1.setStyle("-fx-font-family: Arial; -fx-font-size: 12;");
                    GUI.flow.getChildren().add(t1);
                }
            }
        }
    }
    
    public static boolean isSpelledCorrectly(Word w){
        return HashTable.contains(w.formatted);
    }
    public static int getNumIncorrect(){
        return numIncorrect;
    }
    
    public static void changeWord(){
               for (Word w : rawText){
            if (GUI.previousWord.equals(w.formatted.toLowerCase())){
                w.unformatted = GUI.newWord;
                w.formatted   = GUI.newWord;
            }
        }
        
    }
}

