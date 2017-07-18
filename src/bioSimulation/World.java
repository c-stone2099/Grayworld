package bioSimulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;









import javax.print.attribute.standard.Compression;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector2d;










import data.Genealogy;
import data.SpeciesOrganizer;
import behaviours.Behaviour;

public class World { //extends Panel{


	public static int MOVEMENT_GENE = 0;
	public static int DIET_GENE = 1;
	public static int REPROD_GENE = 2;
	public static int SENSES_GENE = 3;
	public static int CHROMOSOME1 = 0;
	public static int CHROMOSOME2 = 1;
	public static int CHROMOSOME3 = 2;

	public static int[][] resourceQuadrant = new int[5][5];

	static float genTime = 0;
	private ArrayList<Agent> population = new ArrayList<Agent>();
	private ArrayList<Agent> breedList = new ArrayList<Agent>();
	private ArrayList<Agent> deathList = new ArrayList<Agent>();
	private int[][] respawnTimer = new int[5][5];
	private String dnaTEST1 = "";
	private String dnaTEST2 = "";
	private String dnaTEST3 = "";
	private String dnaFEARtest = "";
	private String dnaFEARtest2 = "";
	private String adam = "";
	private static int Hbound = Gui.HEIGHT;
	private static int Wbound = Gui.WIDTH;
	private static int lightPool;
	public String oldPop = "";

	private LifeFactory lifeFactory;
	private Genealogy genealogy = new Genealogy();
	Random rnd;
	private int day = 0;
	private int resourceBaseVal = 300;
	private int lightVal = 50;
	



	public World(long seed){
		System.out.println("seed:" + seed);
		
		rnd = new Random(seed);
		lifeFactory = new LifeFactory(rnd);
		/*
		JFrame frame = new JFrame("World");
        JPanel panel = (JPanel)frame.getContentPane();
        setBounds(0,0,300,300);
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(null);
        panel.add(this);
        setBackground(Color.gray);
		 */



		// CRYSTAL TEST
		for(int i=0; i <60; i++)
		{			
			//population.add(lifeFactory.createAtom());
		}


		// test for elect

		for(int i=0; i <200; i++)
		{			
			//	population.add(lifeFactory.createElectron());
		}
		 //swarm TEST
		for(int i=0; i <300; i++)
		{
		//		population.add(lifeFactory.createTestAgent());

		}


		//dnaTEST1 = "51,42-61,30,44-76,25-100,99,25-89,34,11,11/1111-1111-1111-1111/22222-2222-2222-22222";
		//dnaTEST2 = "55,40-66,30,44-76,25-100,99,25-89,34/1111-1111-1111-1111/22222-2222-2222-22222";
		//dnaTEST3 = "53,44-66,55,44-76,25-101,99,25-89,34/1111-1111-1111-1111/22222-2222-2222-22222";

		//pray
		dnaFEARtest ="1-100,52-66,1,44,55-76,25-101,99,25-89,34-1,99,65,49,34/111-111-111-111-222/1-161,56,56,22,2-2222-222-222,99-28,99,56,56,56,56,23,22-70,155,60";
		//predator
		dnaFEARtest2 ="1-140,44-66,1,44,55-76,25-101,101,25-89,34-101,250,65,49,34/111-111-111-111-222/1-1,56,56,22,2-2222-222-222-28,56,56,56,56,23,22-28,100,56,56,56,23,22";
		// atoms
		//adam ="1-100,52-66,100,44,55-76,25-101,99,25-89,34-1,99,65,49,34/111-111-111-111-222/50,120,88-161,56,56,22,2-2222-222-222,99,3-28,99,56,56,57,58,23,22-70,155,60";
		//adam ="7-51,33-0,00,0,05-0,0-50,0,0-09,0-1,0,65,49,34/0-0-0-0-0/0,0,0-0,0,0,0,2-0-0-0,99,3-0,0,56,56,57,58,23,22-0,05,0";
		  adam ="7-51:33-0:00:0:05-0:0-50:0:0-09:0-1:0:65:49:34/0-0-0-0-0/0:0:0-0:0:0:0:2-0-0-0:99:3-0:0:56:56:57:58:23:22-0:05:0";
		//Random rnd = new Random();

		//for(int i=0; i < 6; i++)
	//	{
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(355),rnd.nextInt(355)), dnaFEARtest));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(255)), dnaFEARtest));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(455)), dnaFEARtest2));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(455)), dnaFEARtest2));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(455),rnd.nextInt(255)), dnaTEST2));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(255)), dnaTEST3));
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(255)), dnaTEST3));
		
		
	//	population.add(lifeFactory.createAgent(new Vector2d(rnd.nextInt(255),rnd.nextInt(255)), adam,"0"));
		population.add(lifeFactory.createAgent(1,new Vector2d(400,300), adam,"1"));


		//}


		initWorld();
	}



	public void initWorld() {
		// TODO Auto-generated method stub

		for(Agent agent : population )
		{
			agent.initilise();
		}


		// fill the resource quadrants
		for(int i = 0; i <5; i++)
		{
			for(int j = 0; j <5; j++)
			{
				resourceQuadrant[i][j] = rnd.nextInt(100);
				respawnTimer[i][j] = 0;
			}		
		}

		// TEST
		//for(int i=0; i < 1; i++)
		//{
		//System.out.println(lifeFactory.HaploidCrossOver("11,11-11,11-11,11/11,11-11,11/111,11-1111", "22,2-22,22-3,333/222,2-222,22/2,22-22,222"));
		//}
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("0000000000000000"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("0000000000010000"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("0000000000011000"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("1010101001011000"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("1111111111111111"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("1111111111101111"));
		System.out.println("kolm comp: " + NormalisedCompressionDistance.C("1111111111100111"));
		
		System.out.println(NormalisedCompressionDistance.C("00000000 00000000 10000000"));		
		System.out.println(NormalisedCompressionDistance.C("00000000 10000000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("11010100 00000000 00000000"));
		System.out.println(NormalisedCompressionDistance.C("10010100 01000000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 11000000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("00010100 11000000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("00010100 01010000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010100 01000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("01010100 00010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010100 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010111 01010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00010010 00000000 11000000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 00000000 11100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 10000000 01000000"));
		System.out.println(NormalisedCompressionDistance.C("00011100 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010000 00000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010000 00010000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 00010000 11000000"));
		System.out.println(NormalisedCompressionDistance.C("01010100 00000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010000 00000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 10100000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("10010100 01000000 00000000"));
		System.out.println(NormalisedCompressionDistance.C("01000000 00000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00011100 01000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 01000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 00000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("10000000 00000000 01000000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 00000000 11000000"));
		System.out.println(NormalisedCompressionDistance.C("00010001 01010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00010000 00000000 11000000"));
		System.out.println(NormalisedCompressionDistance.C("10010100 01010000 00000000"));
		System.out.println(NormalisedCompressionDistance.C("00010000 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010101 01010000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010101 01010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00010011 01000000 10000000"));
		System.out.println(NormalisedCompressionDistance.C("00010101 01000000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("01010101 00010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00010101 11010000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("00010001 00000000 11000000"));
		System.out.println(NormalisedCompressionDistance.C("00011101 01010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00010100 01010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("01000000 00010000 10100000"));
		System.out.println(NormalisedCompressionDistance.C("00000000 11010000 00100000"));
		System.out.println(NormalisedCompressionDistance.C("00010110 10000000 10000000"));
		
		System.out.println("NCD : " + NormalisedCompressionDistance.ncd("000000000000000000000000","000000000000000010000000"));
		System.out.println("NCD : " + NormalisedCompressionDistance.ncd("000000000000000000000000","000111010101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000100110100000010000000") + "att");
		System.out.println(NormalisedCompressionDistance.C("00010011 01000000 10000000") + "deatt");
		
		System.out.println(NormalisedCompressionDistance.C("000111010101000010100000") + "at");
		System.out.println(NormalisedCompressionDistance.C("0001110 101010000 10100000") + "detat");
		System.out.println(NormalisedCompressionDistance.C("000000000000000000000000"));
		
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000000000010000000"));		
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000001000000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","110101000000000000000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","100101000100000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000001100000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101001100000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101000101000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101000100000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","010101000001000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101000100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101110101000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100100000000011000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000000000011100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000001000000001000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000111000100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100000000000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100000001000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000001000011000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","010101000000000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100000000000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000001010000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","100101000100000000000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","010000000000000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000111000100000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000100000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000000000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","100000000000000001000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000000000000011000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100010101000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100000000000011000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","100101000101000000000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100000100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101010101000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101010101000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100110100000010000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101010100000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","010101010001000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101011101000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000100010000000011000000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000111010101000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101000101000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","010000000001000010100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000000001101000000100000"));
		System.out.println(NormalisedCompressionDistance.ncd("000000000000000000000000","000101101000000010000000"));
		
		System.out.println(NormalisedCompressionDistance.C("000000000000000010000000"));		
		System.out.println(NormalisedCompressionDistance.C("000000001000000000100000"));
		System.out.println(NormalisedCompressionDistance.C("110101000000000000000000"));
		System.out.println(NormalisedCompressionDistance.C("100101000100000000100000"));
		System.out.println(NormalisedCompressionDistance.C("000000001100000000100000"));
		System.out.println(NormalisedCompressionDistance.C("000101001100000000100000"));
		System.out.println(NormalisedCompressionDistance.C("000101000101000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101000100000010100000"));
		System.out.println(NormalisedCompressionDistance.C("010101000001000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000000000100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101000100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101110101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000100100000000011000000"));
		System.out.println(NormalisedCompressionDistance.C("000000000000000011100000"));
		System.out.println(NormalisedCompressionDistance.C("000000001000000001000000"));
		System.out.println(NormalisedCompressionDistance.C("000111000100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000100000000000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000100000001000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000000000001000011000000"));
		System.out.println(NormalisedCompressionDistance.C("010101000000000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000100000000000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000000001010000010100000"));
		System.out.println(NormalisedCompressionDistance.C("100101000100000000000000"));
		System.out.println(NormalisedCompressionDistance.C("010000000000000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000111000100000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000000000100000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000000000100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000000000000000010100000"));
		System.out.println(NormalisedCompressionDistance.C("100000000000000001000000"));
		System.out.println(NormalisedCompressionDistance.C("000000000000000011000000"));
		System.out.println(NormalisedCompressionDistance.C("000100010101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000100000000000011000000"));
		System.out.println(NormalisedCompressionDistance.C("100101000101000000000000"));
		System.out.println(NormalisedCompressionDistance.C("000100000100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101010101000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101010101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000100110100000010000000"));
		System.out.println(NormalisedCompressionDistance.C("000101010100000010100000"));
		System.out.println(NormalisedCompressionDistance.C("010101010001000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000101011101000000100000"));
		System.out.println(NormalisedCompressionDistance.C("000100010000000011000000"));
		System.out.println(NormalisedCompressionDistance.C("000111010101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000101000101000010100000"));
		System.out.println(NormalisedCompressionDistance.C("010000000001000010100000"));
		System.out.println(NormalisedCompressionDistance.C("000000001101000000100000"));
		System.out.println(NormalisedCompressionDistance.C("000101101000000010000000"));
		//System.out.println(lifeFactory.mutate("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"));
		//System.out.println("NCD : " + NormalisedCompressionDistance.ncd("0445435435543","lkaelfkjlsfjdlkfjsdfjsakfadfjadfdas;'f;'sf."));

	}


	public void updateWorld() {
		day++;
		lightPool = lightVal;
		for(Agent agent : population )
		{
			agent.Update(population);

			if(agent.hasMated())
			{
				breedList.add(agent);
			}
			if(!agent.isAlive())
			{
				deathList.add(agent);
			}
		}

		// create offspring
		for(Agent agent : breedList)
		{
			Agent child = lifeFactory.createAgent(day,agent.getPosition(),lifeFactory.HaploidCrossOver(agent.getDNA(), agent.getPartnerDNA()),agent.getAgentParent());
			population.add(child);
			
			if (!agent.getColor().equals(child.getColor()))
			{
							child.setAgentParent(""+child.getAgentID());
							//System.out.println("agent color" + agent.getColor() + " chield col " +
							//child.getColor());
							
			}
			genealogy.newBorn(child,agent);
			//  System.out.println("population :" + population.size());
			child.initilise();
			agent.setMated(false);
			agent.addEnergy(-100);
		}
		breedList.clear();

		//eliminate the dead
		for(Agent agent : deathList)
		{
			if(agent.isDevoured())
				population.remove(agent);		
		}
		deathList.clear();


		// refill resources
		for(int i = 0; i <5; i++)
		{
			for(int j = 0; j <5; j++)
			{
				if(resourceQuadrant[i][j] <= 0)
				{
					if(respawnTimer[i][j] > 300)
					{
						resourceQuadrant[i][j] = resourceBaseVal;
						respawnTimer[i][j] = 0;
					}
					else
					{
						respawnTimer[i][j]++;
					}
				}
			}		
		}

	}
	
	public SpeciesOrganizer getWorldSpecies()
	{
		//Map<Color, Integer> speciesMap = new HashMap<Color, Integer>();
		
		
		SpeciesOrganizer mySpecies = new SpeciesOrganizer();
		
		mySpecies.speciesData(population);
		
		//System.out.println(mySpecies);
		return mySpecies;
		
	}
	

	public ArrayList<Agent> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Agent> population) {
		this.population = population;
	}

	//****************************************************
	//             
	//				PHYSICS OF THE WORLD
	//
	//*****************************************************


	public static Double consumedEnergy(int size,Double speedSquared)
	{
		Double energy;
		if (speedSquared >0)
		{
			energy =  (size*speedSquared)/2;
		}
		else
		{
			energy = (double) 0;
		}

		return energy;

	}

	public static Boolean resourceGather(Vector2d position)
	{
		Boolean foodGathered = false;
		int x;
		int y;
		x = (int)((position.x/320));  // System.out.println("vec x "+position.x + " x = " +x);
		y = (int)((position.y/240));		//System.out.println("vec y "+position.y +" y = " + y);
		if ( x > 4) x = 4;
		if ( y > 4) y = 4;
		if (resourceQuadrant[x][y] > 0)
		{
			resourceQuadrant[x][y]--;
			foodGathered = true;
		}

		return foodGathered;

	}
	
	public static int photosyntesis(int size)
	{
		if (lightPool >= size)
		{
			lightPool -= size;
			return size;
		}
		else
			return 0;
	}
	


	public static int getHbound() {
		return Hbound;
	}



	public static void setHbound(int hbound) {
		Hbound += hbound;
	}



	public static int getWbound() {
		return Wbound;
	}



	public static void setWbound(int wbound) {
		Wbound += wbound;
	}

	public double getPopulationComplexity(){
		String popDNA= "";
		double complexity;
		for(Agent agent : population )
		{
			popDNA += agent.getColor() + "\n";
		}
		
		complexity = (NormalisedCompressionDistance.ncd(oldPop,popDNA)); //population.size());
		oldPop=popDNA;
		return complexity;
	}



	public Genealogy getGenealogy() {
		return genealogy;
	}



	public void setGenealogy(Genealogy genealogy) {
		this.genealogy = genealogy;
	}



	public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}


}
