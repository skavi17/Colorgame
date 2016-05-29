package com.kavi.colorgame;

import java.util.*;

public class Utils {
	
	private static final int GRID_SIZE = 13;
    private List<Player> playerList = new ArrayList<Player>();
    private Map<Integer, Colors> colorsMap = new HashMap<Integer, Colors>();
    private Player currentPlayer;

    public void initGame() {
        Player player1 = new Player();
        player1.name = "Joueur 1";
        player1.controlledBlocks = new ArrayList<Integer>();
        player1.controlledBlocks.add(1);
        player1.color = Colors.blue;
        colorsMap.put(1, Colors.blue);

        Player player2 = new Player();
        player2.name = "Joueur 2";
        player2.controlledBlocks = new ArrayList<Integer>();
        player2.controlledBlocks.add(GRID_SIZE*GRID_SIZE);
        player2.color = Colors.green;
        colorsMap.put(GRID_SIZE*GRID_SIZE, Colors.green);
//*explique ce qui est définit dans la classe Player*/

        playerList.add(player1);
        playerList.add(player2);

        currentPlayer = player1;


        for (int i = 2; i < GRID_SIZE*GRID_SIZE; i++) {
            colorsMap.put(i, Colors.random());
        }
//*affiche toute la map avec des couleurs randoms*/
        play();
    }

    private void savePlayerStatus() {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).id.equals(currentPlayer)) {
                playerList.set(i, currentPlayer);
                break;
            }
        }
    }
 //* Enregistre les cases contrôlées par le joueur*/

    private void play() {
        while (!endOfGame()) {
            System.out.println("C'est au tour de "  + currentPlayer.name + " !");
            renderBlocks();
            List<Colors> usedColors = new ArrayList<Colors>();
            for (Player player : playerList) {
                usedColors.add(player.color);
            }
            //*permet de mettre en place la partie où on pourra voir les différentes couleurs disponibles à choisir*/

            Colors choosedColor = null;
            while (choosedColor == null) {
                System.out.println("Veuillez choisir une couleur parmis : " + Colors.listOptions(usedColors));
                Scanner scanner = new Scanner(System.in);
                String colorChoice = scanner.nextLine();
                choosedColor = Colors.findByUnControlled(colorChoice);
                if (usedColors.contains(choosedColor)) {
                    choosedColor = null;
                }
            }
            //*permet de sélectionner la couleur voulue*/

            currentPlayer.color = choosedColor;

            for (int i = 1; i <= GRID_SIZE; i++) {
                for (int j = 1; j <= GRID_SIZE; j++) {
                	//* parcourt tout le tableau*/
                    int currentBlockNumber = (i - 1) * GRID_SIZE + j;
                    for (Player player : playerList) {
                        if (!player.id.equals(currentPlayer.id) && checkPlayerControlled(player, currentBlockNumber)) {
                            //*case contrôlé par l'autre joueur*/
                        } else {
                            if (checkPlayerControlled(currentPlayer, currentBlockNumber)) {
                                if (currentBlockNumber % 13 == 0) {
                                    //*fin de la ligne*/
                                    getControlOnBlock(choosedColor, currentBlockNumber - 1);
                                } else if (currentBlockNumber % 14 == 0) {
                                    //*début de la ligne*/
                                    getControlOnBlock(choosedColor, currentBlockNumber + 1);
                                } else {
                                    getControlOnBlock(choosedColor, currentBlockNumber + 1);
                                    getControlOnBlock(choosedColor, currentBlockNumber - 1);
                                }
                                getControlOnBlock(choosedColor, currentBlockNumber - GRID_SIZE);
                                getControlOnBlock(choosedColor, currentBlockNumber + GRID_SIZE);
                            }
                        }
                    }
                }
            }

            savePlayerStatus();
            currentPlayer = nextPlayer();
        }

        System.out.println("Le jeu et fini ! Et le grand gagnant est ... " + getWinner().name + " !!! Bravo !!!");
    }

    private Player nextPlayer() {
        boolean next = false;
        for (Player player : playerList) {
            if (next) {
                return player;
            }
            if (currentPlayer.id.equals(player.id)) {
                next = true;
            }

        }
        return playerList.get(0);
    }

    private boolean endOfGame() {
        int numberOfControlledBlocks = 0;
        for (Player player : playerList) {
            numberOfControlledBlocks += player.controlledBlocks.size();
            if (player.controlledBlocks.size() > GRID_SIZE*GRID_SIZE/2) {
                return true;
            }
        }

        return numberOfControlledBlocks>=GRID_SIZE*GRID_SIZE;
    }

    private Player getWinner() {
        Player winner = playerList.get(0);
        for (Player player : playerList) {
            if (player.controlledBlocks.size() > winner.controlledBlocks.size()) {
                winner = player;
            }
        }
        return winner;
    }

    private void getControlOnBlock(Colors choosedColor, int blockNumber) {
        if (colorsMap.containsKey(blockNumber) && colorsMap.get(blockNumber).equals(choosedColor) && !checkPlayerControlled(currentPlayer, blockNumber)) {
            currentPlayer.controlledBlocks.add(blockNumber);
        }
    }
    //* permet de faire passer une case de non contrôlée à contrôlée*/


    public boolean checkPlayerControlled(Player player, int blockNumber) {
        return player.controlledBlocks.contains(blockNumber);
    }
    //* fait le récapitulatif de toutes les cases contrôlées par le joueur*/
    
    public void renderBlocks() {
        for (int i = 1; i <= GRID_SIZE; i++) {
            for (int j = 1; j <= GRID_SIZE; j++) {
                int currentBlockNumber = (i-1)*GRID_SIZE+j;
                String currentBlock = colorsMap.get(currentBlockNumber).getUncontrolled();
                for (Player player : playerList) {
                    if (checkPlayerControlled(player, currentBlockNumber)) {
                        if (currentPlayer.id.equals(player.id)) {
                            currentBlock = player.color.getControlled();
                        } else {
                            currentBlock = player.color.getUncontrolled();
                        }
                    }
                }
                System.out.print(" | " + currentBlock);

                if (j == GRID_SIZE) {
                    System.out.print(" |");
                }
            }
            System.out.println("");
        }
    }
  //*renvoie la case contrôlée par le personnage, affiche toute la map*/
    


}
