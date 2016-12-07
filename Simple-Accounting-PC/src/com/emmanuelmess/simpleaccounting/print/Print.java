package com.emmanuelmess.simpleaccounting.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import com.emmanuelmess.simpleaccounting.Main;

public class Print implements Printable {

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
		
		printHeader(g, (int)pf.getImageableWidth());
		
		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;
	}
	
	
	/*
	 * Everything above the actual data
	 */
	private void printHeader(Graphics g, int pageEnd) {
		// Now we perform our rendering
		g.drawString(Main.columnNames[0], 50, 100);
		g.drawString(Main.columnNames[1], 100, 100);
		for(int i = 2; i < Main.columnNames.length; i++)
			g.drawString(Main.columnNames[i], 100+100*i, 100);
	}
	
}