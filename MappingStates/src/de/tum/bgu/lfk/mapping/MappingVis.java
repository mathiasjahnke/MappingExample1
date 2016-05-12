package de.tum.bgu.lfk.mapping;

import mj.processing.button.PButton;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;
import src.de.tum.bgu.lfk.puicomponents.charts.BarChart;

public class MappingVis extends PApplet {

	private PImage mapImage;

	//private Table locationTable;
	private Table locationTable;
	private Table nameTable;
	private Table dataTable;

	private int rowCount;

	private BarChart bc; 

	private float dataMin = MAX_FLOAT;
	private float dataMax = MIN_FLOAT;
	private float closestDist;
	private float closestTextX;
	private float closestTextY;
	private float offsetX;
	private float offsetY;
	private float hlRadius;
	private String hlbc; //highlight bar chart

	private String closestText;

	private PFont copyFont;
	private PFont font;
	private PFont titleFont;

	private PButton v;
	private PButton a;
	private PButton b;
	private PButton c;
	private PButton d;
	private PButton e;
	private PButton f;
	private PButton g;

	public static void main(String[] args) {
		PApplet.main(MappingVis.class.getName());
	}

	public void settings(){
		size(750,550);
	}

	public void setup(){

		frameRate(10);
		smooth();
		
		mapImage = loadImage("C:/Users/mjahnke/git/MappingStates/MappingStates/data/map.png");

		locationTable = loadTable("C:/Users/mjahnke/git/MappingStates/MappingStates/data/locations.tsv");
		rowCount = locationTable.getRowCount();

		dataTable = loadTable("C:/Users/mjahnke/git/MappingStates/MappingStates/data/random.tsv");

		//setting up the bar chart
		bc = new BarChart(this, 0, height - 100, width, 100);
		bc.setData(dataTable, 0, 1);

		//read the name Table
		nameTable = loadTable("C:/Users/mjahnke/git/MappingStates/MappingStates/data/names.tsv");
		
		//find min and max in the data table
		for (int row = 0; row < rowCount; row++) {
			//float value = dataTable.getFloatByRowIndex(row, 1);
			float value = dataTable.getFloat(row, 1);
			if (value > dataMax) {
				dataMax = value;
			}
			if (value < dataMin) {
				dataMin = value;
			}
		}

		//Offset to draw points according to the space needed
		//for the title area
		offsetX = 0;
		offsetY = 50;

		//Load different fonts
		copyFont = loadFont("C:/Users/mjahnke/git/MappingStates/MappingStates/data/ArialMT-9.vlw");
		font = loadFont("C:/Users/mjahnke/git/MappingStates/MappingStates/data/ArialMT-12.vlw");
		titleFont = loadFont("C:/Users/mjahnke/git/MappingStates/MappingStates/data/ArialMT-25.vlw");

		//Setup the Menu-Buttons 
		v = new PButton(width - 55, 70, "Draw values 0", this);
		v.setAdaptToLabel(false);
		v.setSize(95, 25);
		v.setFont(font);
		v.setTextStyling(100, 255);
		v.setStyling(150, 230, null, 160);
		v.setChecked(true);

		a = new PButton(width - 55, 105, "Draw values 1", this);
		a.setAdaptToLabel(false);
		a.setSize(95, 25);
		a.setFont(font);
		a.setTextStyling(100, 255);
		a.setStyling(150, 230, null, 160);
		//a.setButtonLabel("Draw Values 1", font, 100, 255);
		//a.setButtonLook(150, 230, null, 160);

		b = new PButton(width - 55, 140, "Draw values 2", this);
		b.setAdaptToLabel(false);
		b.setSize(95, 25);
		b.setFont(font);
		b.setTextStyling(100, 255);
		b.setStyling(150, 230, null, 160);
		//b.setButtonLabel("Draw Values 2", font, 100, 255);
		//b.setButtonLook(150, 230, null, 160);

		c = new PButton(width - 55, 175, "Draw values 3", this);
		c.setAdaptToLabel(false);
		c.setSize(95, 25);
		c.setFont(font);
		c.setTextStyling(100, 255);
		c.setStyling(150, 230, null, 160);
		//c.setButtonLabel("Draw Values 3", font, 100, 255);
		//c.setButtonLook(150, 230, null, 160);

		d = new PButton(width - 55, 210, "Draw values 4", this);
		d.setAdaptToLabel(false);
		d.setSize(95, 25);
		d.setFont(font);
		d.setTextStyling(100, 255);
		d.setStyling(150, 230, null, 160);
		//d.setButtonLabel("Draw Values 4", font, 100, 255);
		//d.setButtonLook(150, 230, null, 160);

		e = new PButton(width - 55, 245, "Draw values 5", this);
		e.setAdaptToLabel(false);
		e.setSize(95, 25);
		e.setFont(font);
		e.setTextStyling(100, 255);
		e.setStyling(150, 230, null, 160);
		//e.setButtonLabel("Draw Values 5", font, 100, 255);
		//e.setButtonLook(150, 230, null, 160);

		f = new PButton(width - 55, 280, "Draw values 6", this);
		f.setAdaptToLabel(false);
		f.setSize(95, 25);
		f.setFont(font);
		f.setTextStyling(100, 255);
		f.setStyling(150, 230, null, 160);
		//f.setButtonLabel("Draw Values 6", font, 100, 255);
		//f.setButtonLook(150, 230, null, 160);

		g = new PButton(width - 55, 315, "Draw values 7", this);
		g.setAdaptToLabel(false);
		g.setSize(95, 25);
		g.setFont(font);
		g.setTextStyling(100, 255);
		g.setStyling(150, 230, null, 160);
		//g.setButtonLabel("Draw Values 7", font, 100, 255);
		//g.setButtonLook(150, 230, null, 160);

	}

	public void draw(){

		background(255);

		image(mapImage, 0, 50, width - 110, height - 150);

		//menu area
		rectMode(CORNER);
		noStroke();
		fill(255);
		rect(width - 110, 0, 110, height);
		//title area
		rect(0, 0, width - 110, 50);

		//draw the menu buttons
		v.draw();
		a.draw();
		b.draw();
		c.draw();
		d.draw();
		e.draw();
		f.draw();
		g.draw();


		noStroke();
		closestDist = MAX_FLOAT;

		//Loop through the rows of the table (locations.tsv)
		for (int  row = 0; row < rowCount; row++) {
			String abbrev = dataTable.getString(row, 0); 
			TableRow xy = locationTable.findRow(abbrev, 0);
			float x = xy.getFloat(1) + offsetX;
			float y = xy.getFloat(2) + offsetY;

			//drawData according to which button is pressed
			if(v.isChecked()){
				drawData0(x, y, abbrev);
			}
			else if (a.isChecked()) {
				drawData1(x, y, abbrev);
			}
			else if (b.isChecked()) {
				drawData2(x, y, abbrev);
			}
			else if (c.isChecked()) {
				drawData3(x, y, abbrev);
			}
			else if (d.isChecked()) {
				drawData4(x, y, abbrev);
			}
			else if (e.isChecked()) {
				drawData5(x, y, abbrev);
			}
			else if (f.isChecked()) {
				drawData6(x, y, abbrev);
			}
			else if (g.isChecked()) {
				drawData7(x, y, abbrev);
			}
		}


		bc.drawChart();

		if(bc.isInside(mouseX, mouseY) != "noId"){
			hlbc = bc.isInside(mouseX, mouseY);
			TableRow result = locationTable.findRow(hlbc, 0); // as well for highlightning the bar chart
			stroke(255, 141, 0);
			strokeWeight(3);
			noFill();
			ellipse(result.getInt(1) + offsetX, result.getInt(2) + offsetY, hlRadius, hlRadius);
			bc.highlightData(hlbc);
		}

		//draw the labels after drawing the circles
		if (closestDist != MAX_FLOAT) {
			fill(0);
			textFont(font);
			textAlign(CENTER);
			text(closestText, closestTextX, closestTextY);

			//Mark the closest circle to the mouse with a orange border 
			TableRow result = locationTable.findRow(hlbc, 0); // as well for highlightning the bar chart
			stroke(255, 141, 0);
			strokeWeight(3);
			noFill();
			ellipse(result.getInt(1) + offsetX, result.getInt(2) + offsetY, hlRadius, hlRadius);
			bc.highlightData(hlbc);

		}

		//draw copyright information
		textAlign(LEFT);
		textFont(copyFont);
		fill(175);
		text("data: www.benfry.com", 5, height - 105);

		//draw title information
		textAlign(CENTER, CENTER);
		textFont(titleFont);
		fill(100);
		text("Drawing locations and values on a map", width/2, 25);

	}

	public void mouseClicked(){
		if(v.contains(mouseX, mouseY)){
			v.setChecked(true);
			a.setChecked(false);
			b.setChecked(false); 
			c.setChecked(false);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(false);
			g.setChecked(false);
		}
		else if (a.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(true);
			b.setChecked(false); 
			c.setChecked(false);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(false);
			g.setChecked(false);
		}
		else if (b.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(true);
			c.setChecked(false);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(false);
			g.setChecked(false);
		}
		else if (c.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(false);
			c.setChecked(true);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(false); 
			g.setChecked(false);
		}
		else if (d.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(false);
			c.setChecked(false);
			d.setChecked(true);
			e.setChecked(false);
			f.setChecked(false);
			g.setChecked(false);
		}
		else if (e.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(false);
			c.setChecked(false); 
			d.setChecked(false);
			e.setChecked(true);
			f.setChecked(false);
			g.setChecked(false);
		}
		else if (f.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(false);
			c.setChecked(false);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(true); 
			g.setChecked(false);
		}
		else if (g.contains(mouseX, mouseY)) {
			v.setChecked(false);
			a.setChecked(false);
			b.setChecked(false);
			c.setChecked(false);
			d.setChecked(false);
			e.setChecked(false);
			f.setChecked(false); 
			g.setChecked(true);
		}
	}

	/**
	 * collection of draw methods for the mapping example
	 * gets the x and y coordinate where to put the text lable 
	 * for the dot. 
	 */

	//map the size of the ellipse to the data value
	private void drawData0(float x, float y, String abbrev) {
		smooth();
		fill(192, 0, 0);
		noStroke();
		//get data value
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		ellipseMode(RADIUS);
		ellipse(x, y, (float)7.5, (float)7.5); 

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < 7.5 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = (float) (y - 7.5 - 4);
			hlbc = abbrev;
			hlRadius = (float) 7.5;
		}
	}


	//map the size of the ellipse to the data value
	private void drawData1(float x, float y, String abbrev) {
		smooth();
		fill(192, 0, 0);
		noStroke();
		//get data value
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		//remap the data to an number between 2 and 40
		float radius = 0;
		radius = map(value, dataMin, dataMax, 1, 20);
		//draw an circle for this item
		ellipseMode(RADIUS);
		ellipse(x, y, radius, radius); 

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < radius + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = y - radius - 4;
			hlbc = abbrev;
			hlRadius = radius;
		}
	}

	//interpolate between color values of the ellipse according to the data value
	private void drawData2(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		float percent = norm(value, dataMin, dataMax);
		int between = lerpColor(color(255,68,34), color(68,34,204), percent);
		fill(between);
		ellipseMode(RADIUS);
		ellipse(x, y, (float)7.5, (float)7.5);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < 7.5 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = (float) (y - 7.5 - 4);
			hlbc = abbrev;
			hlRadius = (float) 7.5;
		}
	}

	//interpolate between color values and the size of the ellipse to the data value
	private void drawData3(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);


		float percent = norm(value, dataMin, dataMax);
		int between = lerpColor(color(255,68,34), color(68,34,204), percent);
		float mapped = map(value, dataMin, dataMax, 2, 40);
		fill(between);
		ellipseMode(RADIUS);
		ellipse(x, y, mapped/2, mapped/2);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < mapped/2 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = y - mapped/2 - 4;
			hlbc = abbrev;
			hlRadius = mapped/2;
		}
	}

	//interpolate between color values of the ellipse according to the data value
	private void drawData4(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		float percent = norm(value, dataMin, dataMax);
		int between = lerpColor(color(41,111,52), color(97,226,240), percent); //green Türkis
		fill(between);
		ellipseMode(RADIUS);
		ellipse(x, y, (float)7.5, (float)7.5);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < 7.5 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = (float) (y - 7.5 - 4);
			hlbc = abbrev;
			hlRadius = (float) 7.5;
		}
	}

	//interpolate between color values in HSB color space of the ellipse according to the data value
	private void drawData5(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		float percent = norm(value, dataMin, dataMax);
		int between = lerpColor(color(41,111,52), color(97,226,240), percent, HSB); //green Türkis
		fill(between);
		ellipseMode(RADIUS);
		ellipse(x, y, (float)7.5, (float)7.5);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < 7.5 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = (float) (y - 7.5 - 4);
			hlbc = abbrev;
			hlRadius = (float) 7.5;
		}
	}

	//zwo sided color range (color and transparency) two sided
	private void drawData6(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		if (value >= 0) {
			float a = map(value, 0, dataMax, 0, 255);
			fill(color(51,51,102), a);
		}
		else {
			float a = map(value, 0, dataMin, 0, 255);
			fill(color(236,81,102), a);
		}
		ellipseMode(RADIUS);
		ellipse(x, y, (float)7.5, (float)7.5);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < 7.5 + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = (float) (y - 7.5 - 4);
			hlbc = abbrev;
			hlRadius = (float) 7.5;
		}
	}

	// draw statenames after drwaing the dots
	private void drawData7(float x, float y, String abbrev) {
		TableRow row = dataTable.findRow(abbrev, 0);
		float value = row.getFloat(1);
		TableRow row1 = nameTable.findRow(abbrev, 0);
		String name = row1.getString(1);

		float radius = 0;
		if (value >= 0) {
			radius = map(value, 0, dataMax, (float) 1.5, 15);
			fill(color(51,51,102));
		}
		else {
			radius = map(value, 0, dataMin, (float) 1.5, 15);
			fill(color(236,81,102));
		}
		ellipseMode(RADIUS);
		ellipse(x, y, radius, radius);

		float d = dist(x, y, mouseX, mouseY);
		//the following check is done each time a new 
		//circle is drawn, we end up with the circle 
		//closest to the mouse 
		if ((d < radius + 2) && (d < closestDist)) {
			closestDist = d;
			closestText = name + " (" + value + ")";
			closestTextX = x;
			closestTextY = y - radius - 4;
			hlbc = abbrev;
			hlRadius = radius;
		}
	}
}
