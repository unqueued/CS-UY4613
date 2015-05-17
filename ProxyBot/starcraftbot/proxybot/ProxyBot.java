package starcraftbot.proxybot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JList;

import starcraftbot.proxybot.bot.AttackBot;
import starcraftbot.proxybot.bot.ExampleStarCraftBot;
import starcraftbot.proxybot.bot.FarmBot;
import starcraftbot.proxybot.bot.MetaBot;
import starcraftbot.proxybot.bot.StarCraftBot;
/**
 * ProxyBot.
 * 
 * Manages socket connections with StarCraft and handles the
 * agent <-> StarCraft communication.
 */
public class ProxyBot {

	/** port to start the server socket on */
	public static int port = 12345;
	
	/** allow the user to control units */
	public static boolean allowUserControl = true;
	
	/** turn on complete information */
	public static boolean completeInformation = true;

	/** display agent commands in SC? */
	public static boolean logCommands = true;

	/** display agent commands in SC? */
	public static boolean terrainAnalysis = true;

	/** display the GUI? */
	public static boolean showGUI = true;

	public static boolean showSpeedPanel = true;
	
	public static void main(String[] args) {
		new ProxyBot().start();
	}

	/**
	 * Starts the ProxyBot.
	 * 
	 * A server socket is opened and waits for client connections.
	 */
	public void start() {
		try {			
		    ServerSocket serverSocket = new ServerSocket(port);
		    
		    while (true) {
			    System.out.println("Waiting for client connection");
			    Socket clientSocket = serverSocket.accept();			
			    
			    System.out.println("Client connected");		    
			    runGame(clientSocket);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Manages communication with StarCraft.
	 */
	private void runGame(Socket socket) {		
		
		StarCraftFrame frame = null;
    	SpeedPanel speedPanel = null;
    	JFrame mainFrame = null;
		//final StarCraftBot bot = new ExampleStarCraftBot();
    	//final StarCraftBot bot = new MetaBot();
		final FarmBot farmBot = new FarmBot();
		final AttackBot attackBot = new AttackBot();
    	
		try {
			// 1. get the initial game information
		    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	String playerData = reader.readLine();
	
	    	// 2. respond with bot options
	    	System.out.println("Sending bot options");
	    	String botOptions = (allowUserControl ? "1" : "0") 
	    					  + (completeInformation ? "1" : "0")
	    					  + (logCommands ? "1" : "0")
      					      + (terrainAnalysis ? "1" : "0");
	    	socket.getOutputStream().write(botOptions.getBytes());
			
	    	// 3. get the starting locations and map information
	    	String locationData = reader.readLine();
	    	String mapData = reader.readLine();
	    	
	    	// TA
	    	String chokesData = "Chokes:";
	    	String basesData = "Bases:";
	    	if (terrainAnalysis) {
	    		chokesData = reader.readLine();
	    		basesData = reader.readLine();
	    	}

	    	System.out.println("Game starting");
	    	final Game game = new Game(playerData, locationData, mapData, chokesData, basesData);
	    	boolean firstFrame = true;
	    	
	    	// show the game speed panel
	    	if (showSpeedPanel) {
	    		speedPanel = new SpeedPanel(game);
	    	}
	    	else {
	    		game.getCommandQueue().setGameSpeed(0);
	    	}
	    	
	    	// 4. game updates
	    	while (true) {
	    		String update = reader.readLine();
	    		if (update == null) {
	    			break;
	    		}
	    		else {	    				    			
	    			// update the game
	    			game.update(update);	    			

	    			if (firstFrame) {	    				
	    				firstFrame = false;
	    				
	    				// start the agent
	    				/*
	    				new Thread() {
	    					public void run() {
	    	    				bot.start(game);
	    					}
	    				}.start();
	    				*/
	    				// start the agent(s)
	    				new Thread() {
	    					public void run() {
	    	    				farmBot.start(game);
	    					}
	    				}.start();
	    				new Thread() {
	    					public void run() {
	    	    				attackBot.start(game);
	    					}
	    				}.start();
	    				
	    				// initialize the GUI
	    				if (showGUI) {
		    				frame = new StarCraftFrame(game);
		    				mainFrame = new MainFrame();
		    				mainFrame.show();
		    				
		    				JFrame BotControlFrame;
		    				BotControl botControl = new BotControl();
		    				BotControlFrame = new JFrame();
		    				BotControlFrame.add(botControl);
		    				BotControlFrame.pack();
		    				BotControlFrame.setVisible(true);
		    				
		    				// Create bot thread objects
		    				/*
		    				Thread farmBotThread = new Thread() {
		    					public void run() {
		    	    				farmBot.start(game);
		    					}
		    				};
		    				*/
		    				
		    				// Set up event listeners
		    				JList botList = botControl.getBotList();
		    				botList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
		    		            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
		    		            	JList jList = (JList) evt.getSource();
		    		            	
		    		            	if(contains(jList.getSelectedIndices(), 0)) {
		    		            		//farmBot.start(game);
		    		            		//farmBotThread.start();
		    		            		System.out.println("FarmBot running");
		    		            		farmBot.setPaused(false);
		    		            	} else {
		    		            		//farmBot.stop();
		    		            		System.out.println("FarmBot not running");
		    		            		farmBot.setPaused(true);
		    		            	}
		    		            	
		    		            	if(contains(jList.getSelectedIndices(), 1)) {
		    		            		System.out.println("AttackBot running");
		    		            		attackBot.setPaused(false);
		    		            	} else {
		    		            		System.out.println("AttackBot not running");
		    		            		attackBot.setPaused(true);
		    		            	}
		    		            	
		    		            }
		    		        });
		    			}
	    			}
	    				    			
	    			// 5. send commands
	    	    	socket.getOutputStream().write(game.getCommandQueue().getCommands().getBytes());
	    	    	
	    			if (frame != null) {
	    				frame.repaint();
	    			}
	    		}
	    	}
	    	
	    	// wait for game to terminate
	    	System.out.println("Game Ended");
		}
		catch (SocketException e) {
			System.out.println("StarCraft has disconnected");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			
			// stop the bot
			//if (bot != null) {
			//	bot.stop();
			//}
			if(farmBot != null) {
				farmBot.stop();
			}
			
			// close the frame
			if (frame != null) {
				frame.stop();
			}
			
			// close the speed panel
			if (speedPanel != null) {
				speedPanel.stop();
			}
		}
	}
	
	// Kludge, I know
    public boolean contains(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
}
