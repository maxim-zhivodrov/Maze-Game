package View;

import java.applet.AudioClip;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.applet.AppletAudioClip;
import Server.Configurations;


public class MyViewController implements Observer,IView
{
    public static Stage root;
    public static String mazeRows;
    public static String mazeColumns;
    private static MyViewModel SmyViewModel;
    private static boolean[] trainerWallBooleanArray=new boolean[6];;
    private static boolean isGoalReached;
    private boolean goodRow=true;
    private boolean goodCol=true;

    public static MediaPlayer mp;
    private Stage playStage=new Stage();
    private MyViewModel myViewModel;
    @FXML
    public Pane pane;
    //My Additions 08/06
    public BorderPane Bpane;
    public MenuItem saveView;
    ////
    public MenuBar menuIntro;
    public Button playButton;
    public BorderPane MainPane;
    public MazeDisplayer mazeDisplayer;
    public TextField textField_mazeRowss;
    public TextField textField_mazeColumnss;
    public Button catchButton;
    public Button solutionButton;
    public CharDisplayer charDisplayer;
    public SolutionDisplayer solutionDisplayer;
    public MenuItem mute;
    public MenuItem unmute;
    public RadioMenuItem instrumentalBUT;
    public RadioMenuItem hebrewBUT;
    public RadioMenuItem englishBUT;
    public Button ashBUT;
    public Button mistiBUT;
    public Button brendanBUT;
    public Button grassBUT;
    public Button rockBUT;
    public Button treeBUT;
    public Label algLAB;
    public Label tpLAB;
    public Label slvLAB;
    public Button closeProp;
    public Button closeAboutBUT;
    public Button closeINS;
    public MenuItem loadView;


    public double  currX;
    public double  currY;

    public void setMyViewModel(MyViewModel viewModel) {
        this.myViewModel = viewModel;
        SmyViewModel=viewModel;
    }

    public static void setMVM(MyViewModel mvm){SmyViewModel=mvm;}
    public static MyViewModel getMVM(){return SmyViewModel;}
    public static void setIsGoalReached(boolean flag){ isGoalReached=flag; }
    public static boolean getIsGoalReached(){return isGoalReached;}

    @Override
    public void update(Observable o, Object arg) {
        if (o == myViewModel) {
            if(myViewModel.isMazeGenerated()){
                displayMaze(myViewModel.getMaze());
                myViewModel.setMazeGenerated(false);
            }
            else if(myViewModel.isCharMoved()){
                this.charDisplayer.changePos(myViewModel.getDirectory());
                myViewModel.setCharMoved(false);
                //My Additions 07/06 1:46
                if(isGoalReached){
                    displayersCanvasClean();
                    this.solutionButton.setDisable(true);
                    isGoalReached=false;
                }
                //////
            }
            else if(myViewModel.isSolved()){
                Solution x=myViewModel.getSol();
                this.solutionDisplayer.setSol(myViewModel.getSol());
                this.solutionDisplayer.setMaze(myViewModel.getMaze());
                this.solutionDisplayer.redraw();
                myViewModel.setSolved(false);
            }
        }
    }

    public void displayMaze(Maze maze){
        //My Additions 07/06 00:22
        if(mazeRows!=null&&mazeColumns!=null){
            this.catchButton.setDisable(false);
        }
        this.solutionButton.setDisable(false);
        this.saveView.setDisable(false);
        if(trainerWallBooleanArray[0])
            this.charDisplayer.setAsh();
        if(trainerWallBooleanArray[1])
            this.charDisplayer.setMisty();
        if(trainerWallBooleanArray[2])
            this.charDisplayer.setBrendan();
        if(trainerWallBooleanArray[3])
            this.mazeDisplayer.setGrass();
        if(trainerWallBooleanArray[4])
            this.mazeDisplayer.setRock();
        if(trainerWallBooleanArray[5])
            this.mazeDisplayer.setTree();
        ////
        this.mazeDisplayer.setDimentions(maze);
        this.charDisplayer.setMaze(maze);
        this.charDisplayer.redraw();
        this.solutionDisplayer.cleanCanvas();
    }

    public void generateMaze() throws FileNotFoundException {
//        catchButton.setOpacity(0);
        //My Additions 07/06 00:57
        this.catchButton.setDisable(true);
        this.solutionButton.setDisable(true);
        //////
        int rows = Integer.parseInt(mazeRows);
        int columns = Integer.parseInt(mazeColumns);

        mazeDisplayer.widthProperty().bind(pane.widthProperty());
        mazeDisplayer.heightProperty().bind(pane.heightProperty());
        charDisplayer.widthProperty().bind(pane.widthProperty());
        charDisplayer.heightProperty().bind(pane.heightProperty());
        playTheme();

        this.myViewModel.generateMaze(rows,columns);
        this.catchButton.setDisable(false);

        mute.setDisable(false);
        unmute.setDisable(true);
    }

    public void showSolution()
    {
        solutionDisplayer.widthProperty().bind(pane.widthProperty());
        solutionDisplayer.heightProperty().bind(pane.heightProperty());
        this.myViewModel.showSolution(this.charDisplayer.getCharCurrPosRow(),this.charDisplayer.getCharCurrPosCol());

    }

    public void KeyPressed(KeyEvent keyEvent) throws FileNotFoundException {

        myViewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }


    /*------------------------------MAZE---------------------------------*/

    public void setAbout() throws IOException {

        Stage aboutStage=new Stage();
        aboutStage.setTitle("About us...");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent playRoot = fxmlLoader.load(getClass().getResource("../View/MyAbout.fxml").openStream());
        Scene aboutScene=new Scene(playRoot, 700, 400);

        aboutStage.setScene(aboutScene);

        aboutStage.show();
    }
    public void closeAbout()
    {
        ((Stage)closeAboutBUT.getScene().getWindow()).close();
    }

    public void playThemeHebrew()
    {
        if(hebrewBUT.isSelected())
        {
            instrumentalBUT.setSelected(false);
            hebrewBUT.setSelected(true);
            englishBUT.setSelected(false);
            audio.playThemeHebrew();
        }
        hebrewBUT.setSelected(true);
    }
    public void playThemeEnglish()
    {
        if(englishBUT.isSelected()) {
            instrumentalBUT.setSelected(false);
            hebrewBUT.setSelected(false);
            englishBUT.setSelected(true);
            audio.playThemeEnglish();
        }
        englishBUT.setSelected(true);

    }
    public void playThemeInstrumental()
    {
        if(instrumentalBUT.isSelected()) {
            instrumentalBUT.setSelected(true);
            hebrewBUT.setSelected(false);
            englishBUT.setSelected(false);
            audio.playThemeInstrumental();
        }
        instrumentalBUT.setSelected(true);

    }
    public void playTheme()
    {
        instrumentalBUT.setSelected(true);
        hebrewBUT.setSelected(false);
        englishBUT.setSelected(false);
        audio.playTheme();
    }
    public void playStart() { audio.playStart(); }


    public void musicOptions()
    {
        MediaPlayer currSong=audio.theme;
        double currVolume=audio.theme.getVolume();
        Stage musicOptions=new Stage();
        musicOptions.initModality(Modality.APPLICATION_MODAL);
        VBox layout=new VBox();
        Label congrats=new Label("You are Welcome to change any settings you want, we won't be affended ");
        Button close=new Button("close");
        Button IncreaseVol=new Button("Increase Volume");
        Button DecreaseVol=new Button("Decrease Volume");
        Button pause=new Button("Pause Song");
        Button resume=new Button("Resume Song");
        pause.setOnAction(e -> {
            audio.theme.pause();
            audio.currSec=audio.theme.getCurrentTime();
        });
        resume.setOnAction(e ->
                {
                    audio.theme.seek(audio.currSec);
                    audio.theme.play();
                });
        IncreaseVol.setOnAction(e -> audio.theme.setVolume(audio.theme.getVolume()+100));
        DecreaseVol.setOnAction(e -> audio.theme.setVolume(audio.theme.getVolume()-1));
        close.setOnAction(e -> {
            musicOptions.close();
        });
        layout.getChildren().addAll(congrats,pause,resume,IncreaseVol,DecreaseVol,close);
        Scene scene=new Scene(layout,400,200);
        musicOptions.setScene(scene);
        musicOptions.showAndWait();
    }

    public void muteFunc()
    {
        unmute.setDisable(false);
        audio.theme.setMute(true);
        mute.setDisable(true);
    }

    public void unmuteFunc()
    {
        mute.setDisable(false);
        audio.theme.setMute(false);
        unmute.setDisable(true);
    }

    public void Xclose()
    {
        this.myViewModel.exit();
    }

    public void exit()
    {
        this.myViewModel.exit();
        if(catchButton!=null)
            ((Stage)catchButton.getScene().getWindow()).close();
        if(menuIntro!=null)
            ((Stage)menuIntro.getScene().getWindow()).close();

    }

    /*------------------------------INTRO---------------------------------*/



    public void openPlay(ActionEvent e) throws IOException {
        if(menuIntro !=null)
            root = (Stage) menuIntro.getScene().getWindow();
//        playStage.initModality(Modality.APPLICATION_MODAL);
        playStage.setTitle("Make Your Game Awesome!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent playRoot = fxmlLoader.load(getClass().getResource("../View/playOptions.fxml").openStream());
        Scene playScene=new Scene(playRoot, 700, 400);
        playScene.getStylesheets().add(getClass().getResource("playOptions.css").toExternalForm());
        //My Addition 07/06 01:16
        MyViewController view=fxmlLoader.getController();
        if (view.playButton!=null) {
            view.playButton.setDisable(true);
        }
        /////
        playStage.setScene(playScene);
        playStage.setOnCloseRequest(event -> resetTrainerWallArray());
        playStart();
        playStage.show();
    }

    /*------------------------------PLAY---------------------------------*/


    public void playGame() throws IOException, InterruptedException {

        mazeRows=textField_mazeRowss.getText();
        mazeColumns=textField_mazeColumnss.getText();
        ((Stage)playButton.getScene().getWindow()).close();
        setMainStyle();

    }

    public void cancel()
    {
        ((Stage)playButton.getScene().getWindow()).close();
    }

    public void saveMaze(){
        this.myViewModel.saveMaze(this.charDisplayer.getCharCurrPosRow(),this.charDisplayer.getCharCurrPosCol());
        this.loadView.setDisable(false);
    }

    public void loadMaze() throws IOException {
            if(menuIntro !=null)
                root = (Stage) menuIntro.getScene().getWindow();
            MyViewController view=setMainStyle();
            //Similar to generating maze
            view.mazeDisplayer.widthProperty().bind(view.pane.widthProperty());
            view.mazeDisplayer.heightProperty().bind(view.pane.heightProperty());
            view.charDisplayer.widthProperty().bind(view.pane.widthProperty());
            view.charDisplayer.heightProperty().bind(view.pane.heightProperty());
            view.playTheme();
            view.myViewModel.loadMaze();
            if(mazeRows==null||mazeColumns==null)
                view.catchButton.setDisable(true);
            view.mute.setDisable(false);
            view.unmute.setDisable(true);
            /////
    }

    //My Additions 07/06 00:22
    public void chooseAsh(){
        trainerWallBooleanArray[0]=true;
        trainerWallBooleanArray[1]=false;
        trainerWallBooleanArray[2]=false;
        ashBUT.setStyle("-fx-background-color: #a6ff2f");
        mistiBUT.setStyle("");
        brendanBUT.setStyle("");
        releasePlayButton();
    }
    public void chooseMisty(){
        trainerWallBooleanArray[0]=false;
        trainerWallBooleanArray[1]=true;
        trainerWallBooleanArray[2]=false;
        ashBUT.setStyle("");
        mistiBUT.setStyle("-fx-background-color: #a6ff2f");
        brendanBUT.setStyle("");
        ashBUT.setStyle("");
        releasePlayButton();
    }
    public void chooseBrendan(){
        trainerWallBooleanArray[0]=false;
        trainerWallBooleanArray[1]=false;
        trainerWallBooleanArray[2]=true;
        ashBUT.setStyle("");
        mistiBUT.setStyle("");
        brendanBUT.setStyle("-fx-background-color: #a6ff2f");
        releasePlayButton();
    }
    public void chooseGrass(){
        trainerWallBooleanArray[3]=true;
        trainerWallBooleanArray[4]=false;
        trainerWallBooleanArray[5]=false;
        grassBUT.setStyle("-fx-background-color: #a6ff2f");
        rockBUT.setStyle("");
        treeBUT.setStyle("");
        releasePlayButton();
    }
    public void chooseRock(){
        trainerWallBooleanArray[3]=false;
        trainerWallBooleanArray[4]=true;
        trainerWallBooleanArray[5]=false;
        grassBUT.setStyle("");
        rockBUT.setStyle("-fx-background-color: #a6ff2f");
        treeBUT.setStyle("");
        releasePlayButton();
    }
    public void chooseTree(){
        trainerWallBooleanArray[3]=false;
        trainerWallBooleanArray[4]=false;
        trainerWallBooleanArray[5]=true;
        grassBUT.setStyle("");
        rockBUT.setStyle("");
        treeBUT.setStyle("-fx-background-color: #a6ff2f");
        releasePlayButton();
    }
    private void releasePlayButton(){
        int counter=0;
        for(int i=0;i<6;i++){
            if(trainerWallBooleanArray[i]==true)
                counter++;
        }
        if(counter==2 && goodRow && goodCol){
            this.playButton.setDisable(false);
        }
        else this.playButton.setDisable(true);
    }
    public static void resetTrainerWallArray(){
        for(int i=0;i<6;i++)
            trainerWallBooleanArray[i]=false;
    }

    public void zoom(ScrollEvent event){
        if (event.isControlDown()) {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 0.95;
            }
            if(pane!=null){
                pane.setScaleX(pane.getScaleX() * zoomFactor);
                pane.setScaleY(pane.getScaleY() * zoomFactor);
            }
        }
        event.consume();
    }

    private void displayersCanvasClean(){
        if(this.mazeDisplayer!=null)
            this.mazeDisplayer.cleanCanvas();
        if(this.charDisplayer!=null)
            this.charDisplayer.cleanCanvas();
        if(this.solutionDisplayer!=null)
            this.solutionDisplayer.cleanCanvas();
    }

    public void mouseDrag(MouseEvent e){
        if (this.mazeDisplayer!=null&&this.mazeDisplayer.getMaze()!=null) {
            double cellHeight=(this.mazeDisplayer.getHeight())/(this.mazeDisplayer.getMaze().getRows());
            double cellWidth=(this.mazeDisplayer.getWidth())/(this.mazeDisplayer.getMaze().getColumns());
            if(e.getSceneY()-this.currY>cellHeight){
                this.myViewModel.mouseDragDown();
                this.currY=e.getSceneY();
            }
            else if(e.getSceneY()-this.currY<-cellHeight){
                this.myViewModel.mouseDragUp();
                this.currY=e.getSceneY();
            }
            else if(e.getSceneX()-this.currX>cellWidth){
                this.myViewModel.mouseDragRight();
                this.currX=e.getSceneX();
            }
            else if(e.getSceneX()-this.currX<-cellWidth){
                this.myViewModel.mouseDragLeft();
                this.currX=e.getSceneX();
            }
        }

        e.consume();
    }

    public void setCurrXY(MouseEvent e){
        this.currX=e.getSceneX();
        this.currY=e.getSceneY();
        e.consume();
}

    private MyViewController setMainStyle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent viewRoot = fxmlLoader.load(getClass().getResource("../View/MyView.fxml").openStream());
        Scene viewScene=new Scene(viewRoot, 800, 500);
        viewScene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        MyViewController view = fxmlLoader.getController();
        view.loadView.setDisable(!((new File("savedMaze.maze")).exists()));
        view.myViewModel=SmyViewModel;
        SmyViewModel.addObserver(view);
        view.solutionButton.setDisable(true);
        view.saveView.setDisable(true);
        root.setScene(viewScene);
        root.show();
        return view;
    }

    public void checkRowInput()
    {
        try
        {
            int rows=Integer.parseInt(textField_mazeRowss.getText());
            if(rows>0) goodRow=true;
            else goodRow=false;
        }
        catch (NumberFormatException e)
        {
            goodRow=false;
        }

        if(goodRow) textField_mazeRowss.setStyle("-fx-background-color: #a6ff2f");
        else textField_mazeRowss.setStyle("-fx-background-color: #ff781b");

        releasePlayButton();
    }

    public void checkColInput()
    {
        try
        {
            int cols=Integer.parseInt(textField_mazeColumnss.getText());
            if((cols>0)) goodCol=true;
            else goodCol=false;
        }
        catch (NumberFormatException e)
        {
            goodCol=false;
        }
        if(goodCol) textField_mazeColumnss.setStyle("-fx-background-color: #a6ff2f");
        else textField_mazeColumnss.setStyle("-fx-background-color: #ff781b");
        releasePlayButton();

    }

    public void openProp() throws IOException {
        Stage propStage=new Stage();
        propStage.setTitle("Properties");
        propStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent playRoot = fxmlLoader.load(getClass().getResource("../View/MyProperties.fxml").openStream());
        Scene propScene=new Scene(playRoot, 550, 500);
        propStage.setScene(propScene);
        propStage.show();
    }
    public void closeProp(){((Stage)algLAB.getScene().getWindow()).close();}

    public void openINS() throws IOException {
        Stage INSStage=new Stage();
        INSStage.setTitle("Instructions");
        INSStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent playRoot = fxmlLoader.load(getClass().getResource("../View/MyInstructions.fxml").openStream());
        Scene INSScene=new Scene(playRoot, 700, 500);
        INSStage.setScene(INSScene);
        INSStage.show();
    }
    public void closeINS(){((Stage)closeINS.getScene().getWindow()).close();}

    public void initialize() throws FileNotFoundException {
        if(algLAB!=null){
            AMazeGenerator gen=Configurations.MazeGenerateRead();
            if(gen instanceof EmptyMazeGenerator)
                algLAB.setText("Empty Maze Algorithm");
            if(gen instanceof SimpleMazeGenerator)
                algLAB.setText("Simple Maze Algorithm");
            if(gen instanceof EmptyMazeGenerator)
                algLAB.setText("My Maze Algorithm");

        }
        if(tpLAB!=null){
            int numOfThreads=Configurations.readNumOfThreads();
            tpLAB.setText(String.valueOf(numOfThreads));
        }
        if(slvLAB!=null) {
            ASearchingAlgorithm solver=Configurations.SolvingAlgorithmRead();
            if(solver instanceof BreadthFirstSearch && !(solver instanceof BestFirstSearch))
                slvLAB.setText("Bread First Search");
            if(solver instanceof DepthFirstSearch)
                slvLAB.setText("Depth First Search");
            if(solver instanceof BestFirstSearch)
                slvLAB.setText("Best First Search");

        }
    }
}
