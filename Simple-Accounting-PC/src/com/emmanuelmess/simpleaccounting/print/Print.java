package com.emmanuelmess.simpleaccounting.print;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import static com.emmanuelmess.simpleaccounting.Main.columnNames;

public class Print implements Printable {

	private final int MARGIN = 50;
	
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		// We have only one page, and 'page'
		// is zero-based
		if (page > 0) {
		     return NO_SUCH_PAGE;
		}
		
		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		printHeader(g2d, (int)(pf.getImageableX() + pf.getImageableWidth()));
		
		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;
	}
	
	
	/*
	 * Everything above the actual data
	 */
	private void printHeader(Graphics g, int pageEnd) {
		g.drawString(columnNames[0], MARGIN, 100);
		g.drawString(columnNames[1], 100, 100);
		
		int lengthLast = measureText(columnNames[4], g)-MARGIN;
		g.drawString(columnNames[2], pageEnd-300-lengthLast, 100);
		g.drawString(columnNames[3], pageEnd-200-lengthLast, 100);
		g.drawString(columnNames[4], pageEnd-100-lengthLast, 100);
	}
	
	private int measureText(String s, Graphics g) {
		// get metrics from the graphics
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		// get the advance of my text in this font
		// and render context
		return metrics.stringWidth(s);
	}
	
}