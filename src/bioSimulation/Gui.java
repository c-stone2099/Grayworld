package bioSimulation;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.vecmath.Vector2d;

import org.jfree.ui.RefineryUtilities;

import data.AreaChartTest;
import data.DTSCTest;
import data.SpeciesOrganizer;
import nonBiological.PhLab;
import simLoop.BaseLoop;

public class Gui extends Canvas implements KeyListener, MouseMotionListener, BaseLoop {

	public static final int WIDTH = 1600, HEIGHT = 1000; // WIDTH = 900, HEIGHT =
														// 600;
	public final static int FPS = 30;

	private BufferStrategy strategy;
	private boolean pause = false;
	private boolean manualPause = false;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final Action action = new SwingAction();
	private final Action action2 = new SwingAction2();
	private final Action action3 = new SwingAction3();
	private final Action action4 = new SwingAction4();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final static long seed = 878911902; // 878985900 demo seed
	private final static World world = new World(seed); ///// // SEED HERE
	// private SpeciesOrganizer speciesOrganizer= new SpeciesOrganizer();
	AreaChartTest demo;
	DTSCTest entropyGraph;
	private boolean autostop = false;
	private int stopDay;

	private int counterz;

	// int k = 0;

	public Gui() {

		JFrame frame = new JFrame("Grayworld");
		JPanel panel = (JPanel) frame.getContentPane();
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		panel.add(this);
		frame.setBounds(0, 0, WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowIconified(WindowEvent e) {
				pause = true;
			}

			public void windowDeiconified(WindowEvent e) {
				if (!manualPause)
					pause = false;
			}
		});

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmStartSimulation = new JMenuItem("Start/Pause Simulation");
		mntmStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// START SIMULATION
				// pause = false;
				if (pause) {
					pause = false;

				} else {
					pause = true;
					// speciesOrganizer.speciesData(world.getPopulation());
				}

			}
		});
		mnFile.add(mntmStartSimulation);

		JMenuItem mntmLoad = new JMenuItem("Load...");
		mnFile.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					pause = true;

					String fileName = JOptionPane.showInputDialog("File Name");
					world.getGenealogy().saveGenealogy(fileName + "Seed" + seed);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnNewMenu = new JMenu("Speed");
		menuBar.add(mnNewMenu);

		JRadioButtonMenuItem slowRadioBut = new JRadioButtonMenuItem("Slow");
		buttonGroup_2.add(slowRadioBut);
		slowRadioBut.setAction(action);
		mnNewMenu.add(slowRadioBut);

		JRadioButtonMenuItem fastRadioBut = new JRadioButtonMenuItem("Fast");
		buttonGroup_2.add(fastRadioBut);
		fastRadioBut.setAction(action2);
		mnNewMenu.add(fastRadioBut);

		JRadioButtonMenuItem maxRadioBut = new JRadioButtonMenuItem("Max (repaint disabled)");
		buttonGroup_2.add(maxRadioBut);
		maxRadioBut.setAction(action3);
		mnNewMenu.add(maxRadioBut);

		JRadioButtonMenuItem ffToDate = new JRadioButtonMenuItem("Fast Forward to Date...");
		buttonGroup_2.add(ffToDate);
		ffToDate.setAction(action4);
		mnNewMenu.add(ffToDate);

		JMenu mnGeganke = new JMenu("Geganke");
		menuBar.add(mnGeganke);

		JMenu mnStatistics = new JMenu("Statistics");
		menuBar.add(mnStatistics);

		frame.setResizable(false);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		requestFocus();

		addKeyListener(this);
		addMouseMotionListener(this);
		setIgnoreRepaint(true);

		// Lab lab = new Lab();
		// PhLab lab = new PhLab();
		// demo = new AreaChartTest("graph",world);
		// demo.pack();
		// RefineryUtilities.centerFrameOnScreen(demo);
		// demo.setVisible(true);
		// demo.start();
		// entropyGraph = new DTSCTest("Complexity", world);
		// entropyGraph.pack();
		// RefineryUtilities.centerFrameOnScreen(demo);
		// entropyGraph.setVisible(true);
		// entropyGraph.start();
		runner.init(this, 30);
		runner.begin();

	}

	public void updateWorld() {
		if (!pause) {
			world.updateWorld();

			if (world.getDay() > stopDay && autostop) {
				runner.setFPS(30);
				autostop = false;
			}
			if (counterz > 30) {
				// entropyGraph.updateChart(world);
				counterz = 0;
			} else
				counterz++;
		}

	}

	public void renderWorld() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.drawString("Day :" + world.getDay(), 20, 20);
		g.drawString("Agents N:" + world.getPopulation().size(), 20, 40); // DISPLAY
																			// COMPLEXITY
		g.setColor(Color.RED);
		// g.drawOval(50, 50, 3, 3);

		/*
		 * for (Agent agent : world.getPopulation()) {
		 * 
		 * g.setColor(agent.getColor());
		 * g.fillOval((int)agent.getPosition().x-(agent.getSize()/2),(int)agent.
		 * getPosition().y-(agent.getSize()/2),agent.getSize(),agent.getSize());
		 * g.drawLine((int)agent.getPosition().x, (int)agent.getPosition().y,
		 * (int)(agent.getPosition().x - agent.getVelocity().x),
		 * (int)(agent.getPosition().y - agent.getVelocity().y));
		 * 
		 * }
		 */

		// draw resource

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				g.setColor(new Color(180, 180, 180, World.resourceQuadrant[i][j] / 3));
				g.fillRect(i * WIDTH / 5, j * HEIGHT / 5, WIDTH / 5, HEIGHT / 5);
			}

		}

		// draw population
		for (Agent agent : world.getPopulation()) {

			if (agent.isAlive()) {
				agent.paintShadow(g);
			}
		}
		for (Agent agent : world.getPopulation()) {
			agent.paint(g);
			// if(agent.getAgentID() == 10080)
			// {
			// g.drawString("I'm Here!", (int)agent.getPosition().x,
			// (int)agent.getPosition().y);
			// }
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				g.setColor(new Color(180, 180, 180, World.resourceQuadrant[i][j] / 4));
				g.fillRect(i * WIDTH / 5, j * HEIGHT / 5, WIDTH / 5, HEIGHT / 5);
			}

		}

		strategy.show();
		g.clearRect(0, 0, WIDTH, HEIGHT);

	}

	public void initWorld() {
		Random r = new Random();

	}

	public void reset() {

	}

	public void keyPressed(KeyEvent e) {

	}

	public static void main(String args[]) {
		new Gui();

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Slow");
			putValue(SHORT_DESCRIPTION, "Run the simulation at minimum speed");
		}

		public void actionPerformed(ActionEvent e) {
			runner.setFPS(20);
		}

	}

	private class SwingAction2 extends AbstractAction {
		public SwingAction2() {
			putValue(NAME, "Fast");
			putValue(SHORT_DESCRIPTION, "Run the simulation at fast speed");
		}

		public void actionPerformed(ActionEvent e) {
			runner.setFPS(90);
		}

	}

	private class SwingAction3 extends AbstractAction {
		public SwingAction3() {
			putValue(NAME, "MAX");
			putValue(SHORT_DESCRIPTION, "Disable rendering and run the simulation at MAX speed");
		}

		public void actionPerformed(ActionEvent e) {
			runner.setFPS(3000);
		}
	}

	private class SwingAction4 extends AbstractAction {
		public SwingAction4() {
			putValue(NAME, "FFtoDate");
			putValue(SHORT_DESCRIPTION, "Disable rendering and run the simulation at MAX speed until date reached");
		}

		public void actionPerformed(ActionEvent e) {

			pause = true;
			String stopDayS = JOptionPane.showInputDialog("Insert end of Fast Forward");
			stopDay = Integer.parseInt(stopDayS);
			runner.setFPS(3000);
			autostop = true;
			pause = false;
		}

	}

}