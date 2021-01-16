package main.java.view;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainWindow implements ActionListener, Runnable, MouseListener {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int SCALE = 8;

    // GUI components
    private static SimulationPanel simulationPanel = new SimulationPanel();
    private static EditorPanel editorPanel = new EditorPanel();
    private JFrame mainWindow = new JFrame("Traffic Simulator");
    private JPanel bottomPanel = new JPanel();
    private JLabel modeLabel = new JLabel("Mode: ");
    private JLabel statusLabel = new JLabel("Status: ");
    private JLabel labelXPosField = new JLabel("Scaled X:");
    private JTextField xPosField = new JTextField("0");
    private JLabel labelYPosField = new JLabel("Scaled Y:");
    private JTextField yPosField = new JTextField("0");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu editMenu = new JMenu("City Editor");
    private JMenuItem newMapItem = new JMenuItem("New");
    private JMenuItem openMapItem = new JMenuItem("Open");
    private JMenuItem saveMapItem = new JMenuItem("Save");
    private JMenuItem exitProgramItem = new JMenuItem("Exit");
    private JMenu simMenu = new JMenu("Simulation");
    private JMenuItem loadSimItem = new JMenuItem("Load Map");
    private JMenuItem spawnItem = new JMenuItem("Add Vehicles");
    private JMenuItem startSimItem = new JMenuItem("Start");
    private JMenuItem stopSimItem = new JMenuItem("Stop");
    private JMenuItem setUpdateRateItem = new JMenuItem("Update Rate");

    // Simulation Window setup:
    public void initMainWindow(){
        // Initialise the Main Window
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add Status Bar to the bottom of the Main Window
        bottomPanel.setLayout(new GridLayout(1, 0));
        bottomPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        bottomPanel.add(modeLabel);
        bottomPanel.add(statusLabel);
        bottomPanel.add(labelXPosField);
        bottomPanel.add(xPosField);
        bottomPanel.add(labelYPosField);
        bottomPanel.add(yPosField);
        mainWindow.add(bottomPanel, BorderLayout.SOUTH);

        // Add Menu bar to the top of the Main Window
        mainWindow.add(menuBar, BorderLayout.NORTH);

        // Initialise City Editor Menu
        MenuListener cityLis = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                modeLabel.setText("Mode: City Editing");
                mainWindow.repaint();
            }
            @Override
            public void menuDeselected(MenuEvent e) {
            }
            @Override
            public void menuCanceled(MenuEvent e) {
            }
        };
        editMenu.addMenuListener(cityLis);
        menuBar.add(editMenu);

        // create a new city map
        newMapItem.addActionListener(e -> {
            // hide simulation panel
            simulationPanel.setVisible(false);
            // remove any existing editor panel
            mainWindow.remove(editorPanel);
            // create a new editor panel
            editorPanel = new EditorPanel();
            editorPanel.setScale(SCALE);
            editorPanel.setVisible(true);
            editorPanel.newMap();
            statusLabel.setText("Status: New Map");
            mainWindow.add(editorPanel);
            mainWindow.validate();
            mainWindow.repaint();
        });
        editMenu.add(newMapItem);

        // open an existing city map
        openMapItem.addActionListener(e -> {
            // TODO
        });
        editMenu.add(openMapItem);

        // save the current city map
        saveMapItem.addActionListener(e -> {
            // TODO
        });
        editMenu.add(saveMapItem);

        // exit the program
        exitProgramItem.addActionListener(e -> System.exit(0));
        editMenu.add(exitProgramItem);

        // Initialise the Simulation Menu
        MenuListener simLis = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                modeLabel.setText("Mode: Simulation");
                mainWindow.repaint();
            }
            @Override
            public void menuDeselected(MenuEvent e) {
            }
            @Override
            public void menuCanceled(MenuEvent e) {
            }
        };
        simMenu.addMenuListener(simLis);

        // Load map
        simMenu.add(loadSimItem);

        // Add vehicles
        spawnItem.setEnabled(false);
        simMenu.add(spawnItem);

        // Start the simulation
        startSimItem.setEnabled(false);
        startSimItem.addActionListener(e -> {
            simulationPanel.simulate();
            statusLabel.setText("Status: Simulation Started");
            simulationPanel.setStopSim(false);
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(startSimItem);

        // Display statistics
        spawnItem.addActionListener(e -> {
            String spawnInput = JOptionPane.showInputDialog("Total number of Vehicles to spawn:");
            int spawns = Integer.parseInt(spawnInput);
            simulationPanel.setVehicleSpawn(spawns);
            String spawnRateInput = JOptionPane.showInputDialog("Number of Simulation tics between spawns:");
            int spawnRate = Integer.parseInt(spawnRateInput);
            simulationPanel.setVehicleSpawnRate(spawnRate);
        });

        // Stop the simulation
        stopSimItem.setEnabled(false);
        stopSimItem.addActionListener(e -> {
            simulationPanel.setStopSim(true);
            statusLabel.setText("Status: Simulation Stopped");
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(stopSimItem);

        // Add listeners
        loadSimItem.addActionListener(e -> {
            statusLabel.setText("Status: Map Loaded!");
            editorPanel.setVisible(false);
            simulationPanel = new SimulationPanel();
            simulationPanel.setScale(SCALE);
            simulationPanel.loadMap(editorPanel.getRoads(), editorPanel.getLights());
            mainWindow.add(simulationPanel);
            startSimItem.setEnabled(true);
            spawnItem.setEnabled(true);
            stopSimItem.setEnabled(true);
            mainWindow.repaint();
        });

        // Set the update rate
        setUpdateRateItem.addActionListener(e -> {
            String updateRateInput = JOptionPane.showInputDialog("Enter the Update Rate of the Simulation");
            int updateRate = Integer.parseInt(updateRateInput);
            simulationPanel.setUpdateRate(updateRate);
            statusLabel.setText("Status: Update Rate set to " + updateRate);
            mainWindow.validate();
            mainWindow.repaint();
        });
        simMenu.add(setUpdateRateItem);
        menuBar.add(simMenu);

        // Main Window settings
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e){
        int x = e.getX() / SCALE;
        int y = e.getY() / SCALE;
        xPosField.setText(Integer.toString(x));
        yPosField.setText(Integer.toString(y));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void run() {

    }
}
