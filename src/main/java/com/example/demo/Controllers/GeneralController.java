/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Controllers;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Logging.LogExecution;
import com.example.demo.RepresentationClasses.StringMsg;
import com.github.sarxos.webcam.Webcam;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class GeneralController {

    private static final Logger logger = Logger.getLogger(GeneralController.class);

    //private Triplet<String, String, String> nodes = new Triplet<>("", "", "");
    private HashMap<String, ArrayList<String>> nodes = new HashMap<>();

    @Value("${myLoc}")
    private String myLoc;

    @Autowired
    private WebClient webClient;

    @GetMapping("/info")
    public StringMsg getInfo() {
        String msg= "Location = "+myLoc + ", running java tunnel and control code. Registered nodes:";
        for(String key: nodes.keySet()){
            msg += "\n IP = "+nodes.get(key).get(0) + ", port = " +nodes.get(key).get(1)+", at loc="+key;
        }
        return new StringMsg(msg, true);
    }

    @GetMapping("/getAllLocations")
    public Set<String> getAllLocations(){
        Set<String> loc = new HashSet<String>(nodes.keySet());
        loc.add(myLoc);
        return loc;
    }

    @GetMapping("/exit")
    public void terminateService() {
        System.exit(0);
    }

    @GetMapping("/register/{ip}/{port}/{loc}")
    public StringMsg register(@PathVariable String ip, @PathVariable String port, @PathVariable String loc) {
        nodes.put(loc, new ArrayList<String>(Arrays.asList(ip, port)));//Triplet.with(ip, port, loc));
        return new StringMsg("done", true);
    }

    @GetMapping("/try2")
    public String getIn() {
        logger.info("------------ \n\n\n i am here\n\n\n------------------\n\n\n");
        // String s = "";
        // for (int i = 0; i < 100000; i++) {
        //     if (i % 3 == 0 && (i - 4) % 7 != 0) {
        //         s += "1";
        //     } else {
        //         s += "0";
        //     }
        // }

        //return s;
        //return "<html><marquee><h1>Ishita</h1></marquee></html>";
        //return "<html><a href=\"javascript:close_window();\">click me motu</a></html>";
        //sudoku
        //return "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <meta name=\"viewport\" content=\"width=device-width,                                   initial-scale=1.0\">    <title>Sudoku Puzzle</title>    <style>        body {            font-family: Arial, sans-serif;            background-color: #F5FCFF;        }        .container {            display: flex;            flex-direction: column;            align-items: center;            margin-top: 50px;        }        .row {            display: flex;        }        .cell {            width: 30px;            height: 30px;            border: 1px solid black;            text-align: center;            line-height: 30px;            box-sizing: border-box;        }        .lightBackground {            background-color: #A9A9A9;        }        .darkBackground {            background-color: #EBF3E8;        }        .buttonContainer {            margin-top: 20px;            display: flex;            /* Ensure buttons are always displayed */            align-items: center;            justify-content: center;        }        .buttonContainer button {            margin-right: 10px;            /* Add margin between buttons */        }        .result {            margin-top: 20px;            font-weight: bold;        }        .correct {            color: green;        }        .incorrect {            color: red;        }    </style></head><body>    <div class=\"container\" id=\"container\"></div>    <div class=\"buttonContainer\">        <button id=\"solveButton\">Solve</button>        <button id=\"resetButton\">Reset</button>    </div>    <script>        document.addEventListener(\"DOMContentLoaded\", function () {            const container = document.getElementById(\"container\");                        function generateRandomSudoku() {                                const puzzle = [                    [5, 3, 0, 0, 7, 0, 0, 0, 0],                    [6, 0, 0, 1, 9, 5, 0, 0, 0],                    [0, 9, 8, 0, 0, 0, 0, 6, 0],                    [8, 0, 0, 0, 6, 0, 0, 0, 3],                    [4, 0, 0, 8, 0, 3, 0, 0, 1],                    [7, 0, 0, 0, 2, 0, 0, 0, 6],                    [0, 6, 0, 0, 0, 0, 2, 8, 0],                    [0, 0, 0, 4, 1, 9, 0, 0, 5],                    [0, 0, 0, 0, 8, 0, 0, 7, 9]                ];                return puzzle;            }                        function solveSudoku(board) {                                const solvedPuzzle = JSON.parse(JSON.stringify(board));                solveHelper(solvedPuzzle);                return solvedPuzzle;            }                        function solveHelper(board) {                const emptyCell = findEmptyCell(board);                if (!emptyCell) {                    return true;                 }                const [row, col] = emptyCell;                for (let num = 1; num <= 9; num++) {                    if (isValidMove(board, row, col, num)) {                        board[row][col] = num;                        if (solveHelper(board)) {                            return true;                        }                        board[row][col] = 0;                     }                }                return false;             }                        function findEmptyCell(board) {                for (let row = 0; row < 9; row++) {                    for (let col = 0; col < 9; col++) {                        if (board[row][col] === 0) {                            return [row, col];                        }                    }                }                return null;             }                        function isValidMove(board, row, col, num) {                                for (let i = 0; i < 9; i++) {                    if (board[row][i] === num) {                        return false;                    }                }                                for (let i = 0; i < 9; i++) {                    if (board[i][col] === num) {                        return false;                    }                }                                const startRow = Math.floor(row / 3) * 3;                const startCol = Math.floor(col / 3) * 3;                for (let i = startRow; i < startRow + 3; i++) {                    for (let j = startCol; j < startCol + 3; j++) {                        if (board[i][j] === num) {                            return false;                        }                    }                }                return true;             }                        function createSudokuGrid(puzzle) {                container.innerHTML = '';                puzzle.forEach((row, rowIndex) => {                    const rowElement = document.createElement('div');                    rowElement.classList.add('row');                    row.forEach((cell, columnIndex) => {                        const cellElement = document.createElement('input');                        cellElement.classList.add('cell');                        cellElement.classList                            .add((rowIndex + columnIndex) % 2 === 0 ?                                'lightBackground' : 'darkBackground');                        cellElement.type = 'text';                        cellElement.maxLength = 1;                        cellElement.value = cell !== 0 ? cell : '';                        rowElement.appendChild(cellElement);                    });                    container.appendChild(rowElement);                });            }                        let initialPuzzle = generateRandomSudoku();            let puzzle = JSON.parse(JSON.stringify(initialPuzzle));            let solvedPuzzle = [];                        function solvePuzzle() {                solvedPuzzle = solveSudoku(puzzle);                createSudokuGrid(solvedPuzzle);            }                        function resetPuzzle() {                initialPuzzle = generateRandomSudoku();                puzzle = JSON.parse(JSON.stringify(initialPuzzle));                solvedPuzzle = [];                createSudokuGrid(puzzle);            }                        createSudokuGrid(puzzle);                        document.getElementById(\"solveButton\")                .addEventListener(\"click\", solvePuzzle);            document.getElementById(\"resetButton\")                .addEventListener(\"click\", resetPuzzle);        });    </script></body></html>";
        //word guessing
        //return "<!-- index.html --><!DOCTYPE html><html><head>    <title>Word Guessing Game</title>    <style>                        /* style.css */                /* Styling for the heading */                h1 {                    background-color: rgb(231, 231, 231);                    color: green;                    font-size: xx-large;                    padding: 2%;                    /* width: 70vh; */                }                /* Style for the card and border  */                .card {                    min-width: 30%;                    width: 50vh;                    min-height: 30vh;                    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);                    transition: 0.3s;                    border-radius: 5px;                    margin: auto;                    padding: 2%;                }                /* Font and display style for complete body */                body {                    display: flex;                    text-align: center;                    font-family: \"Gill Sans\",                                 \"Gill Sans MT\",                                 Calibri, \"Trebuchet MS\",                                sans-serif;                }                /* Adding style for input box */                input {                    width: 30px;                    padding: 1%;                    font-size: larger;                    text-align: center;                    margin-left: 2%;                }                /* Adding style for display word*/                #displayWord {                    font-size: xx-large;                }    </style>        <script>                                                        const words = [                    \"java\",                    \"javascript\",                    \"python\",                    \"pascal\",                    \"ruby\",                    \"perl\",                    \"swift\",                    \"kotlin\",                ];                                let randomIndex = Math.floor(Math.random() * words.length);                let selectedWord = words[randomIndex];                console.log(selectedWord);                                let guessedlist = [];                                let displayWord = \"\";                for (let i = 0; i < selectedWord.length; i++) {                    displayWord += \"_ \";                }                document.getElementById(\"displayWord\").textContent = displayWord;                                function guessLetter() {                    let inputElement =                         document.getElementById(\"letter-input\");                                        if (!inputElement.value) {                        alert(\"Empty Input box. Please add input letter\");                        return;                    }                    let letter = inputElement.value.toLowerCase();                                        inputElement.value = \"\";                                        if (guessedlist.includes(letter)) {                        alert(\"You have already guessed that letter!\");                        return;                    }                                        guessedlist.push(letter);                                        let updatedDisplay = \"\";                    let allLettersGuessed = true;                    for (let i = 0; i < selectedWord.length; i++) {                        if (guessedlist.includes(selectedWord[i])) {                            updatedDisplay += selectedWord[i] + \" \";                        } else {                            updatedDisplay += \"_ \";                            allLettersGuessed = false;                        }                    }                    document.getElementById(\"displayWord\")                        .textContent = updatedDisplay;                                        if (allLettersGuessed) {                        alert(\"Congratulations! You guessed the word correctly!\");                    }                }    </script></head><body>    <div class=\"card\">        <h1>Guess the word</h1>        <p>              <b>Hint:</b> A programming language          </p>        <p id=\"displayWord\"></p>        <p>Guess one letter:            <input type=\"text\"                    maxlength=\"1\"                    id=\"letter-input\">        </p>        <button onclick=\"guessLetter()\">              Submit          </button>    </div></body></html>";
        /*try{
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("cmd.exe /C d:/music/songs/husan-tera-tauba-tauba.mp3");
        }catch(IOException e){
            
        }
        return "listen chup chaap";*/
        //html image
        return "<html><img src=\"c:/users/abhay/onedrive/desktop/lion.jpg\" width=\"500\" height = \"660\" ></html>";
    }

    
    @GetMapping("/runCmd")
    public void getBubbles(@RequestParam String cmd) throws IOException {
        logger.info("\n\n\n-----------------------\ncommand received: " + cmd + "\n\n----------------------------\n\n");
        Runtime rt = Runtime.getRuntime();
        // Process pr = 
        rt.exec(cmd);
    }

    @GetMapping("/sysImage")
    public ResponseEntity<Resource> getImage() throws Exception {
        System.setProperty("java.awt.headless", "false");
        Robot r = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage Image = r.createScreenCapture(capture);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(Image, "jpg", baos);
        byte[] bytes = baos.toByteArray();

        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
        // Return ResponseEntity with image content type
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(byteArrayResource);
    }

    @GetMapping("/camera/{locationAsked}")
    public ResponseEntity<Resource> getCameraImage(@PathVariable String locationAsked) throws Exception {
        
        if (myLoc.equals(locationAsked)) {
            //capture from web cam and return

            Webcam webcam = Webcam.getDefault();
            /*for(Dimension d: webcam.getViewSizes()){
                System.out.println("dimension -->"+d.width+","+  d.height);
            }*/
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();
            BufferedImage image = webcam.getImage();
            webcam.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(byteArrayResource);
        } else {
            //ask one of the slave nodes to capture and return   
            ArrayList<String> node = nodes.get(locationAsked);
            String nodeIP = node.get(0);
            String nodePort = node.get(1);
            byte[] bytes = webClient
                .get().uri("http://" + nodeIP + ":" + nodePort + "/camera/" + locationAsked)
            .retrieve().bodyToMono(Resource.class).block().getContentAsByteArray();
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(bytes));
        }

        

    }

    
    //@CircuitBreaker(name = "default")
    @GetMapping("/circuit-breaker/{key}")
    @LogExecution(parameter=" |circuit breaker logging using custom annotation| ")
    public void getCircuitBreaker(@PathVariable String key) throws Exception {
        System.out.println("inside circuit breaker emthod");
    }

}
