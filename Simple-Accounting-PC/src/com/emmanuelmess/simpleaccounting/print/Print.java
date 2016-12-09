package com.emmanuelmess.simpleaccounting.print;

import static com.emmanuelmess.simpleaccounting.Main.columnNames;
import static com.emmanuelmess.simpleaccounting.Utils.convert;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import com.emmanuelmess.simpleaccounting.databases.ProcessData;
import com.emmanuelmess.simpleaccounting.databases.TableGeneral;

public class Print implements Printable {

	private final int TOP_MARGIN = 100, MARGIN = 50, SPACE_BETWEEN_LINES = 30;
	private TableGeneral dbGeneral;
	Object[][] month;
	private int k = 0;
	
	public Print(TableGeneral db) {
		dbGeneral = db;
		
		month = ProcessData.digest(db.getMonth(-1,  -1));
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		double pageHeight = pf.getImageableHeight();
		int linesPerPage = (int) ((pageHeight-TOP_MARGIN*2)/SPACE_BETWEEN_LINES);
		
		// We have only one page, and 'page'
		// is zero-based
		if (month.length/linesPerPage < page) 
		     return NO_SUCH_PAGE;
		
		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		int[] spacings = printHeader(g2d, (int)(pf.getImageableX() + pf.getImageableWidth()));

		for(int line = 0; line < linesPerPage && k < month.length; line++, k++) {
			System.out.print("Printing ");
			for(int i = 0; i < columnNames.length; i++){
				g.drawString(parseAsString(month[k] [i]), spacings [i], TOP_MARGIN + SPACE_BETWEEN_LINES*(line+1));
				System.out.print(" '" + parseAsString(month[k] [i]) + "' at (" + spacings [i] + ", " + (TOP_MARGIN + SPACE_BETWEEN_LINES*(line+1)) + ") &");
			}
			System.out.println("\n");
		}

		return PAGE_EXISTS;
	}
	
	/*
	 * Everything above the actual data
	 */
	private int[] printHeader(Graphics g, int pageEnd) {
		g.drawString(columnNames[0], MARGIN, TOP_MARGIN);
		g.drawString(columnNames[1], 100, TOP_MARGIN);
		
		int lengthLast = measureText(columnNames[4], g)-MARGIN;
		g.drawString(columnNames[2], pageEnd-300-lengthLast, TOP_MARGIN);
		g.drawString(columnNames[3], pageEnd-200-lengthLast, TOP_MARGIN);
		g.drawString(columnNames[4], pageEnd-100-lengthLast, TOP_MARGIN);
		
		return new int [] {MARGIN, 100, pageEnd-300-lengthLast, pageEnd-200-lengthLast, pageEnd-100-lengthLast};
	}
	
	private int measureText(String s, Graphics g) {
		// get metrics from the graphics
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		// get the advance of my text in this font
		// and render context
		return metrics.stringWidth(s);
	}
	
	private String parseAsString(Object o) {
		if(o == null)
			return "";
		else
			return o.toString();
	}

}